package com.cl.earosb.iipzs.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cl.earosb.iipzs.R;

/**
 * Created by earosb on 16-10-15.
 */
public class ControlEstandarAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] fecha;
    private final int[] km_inicio;

    public ControlEstandarAdapter(Activity context, String[] fecha, int[] km_inicio) {
        super(context, R.layout.control_estandar_item, fecha);
        this.context = context;
        this.fecha = fecha;
        this.km_inicio = km_inicio;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.control_estandar_item, null, true);

        TextView textFecha = (TextView) rowView.findViewById(R.id.inspeccion_fecha);
        TextView textKm = (TextView) rowView.findViewById(R.id.inspeccion_km_inicio);
//        ImageView imageSync = (ImageView) rowView.findViewById(R.id.inspeccion_sync);
        ImageButton imageButton = (ImageButton) rowView.findViewById(R.id.inspeccion_delete);

        textFecha.setText(this.getContext().getString(R.string.control_estandar_fecha) + " " + fecha[position]);
        textKm.setText(R.string.control_estandar_km_inicio + " " + km_inicio[position]);
//        imageButton.setImageResource(android.R.drawable.ic_popup_sync);
        return rowView;
    }
}
