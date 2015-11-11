package com.cl.earosb.iipzs.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.cl.earosb.iipzs.R;

/**
 * Created by earosb on 07-11-15.
 */
public class PartidaRecyclerItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView mPartidaName;
    private final TextView mPartidaUnidad;

    public PartidaRecyclerItemViewHolder(final View parent, TextView partidaName, TextView partidaUnidad) {
        super(parent);
        mPartidaName = partidaName;
        mPartidaUnidad = partidaUnidad;
    }

    public static PartidaRecyclerItemViewHolder newInstance(View parent) {
        TextView partidaName = (TextView) parent.findViewById(R.id.card_partida_nombre);
        TextView partidaUnidad = (TextView) parent.findViewById(R.id.card_partida_unidad);
        return new PartidaRecyclerItemViewHolder(parent, partidaName, partidaUnidad);
    }

    public void setPartidaName(CharSequence text) {
        mPartidaName.setText(text);
    }

    public void setPartidaUnidad(CharSequence text) {
        mPartidaUnidad.setText(text);
    }

}
