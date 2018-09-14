package com.vinicius.myhelloworld.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.activity.AulaActivity;
import com.vinicius.myhelloworld.adapter.AulaAdapter;
import com.vinicius.myhelloworld.config.ConfiguracaoFireBase;
import com.vinicius.myhelloworld.model.Aula;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AulaFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Aula> aulas;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerAulas;

    public AulaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        firebase.addValueEventListener(valueEventListenerAulas);
    }

    @Override
    public void onStop() {
        super.onStop();

        firebase.removeEventListener(valueEventListenerAulas);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Intanciar objeto
        aulas = new ArrayList<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_aprende, container, false);

        //Montar listView Adapter
        listView = view.findViewById(R.id.lv_aula);
        adapter = new AulaAdapter(getActivity(), aulas);
        listView.setAdapter(adapter);

        //Recuperar contatos do firebase
        firebase = ConfiguracaoFireBase.getFirebase()
                .child("Aula");

        //Listener para recuperar contatos
        valueEventListenerAulas = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpar lista
                aulas.clear();

                //Listar Contatos
                for (DataSnapshot dado: dataSnapshot.getChildren()){

                    Aula aula = dado.getValue(Aula.class);
                    aulas.add(aula);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        //OnClick item da lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), AulaActivity.class);

                //Recuperar contato
                Aula aula = aulas.get(i);

                //Enviando dados para conversa activity
                intent.putExtra("aula", aula);

                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
