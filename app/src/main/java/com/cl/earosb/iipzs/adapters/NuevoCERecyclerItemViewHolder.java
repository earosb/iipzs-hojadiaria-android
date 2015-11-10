package com.cl.earosb.iipzs.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cl.earosb.iipzs.R;
import com.cl.earosb.iipzs.models.Partida;

/**
 * Created by earosb on 07-11-15.
 */
public class NuevoCERecyclerItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView mPartidaName;
    private final TextView mPartidaCont;
    private final Button mPlus1;
    private final Button mPlus10;
    private final Button mObs;

    public NuevoCERecyclerItemViewHolder(final View parent, TextView partidaName, TextView partidaCont, Button plus1, Button plus10, Button obs) {
        super(parent);
        mPartidaName = partidaName;
        mPartidaCont = partidaCont;
        mPlus1 = plus1;
        mPlus10 = plus10;
        mObs = obs;
    }

    public static NuevoCERecyclerItemViewHolder newInstance(final View parent) {
        final TextView partidaName = (TextView) parent.findViewById(R.id.card_text);
        final TextView partidaCont = (TextView) parent.findViewById(R.id.card_cont);
        Button plus1 = (Button) parent.findViewById(R.id.card_plus1);
        Button plus10 = (Button) parent.findViewById(R.id.card_plus10);
        Button obs = (Button) parent.findViewById(R.id.card_obs);

        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Partida p = Partida.load(Partida.class, Long.parseLong(partidaName.getTag().toString()));
                p.ranking = (p.ranking + 1);
                p.save();
                partidaCont.setText(String.valueOf(p.ranking));
                //Log.d("MyApp", view.getContext().get);
            }
        });
        plus1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int aux = Integer.parseInt(partidaCont.getText().toString()) - 1;
                partidaCont.setText(String.valueOf(aux));
                return true;
            }
        });
        plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Partida p = Partida.load(Partida.class, Long.parseLong(partidaName.getTag().toString()));
                p.ranking = (p.ranking + 1);
                p.save();
                partidaCont.setText(String.valueOf(p.ranking));
            }
        });
        plus10.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int aux = Integer.parseInt(partidaCont.getText().toString()) - 10;
                partidaCont.setText(String.valueOf(aux));
                return true;
            }
        });
        obs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderKm_inicio = new AlertDialog.Builder(view.getContext());
                builderKm_inicio.setTitle("Observaciones");
                final EditText inputText = new EditText(view.getContext());
                inputText.setInputType(InputType.TYPE_CLASS_TEXT);
                builderKm_inicio.setView(inputText);
                builderKm_inicio.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MyApp", inputText.getText().toString());
                    }
                });
                builderKm_inicio.show();
            }
        });
        return new NuevoCERecyclerItemViewHolder(parent, partidaName, partidaCont, plus1, plus10, obs);
    }

    public void setItemText(CharSequence text) {
        mPartidaName.setText(text);
    }

    public void setTagId(long id){
        mPartidaName.setTag(id);
    }

    public void setmPartidaCont(int i){
        this.mPartidaCont.setText(String.valueOf(i));
    }
}
