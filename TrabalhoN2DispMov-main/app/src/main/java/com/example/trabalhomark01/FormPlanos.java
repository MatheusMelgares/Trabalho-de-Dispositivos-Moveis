package com.example.trabalhomark01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormPlanos extends AppCompatActivity {

    private Button bt_salvar_dados_cartao;
    private RadioButton plano_bronze,plano_prata,plano_ouro;
    private RadioGroup radiogroup;
    private SharedPreferences sharedPreferences;
    private EditText num_cartao,num_val_cartao,num_seg_cartao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_planos);

        getSupportActionBar().hide();

        bt_salvar_dados_cartao = findViewById(R.id.bt_salvar_dados_cartao);

        plano_bronze = findViewById(R.id.plano_bronze);
        plano_prata = findViewById(R.id.plano_prata);
        plano_ouro = findViewById(R.id.plano_ouro);
        radiogroup = findViewById(R.id.radiogroup);

        //Recupera os ultimos valores selecionados quando o usuário fecha o aplicativo.
        plano_bronze.setChecked(Update("plano_bronze"));
        plano_prata.setChecked(Update("plano_prata"));
        plano_ouro.setChecked(Update("plano_ouro"));


        //Evento que checa o botão selecionado informando na variável boolean
        plano_bronze.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean one_isChecked) {
                //Salva o valor em SavePreferences quando selecionado
                SavePreferences("plano_bronze",one_isChecked);
            }
        });

        plano_prata.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean two_isChecked) {
                //Salva o valor em SavePreferences quando selecionado
                SavePreferences("plano_prata",two_isChecked);
            }
        });

        plano_ouro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean three_isChecked) {
                //Salva o valor em SavePreferences quando selecionado
                SavePreferences("plano_ouro",three_isChecked);
            }
        });

        // -----------------------------------------------------------------------------------------

        //Evento ao clicar no botão Salvar
        bt_salvar_dados_cartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FormPlanos.this, "Dados salvos com sucesso", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Método que salva os valor do nome e Boolean do RadioButton selecionado
    private void SavePreferences(String key, boolean value){

        //Salva os valores no SharedPreferences, Nomeando o parametro String
        SharedPreferences sp = getSharedPreferences("PLANOS",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        //Utiliza o método Editor para salvar os valores nos parämetros
        editor.putBoolean(key, value);
        editor.apply();
    }

    //Método para recueprar os valores
    private boolean Update(String key){
        //
        SharedPreferences sp = getSharedPreferences("PLANOS",MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    // ---------------------------------------------------------------------------------------------


}