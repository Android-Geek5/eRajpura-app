package com.erajpura;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
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
import com.erajpura.common.GridSpacesItemDecoration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    TextView toolBarTitle;
    LinearLayout backButton;


    RecyclerView recyclerViewCategory;
    GridLayoutManager gridLayoutManager;
    ShopListAdapter shopListAdapter;

    String searchString;
    SwipeRefreshLayout swipe;
    GsonBuilder gsonBuilder;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        inflateToolbar();
        inflateLayout();
    }
    public void inflateToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBarTitle=(TextView) toolbar.findViewById(R.id.toolbar_title);
        backButton=(LinearLayout) toolbar.findViewById(R.id.imageView_back);


        setSupportActionBar(toolbar);
        toolBarTitle.setText("Search");
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
        recyclerViewCategory=(RecyclerView)findViewById(R.id.recyclerViewSearch);
        swipe=(SwipeRefreshLayout)findViewById(R.id.swipe);
        gridLayoutManager= new GridLayoutManager(SearchActivity.this,1);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin5);
        recyclerViewCategory.addItemDecoration(new GridSpacesItemDecoration(spacingInPixels,true));
        recyclerViewCategory.setLayoutManager(gridLayoutManager);

        gsonBuilder = new GsonBuilder();

        gson = gsonBuilder.create();
    }
    public void getCategoryList() {

        try {
            swipe.setRefreshing(true);

            String LOGIN_URL= Global.BASE_URL + Global.API_SEARCH;
            StringRequest sr = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    swipe.setRefreshing(false);
                    try{
                        JSONObject obj=new JSONObject(response);
                        if(obj.getInt("code")==1){
                            JSONObject obj1=new JSONObject(obj.getString("data"));

                            List<ShopDetail> shopsList = Arrays.asList(gson.fromJson(obj1.getString("results_array"), ShopDetail[].class));
                            shopListAdapter =new ShopListAdapter(SearchActivity.this,shopsList,recyclerViewCategory);
                            recyclerViewCategory.setAdapter(shopListAdapter);
                        }
                    }catch (Exception e){e.printStackTrace();}
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    swipe.setRefreshing(false);
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(SearchActivity.this, "No Internet Connection",
                                Toast.LENGTH_LONG).show();
                    } else  {
                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
                    }}
            }
            )
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("search_term", searchString);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(sr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item=menu.findItem(R.id.action_search);
        MenuItem item1=menu.findItem(R.id.action_search1);

        SearchView searchView= (SearchView) MenuItemCompat.getActionView(item1);
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(true);
        searchView.requestFocus();
        MenuItemCompat.expandActionView(item1);
        item.setVisible(false);
        item1.setVisible(true);

        MenuItemCompat.setOnActionExpandListener(item1,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {

                        return true; // KEEP IT TO TRUE OR IT DOESN'T OPEN !!
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        onBackPressed();
                        return true; // OR FALSE IF YOU DIDN'T WANT IT TO CLOSE!
                    }
                });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>0){
                    searchString=newText;
                    getCategoryList();
                }
                else{
                    List<ShopDetail> shopsList = new ArrayList<ShopDetail>();
                    shopListAdapter =new ShopListAdapter(SearchActivity.this,shopsList,recyclerViewCategory);
                    recyclerViewCategory.setAdapter(shopListAdapter);
                }
                return true;
            }
        });

        return true;
    }
}
