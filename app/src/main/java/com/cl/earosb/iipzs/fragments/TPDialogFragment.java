package com.cl.earosb.iipzs.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cl.earosb.iipzs.R;

/**
 * Created by earosb on 24-11-15.
 * TP = Trocha y Peralte
 */
public class TPDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener {

    String[] listitems = {"item01", "item02", "item03", "item04", "item05", "item06", "item07", "item08", "item09", "item10", "item11", "item12", "item13"};

    ListView mylist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_tp, container, false);
        mylist = (ListView) view.findViewById(R.id.dialog_tp_list);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        String title = "Trocha y Peralte";

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the menu item
                return true;
            }
        });
        toolbar.inflateMenu(R.menu.dialog_fragment_tp);
        toolbar.setTitle(title);

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, listitems);

        mylist.setAdapter(adapter);

        mylist.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        dismiss();
        Toast.makeText(getActivity(), listitems[i], Toast.LENGTH_SHORT).show();
    }
}
