package com.vinicius.myhelloworld.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vinicius.myhelloworld.R;
import com.vinicius.myhelloworld.model.Chat;

import java.util.ArrayList;

public class ChatAdapter extends ArrayAdapter<Chat> {

    private ArrayList<Chat> chats;
    private Context context;

    public ChatAdapter(@NonNull Context c, @NonNull ArrayList<Chat> objects) {
        super(c, 0, objects);

        this.chats = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        //Verifica se a lista está vazia
        if(chats != null){
            //inicializar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Montar view a partir do xml
            view = inflater.inflate(R.layout.lista_fragment, parent, false);

            //Recupera elemento para exibição
            TextView titulo = view.findViewById(R.id.tv_titulo);
            TextView subtitulo = view.findViewById(R.id.tv_subtitulo);

            Chat chat = chats.get(position);
            titulo.setText(chat.getNome());
            subtitulo.setText(chat.getAula().replace("Aula", "Chat") );

        }

        return view;
    }
}
