package com.cl.earosb.iipzs.adapters;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
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

    public NuevoCERecyclerItemViewHolder(final View parent, TextView partidaNombre, TextView trabajoCont, TextView trabajoObs, Button plus1, Button plus10) {
        super(parent);
        mCardText = partidaNombre;
        mCardCont = trabajoCont;
        mCardObs = trabajoObs;
        mPlus1 = plus1;
        mPlus10 = plus10;
    }

    public static NuevoCERecyclerItemViewHolder newInstance(final View parent) {
        final TextView partidaName = (TextView) parent.findViewById(R.id.card_text);
        final TextView partidaCont = (TextView) parent.findViewById(R.id.card_cont);
        final TextView trabajoObs = (TextView) parent.findViewById(R.id.card_obs_text);
        trabajoObs.setMovementMethod(new ScrollingMovementMethod());
        Button plus1 = (Button) parent.findViewById(R.id.card_plus1);
        Button plus10 = (Button) parent.findViewById(R.id.card_plus10);
        Button obs = null;//(Button) parent.findViewById(R.id.card_obs);
        Toolbar toolbar = (Toolbar) parent.findViewById(R.id.card_toolbar);

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
                } else {
                    t.cantidad = 0;
                    t.save();
                    partidaCont.setText(String.valueOf(t.cantidad));
                }
                return true;
            }
        });

        if (toolbar != null) {
            toolbar.inflateMenu(R.menu.card_nuevo_ce);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    Trabajo t = Trabajo.load(Trabajo.class, Long.parseLong(partidaName.getTag().toString()));
                    switch (id) {
                        case R.id.action_obs:
                            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                            builder.setTitle("Observaciones");
                            final EditText inputText = new EditText(parent.getContext());
                            inputText.setInputType(InputType.TYPE_CLASS_TEXT);
                            inputText.setText(t.observaciones);
                            builder.setView(inputText);
                            builder.setPositiveButton(R.string.action_save, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Trabajo t = Trabajo.load(Trabajo.class, Long.parseLong(partidaName.getTag().toString()));
                                    t.observaciones = inputText.getText().toString();
                                    t.save();
                                    trabajoObs.setText(t.observaciones);
                                }
                            });
                            builder.setNegativeButton(R.string.action_cancel, null);
                            builder.show();
                            break;

                    }
                    return true;
                }
            });
        }

        trabajoObs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = trabajoObs.getText().toString();
                if (txt != null && !txt.isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                    alertDialog.setTitle(R.string.text_observaciones);
                    alertDialog.setMessage(trabajoObs.getText());
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });

        return new NuevoCERecyclerItemViewHolder(parent, partidaName, partidaCont, trabajoObs, plus1, plus10);
    }

    public void setTrabajo(Trabajo t) {
        mCardText.setText(t.partida.nombre + " (" + t.partida.unidad + ")");
        mCardText.setTag(t.getId());
        mCardCont.setText(String.valueOf(t.cantidad));
        mCardObs.setText(t.observaciones);
    }
}
