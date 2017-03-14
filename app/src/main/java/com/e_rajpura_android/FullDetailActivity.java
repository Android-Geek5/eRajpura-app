package com.e_rajpura_android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.e_rajpura_android.common.Utils;

public class FullDetailActivity extends AppCompatActivity {
    RelativeLayout retryLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_detail);
        inflateLayout();
        if(!Utils.isConnectingToInternet(FullDetailActivity.this))  //If no internet connection,then show layout
            retryLayout();

    }

    public void inflateLayout()
    {

    }

    public void retryLayout()
    {
        retryLayout=(RelativeLayout) findViewById(R.id.retryLayout);
        retryLayout.setVisibility(View.VISIBLE);
    }
}
