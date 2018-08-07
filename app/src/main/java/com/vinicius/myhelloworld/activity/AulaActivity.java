package com.vinicius.myhelloworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.adapter.RespostaAdapter;
import com.vinicius.myhelloworld.model.Aula;
import com.vinicius.myhelloworld.model.Exercicio;
import com.vinicius.myhelloworld.model.Resposta;

import java.util.ArrayList;

public class AulaActivity extends AppCompatActivity {

    private TextView texto;
    private Aula aula;
    private ArrayAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula);

        texto = findViewById(R.id.textView_questao);

        //Recuperar dados da intent
        Intent intent = getIntent();
        if(intent != null) aula = (Aula) intent.getSerializableExtra("aula");

        editarView(aula.getExercicios().get(1));
    }

    private void editarView(Exercicio exercicio){

        texto.setText(exercicio.getQuestao());

        ArrayList<Resposta> respostas = exercicio.getRespostas();

        //Montar listView Adapter
        listView = findViewById(R.id.lv_repostas);
        adapter = new RespostaAdapter(this, respostas);
        listView.setAdapter(adapter);

    }
}
