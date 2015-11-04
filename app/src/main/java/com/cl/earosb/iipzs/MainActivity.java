package com.cl.earosb.iipzs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;

import com.cl.earosb.iipzs.model.ControlEstandar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ControlEstandarFragment.OnFragmentInteractionListener, PartidaFragment.OnFragmentInteractionListener {

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
                                Log.d("Fecha: ", dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                fechaNuevoCE = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                AlertDialog.Builder builderKm_inicio = new AlertDialog.Builder(MainActivity.this);
                                builderKm_inicio.setTitle("Kilómetro de inicio");
                                final EditText input = new EditText(MainActivity.this);
                                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                                builderKm_inicio.setView(input);
                                builderKm_inicio.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("Km Inicio: ", input.getText().toString());
                                        kmInicioNuevoCE = Integer.parseInt(input.getText().toString());
                                        Log.d("NuevoCE", fechaNuevoCE + " - " + kmInicioNuevoCE);

                                        ControlEstandar controlEstandar = new ControlEstandar();
                                        controlEstandar.fecha = fechaNuevoCE;
                                        controlEstandar.km_inicio = kmInicioNuevoCE;
                                        controlEstandar.save();
                                        startActivity(new Intent(getApplicationContext(), NuevoCEActivity.class));
                                    }
                                });
                                builderKm_inicio.show();
                            }
                        }, mYear, mMonth, mDay);
                dpd.setTitle("Fecha Control de Estándar");
                dpd.show();
            }
        });

        String title = getString(R.string.app_name);
        Fragment fragment = new ControlEstandarFragment();
        title = "Control de estándar";

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
        getSupportActionBar().setTitle(title);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (id) {
            case R.id.nav_inspecciones:
                fragment = new ControlEstandarFragment();
                title = "Control de estándar";
                break;
            case R.id.nav_partidas:
                fragment = new PartidaFragment();
                title = "Partidas";
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_logout:
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("logueado", false).commit();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        getSupportActionBar().setTitle(title);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String id) {
        Log.v("InspeccionTAG", "ID: " + id);
    }

}
