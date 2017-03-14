package com.e_rajpura_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.e_rajpura_android.common.Utils;

public class SplashScreenActivity extends AppCompatActivity {
    RelativeLayout retryLayout;
    public static final int SPLASH_DISPLAY_LENGTH=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handlerForSplash();
        inflateLayout();
        if(!Utils.isConnectingToInternet(SplashScreenActivity.this))  //If no internet connection,then show layout
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

  public void handlerForSplash()
  {
      new Handler().postDelayed(new Runnable(){
          @Override
          public void run() {
                /* Create an Intent that will start the Menu-Activity. */
              Intent mainIntent = new Intent(SplashScreenActivity.this,MainActivity.class);
              SplashScreenActivity.this.startActivity(mainIntent);
              SplashScreenActivity.this.finish();
          }
      }, SPLASH_DISPLAY_LENGTH);
  }
}
