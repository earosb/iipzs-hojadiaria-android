package com.cl.earosb.iipzs.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cl.earosb.iipzs.R;
import com.cl.earosb.iipzs.adapters.NuevoCERecyclerAdapter;
import com.cl.earosb.iipzs.models.Partida;

/**
 * Created by earosb on 07-11-15.
 */
public class NuevoCEFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    private int columnsGridNumber;

    public static NuevoCEFragment createInstance() {
        NuevoCEFragment ceFragment = new NuevoCEFragment();
        return ceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_nuevo_ce, container, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        // register preference change listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        // and set remembered preferences
        columnsGridNumber = Integer.parseInt(sharedPreferences.getString("columns_grid_number", "2"));

        setupRecyclerView(recyclerView);

        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnsGridNumber));
        NuevoCERecyclerAdapter recyclerAdapter = new NuevoCERecyclerAdapter(Partida.getAll());
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals("columns_grid_number")) {
            columnsGridNumber = Integer.parseInt(sharedPreferences.getString("columns_grid_number", "2"));
        }
    }
}
