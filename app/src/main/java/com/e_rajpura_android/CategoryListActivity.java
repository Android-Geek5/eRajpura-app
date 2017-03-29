package com.e_rajpura_android;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.e_rajpura_android.Fragment.CategoryFragment;
import com.e_rajpura_android.common.Fragments;

public class CategoryListActivity extends AppCompatActivity implements CategoryFragment.OnFragmentInteractionListener{
    LinearLayout rightToolbarLayout,leftToolbarLayout;
    TextView toolBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        inflateLayout();
        setFragment();


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return false; // Return false to hide menu
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CategoryListActivity.this.finish();
    }

    public void inflateLayout()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarTitle=(TextView) toolbar.findViewById(R.id.toolbar_title);
        leftToolbarLayout=(LinearLayout) toolbar.findViewById(R.id.imageView_back);
        rightToolbarLayout=(LinearLayout) toolbar.findViewById(R.id.imageView_back2);

        toolBarTitle.setText(Fragments.CATEGORIES);
        rightToolbarLayout.setVisibility(View.VISIBLE);
        leftToolbarLayout.setVisibility(View.VISIBLE);

        leftToolbarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void setFragment()
    {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, new CategoryFragment());
        fragmentTransaction.commit();
    }

}
