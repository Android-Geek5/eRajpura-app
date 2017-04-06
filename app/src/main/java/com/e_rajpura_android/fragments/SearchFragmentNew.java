package com.e_rajpura_android.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.e_rajpura_android.Model.ShopDetail;
import com.e_rajpura_android.R;
import com.e_rajpura_android.SearchActivity;
import com.e_rajpura_android.adapter.ShopListAdapter;
import com.e_rajpura_android.common.AppController;
import com.e_rajpura_android.common.Global;
import com.e_rajpura_android.common.GridSpacesItemDecoration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by erginus_android on 4/4/17.
 */

public class SearchFragmentNew extends Fragment {

    RecyclerView recyclerViewSearch;
    LinearLayoutManager linearLayoutManager;
    String searchString;
    SwipeRefreshLayout swipe;
    GsonBuilder gsonBuilder;
    ShopListAdapter shopListAdapter;
    private Gson gson;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.activity_search, container, false);
        setHasOptionsMenu(true);
        inflateLayout(view);


        return view;
    }
    public void inflateLayout(View view)
    {
        recyclerViewSearch=(RecyclerView)view.findViewById(R.id.recyclerViewSearch);
        swipe=(SwipeRefreshLayout)view.findViewById(R.id.swipe);
        linearLayoutManager= new GridLayoutManager(getActivity(),1);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin5);
        recyclerViewSearch.addItemDecoration(new GridSpacesItemDecoration(spacingInPixels,true));
        recyclerViewSearch.setLayoutManager(linearLayoutManager);

        gsonBuilder = new GsonBuilder();

        gson = gsonBuilder.create();

        getActivity().setTitle("Search");


        recyclerViewSearch=(RecyclerView)view.findViewById(R.id.recyclerViewSearch);

        linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerViewSearch.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.main, menu);
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
                    shopListAdapter =new ShopListAdapter(getActivity(),shopsList,recyclerViewSearch);
                    recyclerViewSearch.setAdapter(shopListAdapter);
                }
                return true;
            }
        });


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
                            shopListAdapter =new ShopListAdapter(getActivity(),shopsList,recyclerViewSearch);
                            recyclerViewSearch.setAdapter(shopListAdapter);
                        }
                    }catch (Exception e){e.printStackTrace();}
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    swipe.setRefreshing(false);
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(getActivity(), "No Internet Connection",
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
}
