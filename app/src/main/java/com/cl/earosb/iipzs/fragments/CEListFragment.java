package com.cl.earosb.iipzs.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cl.earosb.iipzs.R;
import com.cl.earosb.iipzs.adapters.CEListAdapter;
import com.cl.earosb.iipzs.models.ControlEstandar;

import java.util.List;

/**
 *
 */
public class CEListFragment extends ListFragment {

    private CEListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ce_list, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new CEListAdapter(getActivity());

        List<ControlEstandar> list = ControlEstandar.getAll();
        mAdapter.addAll(ControlEstandar.getAll());

        setListAdapter(mAdapter);
    }

}
