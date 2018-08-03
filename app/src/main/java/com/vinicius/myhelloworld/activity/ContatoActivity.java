package com.vinicius.myhelloworld.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.config.ConfiguracaoFireBase;
import com.vinicius.myhelloworld.model.Contato;
import com.vinicius.myhelloworld.model.Usuario;


public class ContatoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Contato contato;
    private Usuario usuarioContato;
    private TextView nome;
    private TextView email;
    private TextView level;
    private TextView ultimaAula;
    private TextView experiencia;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        toolbar = findViewById(R.id.tb_contato);
        nome = findViewById(R.id.textView_contato_nome);
        email = findViewById(R.id.textView_contato_email);
        level = findViewById(R.id.textView_contato_level);
        ultimaAula = findViewById(R.id.textView_contato_ultima_aula);
        experiencia = findViewById(R.id.textView_contato_experiencia);

        //Recuperar dados da intent
        Intent intent = getIntent();
        if(intent != null) contato = (Contato) intent.getSerializableExtra("contato");

        //Configurar Toolbar
        toolbar.setTitle(contato.getNome());
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);


        //Recuperar contatos do firebase
        firebase = ConfiguracaoFireBase.getFirebase()
                .child("Usuario")
                .child(contato.getIdentificadorUsuario());

        firebase.addListenerForSingleValueEvent( new ValueEventListener(){

            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    usuarioContato = dataSnapshot.getValue(Usuario.class);

                    nome.setText(usuarioContato.getNome());
                    email.setText(usuarioContato.getEmail());
                    ultimaAula.setText(usuarioContato.getUltimaAula());
                    level.setText(Integer.toString(usuarioContato.getLevel()));
                    experiencia.setText(Integer.toString(usuarioContato.getExperiencia()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
