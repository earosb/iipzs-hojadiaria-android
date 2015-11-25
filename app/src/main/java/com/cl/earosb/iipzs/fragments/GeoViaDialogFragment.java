package com.cl.earosb.iipzs.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.cl.earosb.iipzs.R;
import com.cl.earosb.iipzs.models.ControlEstandar;
import com.cl.earosb.iipzs.models.GeoVia;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by earosb on 24-11-15.
 * Geometría de la vía
 * TP = Trocha y Peralte
 */
public class GeoViaDialogFragment extends DialogFragment implements AdapterView.OnItemLongClickListener {

    List<Item> listitems = new ArrayList<>();
    ArrayAdapter<Item> mAdapter;

    ListView mylist;
    EditText mTrocha;
    EditText mPeralte;

    long ceId;
    int kmHec;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_geovia, container, false);
        mylist = (ListView) view.findViewById(R.id.dialog_geovia_list);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        mTrocha = (EditText) view.findViewById(R.id.dialog_geovia_trocha);
        mPeralte = (EditText) view.findViewById(R.id.dialog_geovia_peralte);

        Bundle args = getArguments();
        ceId = args.getLong("ce_id");
        kmHec = args.getInt("km_hec");

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_tp_save:
                        attemptForm();
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

        ControlEstandar controlEstandar = ControlEstandar.load(ControlEstandar.class, ceId);
        List<GeoVia> geoViaList = controlEstandar.getGeo();

        for (GeoVia geoVia : geoViaList) {
            listitems.add(new Item(geoVia));
        }

        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listitems);

        mylist.setAdapter(mAdapter);

        mylist.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Item item = listitems.get(i);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("¿Eliminar " + item.toString() + "?");
        builder.setPositiveButton(R.string.action_agree, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GeoVia.delete(GeoVia.class, item.id);
                mAdapter.remove(item);
                mAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.action_cancel, null);
        builder.show();
        return true;
    }

    /**
     * Valida el formulario
     */
    private void attemptForm() {
        mTrocha.setError(null);
        mPeralte.setError(null);

        String trocha = mTrocha.getText().toString();
        String peralte = mPeralte.getText().toString();

        Boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(trocha) && !isNumeric(trocha)) {
            mTrocha.setError("Trocha inválida");
            focusView = mTrocha;
            cancel = true;
        } else if (TextUtils.isEmpty(peralte) && !isNumeric(peralte)) {
            mPeralte.setError("Peralte inválido");
            focusView = mPeralte;
            cancel = true;
        }

        if (cancel)
            focusView.requestFocus();
        else
            createGeoVia();
    }

    /**
     * Crea el objeto GeoVia
     */
    private void createGeoVia() {
        ControlEstandar controlEstandar = ControlEstandar.load(ControlEstandar.class, ceId);
        GeoVia geoVia = new GeoVia();
        geoVia.controlEstandar = controlEstandar;
        geoVia.km = kmHec;
        geoVia.trocha = Float.parseFloat(mTrocha.getText().toString());
        geoVia.peralte = Float.parseFloat(mPeralte.getText().toString());
        geoVia.save();

        // Limpia los EditTexts
        mTrocha.getText().clear();
        mPeralte.getText().clear();

        mTrocha.clearFocus();
        mPeralte.clearFocus();

        // Ocultra el teclado virtual
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mTrocha.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        Item item = new Item(geoVia);
        mAdapter.add(item);
        mAdapter.notifyDataSetChanged();
    }

    private boolean isNumeric(String str) {
        return !TextUtils.isEmpty(str) && str.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * Objeto estático para ahorrarse un CustomAdapter
     */
    public static class Item {
        public final long id;
        public final String content;

        public Item(GeoVia g) {
            this.id = g.getId();
            this.content = "Km: " + g.km + "  T:" + g.trocha + "  P:" + g.peralte;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
