package com.example.trabalhomark01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class formCadastro extends AppCompatActivity{

    //Objeto para o cadastro do Nome,Email, Senha, telefone, CPF.
    private EditText nome_cadastro;
    private EditText email_cadastro;
    private EditText senha_cadastro;
    private EditText telefone_cadastro;
    private EditText cpf_cadastro;

    //Objeto para o botão cadastrar.
    private Button bt_cadastrar;
    //String para as mensagens de erro e sucesso.
    String[] mensagens = {"Preencha todos os campos", "Cadastro realizado com sucesso"};
    //String para obter o usuário que esta sendo cadastrado.
    String usuarioID;
    //Declarando Forma de pagamento




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);
        getSupportActionBar().hide();
        IniciarComponentes();

        //Evento quando o usuário clicar no botão cadastrar
        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //String que captura o que foi digitado no campo dos IDs convertendo para String.
                String nome = nome_cadastro.getText().toString();
                String email = email_cadastro.getText().toString();
                String senha = senha_cadastro.getText().toString();
                String telefone = telefone_cadastro.getText().toString();
                String cpf = cpf_cadastro.getText().toString();




                //Se esses campos estiverem vazios
                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty() || telefone.isEmpty()) {
                    //Snackbar que utiliza da view do evento onClick, utilizado um array de strings, onde utiliza a string na posição 0 como mensagem de erro.
                    //Lenght short que faz com que a mensagem apareça e suma após um certo
                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                    //Seta o fundo da mensagem com a cor Branca.
                    snackbar.setBackgroundTint(Color.WHITE);
                    //Seta a cor do texto com a cor preta.
                    snackbar.setTextColor(Color.BLACK);
                    //Variável para Executar a snackbar.
                    snackbar.show();
                } else {
                    //Executa o Método caso o usuária tenha preenchido todos os campos, vai cadastrar o usuário no Firebase.
                    CadastrarUsuario(v);
                }
            }
        });
    }



    private void CadastrarUsuario(View v) {

        //Capturam os campos digitados e converte para String.
        String email = email_cadastro.getText().toString();
        String senha = senha_cadastro.getText().toString();
        String telefone = telefone_cadastro.getText().toString();
        String cpf = cpf_cadastro.getText().toString();



        //Firebase Auth para autenticar o usuário, capturando a instancia do firebase e utlizando o metodo "criar usuário com email e senha"
        //Passando as variáveis ja criadas.
        //Instanciando Complete Listener
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //Se o cadastro foi sucedido, executará o método "SalvarDadosUsuário" mostrando a posição dois no array de strings.
                if (task.isSuccessful()) {

                    SalvarDadosUsuário();

                    Snackbar snackbar = Snackbar.make(v, mensagens[1], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {
                    //Criado exceções caso cadastro não cumprir alguns requisitos
                    String erro;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        //Exceção para caso a senha tenha menos que 6 caracteres, pois o Firebase só aceita senhas a partir de 6 caracteres.
                        erro = "Digite uma senha com no mínimo 6 caracteres";
                    } catch (FirebaseAuthUserCollisionException e) {
                        //Exceção para caso o email ja tenha seido cadastrado no Firebase.
                        erro = "Essa conta já foi cadastrada";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        //Exceção caso o campoemail não seja cadastrado corretamente.
                        erro = "Email inválido";
                    } catch (Exception e) {
                        //Exceção caso algum outro erro ocorra.
                        erro = "Erro ao cadastrar usuário";
                    }
                    //Snackbars para mostrar os erros acima.
                    Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });

    }

    //Método para salvar o usuário no Firebase
    private void SalvarDadosUsuário() {
        String nome = nome_cadastro.getText().toString();
        String telefone = telefone_cadastro.getText().toString();
        String cpf = cpf_cadastro.getText().toString();

        //Inicia o banco de dados do Firebase no código.
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Mapde usuário para inserir o objeto "nome"no banco de dados.
        Map<String, Object> usuarios = new HashMap<>();
        //Chave "nome"onde o PUT insere o objeto no banco de dados.
        usuarios.put("nome", nome);
        usuarios.put("telefone", telefone);
        usuarios.put("cpf", cpf);


        //Obtem o usuário atual e o ID para inserir no banco.
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Documenta cada usuário cadastrado no banco de dados.
        //Fazendo um Registro dos usuários cadastrados no app, no firebase.
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Se o usuário foi cadastrado com sucesso, exibe a mensagem.
                        Log.d("db", "Sucesso ao Salvar os dados");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Se o usuário não foi cadastrado com sucesso, exibe a mensagem.
                        Log.d("db_error", "Erro ao Salvar os dados" + e.toString());
                    }
                });
    }

    //Método para conectar os objetos acima nos Ids na Activity Cadastro.
    @SuppressLint("WrongViewCast")
    private void IniciarComponentes() {

        nome_cadastro = findViewById(R.id.nome_cadastro);
        email_cadastro = findViewById(R.id.email_cadastro);
        senha_cadastro = findViewById(R.id.senha_cadastro);
        bt_cadastrar = findViewById(R.id.bt_cadastrar);
        telefone_cadastro = findViewById(R.id.telefone_cadastro);
        cpf_cadastro = findViewById(R.id.cpf_cadastro);
    }


}



