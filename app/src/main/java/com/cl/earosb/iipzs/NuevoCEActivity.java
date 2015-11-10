package com.cl.earosb.iipzs;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.cl.earosb.iipzs.fragments.NuevoCEFragment;
import com.cl.earosb.iipzs.models.ControlEstandar;
import com.cl.earosb.iipzs.models.Hectometro;

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

        initToolbar(controlEstandar.fecha);

        List<Hectometro> hectometros = controlEstandar.getHectometros();
        initViewPagerAndTabs(hectometros);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_ce);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cont = tabLayout.getTabCount();
                String aux = tabLayout.getTabAt(cont - 1).getText().toString();
                int km = Integer.parseInt(tabLayout.getTabAt(cont - 1).getText().toString()) + 100;

                Hectometro hectometro = new Hectometro();
                hectometro.km_inicio = km;
                hectometro.controlEstandar = controlEstandar;
                hectometro.save();

                addViewPagerAndTabs(km);
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
            pagerAdapter.addFragment(NuevoCEFragment.createInstance(), String.valueOf(h.km_inicio));
        }
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("TAB", "onTabSelected " + tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("TAB", "onTabUnselected " + tab.getText().toString());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("TAB", "onTabReselected " + tab.getText().toString());
            }
        });
    }

    private void addViewPagerAndTabs(int km) {
        pagerAdapter.addFragment(NuevoCEFragment.createInstance(), String.valueOf(km));
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
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
