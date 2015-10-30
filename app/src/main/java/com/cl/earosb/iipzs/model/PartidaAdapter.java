package com.cl.earosb.iipzs.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cl.earosb.iipzs.R;

import java.util.List;

/**
 * Created by earosb on 22-10-15.
 */
public class PartidaAdapter extends BaseAdapter {
    private Context mContext;

    private List<Partida> partidas;

    public PartidaAdapter(Context c) {
        mContext = c;
        partidas = Partida.getAll();
    }

    public int getCount() {
        return partidas.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView textView;

        if (convertView == null) {
            textView = new TextView(mContext);

        } else {
            textView = (TextView) convertView;
        }

        textView.setText(partidas.get(position).nombre);

        return textView;
    }

}