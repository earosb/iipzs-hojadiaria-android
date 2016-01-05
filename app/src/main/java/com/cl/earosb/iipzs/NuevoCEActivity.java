package com.cl.earosb.iipzs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.activeandroid.ActiveAndroid;
import com.cl.earosb.iipzs.fragments.GeoViaDialogFragment;
import com.cl.earosb.iipzs.fragments.NuevoCEFragment;
import com.cl.earosb.iipzs.models.ControlEstandar;
import com.cl.earosb.iipzs.models.Hectometro;
import com.cl.earosb.iipzs.models.Partida;
import com.cl.earosb.iipzs.models.Trabajo;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity para Nuevo Control de Estandar
 */
public class NuevoCEActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String LOG_TAG = "NuevoCEActivity";

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    private ControlEstandar controlEstandar;
    private boolean editable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ce);

        Bundle b = getIntent().getExtras();
        long ceId = b.getLong("ce_id");

        controlEstandar = ControlEstandar.load(ControlEstandar.class, ceId);
        editable = !controlEstandar.sync;
        String title = controlEstandar.causa + " " + controlEstandar.fecha;
        List<Hectometro> hectometros = controlEstandar.getHectometros();

        initToolbar(title);
        initFabButtons();
        initViewPagerAndTabs(hectometros);

    }

    private void initToolbar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(title);
    }

    private void initFabButtons() {
        FloatingActionButton fabCe = (FloatingActionButton) findViewById(R.id.fab_ce);
        if (editable)
            fabCe.setOnClickListener(this);
        else
            fabCe.hide();
        FloatingActionButton fabTp = (FloatingActionButton) findViewById(R.id.fab_ce_tp);
        fabTp.setOnClickListener(this);
    }

    private void initViewPagerAndTabs(List<Hectometro> hectometros) {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        for (Hectometro h : hectometros) {
            pagerAdapter.addFragment(NuevoCEFragment.createInstance(), String.valueOf(h.km_inicio), h.getId());
        }
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void addViewPagerAndTabs(Hectometro h) {
        pagerAdapter.addFragment(NuevoCEFragment.createInstance(), String.valueOf(h.km_inicio), h.getId());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Crea el Hectómetro con la lista de partidas
     *
     * @param km
     */
    private void createHectometro(int km) {
        ActiveAndroid.beginTransaction();
        try {
            Hectometro hectometro = new Hectometro();
            hectometro.km_inicio = km;
            hectometro.controlEstandar = controlEstandar;
            hectometro.save();

            List<Partida> partidas = Partida.getAll("ranking DESC");

            for (Partida partida : partidas) {
                Trabajo trabajo = new Trabajo();
                trabajo.hectometro = hectometro;
                trabajo.partida = partida;
                trabajo.cantidad = 0;
                trabajo.observaciones = "";
                trabajo.save();
            }
            addViewPagerAndTabs(hectometro);

            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    @Override
    public void onClick(View view) {
        int km, id = view.getId();
        switch (id) {
            case R.id.fab_ce:
                int cont = tabLayout.getTabCount();
                km = Integer.parseInt(tabLayout.getTabAt(cont - 1).getText().toString()) + 100;
                createHectometro(km);
                tabLayout.getTabAt(cont).select();
                break;
            case R.id.fab_ce_tp:
                int position = tabLayout.getSelectedTabPosition();
                km = Integer.parseInt(tabLayout.getTabAt(position).getText().toString());
                Bundle args = new Bundle();
                args.putInt("km_hec", km);
                args.putLong("ce_id", controlEstandar.getId());
                FragmentManager manager = getSupportFragmentManager();
                GeoViaDialogFragment dialog = new GeoViaDialogFragment();
                dialog.setArguments(args);
                dialog.show(manager, "dialog");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_nuevo_ce, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (id) {
            case R.id.action_one_row:
                editor.putString("columns_grid_number", "1");
                break;
            case R.id.action_two_row:
                editor.putString("columns_grid_number", "2");
                break;
            case R.id.action_three_row:
                editor.putString("columns_grid_number", "3");
                break;
            case R.id.action_four_row:
                editor.putString("columns_grid_number", "4");
                break;
        }
        editor.apply();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals("columns_grid_number")) {
            int pos = tabLayout.getSelectedTabPosition();
            viewPager.setAdapter(pagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(pos).select();
        }
    }

    static class PagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();
        private final List<Long> fragmentId = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title, long id) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
            fragmentId.add(id);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragmentList.get(position);
            Bundle args = new Bundle();
            args.putLong(NuevoCEFragment.ARG_HOCTOMETRO, fragmentId.get(position));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

}
