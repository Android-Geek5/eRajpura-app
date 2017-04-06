package com.e_rajpura_android.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.e_rajpura_android.Model.SearchResults;
import com.e_rajpura_android.Model.ShopDetail;
import com.e_rajpura_android.R;
import com.e_rajpura_android.ShopListActivity;
import com.e_rajpura_android.adapter.ShopListAdapter;
import com.e_rajpura_android.common.AppController;
import com.e_rajpura_android.common.Global;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements ScreenShotable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View containerView;
    protected int res;
    private Bitmap bitmap;
    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerViewSearch;
    LinearLayoutManager linearLayoutManager;
    ShopListAdapter shopListAdapter;
    List<SearchResults> searchResultsList=new ArrayList<>();
    LinearLayout mainLayout;
    protected Handler handler;

    GsonBuilder gsonBuilder;
    private Gson gson;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);
        inflateLayout(view);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void takeScreenShot() {
     /*   Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                SearchFragment.this.bitmap = bitmap;
            }
        };

        thread.start();*/

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void inflateLayout(View view)
    {
        gsonBuilder = new GsonBuilder();

        gson = gsonBuilder.create();

        getActivity().setTitle("Search");
        mainLayout=(LinearLayout) view.findViewById(R.id.main_fragment);

        recyclerViewSearch=(RecyclerView)view.findViewById(R.id.recyclerViewSearch);

        linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerViewSearch.setLayoutManager(linearLayoutManager);
       // shopListAdapter =new ShopListAdapter(getActivity(),searchResultsList,recyclerViewSearch);
        handler=new Handler();
       /* shopListAdapter.setOnLoadMoreListener(new ShopListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                recyclerViewSearch.post(new Runnable() {
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
        recyclerViewSearch.setAdapter(shopListAdapter);*/

        getShopsList("");
    }

    public void doSearch(String text)
    {
        mainLayout.setVisibility(View.GONE);
        recyclerViewSearch.setVisibility(View.VISIBLE);
        searchResultsList.clear();
        for(int i=0;i<2;i++)
        {
            searchResultsList.add(new SearchResults());
        }
       // shopListAdapter.addTopSearchArrayList(searchResultsList);
        // HERE SET NULL LISTENER AFTER SEARCH FOR NOW
        //shopListAdapter.setOnLoadMoreListener(null);
        //shopListAdapter.notifyDataSetChanged();
    }
    public void getShopsList(String search) {

        try {
            final ProgressDialog pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();

            String LOGIN_URL= Global.BASE_URL + Global.API_SEARCH;
            StringRequest sr = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    Log.e("Responce",response);
                    try{
                        JSONObject obj=new JSONObject(response);
                        //JSONObject dataobj=new JSONObject(obj.getString("data"));
/*
                        List<ShopDetail> shopsList = Arrays.asList(gson.fromJson(obj.getString("data"), ShopDetail[].class));
                        shopListAdapter =new ShopListAdapter(getActivity(),shopsList,recyclerViewSearch);
                        recyclerViewSearch.setAdapter(shopListAdapter);*/

                    }catch (Exception e){e.printStackTrace();}
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(getActivity(), "No Internet Connection",
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
                    params.put("search_term", "");
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(sr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        MenuItemCompat.setOnActionExpandListener(searchViewItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {

                        return true; // KEEP IT TO TRUE OR IT DOESN'T OPEN !!
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        getActivity().onBackPressed();
                        return true; // OR FALSE IF YOU DIDN'T WANT IT TO CLOSE!
                    }
                });

        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               searchViewAndroidActionBar.clearFocus();
                Log.e("AA",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
    public void closeSearch()
    {
        mainLayout.setVisibility(View.VISIBLE);
        recyclerViewSearch.setVisibility(View.GONE);
    }

}
