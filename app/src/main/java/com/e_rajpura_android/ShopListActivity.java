package com.erajpura;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.erajpura.Model.ShopDetail;
import com.erajpura.adapter.ShopListAdapter;
import com.erajpura.common.AppController;
import com.erajpura.common.Global;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopListActivity extends AppCompatActivity {
    TextView toolBarTitle;
    LinearLayout backButton;
    RecyclerView recyclerViewList;
    LinearLayoutManager linearLayoutManager;
    ShopListAdapter shopListAdapter;
    protected Handler handler;

    int catId;
    String catName;

    GsonBuilder gsonBuilder;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        if(getIntent().getExtras()!=null){
            catId=getIntent().getExtras().getInt("catId");
            catName=getIntent().getExtras().getString("catName");
        }

        inflateToolbar();
        inflateLayout();

        getShopsList();
    }

    public void inflateToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBarTitle=(TextView) toolbar.findViewById(R.id.toolbar_title);
        backButton=(LinearLayout) toolbar.findViewById(R.id.imageView_back);

        setSupportActionBar(toolbar);
        toolBarTitle.setText(catName);
        backButton.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public void inflateLayout()
    {

        gsonBuilder = new GsonBuilder();

        gson = gsonBuilder.create();

        recyclerViewList=(RecyclerView)findViewById(R.id.recyclerViewSearch);
        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        handler=new Handler();

     /*   shopListAdapter.setOnLoadMoreListener(new ShopListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                recyclerViewList.post(new Runnable() {
                    @Override
                    public void run() {
                        searchResultsList.add(null);
                        shopListAdapter.notifyItemInserted(searchResultsList.size() - 1);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //   remove progress item
                                searchResultsList.remove(searchResultsList.size() - 1);
                                shopListAdapter.notifyItemRemoved(searchResultsList.size());
                                //add items one by one
                                int start = searchResultsList.size();
                                int end = start + 7;

                                for (int i = start + 1; i <= end; i++) {
                                    searchResultsList.add(new SearchResults());
                                    shopListAdapter.notifyItemInserted(searchResultsList.size());
                                }
                                shopListAdapter.setLoaded();
                                //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
                            }
                        }, 2000);// a method which requests remote data
                    }
                });
                //Calling loadMore function in Runnable to fix the
                // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
            }
        });*/
    }


   /* public void addSearchList()
    {
        for(int i=0;i<7;i++)
        {
            searchResultsList.add(new SearchResults());
        }
        shopListAdapter.addTopSearchArrayList(searchResultsList);
        shopListAdapter.notifyDataSetChanged();
    }*/
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main, menu);
       MenuItem item=menu.findItem(R.id.action_search);
       MenuItem item1=menu.findItem(R.id.action_search1);
       item1.setVisible(false);
       item.setVisible(false);

       return true;
   }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ShopListActivity.this.finish();
    }
    public void getShopsList() {

        try {
            final ProgressDialog pDialog = new ProgressDialog(ShopListActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();

            String LOGIN_URL= Global.BASE_URL + Global.API_SHOPS_BY_CATEGORIES;
            StringRequest sr = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    Log.e("Responce",response);
                    try{
                        JSONObject obj=new JSONObject(response);
                        //JSONObject dataobj=new JSONObject(obj.getString("data"));

                        List<ShopDetail> shopsList = Arrays.asList(gson.fromJson(obj.getString("data"), ShopDetail[].class));
                        shopListAdapter =new ShopListAdapter(ShopListActivity.this,shopsList,recyclerViewList);
                        recyclerViewList.setAdapter(shopListAdapter);

                    }catch (Exception e){e.printStackTrace();}
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(ShopListActivity.this, "No Internet Connection",
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
                    params.put("category_id", catId+"");
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(sr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   /* public void doSearch(String text)
    {
        searchResultsList.clear();
        for(int i=0;i<2;i++)
        {
            searchResultsList.add(new SearchResults());
        }
        shopListAdapter.addTopSearchArrayList(searchResultsList);
        // HERE SET NULL LISTENER AFTER SEARCH FOR NOW
        shopListAdapter.setOnLoadMoreListener(null);
        shopListAdapter.notifyDataSetChanged();
    }*/
}
