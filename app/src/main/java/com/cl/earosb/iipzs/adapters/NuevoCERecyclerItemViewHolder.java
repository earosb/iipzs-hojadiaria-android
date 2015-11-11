package com.cl.earosb.iipzs.adapters;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cl.earosb.iipzs.R;
import com.cl.earosb.iipzs.models.Trabajo;

/**
 * Created by earosb on 07-11-15.
 */
public class NuevoCERecyclerItemViewHolder extends RecyclerView.ViewHolder {

    private TextView mCardText;
    private TextView mCardCont;
    private TextView mCardObs;
    private Button mPlus1;
    private Button mPlus10;
    private Button mObs;

    public NuevoCERecyclerItemViewHolder(final View parent, TextView partidaNombre, TextView trabajoCont,TextView trabajoObs, Button plus1, Button plus10, Button obs) {
        super(parent);
        mCardText = partidaNombre;
        mCardCont = trabajoCont;
        mCardObs = trabajoObs;
        mPlus1 = plus1;
        mPlus10 = plus10;
        mObs = obs;
    }

    public static NuevoCERecyclerItemViewHolder newInstance(final View parent) {
        final TextView partidaName = (TextView) parent.findViewById(R.id.card_text);
        final TextView partidaCont = (TextView) parent.findViewById(R.id.card_cont);
        final TextView trabajoObs = (TextView) parent.findViewById(R.id.card_obs_text);
        Button plus1 = (Button) parent.findViewById(R.id.card_plus1);
        Button plus10 = (Button) parent.findViewById(R.id.card_plus10);
        Button obs = (Button) parent.findViewById(R.id.card_obs);

        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Trabajo t = Trabajo.load(Trabajo.class, Long.parseLong(partidaName.getTag().toString()));
                t.cantidad = t.cantidad + 1;
                t.partida.ranking = t.partida.ranking + 1;
                t.partida.save();
                t.save();
                partidaCont.setText(String.valueOf(t.cantidad));
            }
        });
        plus1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Trabajo t = Trabajo.load(Trabajo.class, Long.parseLong(partidaName.getTag().toString()));
                if (t.cantidad > 0) {
                    t.cantidad = t.cantidad - 1;
                    t.save();
                    partidaCont.setText(String.valueOf(t.cantidad));
                }
                return true;
            }
        });
        plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Trabajo t = Trabajo.load(Trabajo.class, Long.parseLong(partidaName.getTag().toString()));
                t.cantidad = t.cantidad + 10;
                t.partida.ranking = t.partida.ranking + 1;
                t.partida.save();
                t.save();
                partidaCont.setText(String.valueOf(t.cantidad));
            }
        });
        plus10.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Trabajo t = Trabajo.load(Trabajo.class, Long.parseLong(partidaName.getTag().toString()));
                if (t.cantidad > 9) {
                    t.cantidad = t.cantidad - 10;
                    t.save();
                    partidaCont.setText(String.valueOf(t.cantidad));
                }
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
                Trabajo t = Trabajo.load(Trabajo.class, Long.parseLong(partidaName.getTag().toString()));
                inputText.setText(t.observaciones);
                builderKm_inicio.setView(inputText);
                builderKm_inicio.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Trabajo t = Trabajo.load(Trabajo.class, Long.parseLong(partidaName.getTag().toString()));
                        t.observaciones = inputText.getText().toString();
                        t.save();
                        trabajoObs.setText(t.observaciones);
                    }
                });
                builderKm_inicio.show();
            }
        });
        return new NuevoCERecyclerItemViewHolder(parent, partidaName, partidaCont, trabajoObs, plus1, plus10, obs);
    }

    public void setTrabajo(Trabajo t) {
        mCardText.setText(t.partida.nombre);
        mCardText.setTag(t.getId());
        mCardCont.setText(String.valueOf(t.cantidad));
        mCardObs.setText(t.observaciones);
    }
}
