package com.vinicius.myhelloworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.model.Aula;
import com.vinicius.myhelloworld.model.Exercicio;

import java.util.ArrayList;

public class AulaActivity extends AppCompatActivity {

    private TextView texto;
    private Aula aula;
    private ArrayList<Exercicio> exercicios;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula);

        texto = findViewById(R.id.textView_aula);

        //Recuperar dados da intent
        Intent intent = getIntent();
        if(intent != null) aula = (Aula) intent.getSerializableExtra("aula");

        texto.setText(aula.getExercicios().get(1).getQuestao());
    }
}
