package com.vinicius.myhelloworld.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.config.ConfiguracaoFireBase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Hello World");

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_configuracoes:
                return true;
            case R.id.item_sair:
                deslogaUsuario();
                return true;
            case R.id.item_pesquisa:
                return true;
             default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void deslogaUsuario(){

        autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
        autenticacao.signOut();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

        finish();
    }

}
