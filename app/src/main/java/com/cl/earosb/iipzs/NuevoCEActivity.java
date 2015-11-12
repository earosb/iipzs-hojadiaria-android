package com.cl.earosb.iipzs;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.activeandroid.ActiveAndroid;
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
public class NuevoCEActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ce);

        Bundle b = getIntent().getExtras();
        long ceId = b.getLong("ce_id");

        final ControlEstandar controlEstandar = ControlEstandar.load(ControlEstandar.class, ceId);

        initToolbar(controlEstandar.fecha_title);

        List<Hectometro> hectometros = controlEstandar.getHectometros();

        initViewPagerAndTabs(hectometros);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_ce);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cont = tabLayout.getTabCount();
                String aux = tabLayout.getTabAt(cont - 1).getText().toString();
                int km = Integer.parseInt(tabLayout.getTabAt(cont - 1).getText().toString()) + 100;

                ActiveAndroid.beginTransaction();
                try {
                    Hectometro hectometro = new Hectometro();
                    hectometro.km_inicio = km;
                    hectometro.controlEstandar = controlEstandar;
                    hectometro.save();

                    List<Partida> partidas = Partida.getAll();

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


                tabLayout.getTabAt(cont).select();
            }
        });

    }

    private void initToolbar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getTitle().toString() + title);
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
