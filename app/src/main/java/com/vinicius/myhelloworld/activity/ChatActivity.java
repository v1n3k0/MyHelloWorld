package com.vinicius.myhelloworld.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.adapter.MensagemAdapter;
import com.vinicius.myhelloworld.config.ConfiguracaoFireBase;
import com.vinicius.myhelloworld.helper.Preferencias;
import com.vinicius.myhelloworld.model.Aula;
import com.vinicius.myhelloworld.model.Chat;
import com.vinicius.myhelloworld.model.Mensagem;
import com.vinicius.myhelloworld.model.Usuario;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Chat chat;
    private EditText editMensagem;
    private ImageButton btMensagem;
    private DatabaseReference firebase;
    private ListView listView;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;
    private ValueEventListener valueEventListener;
    private String identificadorUsuario;
    private String nomeUsuario;

    @Override
    protected void onStop() {
        super.onStop();

        firebase.removeEventListener(valueEventListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = findViewById(R.id.tb_chat);
        editMensagem = findViewById(R.id.edit_mensagem);
        btMensagem = findViewById(R.id.bt_enviar);
        listView = findViewById(R.id.lv_conversas);

        //dados do usuario logado
        Preferencias preferencias = new Preferencias(this);
        identificadorUsuario = preferencias.getIdentificador();
        nomeUsuario = preferencias.getNome();

        //Recuperar dados da intent
        Intent intent = getIntent();
        if(intent != null) chat = (Chat) intent.getSerializableExtra("chat");

        //Configurar toolbar
        toolbar.setTitle(chat.getNome());
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        //Monta listView e adapter
        mensagens = new ArrayList<>();
        adapter = new MensagemAdapter(ChatActivity.this, mensagens);
        listView.setAdapter(adapter);

        //Recuperar mensagens do firebase
        firebase = ConfiguracaoFireBase.getFirebase()
                .child("Chat")
                .child(String.valueOf(chat.getId()))
                .child("mensagens");

        //Criar listener para mensagens
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mensagens.clear();

                //recuperara mensagem
                for( DataSnapshot dados: dataSnapshot.getChildren()){
                    Mensagem mensagem = dados.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        firebase.addValueEventListener(valueEventListener);

        //Enviar mensagem
        btMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoMensagem = editMensagem.getText().toString();

                if(textoMensagem.isEmpty() ){
                    Toast.makeText(ChatActivity.this, "Digite uma mensagem para enviar!", Toast.LENGTH_LONG).show();
                }else {

                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(identificadorUsuario);
                    mensagem.setNomeUsuario(nomeUsuario);
                    mensagem.setMensagem(textoMensagem);

                    //Salvar mensagem
                    Boolean retornoMensagemRemetente = salvaMensagem(mensagem);

                    if(!retornoMensagemRemetente){
                        Toast.makeText(ChatActivity.this, "Problema ao salvar mensagem, tente novamente!", Toast.LENGTH_LONG).show();
                    }

                    editMensagem.setText("");
                }
            }
        });
    }

    private boolean salvaMensagem(Mensagem mensagem){
        try{

            firebase.push()
                    .setValue(mensagem);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
