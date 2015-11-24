package com.cl.earosb.iipzs.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
 * Geometría de la vía
 * TP = Trocha y Peralte
 */
public class GeoViaDialogFragment extends DialogFragment implements AdapterView.OnItemLongClickListener {

    private static final String TAG = "TPDialog";

    String[] listitems = {"item01", "item02", "item03", "item04", "item05", "item06", "item07", "item08", "item09", "item10", "item11", "item12", "item13"};

    ListView mylist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_geovia, container, false);
        mylist = (ListView) view.findViewById(R.id.dialog_tp_list);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        Bundle args = getArguments();

        final long ceId = args.getLong("ce_id");
        final int kmHec = args.getInt("km_hec");

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_tp_save:
                        Log.d(TAG, "Guardar!! ");
                        Log.d(TAG, "ce_id " + ceId);
                        Log.d(TAG, "kmHec " + kmHec);
                        break;
                    case R.id.action_tp_cancel:
                        dismiss();
                        break;
                }
                return true;
            }
        });
        toolbar.inflateMenu(R.menu.dialog_fragment_geovia);
        toolbar.setTitle(R.string.geometria_via);

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, listitems);

        mylist.setAdapter(adapter);

        mylist.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("¿Eliminar " + listitems[i] + "?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Eliminado " + listitems[i]);
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
        return true;
    }
}
