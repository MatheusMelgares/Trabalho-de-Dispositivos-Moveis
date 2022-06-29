package com.example.trabalhomark01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FromLogin extends AppCompatActivity {

    private TextView text_tela_cadastro;
    private EditText edit_email,edit_senha;
    private TextView text_recuperar_senha;
    private Button bt_entrar;
    private ProgressBar progressBar;

    //Array de uma posição que mostra a mensagem abaixo
    String[] mensagens = {"Email ou Senha não Preenchido"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_login);

        //Esconde a action bar roxa do android.
        getSupportActionBar().hide();
        //Eexecuta o método que busca os IDS do Layout e insere nos objetos
        IniciarComponentes();

        //Evento ao clicar no botão Cadastrar, leva para tela de recuperação de senha
        text_tela_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FromLogin.this,formCadastro.class);
                startActivity(intent);
            }
        });

        //Evento ao clicar no botão recuperar senha, leva a tela de recuperação de senha
        text_recuperar_senha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(FromLogin.this,ResetSenha.class);
                    startActivity(intent);
                }
        });

        bt_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recebe o que foi inserido nos IDs e converte para String.
                String email = edit_email.getText().toString();
                String senha = edit_senha.getText().toString();

                //Se email ou senha estão vazios, executa a msenagem da String na primeira posição.
                if(email.isEmpty() || senha.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v,mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    //Se os campos foram preenchidos, executa o método autenticar usuário.
                    AutenticarUsuario(v);
                }
            }
        });
    }

    private void AutenticarUsuario(View view){
        //Recebe o que foi inserido nos IDs e converte para String.
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //Evento que executa a progressbar. Tornando-a visível.
                    progressBar.setVisibility(View.VISIBLE);

                    //Classe para animação da progressbar
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            TelaPrincipal();
                        } //Após a animação da Progress Bar executa o método
                    },2200); //Tempo de animação da Progressbar passar para a terceira tela. (2.2 segundos).
                }else{
                    //String de erro para as exceções.
                    String erro;

                    try{
                        //Try e Catch para as exceções.
                        throw task.getException();
                    }catch (Exception e){
                        erro = "Erro ao logar usuário, Email ou Senha incorretos";
                    }
                    //Snackbar para executar a mensagem acima.
                    Snackbar snackbar = Snackbar.make(view,erro,Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    @Override
    //Ciclo de vida, onde Sempre que o usuário estiver logado, ele vai iniciar o aplicativo nesse usuário
    protected void onStart() {
        super.onStart();

        //Validação onde recupera o usuário atual atentificado no Firebase
        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
        //Condicional onde se o usuário for diferente de Nulo, acessa a tela Principal
        if(usuarioAtual != null){
            TelaPrincipal();
        }
    }


    private void TelaPrincipal(){
        //Intenção para navegar da tela de login para a tela principal
        Intent intent = new Intent(FromLogin.this,TelaPrincipal.class);
        startActivity(intent);
        finish();
    }

    //Método para conectar os objetos acima nos Ids na Activity Login.
    private void IniciarComponentes(){
        text_tela_cadastro = findViewById(R.id.text_tela_cadastro);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        bt_entrar = findViewById(R.id.bt_entrar);
        progressBar = findViewById(R.id.progressbar);
        text_recuperar_senha = findViewById(R.id.text_recuperar_senha);
    }

}