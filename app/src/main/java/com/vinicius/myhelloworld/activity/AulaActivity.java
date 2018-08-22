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
    private TextView numeroPergunta;
    private Aula aula;
    private ArrayAdapter adapter;
    private ListView listView;
    private ArrayList<Resposta> respostas;
    private int numeroExercicio;
    private int tamanhoListaExercicio;
    private int peso;
    private int ponto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula);

        questao = findViewById(R.id.textView_questao);
        questaoId = findViewById(R.id.textView_questao_id);
        numeroPergunta = findViewById(R.id.textView_aula_numeroPergunta);
        listView = findViewById(R.id.lv_repostas);

        //Recuperar dados da intent
        Intent intent = getIntent();
        if(intent != null) aula = (Aula) intent.getSerializableExtra("aula");

        //Definição inicial
        tamanhoListaExercicio = aula.getExercicios().size();
        numeroExercicio = 0;
        peso = aula.getPeso();
        //Iniciar primeiro exercicio
        editarView(aula.getExercicios().get(numeroExercicio));

        //Click da lista de respostas
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                verificarResposta(position);
            }
        });
    }

    //Mostra a questão e respostas
    private void editarView(Exercicio exercicio){

        //Peso inicial do exercicio
        ponto = peso;
        //Lista de resposta
        respostas = exercicio.getRespostas();

        //Mostrar Questão
        questaoId.setText(Integer.toString(exercicio.getId() + 1));
        questao.setText(exercicio.getQuestao());

        //Montar listView Adapter
        adapter = new RespostaAdapter(this, respostas);
        listView.setAdapter(adapter);

        //Numero de perguntas
        numeroPergunta.setText((exercicio.getId() + 1) + " / " + tamanhoListaExercicio );
    }

    //Verificar reposta selecionada
    private void verificarResposta(int position){
        //Lista de resposta
        Resposta resposta = respostas.get(position);

        if(resposta.isCerto()){
            //proximo exercicio
            numeroExercicio++;
            //Soma pontuação
            aula.setPontos(aula.getPontos() + ponto);
            Toast.makeText(AulaActivity.this, "Resposta Correta", Toast.LENGTH_SHORT).show();

            if(numeroExercicio < tamanhoListaExercicio){
                editarView(aula.getExercicios().get(numeroExercicio));
            }else{
                //Toast.makeText(AulaActivity.this, "Fim da Aula " + aula.getPontos(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AulaFimActivity.class);

                //Enviando dados para conversa activity
                intent.putExtra("aula", aula);

                startActivity(intent);
            }
        }else{
            //Penaliza a pontuação
            ponto = ponto / 2;
            Toast.makeText(AulaActivity.this, "Resposta errada. Tente novamente", Toast.LENGTH_SHORT).show();
        }
    }
}
