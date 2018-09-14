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
import com.vinicius.myhelloworld.activity.ChatActivity;
import com.vinicius.myhelloworld.adapter.ChatAdapter;
import com.vinicius.myhelloworld.config.ConfiguracaoFireBase;
import com.vinicius.myhelloworld.model.Chat;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Chat> chats;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        firebase.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        firebase.removeEventListener(valueEventListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Intanciar objeto
        chats = new ArrayList<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        //Montar listView Adapter
        listView = view.findViewById(R.id.lv_chats);
        adapter = new ChatAdapter(getActivity(), chats);
        listView.setAdapter(adapter);

        //Recuperar chat do firebase
        firebase = ConfiguracaoFireBase.getFirebase()
                .child("Chat");

        //Listener para recuperar contatos
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpar lista
                chats.clear();

                //Listar Contatos
                for (DataSnapshot dado: dataSnapshot.getChildren()){

                    Chat chat = dado.getValue(Chat.class);
                    chats.add(chat);
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

                Intent intent = new Intent(getActivity(), ChatActivity.class);

                //Recuperar contato
                Chat chat = chats.get(i);

                //Enviando dados para conversa activity
                intent.putExtra("chat", chat);

                startActivity(intent);
            }
        });

        return view;
    }

}
