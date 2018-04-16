package com.vinicius.myhelloworld.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.adapter.ContatoAdapter;
import com.vinicius.myhelloworld.config.ConfiguracaoFireBase;
import com.vinicius.myhelloworld.helper.Preferencias;
import com.vinicius.myhelloworld.model.Contato;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatoFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Contato> contatos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerContatos;


    public ContatoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        firebase.addValueEventListener(valueEventListenerContatos);
    }

    @Override
    public void onStop() {
        super.onStop();

        firebase.removeEventListener(valueEventListenerContatos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Intanciar objeto
        contatos = new ArrayList<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contato, container, false);

        //Montar listView Adapter
        listView = view.findViewById(R.id.lv_contatos);
        adapter = new ContatoAdapter(getActivity(), contatos);
        listView.setAdapter(adapter);

        //Recuperar contatos do firebase
        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorUsuarioLogado = preferencias.getIdentificador();
        firebase = ConfiguracaoFireBase.getFirebase()
                .child("Contatos")
                .child(identificadorUsuarioLogado);

        //Listener para recuperar contatos
        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpar lista
                contatos.clear();

                //Listar Contatos
                for (DataSnapshot dado: dataSnapshot.getChildren()){

                    Contato contato = dado.getValue(Contato.class);
                    contatos.add(contato);

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        return view;
    }

}
