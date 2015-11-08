package com.cl.earosb.iipzs;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.cl.earosb.iipzs.model.Partida;
import com.cl.earosb.iipzs.adapters.PartidaAdapter;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 */
public class PartidaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static PartidaFragment newInstance(String param1, String param2) {
        PartidaFragment fragment = new PartidaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PartidaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mAdapter = new PartidaAdapter(getActivity(), Partida.getAll());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partida, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        // ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        mListView.setAdapter(mAdapter);

        return view;
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

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
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
                        p.cont = 0;
                        p.save();
                    }
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }

            mListView.invalidateViews();

            nDialog.hide();
        }
    }


}
