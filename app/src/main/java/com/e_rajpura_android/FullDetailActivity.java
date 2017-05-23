package com.erajpura;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.erajpura.Model.ShopDetail;
import com.erajpura.adapter.OffersAdapter;
import com.erajpura.common.AppController;
import com.erajpura.common.Global;
import com.erajpura.common.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullDetailActivity extends AppCompatActivity implements View.OnClickListener{
    RelativeLayout retryLayout;
    TextView text_decription,text_name,text_address,text_info,text_location,text_opening_hours,text_services,text_email,
            text_call,text_call1,text_call2;
    RatingBar ratingBar;
    LinearLayout emailLayout,callLayout,callLayout1,callLayout2;
    RecyclerView recyclerViewOffers;
    LinearLayoutManager linearLayoutManager;
    List<String> currentOffersList=new ArrayList<>();
    OffersAdapter offersAdapter;
    LinearLayout offerLayout,detaillayout;
    ImageView backpress,mainimage;

    int shopId;
    ShopDetail shopDetail;
    GsonBuilder gsonBuilder;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_detail);

        shopDetail=(ShopDetail)getIntent().getExtras().getSerializable("shopDetail");

        inflateLayout();

        if(shopDetail==null){
            shopId=getIntent().getExtras().getInt("shopId");

            getShopDetails();
        }
        else{
            setData();
        }
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
        text_call=(TextView) findViewById(R.id.call);
        text_call1=(TextView) findViewById(R.id.call1);
        text_call2=(TextView) findViewById(R.id.call2);
        text_decription=(TextView) findViewById(R.id.description);
        ratingBar=(RatingBar) findViewById(R.id.rating);
        backpress=(ImageView)findViewById(R.id.detail_backpress);
        mainimage=(ImageView)findViewById(R.id.detail_imageView);
        detaillayout=(LinearLayout)findViewById(R.id.detail_layout);


        text_decription.setMovementMethod(new ScrollingMovementMethod()); // To make decription text scrollable

        emailLayout=(LinearLayout) findViewById(R.id.email_layout);
        callLayout=(LinearLayout) findViewById(R.id.call_layout);
        callLayout1=(LinearLayout) findViewById(R.id.call1_layout);
        callLayout2=(LinearLayout) findViewById(R.id.call2_layout);

        emailLayout.setOnClickListener(this);
        callLayout.setOnClickListener(this);
        backpress.setOnClickListener(this);
        callLayout1.setOnClickListener(this);
        callLayout2.setOnClickListener(this);

        offerLayout=(LinearLayout) findViewById(R.id.offer_layout);

        offerLayout.setVisibility(View.GONE);
        emailLayout.setVisibility(View.GONE);
        callLayout1.setVisibility(View.GONE);
        callLayout2.setVisibility(View.GONE);

        recyclerViewOffers=(RecyclerView) findViewById(R.id.recyclerViewOffers);

        linearLayoutManager=new LinearLayoutManager(FullDetailActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerViewOffers.setLayoutManager(linearLayoutManager);
        offersAdapter=new OffersAdapter(FullDetailActivity.this,currentOffersList);
        recyclerViewOffers.setAdapter(offersAdapter);

        gsonBuilder = new GsonBuilder();

        gson = gsonBuilder.create();
    }
    private void setData(){

        ratingBar.setRating(shopDetail.getShop_rating());

        text_name.setText(shopDetail.getShop_name());
        text_location.setText(shopDetail.getShop_address()+" ("+shopDetail.getShop_city_name()+")");
        //text_location.setText(shopDetail.getShop_address());
        text_opening_hours.setText(shopDetail.getShop_timing());

        text_decription.setText(shopDetail.getShop_description());

        Glide.with(this).load(shopDetail.getShop_image()).into(mainimage);

        String serrvicestext="";
        if(shopDetail.getShop_services_name_array()!=null){
            for(int i=0;i<shopDetail.getShop_services_name_array().size();i++){
            serrvicestext=serrvicestext.concat("✓ "+shopDetail.getShop_services_name_array().get(i)).concat("\n");
        }
        }
        text_services.setText(serrvicestext);

        if(!shopDetail.getShop_email().equals("")){
            text_email.setText(shopDetail.getShop_email());
            emailLayout.setVisibility(View.VISIBLE);
        }

        if(!shopDetail.getShop_contact_1().equals("")){
            text_call1.setText(shopDetail.getShop_contact_1());
            callLayout1.setVisibility(View.VISIBLE);
        }

        if(!shopDetail.getShop_contact_2().equals("")){
            text_call2.setText(shopDetail.getShop_contact_2());
            callLayout2.setVisibility(View.VISIBLE);
        }

        setRating();
    }

    public void retryLayout()
    {
        retryLayout=(RelativeLayout) findViewById(R.id.retryLayout);
        retryLayout.setVisibility(View.VISIBLE);
    }

    public void setRating()
    {
        LayerDrawable stars = (LayerDrawable)ratingBar.getProgressDrawable();

        // No star
        DrawableCompat.setTint(DrawableCompat.wrap(stars.getDrawable(0)),
                ContextCompat.getColor(getApplicationContext(),
                        R.color.colorAccent));

        // Partial star
        DrawableCompat.setTint(DrawableCompat.wrap(stars.getDrawable(1)),
                ContextCompat.getColor(getApplicationContext(),
                        // use background_dark instead of colorAccent
                        // R.color.colorAccent));
                        R.color.colorAccent));

        // Custom star
        DrawableCompat.setTint(DrawableCompat.wrap(stars.getDrawable(2)),
                ContextCompat.getColor(getApplicationContext(),
                        R.color.colorRating));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.email_layout:
                dialog(true,text_email.getText().toString());
                break;
            case R.id.call_layout:
                if(text_call1.getText().toString().length()>0){
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    String temp = "tel:"+text_call1.getText().toString();
                    intent.setData(Uri.parse(temp));
                    startActivity(intent);
                }
                else if(text_call2.getText().toString().length()>0){
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    String temp = "tel:"+text_call2.getText().toString();
                    intent.setData(Uri.parse(temp));
                    startActivity(intent);
                    dialog(false,text_call2.getText().toString());
                }
                else{
                    callLayout.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.call1_layout:
                dialog(false,text_call1.getText().toString());
                break;
            case R.id.call2_layout:
                dialog(false,text_call2.getText().toString());
                break;
            case R.id.detail_backpress:
                onBackPressed();
                break;
        }
    }
    public void getShopDetails() {

        try {

            final ProgressDialog pDialog = new ProgressDialog(FullDetailActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();

            detaillayout.setVisibility(View.INVISIBLE);
            String LOGIN_URL= Global.BASE_URL + Global.API_SHOP_BY_SHOPID;
            StringRequest sr = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    detaillayout.setVisibility(View.VISIBLE);
                    pDialog.dismiss();
                    Log.e("shop_id",response);
                    try{
                        JSONObject obj=new JSONObject(response);
                        if(obj.getInt("code")==1){
                           ;
                            shopDetail= gson.fromJson(obj.getString("data"), ShopDetail.class);

                            setData();

                        }
                    }catch (Exception e){e.printStackTrace();}
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(FullDetailActivity.this, "No Internet Connection",
                                Toast.LENGTH_LONG).show();
                    } else  {
                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
                    }
                }
            }
            )

            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("shop_id", ""+shopId);
                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(sr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void dialog(boolean mailOrNot, final String detail)
    {
        final Dialog dialog = new Dialog(FullDetailActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_contact_us);
        TextView txtContact=(TextView)dialog.findViewById(R.id.contact);
        TextView txtMobile=(TextView)dialog.findViewById(R.id.textView_mobile);
        TextView txtEmail=(TextView)dialog.findViewById(R.id.textView_email);
        TextView txtCancel=(TextView)dialog.findViewById(R.id.textView_cancel);

        if(mailOrNot)
        {
            txtEmail.setText(detail);
            txtContact.setText("Contact Us");
            txtMobile.setVisibility(View.GONE);
            txtEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { detail });
                    startActivity(Intent.createChooser(intent, ""));
                }
            });
        }
        else
        {
            txtMobile.setText(detail);
            txtContact.setText("Call Us");
            txtEmail.setVisibility(View.GONE);
            txtMobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    String temp = "tel:"+detail;
                    intent.setData(Uri.parse(temp));
                    startActivity(intent);
                }
            });
        }


        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
