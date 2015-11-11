package com.cl.earosb.iipzs.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.cl.earosb.iipzs.R;
import com.cl.earosb.iipzs.adapters.PartidaRecyclerAdapter;
import com.cl.earosb.iipzs.models.Partida;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PartidasFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private int columnsGridNumber;
    private PartidaRecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;

    public static PartidasFragment createInstance() {
        PartidasFragment partidasFragment = new PartidasFragment();
        return partidasFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_recycler_view, container, false);

        setHasOptionsMenu(true);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        columnsGridNumber = Integer.parseInt(sharedPreferences.getString("columns_grid_number", "2"));

        setupRecyclerView(recyclerView);

        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnsGridNumber));
        recyclerAdapter = new PartidaRecyclerAdapter(Partida.getAll());
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        int id = item.getItemId();

        if (id == R.id.action_update) {
            new DownloadTask().execute("http://icilicafalpzs.cl/api/v1/trabajos");
        }
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals("columns_grid_number")) {
            columnsGridNumber = Integer.parseInt(sharedPreferences.getString("columns_grid_number", "2"));
        }
    }

    private class DownloadTask extends AsyncTask<String, Long, String> {

        private ProgressDialog nDialog;

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
            nDialog = new ProgressDialog(getActivity());
            nDialog.setTitle("Actualizando partidas");
            nDialog.setMessage("Descargando...");
            nDialog.setCancelable(false);
            nDialog.show();
        }

        @Override
        protected void onPostExecute(String response) {

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Partida>>() {
            }.getType();
            List<Partida> partidas = gson.fromJson(response, type);

            ActiveAndroid.beginTransaction();
            try {
                int length = partidas.size();
                for (int i = 0; i < length; i++) {
                    Partida p = new Select().from(Partida.class).where("remote_id = " + partidas.get(i).remote_id).executeSingle();

                    if (p != null) {
                        p.nombre = partidas.get(i).remote_id + " " + partidas.get(i).nombre;
                        p.nombre = partidas.get(i).nombre;
                        p.unidad = partidas.get(i).unidad;
                        p.save();
                    } else {
                        p = new Partida();
                        p.remote_id = partidas.get(i).remote_id;
                        p.nombre = partidas.get(i).nombre;
                        p.unidad = partidas.get(i).unidad;
                        p.ranking = 0;
                        p.save();
                    }
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }

            setupRecyclerView(recyclerView);

            nDialog.hide();
        }
    }


}
