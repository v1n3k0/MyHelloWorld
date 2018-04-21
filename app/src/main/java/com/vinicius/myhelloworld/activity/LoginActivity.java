package com.vinicius.myhelloworld.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.config.ConfiguracaoFireBase;
import com.vinicius.myhelloworld.helper.Base64Custom;
import com.vinicius.myhelloworld.helper.Preferencias;
import com.vinicius.myhelloworld.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText        email;
    private EditText        senha;
    private Button          botaoLogar;
    private Usuario         usuario;
    private FirebaseAuth    autenticacao;
    private ProgressBar     progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificaUsuarioLogado();

        email       = findViewById(R.id.edit_login_email);
        senha       = findViewById(R.id.edit_login_senha);
        botaoLogar  = findViewById(R.id.button_login_logar);
        progressBar = findViewById(R.id.progressBar);

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                botaoLogar.setEnabled(false);

                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validaLogin();
            }
        });
    }

    //Verifica se usuario já esta logado no App
    private void verificaUsuarioLogado(){

        autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();

        if(autenticacao.getCurrentUser() != null){
            abreTelaPrincipal();
        }
    }

    //Faz login do usuario
    private void validaLogin(){

        autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();

        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    //Salva identificador do usuario nas preferencias
                    Preferencias preferencias = new Preferencias(LoginActivity.this);
                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    preferencias.setIdentificador(identificadorUsuario);

                    abreTelaPrincipal();

                    Toast.makeText(LoginActivity.this, "Sucesso ao fazer login", Toast.LENGTH_LONG).show();
                }else{

                    String erroExececao;

                    //Tratamento de excecao para login do usuario
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        erroExececao = "E-mail inválido!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExececao = "Senha inválida!";
                    } catch (Exception e) {
                        e.printStackTrace();
                        erroExececao = "Erro ao fazer login";
                    }

                    Toast.makeText(LoginActivity.this, "Erro: " + erroExececao, Toast.LENGTH_LONG).show();

                }

                progressBar.setVisibility(View.INVISIBLE);
                botaoLogar.setEnabled(true);

            }
        });

    }

    //Abre a view principal
    private void abreTelaPrincipal(){

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    //Abre a view de cadastro de usuario
    public void abreCadastroUsuario(View v){

        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);

    }
}
