package com.erajpura;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.erajpura.common.Utils;

public class SplashScreenActivity extends AppCompatActivity {
    RelativeLayout retryLayout;
    ImageView splash_scooter;
    Animation an;
    public static final int SPLASH_DISPLAY_LENGTH=2200;
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
        splash_scooter=(ImageView)findViewById(R.id.splash_scooter);
        an= AnimationUtils.loadAnimation(this,R.anim.splash);
        splash_scooter.startAnimation(an);
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
              if(Utils.isConnectingToInternet(SplashScreenActivity.this)){
                  Intent mainIntent = new Intent(SplashScreenActivity.this,MainActivity_New.class);
                  SplashScreenActivity.this.startActivity(mainIntent);
                  SplashScreenActivity.this.finish();
              }
              else{
                  dialogue();
              }

          }
      }, SPLASH_DISPLAY_LENGTH);
  }
  private void dialogue(){
      final Dialog dialog=new Dialog(SplashScreenActivity.this);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
      dialog.setContentView(R.layout.internet_dialogue);
      Button okbutton=(Button)dialog.findViewById(R.id.internet_dialogue_ok);
      Button cancel=(Button)dialog.findViewById(R.id.internet_dialogue_exit);

      okbutton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(Utils.isConnectingToInternet(SplashScreenActivity.this)){
                  Intent mainIntent = new Intent(SplashScreenActivity.this,MainActivity_New.class);
                  SplashScreenActivity.this.startActivity(mainIntent);
                  SplashScreenActivity.this.finish();
              }
          }
      });
      cancel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            dialog.dismiss();
              finish();
          }
      });
        dialog.show();
  }
}
