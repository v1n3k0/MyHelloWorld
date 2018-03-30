package com.vinicius.myhelloworld.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.vinicius.myhelloworld.Config.ConfiguracaoFireBase;
import com.vinicius.myhelloworld.R;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference referenceFireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        referenceFireBase = ConfiguracaoFireBase.getFirebase();
        referenceFireBase.child("ponto").setValue("300");
    }

    public void abrirCadastroUsuario(View v){
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }
}
