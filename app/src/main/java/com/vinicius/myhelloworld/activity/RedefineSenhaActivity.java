package com.vinicius.myhelloworld.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.config.ConfiguracaoFireBase;

public class RedefineSenhaActivity extends AppCompatActivity {

    private EditText email;
    private Button redefinir;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefine_senha);

        email = findViewById(R.id.edit_redefine_email);
        redefinir = findViewById(R.id.button_redefine_redefinir);

        redefinir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Recupera instacia da autenticação
                auth = ConfiguracaoFireBase.getFirebaseAutenticacao();

                //Envia um e-mail de redefinição de senha
                auth.sendPasswordResetEmail(email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RedefineSenhaActivity.this,"E-mail enviado.", Toast.LENGTH_LONG).show();
                                    abreLogin();
                                }else {
                                    Toast.makeText(RedefineSenhaActivity.this,"Erro ao enviar um E-mail.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });
    }

    //Abre a view de Login
    public void abreLogin(){

        Intent intent = new Intent(RedefineSenhaActivity.this, LoginActivity.class);
        startActivity(intent);

    }
}
