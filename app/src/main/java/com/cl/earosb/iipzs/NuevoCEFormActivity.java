package com.cl.earosb.iipzs;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.activeandroid.ActiveAndroid;
import com.cl.earosb.iipzs.models.ControlEstandar;
import com.cl.earosb.iipzs.models.Hectometro;
import com.cl.earosb.iipzs.models.Partida;
import com.cl.earosb.iipzs.models.Trabajo;

import java.util.Calendar;
import java.util.List;

public class NuevoCEFormActivity extends AppCompatActivity {

    EditText ceCausa;
    EditText ceKmInicio;
    EditText ceFecha;
    EditText ceObs;

    private int mYear, mMonth, mDay;

    private String fechaTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ceform);

        initToolbar();
        findViewsById();

        ceCausa.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

    }

    /**
     * find views by id po
     */
    private void findViewsById() {
        ceCausa = (EditText) findViewById(R.id.nuevo_ce_form_causa);
        ceKmInicio = (EditText) findViewById(R.id.nuevo_ce_form_km_inicio);
        ceFecha = (EditText) findViewById(R.id.nuevo_ce_form_fecha);
        ceObs = (EditText) findViewById(R.id.nuevo_ce_form_obs);
    }

    /**
     * init Toolbar po
     */
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Método llamado desde activity_nuevo_ceform.xml
     * para desplegar DatePickerDialog
     *
     * @param view
     */
    public void showDatePicker(View view) {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(NuevoCEFormActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, final int monthOfYear, int dayOfMonth) {
                        if (dayOfMonth < 10)
                            ceFecha.setText("0" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        else
                            ceFecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        fechaTitle = dayOfMonth + " " + getResources().getStringArray(R.array.months)[monthOfYear] + " " + year;

                    }
                }, mYear, mMonth, mDay);
        dpd.setTitle(getString(R.string.date_ce));
        dpd.show();
    }

    /**
     * Valida que formulario no esté vacío
     */
    private void attemptForm() {
        ceCausa.setError(null);
        ceKmInicio.setError(null);
        ceFecha.setError(null);

        String causa = ceCausa.getText().toString();
        String kmInicio = ceKmInicio.getText().toString();
        String fecha = ceFecha.getText().toString();

        Boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(causa)) {
            ceCausa.setError("Debe ingresar una causa");
            focusView = ceCausa;
            cancel = true;
        } else if (TextUtils.isEmpty(kmInicio)) {
            ceKmInicio.setError("Debe ingresar un kilómetro");
            focusView = ceKmInicio;
            cancel = true;
        } else if (TextUtils.isEmpty(fecha)) {
            ceFecha.setError("Debe ingresar una fecha");
            focusView = ceFecha;
            cancel = true;
        }

        if (cancel)
            focusView.requestFocus();
        else
            createCE();
    }

    /**
     * Crea Control de Estándar y lanza actividad
     */
    private void createCE() {
        String causa = ceCausa.getText().toString();
        int kmInicio = Integer.parseInt(ceKmInicio.getText().toString());
        String fecha = ceFecha.getText().toString();
        String obs = ceObs.getText().toString();

        ControlEstandar controlEstandar = new ControlEstandar();
        controlEstandar.causa = causa;
        controlEstandar.fecha = fecha;
        controlEstandar.fecha_title = fechaTitle;
        controlEstandar.km_inicio = kmInicio;
        controlEstandar.obs = obs;
        controlEstandar.sync = false;
        controlEstandar.save();

        Hectometro hectometro = new Hectometro();
        hectometro.km_inicio = kmInicio;
        hectometro.controlEstandar = controlEstandar;
        hectometro.save();

        List<Partida> partidas = Partida.getAll("ranking DESC");

        ActiveAndroid.beginTransaction();
        try {
            for (Partida partida : partidas) {
                Trabajo trabajo = new Trabajo();
                trabajo.hectometro = hectometro;
                trabajo.partida = partida;
                trabajo.cantidad = 0;
                trabajo.observaciones = "";
                trabajo.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }

        Intent intent = new Intent(getApplicationContext(), NuevoCEActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("ce_id", controlEstandar.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nuevo_ce_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_nuevo_ce:
                attemptForm();
                break;
            case R.id.action_nuevo_ce_cancel:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
