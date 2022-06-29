package com.example.trabalhomark01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TelaPrincipal extends AppCompatActivity {

    private TextView nomeUsuario,emailUsuario,cpfusUario,telefoneUsuario;
    private Button bt_deslogar;
    private Button bt_to_planos;
    private Button bt_to_treinos;
    //Inicializa a instancia do Firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        getSupportActionBar().hide();
        IniciarComponentes();


        // metodo para deslogar e voltar para tela de login
        bt_deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Evento do Firebase para deslogar o usu;ario atual
                FirebaseAuth.getInstance().signOut();
                //Intenção onde volta a tela de login ao apertar no botão deslogar
                Intent intent = new Intent(TelaPrincipal.this,FromLogin.class);
                //inicia a variável intenção
                startActivity(intent);
                //Finaliza o contexto atual
                finish();
            }
        });

        //Botão que direciona para a tela de planos
        bt_to_planos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intenção que a ativa o evento quando clciar no botão planos na tela principal, ir para a tela Planos.
                Intent intent = new Intent(TelaPrincipal.this,FormPlanos.class);
                startActivity(intent);
            }
        });

        //Botão que direciona para a tela de treinos
        bt_to_treinos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPrincipal.this,treinos.class);
               startActivity(intent);
           }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        //Pega o ID e  usuário atual
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Documentação onde recebe o Firestore e utiliza o Collection para salvar na coleção chamada "Usuários" no database
        //Onde recupera os dados e insere nos layouts da Activity
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID); //Passa o usuário e ID e recupera os dados abaixo
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                //Se essa documentação esta diferente denulo, quer dizer que tem dados para recuperar
                if(documentSnapshot != null){
                    nomeUsuario.setText(documentSnapshot.getString("nome"));
                    cpfusUario.setText(documentSnapshot.getString("cpf"));
                    telefoneUsuario.setText(documentSnapshot.getString("telefone"));
                    emailUsuario.setText(email);
                }
            }
        });
    }

    //Método que recupera os Ids na Activity Tela_principal..
    private void IniciarComponentes(){
        nomeUsuario = findViewById(R.id.text_nome_user);
        emailUsuario = findViewById(R.id.text_email_user);
        cpfusUario = findViewById(R.id.text_cpf);
        telefoneUsuario = findViewById(R.id.text_telefone);
        bt_deslogar = findViewById(R.id.bt_deslogar);
        bt_to_planos = findViewById(R.id.bt_to_planos);
        bt_to_treinos = findViewById(R.id.bt_to_treinos);
    }
}