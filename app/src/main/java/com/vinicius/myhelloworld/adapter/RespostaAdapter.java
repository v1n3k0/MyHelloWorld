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
import com.vinicius.myhelloworld.model.Resposta;

import java.util.ArrayList;

public class RespostaAdapter extends ArrayAdapter<Resposta> {

    private ArrayList<Resposta> respostas;
    private Context context;

    public RespostaAdapter(@NonNull Context c, @NonNull ArrayList<Resposta> objects) {
        super(c, 0, objects);

        this.respostas = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(respostas != null){
            //inicializar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Montar view a partir do xml
            view = inflater.inflate(R.layout.lista_resposta, parent, false);

            //Recupera elemento para exibição
            TextView tvId = view.findViewById(R.id.tv_id);
            TextView tvResposta = view.findViewById(R.id.tv_resposta);

            //Editar campos da view
            Resposta resposta = respostas.get(position);
            tvId.setText(Integer.toString(resposta.getId() + 1));
            tvResposta.setText(resposta.getResposta());
        }

        return view;
    }
}
