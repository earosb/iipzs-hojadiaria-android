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
        Log.d("CE", String.valueOf(ceId));

        ControlEstandar controlEstandar = ControlEstandar.load(ControlEstandar.class, ceId);

        Log.d("CE", String.valueOf(controlEstandar.km_inicio));
        Log.d("CE", controlEstandar.fecha);
        Log.d("CE", String.valueOf(controlEstandar.getId()));

        initToolbar(controlEstandar.fecha);
        initViewPagerAndTabs();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_ce);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cont = tabLayout.getTabCount();
                String aux = tabLayout.getTabAt(cont - 1).getText().toString();
                int km = Integer.parseInt(tabLayout.getTabAt(cont - 1).getText().toString()) + 100;
                addViewPagerAndTabs(String.valueOf(km));
            }
        });

    }

    private void initToolbar(String title){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String old = getTitle().toString();
        setTitle(old + ": "+ title);
    }

    private void initViewPagerAndTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(NuevoCEFragment.createInstance(), "546000");
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MyApp", "viewPager CLICK!");
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void addViewPagerAndTabs(String title) {
        pagerAdapter.addFragment(NuevoCEFragment.createInstance(), title);
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
