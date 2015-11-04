package com.cl.earosb.iipzs.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.cl.earosb.iipzs.R;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by earosb on 16-10-15.
 */
public class ControlEstandarAdapter extends ArrayAdapter<ControlEstandar>{
    private final Activity context;

    //private ControlEstandar item;

    public ControlEstandarAdapter(Activity context) {
        super(context, R.layout.control_estandar_item);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.control_estandar_item, null, true);

        TextView textFecha = (TextView) rowView.findViewById(R.id.inspeccion_fecha);
        TextView textKm = (TextView) rowView.findViewById(R.id.inspeccion_km_inicio);
        ImageButton btn_delete = (ImageButton) rowView.findViewById(R.id.inspeccion_delete);

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

        return rowView;
    }
}
