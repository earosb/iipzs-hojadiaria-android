package com.cl.earosb.iipzs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cl.earosb.iipzs.R;
import com.cl.earosb.iipzs.adapters.NuevoCERecyclerAdapter;
import com.cl.earosb.iipzs.models.Partida;

/**
 * Created by earosb on 07-11-15.
 */
public class NuevoCEFragment extends Fragment {

    public static NuevoCEFragment createInstance() {
        NuevoCEFragment ceFragment = new NuevoCEFragment();
        return ceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_nuevo_ce, container, false);
        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        NuevoCERecyclerAdapter recyclerAdapter = new NuevoCERecyclerAdapter(Partida.getAll());
        recyclerView.setAdapter(recyclerAdapter);
    }

}
