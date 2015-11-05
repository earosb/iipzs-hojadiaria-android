package com.cl.earosb.iipzs;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cl.earosb.iipzs.model.ControlEstandar;
import com.cl.earosb.iipzs.model.ControlEstandarAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ControlEstandarFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ControlEstandarAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static ControlEstandarFragment newInstance(String param1, String param2) {
        ControlEstandarFragment fragment = new ControlEstandarFragment();
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
    public ControlEstandarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_estandar, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mAdapter = new ControlEstandarAdapter(getActivity());

        List<ControlEstandar> list = ControlEstandar.getAll();
        mAdapter.addAll(ControlEstandar.getAll());

        setListAdapter(mAdapter);
    }

}
