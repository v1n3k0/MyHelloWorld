package com.vinicius.myhelloworld.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.config.ConfiguracaoFireBase;
import com.vinicius.myhelloworld.helper.Preferencias;
import com.vinicius.myhelloworld.model.Usuario;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private TextView nome;
    private TextView email;
    private TextView ultimaAula;
    private TextView level;
    private TextView experiencia;
    private Usuario usuario;
    private DatabaseReference firebase;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        nome = view.findViewById(R.id.textView_perfil_nome);
        email = view.findViewById(R.id.textView_perfil_email);
        ultimaAula = view.findViewById(R.id.textView_perfil_ultimaAula);
        level = view.findViewById(R.id.textView_perfil_level);
        experiencia = view.findViewById(R.id.textView_perfil_experiencia);;

        //Recuperar contatos do firebase
        Preferencias preferencias = new Preferencias(getActivity());
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
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
