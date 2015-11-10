package com.cl.earosb.iipzs.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cl.earosb.iipzs.R;
import com.cl.earosb.iipzs.models.Partida;

import java.util.List;

/**
 * Created by earosb on 07-11-15.
 */
public class NuevoCERecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<Partida> mItemList;
    private NuevoCERecyclerItemViewHolder holder;

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
        holder = (NuevoCERecyclerItemViewHolder) viewHolder;
        String itemText = mItemList.get(position).nombre;
        int itemCont = mItemList.get(position).ranking;
        holder.setItemText(itemText);
        holder.setmPartidaCont(itemCont);
        holder.setTagId(mItemList.get(position).getId());
        holder.mObs.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_obs:
                Log.d("APP", "CLOCK!");
                break;
        }
    }
}
