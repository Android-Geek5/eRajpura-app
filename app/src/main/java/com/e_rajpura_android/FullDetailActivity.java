package com.e_rajpura_android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.e_rajpura_android.common.Utils;

public class FullDetailActivity extends AppCompatActivity {
    RelativeLayout retryLayout;
    TextView text_decription,text_name,text_address,text_info,text_location,text_opening_hours,text_services,text_email,text_call1,text_call2;
    RatingBar ratingBar;
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
        text_name=(TextView) findViewById(R.id.name);
        text_address=(TextView) findViewById(R.id.address);
        text_info=(TextView) findViewById(R.id.info);
        text_location=(TextView) findViewById(R.id.location);
        text_opening_hours=(TextView) findViewById(R.id.opening_hours);
        text_services=(TextView) findViewById(R.id.services);
        text_email=(TextView) findViewById(R.id.email);
        text_call1=(TextView) findViewById(R.id.call1);
        text_call2=(TextView) findViewById(R.id.call2);
        text_decription=(TextView) findViewById(R.id.description);
        text_decription.setMovementMethod(new ScrollingMovementMethod()); // To make decription text scrollable
    }

    public void retryLayout()
    {
        retryLayout=(RelativeLayout) findViewById(R.id.retryLayout);
        retryLayout.setVisibility(View.VISIBLE);
    }
}
