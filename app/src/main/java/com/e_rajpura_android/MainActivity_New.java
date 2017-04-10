package com.e_rajpura_android;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.e_rajpura_android.adapter.TabsPagerAdapter;


public class MainActivity_New extends AppCompatActivity {
    private LinearLayout linearLayout;
    public TextView toolBarTitle;
    private BottomNavigationView bottomNavigation;

    private FragmentManager fragmentManager;
    ViewPager viewPager;
    MenuItem prevMenuItem;
    boolean b=true;

    public static boolean homeApiFT=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout_2);
        overridePendingTransition(R.anim.act_fade_in,R.anim.act_fade_out);
        setActionBar();
        init();
        setViewPager();
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

private void init(){
    bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);

    bottomNavigation.inflateMenu(R.menu.menu_bottom_navigation);

    fragmentManager = getSupportFragmentManager();

    viewPager=(ViewPager)findViewById(R.id.mainviewpager);

    bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            switch (id){
                case R.id.action_favorites:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.action_schedules:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.action_music:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.action_advertise:
                    viewPager.setCurrentItem(3);
                    break;
                case R.id.action_folder:
                    viewPager.setCurrentItem(4);
                    break;
            }
            return true;
        }
    });
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item=menu.findItem(R.id.action_search);
        MenuItem item1=menu.findItem(R.id.action_search1);
        item1.setVisible(false);
       item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               viewPager.setCurrentItem(4);
               bottomNavigation.getMenu().getItem(4).setChecked(true);
               return true;
           }
       });
        return true;
    }

    private void setViewPager(){
        TabsPagerAdapter mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigation.getMenu().getItem(0).setChecked(false);
                }
                if(position==0){toolBarTitle.setText("     Home");}else if(position==1){toolBarTitle.setText("   Category");}
                else if(position==2){toolBarTitle.setText("About Us");}else if(position==3){toolBarTitle.setText("Advertise with Us");}
                else if(position==4){toolBarTitle.setText("   Search");}

                bottomNavigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigation.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 4000);
    }
}