package com.vinicius.myhelloworld.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.model.Contato;

public class ContatoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        toolbar = findViewById(R.id.tb_contato);

        //Recuperar dados da intent
        Intent intent = getIntent();
        if(intent != null) contato = (Contato) intent.getSerializableExtra("contato");

        //Configurar Toolbar
        toolbar.setTitle(contato.getNome());
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);
    }
}
