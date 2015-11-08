package com.cl.earosb.iipzs.adapters;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cl.earosb.iipzs.R;
import com.cl.earosb.iipzs.model.Partida;

import java.util.List;

/**
 * Created by earosb on 22-10-15.
 */
public class NuevoCEAdapter extends RecyclerView.Adapter<NuevoCEAdapter.ViewHolder> {

    List<Partida> mPartidas;

    public NuevoCEAdapter() {
        super();
        mPartidas = Partida.getAll();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_nuevo_ce, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Partida p = mPartidas.get(i);
        viewHolder.name.setText(p.nombre);
        viewHolder.cont.setText(String.valueOf(p.cont));
        viewHolder.plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int aux = Integer.parseInt(viewHolder.cont.getText().toString()) + 1;
                viewHolder.cont.setText(String.valueOf(aux));
            }
        });
        viewHolder.plus1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int aux = Integer.parseInt(viewHolder.cont.getText().toString()) - 1;
                viewHolder.cont.setText(String.valueOf(aux));
                Vibrator v = (Vibrator) view.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
                return true;
            }
        });
        viewHolder.plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int aux = Integer.parseInt(viewHolder.cont.getText().toString()) + 10;
                viewHolder.cont.setText(String.valueOf(aux));
            }
        });
        viewHolder.plus10.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int aux = Integer.parseInt(viewHolder.cont.getText().toString()) - 10;
                viewHolder.cont.setText(String.valueOf(aux));
                Vibrator v = (Vibrator) view.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                v.vibrate(500);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {

        return mPartidas.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        public TextView cont;
        public TextView name;
        public Button plus1;
        public Button plus10;

        public ViewHolder(View itemView) {
            super(itemView);
            cont = (TextView) itemView.findViewById(R.id.card_cont);
            name = (TextView) itemView.findViewById(R.id.card_text);
            plus1 = (Button) itemView.findViewById(R.id.card_plus1);
            plus10 = (Button) itemView.findViewById(R.id.card_plus10);
        }
    }

}
