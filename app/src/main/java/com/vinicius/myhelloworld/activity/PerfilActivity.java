package com.vinicius.myhelloworld.activity;

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
import com.vinicius.myhelloworld.helper.Preferencias;
import com.vinicius.myhelloworld.model.Usuario;

public class PerfilActivity extends AppCompatActivity {

    private TextView nome;
    private TextView email;
    private TextView ultimaAula;
    private TextView level;
    private TextView experiencia;
    private Toolbar toolbar;
    private Usuario usuario;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nome = findViewById(R.id.textView_perfil_nome);
        email = findViewById(R.id.textView_perfil_email);
        ultimaAula = findViewById(R.id.textView_perfil_ultimaAula);
        level = findViewById(R.id.textView_perfil_level);
        experiencia = findViewById(R.id.textView_perfil_experiencia);
        toolbar = findViewById(R.id.tb_perfil);

        //Configurar Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        //Recuperar contatos do firebase
        Preferencias preferencias = new Preferencias(PerfilActivity.this);
        String identificadorUsuarioLogado = preferencias.getIdentificador();
        firebase = ConfiguracaoFireBase.getFirebase()
                .child("Usuario")
                .child(identificadorUsuarioLogado);

        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    usuario = dataSnapshot.getValue(Usuario.class);

                    nome.setText(usuario.getNome());
                    email.setText(usuario.getEmail());
                    ultimaAula.setText(usuario.getUltimaAula());
                    level.setText(Integer.toString(usuario.getLevel()));
                    experiencia.setText(Integer.toString(usuario.getExperiencia()));
                    toolbar.setTitle(usuario.getNome());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
