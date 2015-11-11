package com.cl.earosb.iipzs.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cl.earosb.iipzs.R;
import com.cl.earosb.iipzs.models.Partida;

import java.util.List;

/**
 * Created by earosb on 22-10-15.
 */
public class PartidaRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Partida> mPartidas;
    private PartidaRecyclerItemViewHolder holder;

    public PartidaRecyclerAdapter(List<Partida> mPartidas) {
        this.mPartidas = mPartidas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_partida, parent, false);
        return PartidaRecyclerItemViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        holder = (PartidaRecyclerItemViewHolder) viewHolder;
        String nombre = mPartidas.get(position).nombre;
        String unidad = mPartidas.get(position).unidad;
        holder.setPartidaName(nombre);
        holder.setPartidaUnidad(unidad);
    }

    @Override
    public int getItemCount() {
        return mPartidas == null ? 0 : mPartidas.size();
    }

}