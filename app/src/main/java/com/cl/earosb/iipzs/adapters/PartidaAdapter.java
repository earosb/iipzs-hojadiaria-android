package com.cl.earosb.iipzs.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cl.earosb.iipzs.model.Partida;

import java.util.List;

/**
 * Created by earosb on 22-10-15.
 */
public class PartidaAdapter extends ArrayAdapter<Partida> {

    private final Activity context;

    public PartidaAdapter(Activity context, List<Partida> partidas) {
        super(context, android.R.layout.simple_list_item_1, partidas);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(android.R.layout.simple_list_item_1, null, true);

        Partida partida = getItem(position);

        TextView textView = (TextView) view.findViewById(android.R.id.text1);

        textView.setText(partida.nombre);

        return view;
    }

}