package com.cl.earosb.iipzs.model;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cl.earosb.iipzs.NuevoCEActivity;
import com.cl.earosb.iipzs.R;

/**
 * Created by earosb on 22-10-15.
 */
public class NuevoCEAdapter extends BaseAdapter {
    private Context mContext;

    public NuevoCEAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return partidas.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(mContext);
            gridView = inflater.inflate(R.layout.grid_nuevo_ce, null);
            gridView.setLayoutParams(new GridView.LayoutParams(480, 240));

            TextView txtPartida = (TextView) gridView.findViewById(R.id.txtview_partida_realizar);
            txtPartida.setText(partidas[position]);

            TextView txtContador = (TextView) gridView.findViewById(R.id.txtview_contador);
            int cont = (position * position) + 1;
            txtContador.setText("" + cont);

            Button btn_plus_1 = (Button) gridView.findViewById(R.id.btn_plus_1);
            btn_plus_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAG_plus1", "Mensaje +1");
                }
            });

        } else {
            gridView = (View) convertView;
        }


        return gridView;
    }

    private String[] partidas = {
            "Limpieza de causes, ductos, puentes y alcantarillas",
            "Instalar balizas en plena vía PK",
            "Reparación de encalladuras con soldadura",
            "Reponer perno",
            "Recoger, seleccionar y acopiar de pequeño material vía",
            "Reemplazar Aguja de desviadores de reempleo",
            "Sustitución aislada de durmientes de madera",
            "Sustitución de durmientes de puentes",
            "Sustitución de durmientes de desviadores",
            "Reemplazo continuo de rieles",
            "Reparación de obras de arte y puentes menores",
            "Instalar cierro de malla",
    };
}