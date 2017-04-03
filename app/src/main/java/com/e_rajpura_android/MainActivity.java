package com.e_rajpura_android;

import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.e_rajpura_android.Fragment.AboutUsFragment;
import com.e_rajpura_android.Fragment.AdvertiseFragment;
import com.e_rajpura_android.Fragment.CategoryFragment;
import com.e_rajpura_android.Fragment.ContentFragment;
import com.e_rajpura_android.Fragment.HomeFragment;
import com.e_rajpura_android.Fragment.SearchFragment;
import com.e_rajpura_android.common.Fragments;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class MainActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener,
        HomeFragment.OnFragmentInteractionListener,CategoryFragment.OnFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener,AdvertiseFragment.OnFragmentInteractionListener,AboutUsFragment.OnFragmentInteractionListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ContentFragment contentFragment;
    private HomeFragment homeFragment;
    private ViewAnimator viewAnimator;
    private int res = R.mipmap.ic_launcher;
    private LinearLayout linearLayout;
    public static TextView toolBarTitle;
    private Fragment currentFragment;
    LinearLayout rightToolbarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment = HomeFragment.newInstance("param1", "param2");
        contentFragment = ContentFragment.newInstance(R.mipmap.ic_launcher);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, homeFragment, Fragments.HOME)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });
        currentFragment = homeFragment;

        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, homeFragment, drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(Fragments.CLOSE, R.drawable.cancel);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(Fragments.HOME, R.drawable.home);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(Fragments.ADVERTISE, R.drawable.advertise);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(Fragments.CATEGORIES, R.drawable.category);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(Fragments.OFFERS, R.drawable.offers_slide);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(Fragments.ABOUTUS, R.drawable.about_us);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(Fragments.SEARCH, R.drawable.search_slide);
        list.add(menuItem6);
    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        rightToolbarLayout=(LinearLayout) toolbar.findViewById(R.id.imageView_back2);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        MenuItemCompat.setOnActionExpandListener(searchViewItem,
                new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new SearchFragment());
                transaction.addToBackStack(null);
                transaction.commit();// Do whatever you need
                return true; // KEEP IT TO TRUE OR IT DOESN'T OPEN !!
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                //onBackPressed();
                return true; // OR FALSE IF YOU DIDN'T WANT IT TO CLOSE!
            }
        });
        return true;
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.mipmap.ic_launcher ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
       /* SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
*/
        //  findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        //animator.start();
        ContentFragment contentFragment = ContentFragment.newInstance(this.res);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case Fragments.CLOSE:
                return screenShotable;
            case Fragments.HOME:
                toolBarTitle.setText(Fragments.HOME);
                rightToolbarLayout.setVisibility(View.GONE);
                HomeFragment homeFragment = HomeFragment.newInstance("param1", "param2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, homeFragment, Fragments.HOME).commit();
                currentFragment = homeFragment;
                return homeFragment;
            case Fragments.CATEGORIES:
                toolBarTitle.setText(Fragments.CATEGORIES);
                rightToolbarLayout.setVisibility(View.GONE);
                CategoryFragment categoryFragment = CategoryFragment.newInstance("param1", "param2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, categoryFragment, Fragments.CATEGORIES).commit();
                currentFragment = categoryFragment;
                return categoryFragment;
            case Fragments.SEARCH:
                toolBarTitle.setText(Fragments.SEARCH);
                rightToolbarLayout.setVisibility(View.GONE);
                SearchFragment searchFragment = SearchFragment.newInstance("param1", "param2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, searchFragment, Fragments.SEARCH).commit();
                currentFragment = searchFragment;
                return searchFragment;
            case Fragments.ABOUTUS:
                toolBarTitle.setText(Fragments.ABOUTUS);
                rightToolbarLayout.setVisibility(View.VISIBLE);
                AboutUsFragment aboutUsFragment = AboutUsFragment.newInstance("param1", "param2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, aboutUsFragment, Fragments.ABOUTUS).commit();
                currentFragment = aboutUsFragment;
                return aboutUsFragment;
            case Fragments.ADVERTISE:
                toolBarTitle.setText(Fragments.ADVERTISE);
                rightToolbarLayout.setVisibility(View.VISIBLE);
                AdvertiseFragment advertiseFragment = AdvertiseFragment.newInstance("param1", "param2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, advertiseFragment, Fragments.ADVERTISE).commit();
                currentFragment = advertiseFragment;
                return advertiseFragment;
            default:
                return replaceFragment(screenShotable, position);
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void doSearch(String text) {
        if (text != null && !text.isEmpty()) {
            if (currentFragment != null) {
                    Fragment reference = null;
                    List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                    for (int i = 0; i < fragmentList.size(); i++) {
                        reference = fragmentList.get(i);
                        if(reference!=null)
                        if (reference instanceof HomeFragment && currentFragment.getTag().equals(Fragments.HOME) ) {
                            ((HomeFragment) reference).doSearch(text);
                        }
                        if (reference instanceof SearchFragment && currentFragment.getTag().equals(Fragments.SEARCH)) {
                            ((SearchFragment) reference).doSearch(text);
                        }
                        if (reference instanceof CategoryFragment && currentFragment.getTag().equals(Fragments.CATEGORIES)) {
                            ((CategoryFragment) reference).doSearch(text);
                        }
                    }
            }
        }
    }

    public void replaceFragmentCode(Fragment fromFragment, Fragment toFragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, toFragment, tag);
        transaction.hide(fromFragment);
        transaction.addToBackStack(toFragment.getClass().getName());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("AA","AA");

    }

    public void closeSearch() {
        if (currentFragment != null) {
            Fragment reference = null;
            List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
            for (int i = 0; i < fragmentList.size(); i++) {
                reference = fragmentList.get(i);
                if(reference!=null)
                    if (reference instanceof HomeFragment && currentFragment.getTag().equals(Fragments.HOME) ) {
                        ((HomeFragment) reference).closeSearch();
                    }
                if (reference instanceof SearchFragment && currentFragment.getTag().equals(Fragments.SEARCH)) {
                    ((SearchFragment) reference).closeSearch();
                }
                if (reference instanceof CategoryFragment && currentFragment.getTag().equals(Fragments.CATEGORIES)) {
                    ((CategoryFragment) reference).closeSearch();
                }
            }
        }
    }
    private void apiCheck(){

    }

}