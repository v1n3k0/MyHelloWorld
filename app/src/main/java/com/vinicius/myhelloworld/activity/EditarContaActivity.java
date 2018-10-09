package com.vinicius.myhelloworld.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.config.ConfiguracaoFireBase;
import com.vinicius.myhelloworld.helper.Preferencias;
import com.vinicius.myhelloworld.model.Usuario;

public class EditarContaActivity extends AppCompatActivity {

    private EditText nome;
    private Button salvar;
    private Usuario usuario;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_conta);

        nome = findViewById(R.id.edit_editar_conta_nome);
        salvar = findViewById(R.id.button_editar_conta_salvar);

        //Recuperar contatos do firebase
        Preferencias preferencias = new Preferencias(EditarContaActivity.this);
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
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferencias preferencias = new Preferencias(EditarContaActivity.this);
                String identificadorUsuarioLogado = preferencias.getIdentificador();

                //Salvar nas preferencias
                preferencias.setNome(nome.getText().toString());

                //Salvar no Firebase
                usuario.setId(identificadorUsuarioLogado);
                usuario.setNome(nome.getText().toString());
                usuario.salvar();

                abreMain();
            }
        });

    }

    //Abre Activity Login
    public void abreMain(){
        Intent intent = new Intent(EditarContaActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
