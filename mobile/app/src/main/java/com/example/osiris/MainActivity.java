package com.example.osiris;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.osiris.Class.ViewPagerAdapter;
import com.example.osiris.Listagens.ListaAgendamentosActivity;
import com.example.osiris.ui.agendamentos.AgendamentosFragment;
import com.example.osiris.ui.dados.DadosFragment;
import com.example.osiris.ui.dashboard.DashboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    String deviceId = "";
    private ViewPager mViewPager;
//    private SectionsPagerAdapter mSectionsPagerAdapter;
    BottomNavigationView mBottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        //recebendo o deviceID
        Intent intent = getIntent();
        deviceId = intent.getStringExtra("deviceId");

//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        mBottomNavigation = findViewById(R.id.nav_view);
//        mBottomNavigation.setOnNavigationItemSelectedListener(this);

        // Set up the ViewPager with the sections adapter.
//        mViewPager = findViewById(R.id.viewPager);
//        mViewPager.setAdapter(mSectionsPagerAdapter);

//        viewPager = findViewById(R.id.view_pager);
//        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_dados, R.id.navigation_agendamentos, R.id.navigation_dashboard).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            public void onPageScrollStateChanged(int state) {}
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
//
//            public void onPageSelected(int position) {
//                mBottomNavigation.getMenu().getItem(position).setChecked(true);
//            }
//        });
//
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.navigation_dados:
//                mViewPager.setCurrentItem(0);
//                return true;
//            case R.id.navigation_agendamentos:
//                mViewPager.setCurrentItem(1);
//                return true;
//            case R.id.navigation_dashboard:
//                mViewPager.setCurrentItem(2);
//                return true;
//            default:
//                return false;
//        }
//
//    }
//
//    public class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//        public SectionsPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            try{
//                switch (position) {
//                    case 0:
//                        //JogosFragment tab1 = new JogosFragment();
//                        DadosFragment tab1 = new DadosFragment();
//                        return tab1;
//                    case 1:
//                        AgendamentosFragment tab2 = new AgendamentosFragment();
//                        return tab2;
//                    case 2:
//                        DashboardFragment tab3 = new DashboardFragment();
//                        return tab3;
//                    //case 3:
//                    //    MeusDadosFragment tab4 = new MeusDadosFragment();
//                    //    return tab4;
//                    //case 3:
//                    //Tab4Captura tab4 = new Tab4Captura();
//                    //return tab4;
//
//                    default:
//                        return null;
//                }
//            }catch (Exception ex){
//                return null;
//            }
//
//        }
//
//        @Override
//        public int getCount() {
//            // Show 3 total pages.
//            return 3;
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //seleciona o menu em cima, da direita
        switch (item.getItemId()){
            case R.id.menu_agendamentos :
                Intent agendamentos = new Intent(getApplicationContext(), ListaAgendamentosActivity.class);
                agendamentos.putExtra("deviceId", deviceId);
                startActivity(agendamentos);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}