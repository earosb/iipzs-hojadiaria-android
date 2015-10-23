package com.cl.earosb.iipzs;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.cl.earosb.iipzs.dummy.ImageAdapter;
import com.cl.earosb.iipzs.model.NuevoCEAdapter;

/**
 * Activity para Nuevo Control de Estandar
 */
public class NuevoCEActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ce);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new NuevoCEAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(NuevoCEActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void holaPlusDiez(View v){
        Log.d("TAG_plus", "holaPlusDiez");
    }

}
