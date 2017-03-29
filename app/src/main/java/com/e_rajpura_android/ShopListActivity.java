package com.e_rajpura_android;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.e_rajpura_android.Model.SearchResults;
import com.e_rajpura_android.adapter.ShopListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShopListActivity extends AppCompatActivity {
    TextView toolBarTitle;
    LinearLayout backButton;
    RecyclerView recyclerViewList;
    LinearLayoutManager linearLayoutManager;
    ShopListAdapter shopListAdapter;
    List<SearchResults> searchResultsList=new ArrayList<>();

    protected Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        inflateToolbar();
        inflateLayout();
        addSearchList();

    }

    public void inflateToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBarTitle=(TextView) toolbar.findViewById(R.id.toolbar_title);
        backButton=(LinearLayout) toolbar.findViewById(R.id.imageView_back);

        setSupportActionBar(toolbar);
        toolBarTitle.setText("Shops");
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
        recyclerViewList=(RecyclerView)findViewById(R.id.recyclerViewSearch);
        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        shopListAdapter =new ShopListAdapter(this,searchResultsList,recyclerViewList);
        handler=new Handler();
        shopListAdapter.setOnLoadMoreListener(new ShopListAdapter.OnLoadMoreListener() {
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
        });
        recyclerViewList.setAdapter(shopListAdapter);
    }


    public void addSearchList()
    {
        for(int i=0;i<7;i++)
        {
            searchResultsList.add(new SearchResults());
        }
        shopListAdapter.addTopSearchArrayList(searchResultsList);
        shopListAdapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                doSearch(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                doSearch(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ShopListActivity.this.finish();
    }

    public void doSearch(String text)
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
    }
}
