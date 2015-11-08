package com.cl.earosb.iipzs.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cl.earosb.iipzs.R;
import com.cl.earosb.iipzs.models.ControlEstandar;
import com.cl.earosb.iipzs.adapters.CEListAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CEListFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CEListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static CEListFragment newInstance(String param1, String param2) {
        CEListFragment fragment = new CEListFragment();
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
    public CEListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ce_list, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mAdapter = new CEListAdapter(getActivity());

        List<ControlEstandar> list = ControlEstandar.getAll();
        mAdapter.addAll(ControlEstandar.getAll());

        setListAdapter(mAdapter);
    }

}
