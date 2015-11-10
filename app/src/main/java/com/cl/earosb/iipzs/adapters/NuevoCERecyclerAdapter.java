package com.cl.earosb.iipzs.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cl.earosb.iipzs.R;
import com.cl.earosb.iipzs.models.Partida;

import java.util.List;

/**
 * Created by earosb on 07-11-15.
 */
public class NuevoCERecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Partida> mItemList;

    public NuevoCERecyclerAdapter(List<Partida> itemList) {
        mItemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_nuevo_ce, parent, false);
        return NuevoCERecyclerItemViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        NuevoCERecyclerItemViewHolder holder = (NuevoCERecyclerItemViewHolder) viewHolder;
        String itemText = mItemList.get(position).nombre;
        int itemCont = mItemList.get(position).ranking;
        holder.setItemText(itemText);
        holder.setmPartidaCont(itemCont);
        holder.setTagId(mItemList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

}
