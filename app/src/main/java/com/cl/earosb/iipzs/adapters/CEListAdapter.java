package com.cl.earosb.iipzs.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.cl.earosb.iipzs.models.ControlEstandar;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by earosb on 16-10-15.
 */
public class CEListAdapter extends ArrayAdapter<ControlEstandar> {
    private final Activity context;

    public CEListAdapter(Activity context) {
        super(context, R.layout.item_ce_list);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.item_ce_list, null, true);

        TextView textFecha = (TextView) rowView.findViewById(R.id.inspeccion_fecha);
        TextView textKm = (TextView) rowView.findViewById(R.id.inspeccion_km_inicio);
        final ImageButton btn_upload = (ImageButton) rowView.findViewById(R.id.inspeccion_upload);
        ImageButton btn_edit = (ImageButton) rowView.findViewById(R.id.inspeccion_edit);
        ImageButton btn_delete = (ImageButton) rowView.findViewById(R.id.inspeccion_delete);

        final ControlEstandar item = getItem(position);

        textFecha.setText(this.getContext().getString(R.string.control_estandar_fecha) + " " + item.fecha);
        textKm.setText(getContext().getString(R.string.control_estandar_km_inicio) + " " + item.km_inicio);

        if (item.sync){
            btn_upload.setImageResource(R.drawable.ic_check_circle_black_36dp);
        }

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UploadTask(item).execute();
                btn_upload.setImageResource(R.drawable.ic_check_circle_black_36dp);
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

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    private class UploadTask extends AsyncTask<String, Long, String> {

        private ProgressDialog nDialog;

        private ControlEstandar controlEstandar;

        public UploadTask(ControlEstandar controlEstandar) {
            this.controlEstandar = controlEstandar;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return HttpRequest.get(urls[0]).accept("application/json").body();
            } catch (HttpRequest.HttpRequestException exception) {
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getContext());
            nDialog.setTitle("Subiendo Control de estándar");
            nDialog.setMessage("Enviando datos...");
            nDialog.setCancelable(false);
            nDialog.show();
        }

        @Override
        protected void onPostExecute(String response) {

            Log.d("RESPONSE", response);

            nDialog.hide();
        }
    }
}
