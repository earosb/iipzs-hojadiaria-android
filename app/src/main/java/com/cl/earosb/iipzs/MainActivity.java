package com.cl.earosb.iipzs;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.activeandroid.ActiveAndroid;
import com.cl.earosb.iipzs.fragments.CEListFragment;
import com.cl.earosb.iipzs.fragments.PartidasFragment;
import com.cl.earosb.iipzs.fragments.PreferencesFragment;
import com.cl.earosb.iipzs.models.ControlEstandar;
import com.cl.earosb.iipzs.models.Hectometro;
import com.cl.earosb.iipzs.models.Partida;
import com.cl.earosb.iipzs.models.Trabajo;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int mYear, mMonth, mDay;
    private String fechaNuevoCE;
    private int kmInicioNuevoCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                fechaNuevoCE = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                AlertDialog.Builder builderKm_inicio = new AlertDialog.Builder(MainActivity.this);
                                builderKm_inicio.setTitle("Kilómetro de inicio");
                                final EditText input = new EditText(MainActivity.this);
                                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                                builderKm_inicio.setView(input);
                                builderKm_inicio.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        kmInicioNuevoCE = Integer.parseInt(input.getText().toString());

                                        ControlEstandar controlEstandar = new ControlEstandar();
                                        controlEstandar.fecha = fechaNuevoCE;
                                        controlEstandar.km_inicio = kmInicioNuevoCE;
                                        controlEstandar.sync = false;
                                        controlEstandar.save();

                                        Hectometro hectometro = new Hectometro();
                                        hectometro.km_inicio = kmInicioNuevoCE;
                                        hectometro.controlEstandar = controlEstandar;
                                        hectometro.save();

                                        List<Partida> partidas = Partida.getAll();

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
                                });
                                builderKm_inicio.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                builderKm_inicio.show();
                            }
                        }, mYear, mMonth, mDay);
                dpd.setTitle("Fecha Control de Estándar");
                dpd.show();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new CEListFragment()).commit();

        String title = getString(R.string.app_name);
        title = "Control de estándar";
        getSupportActionBar().setTitle(title);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_inspecciones);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /**
         * Comentado para eliminar menu en ActionBar
         */
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        // return true;
        // }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        String title = getString(R.string.app_name);

        switch (id) {
            case R.id.nav_inspecciones:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new CEListFragment()).commit();
                title = "Control de estándar";
                break;
            case R.id.nav_partidas:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new PartidasFragment()).commit();
                title = "Partidas";
                break;
            case R.id.nav_manage:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new PreferencesFragment()).commit();
                title = "Preferencias";
                break;
            case R.id.nav_logout:
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("logueado", false).commit();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }

        getSupportActionBar().setTitle(title);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
