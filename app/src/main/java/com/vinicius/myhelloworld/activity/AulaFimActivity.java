package com.vinicius.myhelloworld.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.config.ConfiguracaoFireBase;
import com.vinicius.myhelloworld.helper.Preferencias;
import com.vinicius.myhelloworld.model.Aula;
import com.vinicius.myhelloworld.model.Usuario;

public class AulaFimActivity extends AppCompatActivity {

    private TextView titulo;
    private TextView pontos;
    private TextView level;
    private TextView experiencia;
    private Aula aula;
    private Usuario usuario;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula_fim);

        titulo = findViewById(R.id.textView_aulaFim_aula);
        pontos = findViewById(R.id.textView_aulaFim_pontos);
        level = findViewById(R.id.textView_aulaFim_level);
        experiencia = findViewById(R.id.textView_aulaFim_experiencia);

        //Recuperar dados da intent
        Intent intent = getIntent();
        if(intent != null) aula = (Aula) intent.getSerializableExtra("aula");

        //Recuperar contatos do firebase
        Preferencias preferencias = new Preferencias(this);
        String identificadorUsuarioLogado = preferencias.getIdentificador();
        firebase = ConfiguracaoFireBase.getFirebase()
                .child("Usuario")
                .child(identificadorUsuarioLogado);

        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    usuario = dataSnapshot.getValue(Usuario.class);

                    usuario.setExperiencia(usuario.getExperiencia() + aula.getPontos());

                    titulo.setText(aula.getNome());
                    pontos.setText(Integer.toString(aula.getPontos()));
                    level.setText(Integer.toString(usuario.getLevel()));
                    experiencia.setText(Integer.toString(usuario.getExperiencia()) + " / 100");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
