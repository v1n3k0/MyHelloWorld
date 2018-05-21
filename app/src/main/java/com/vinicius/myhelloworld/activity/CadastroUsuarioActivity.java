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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.config.ConfiguracaoFireBase;
import com.vinicius.myhelloworld.helper.Base64Custom;
import com.vinicius.myhelloworld.helper.Preferencias;
import com.vinicius.myhelloworld.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText        nome;
    private EditText        email;
    private EditText        senha;
    private Button          cadastrar;
    private Usuario         usuario;
    private FirebaseAuth    autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome        = findViewById(R.id.edit_cadastro_nome);
        email       = findViewById(R.id.edit_cadastro_email);
        senha       = findViewById(R.id.edit_cadastro_senha);
        cadastrar   = findViewById(R.id.button_cadastro_cadastrar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = new Usuario();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                cadastroUsuario();
            }
        });

    }

    //Salva Usuario no Firebase
    public void cadastroUsuario(){

        autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();

        //Criar o usuario
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(CadastroUsuarioActivity.this, "Sucesso ao cadastrar usuário", Toast.LENGTH_LONG).show();

                    //Salva usuario com codificador base64
                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setId(identificadorUsuario);
                    usuario.salvar();

                    //Salva identificador do usuario nas preferencias
                    Preferencias preferencias = new Preferencias(CadastroUsuarioActivity.this);
                    preferencias.setIdentificador(identificadorUsuario, usuario.getNome());

                    abreLoginUsuario();

                }else{

                    String erroExcecao;

                    //Tratamento de Excecao para criar usuario
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte, contendo mais caracteres e com letras e números!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "E-mail digitado inválido, digite um novo e-mail!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "E-mail já cadastrado!";
                    } catch (Exception e) {
                        erroExcecao = "Erro ao efetuar o cadastro!";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroUsuarioActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    //Abre Activity Login
    public void abreLoginUsuario(){
        Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
