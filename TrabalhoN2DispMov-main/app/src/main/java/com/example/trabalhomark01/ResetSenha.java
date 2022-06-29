package com.example.trabalhomark01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetSenha extends AppCompatActivity {

    //Objetos
    private EditText campo_email;
    private Button bt_reset_senha;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_senha);
        //Retira barra azul feia do android
        getSupportActionBar().hide();

        //Instanciado os ID com os objetos
        campo_email = (EditText) findViewById(R.id.campo_email);
        bt_reset_senha = (Button) findViewById(R.id.bt_reset_senha);


        //Instancia o FirebaseAuth
        auth = FirebaseAuth.getInstance();

        //Evento de click no botão
        bt_reset_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //executa método recuperSenha
                recuperarSenha();
            }
        });
    }

    //Método recuperarSenha
    private void recuperarSenha(){
        //Transforma o objeto campo_email em string
        String email = campo_email.getText().toString();

        //Caso o campo email esteja vazio
        if(email.isEmpty()){
            campo_email.setError("Por favor Prencha o Campo de Email!");
            campo_email.requestFocus();
            return;
        }else{
            //evento que faz executar o AUTH FIREBASE e envia o email para o usuário.
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(ResetSenha.this, "Verifique seu email para restaurar a sua senha", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ResetSenha.this, "Erro! Tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        //Caso o email não esteja cadastrado no FIREBASE
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            campo_email.setError("Email não cadastrado no sistema");
            campo_email.requestFocus();
            return;
        }


    }

}