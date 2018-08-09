package com.vinicius.myhelloworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.adapter.RespostaAdapter;
import com.vinicius.myhelloworld.model.Aula;
import com.vinicius.myhelloworld.model.Exercicio;
import com.vinicius.myhelloworld.model.Resposta;

import java.util.ArrayList;

public class AulaActivity extends AppCompatActivity {

    private TextView questao;
    private TextView questaoId;
    private Aula aula;
    private ArrayAdapter adapter;
    private ListView listView;
    private ArrayList<Resposta> respostas;
    private int numeroExercicio;
    private int tamanhoListaExercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula);

        questao = findViewById(R.id.textView_questao);
        questaoId = findViewById(R.id.textView_questao_id);
        listView = findViewById(R.id.lv_repostas);

        //Recuperar dados da intent
        Intent intent = getIntent();
        if(intent != null) aula = (Aula) intent.getSerializableExtra("aula");


        tamanhoListaExercicio = aula.getExercicios().size();
        numeroExercicio = 0;
        editarView(aula.getExercicios().get(numeroExercicio));

        //Click da lista de respostas
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Lista de resposta
                Resposta resposta = respostas.get(position);

                numeroExercicio++;
                if(resposta.isCerto() && numeroExercicio < tamanhoListaExercicio){
                    editarView(aula.getExercicios().get(numeroExercicio));
                }else{
                    Toast.makeText(AulaActivity.this, "Resposta errada. Tente novamente", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //Mostra a questão e respostas
    private void editarView(Exercicio exercicio){

        respostas = exercicio.getRespostas();

        //Mostrar Questão
        questaoId.setText(Integer.toString(exercicio.getId() + 1));
        questao.setText(exercicio.getQuestao());

        //Montar listView Adapter
        adapter = new RespostaAdapter(this, respostas);
        listView.setAdapter(adapter);
    }
}
