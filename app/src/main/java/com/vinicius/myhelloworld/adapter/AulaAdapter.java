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
import com.vinicius.myhelloworld.model.Aula;
import java.util.ArrayList;

public class AulaAdapter extends ArrayAdapter<Aula> {

    private ArrayList<Aula> aulas;
    private Context context;

    public AulaAdapter(@NonNull Context c, @NonNull ArrayList<Aula> objects) {
        super(c, 0, objects);

        this.aulas = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        //Verifica se a lista está vazia
        if(aulas != null) {
            //inicializar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Montar view a partir do xml
            view = inflater.inflate(R.layout.lista_fragment, parent, false);

            //Recupera elemento para exibição
            TextView titulo = view.findViewById(R.id.tv_titulo);
            TextView subtitulo = view.findViewById(R.id.tv_subtitulo);

            //Editar campos da view
            Aula aula = aulas.get(position);
            titulo.setText(aula.getAula());
            subtitulo.setText(aula.getNome());
        }

        return view;
    }
}
