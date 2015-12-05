package com.cl.earosb.iipzs.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.cl.earosb.iipzs.NuevoCEActivity;
import com.cl.earosb.iipzs.R;
import com.cl.earosb.iipzs.models.AuxObject;
import com.cl.earosb.iipzs.models.ControlEstandar;
import com.cl.earosb.iipzs.models.Hectometro;
import com.cl.earosb.iipzs.models.Message;
import com.cl.earosb.iipzs.models.Trabajo;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        TextView textCausaFecha = (TextView) rowView.findViewById(R.id.inspeccion_causa_fecha);
        TextView textKm = (TextView) rowView.findViewById(R.id.inspeccion_km_inicio);
        final ImageButton btn_upload = (ImageButton) rowView.findViewById(R.id.inspeccion_upload);
        ImageButton btn_edit = (ImageButton) rowView.findViewById(R.id.inspeccion_edit);
        ImageButton btn_delete = (ImageButton) rowView.findViewById(R.id.inspeccion_delete);

        final ControlEstandar item = getItem(position);

        textCausaFecha.setText(item.causa + " " + item.fecha_title);
        textKm.setText(getContext().getString(R.string.ce_km_inicio) + " " + item.km_inicio);

        if (item.sync) {
            btn_upload.setImageResource(R.drawable.ic_check_circle_black_36dp);
            btn_upload.setEnabled(false);
        }

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(context).setMessage(R.string.ce_sync_msg)
                        .setPositiveButton(R.string.action_agree, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new UploadTask(item, view).execute();
                            }
                        }).setNegativeButton(R.string.action_cancel, null)
                        .show();
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
                new AlertDialog.Builder(context).setMessage(R.string.ce_delete_msg)
                        .setPositiveButton(context.getString(R.string.action_agree), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                item.delete();
                                remove(item);
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton(context.getString(R.string.action_cancel), null)
                        .show();
            }
        });

        return rowView;
    }

    private class UploadTask extends AsyncTask<Void, Void, Message> {

        private ProgressDialog nDialog;

        private ControlEstandar controlEstandar;

        private View mView;

        public UploadTask(ControlEstandar controlEstandar, View view) {
            this.controlEstandar = controlEstandar;
            mView = view;
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
        protected Message doInBackground(Void... voids) {

            List<AuxObject> dataTrabajos = new ArrayList<AuxObject>();

            ActiveAndroid.beginTransaction();
            try {
                List<Hectometro> hectometros = controlEstandar.getHectometros();
                String obsCe = controlEstandar.obs;

                for (Hectometro h : hectometros) {
                    List<Trabajo> trabajos = h.getTrabajos();
                    for (Trabajo t : trabajos) {
                        if (t.cantidad > 0) {
                            AuxObject aux = new AuxObject(t.hectometro.controlEstandar.causa, t.partida.remote_id, t.hectometro.km_inicio, t.hectometro.km_inicio + 100, t.cantidad, t.observaciones, obsCe);
                            dataTrabajos.add(aux);
                        }
                    }
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }

            String json = new Gson().toJson(dataTrabajos);

            Map<String, String> data = new HashMap<String, String>();
            data.put("token", getContext().getSharedPreferences("PREFERENCE", getContext().MODE_PRIVATE).getString("token_api", "token_api"));
            data.put("trabajos", json);

            try {
                String response = HttpRequest.post("http://icilicafalpzs.cl/api/v1/programar").form(data).body();
                return new Gson().fromJson(response, Message.class);
            } catch (Exception e) {
                return new Message(true, "Error de conexión", null);
            }

        }

        @Override
        protected void onPostExecute(Message msg) {
            Snackbar snackbar;
            if (!msg.isError()) {
                controlEstandar.sync = true;
                controlEstandar.save();
                snackbar = Snackbar.make(mView, "Control de estándar enviado con éxito", Snackbar.LENGTH_LONG);
                notifyDataSetChanged();
            } else {
                snackbar = Snackbar.make(mView, msg.getMsg(), Snackbar.LENGTH_LONG);
            }
            nDialog.hide();
            snackbar.show();

        }
    }
}
