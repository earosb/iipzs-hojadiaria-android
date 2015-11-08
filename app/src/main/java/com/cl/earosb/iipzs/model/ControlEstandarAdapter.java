package com.cl.earosb.iipzs.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cl.earosb.iipzs.NuevoCEActivity;
import com.cl.earosb.iipzs.R;

/**
 * Created by earosb on 16-10-15.
 */
public class ControlEstandarAdapter extends ArrayAdapter<ControlEstandar> {
    private final Activity context;

    public ControlEstandarAdapter(Activity context) {
        super(context, R.layout.item_control_estandar);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.item_control_estandar, null, true);

        TextView textFecha = (TextView) rowView.findViewById(R.id.inspeccion_fecha);
        TextView textKm = (TextView) rowView.findViewById(R.id.inspeccion_km_inicio);
        ImageButton btn_delete = (ImageButton) rowView.findViewById(R.id.inspeccion_delete);
        ImageButton btn_edit = (ImageButton) rowView.findViewById(R.id.inspeccion_edit);

        final ControlEstandar item = getItem(position);

        textFecha.setText(this.getContext().getString(R.string.control_estandar_fecha) + " " + item.fecha);
        textKm.setText(getContext().getString(R.string.control_estandar_km_inicio) + " " + item.km_inicio + " ID: " + item.getId());

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("itemCE", String.valueOf(item.getId()));
                AlertDialog.Builder confirm = new AlertDialog.Builder(getContext());
                confirm.setMessage("¿Eliminar Control de Estándar?");
                confirm.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        item.delete();
                        remove(item);
                        notifyDataSetChanged();
                    }
                });
                confirm.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                confirm.show();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NuevoCEActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("ce_id", item.getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
