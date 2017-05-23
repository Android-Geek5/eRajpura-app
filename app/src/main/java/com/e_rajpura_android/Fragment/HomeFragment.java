package com.erajpura.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.erajpura.MainActivity_New;
import com.erajpura.Model.ShopShortDetail;
import com.erajpura.Model.TopCategoryAndShops;
import com.erajpura.R;
import com.erajpura.ShopListActivity;
import com.erajpura.adapter.CategoryHomeItemsAdapter;
import com.erajpura.adapter.CategoryListAdapter;
import com.erajpura.common.AppController;
import com.erajpura.common.Global;
import com.erajpura.common.GridSpacesItemDecoration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
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
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment  implements ScreenShotable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    GsonBuilder gsonBuilder;
    private Gson gson;

    private OnFragmentInteractionListener mListener;

    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerViewTopSearches,recyclerViewCategory1,recyclerViewCategory2,recyclerViewCategory3,recyclerViewCategory4;
    LinearLayoutManager linearLayoutManager1,linearLayoutManager2,linearLayoutManager3,linearLayoutManager4;
    CategoryListAdapter categoryListAdapter;

    CategoryHomeItemsAdapter categoryHomeItemsAdapter1,categoryHomeItemsAdapter2,categoryHomeItemsAdapter3,categoryHomeItemsAdapter4;
    private View containerView;
    protected int res;
    private Bitmap bitmap;
    View headerView1,headerView2,headerView3,headerView4;
    TextView headerTextView1,headerTextView2,headerTextView3,headerTextView4;
    TextView viewMoreText1,viewMoreText2,viewMoreText3,viewMoreText4;
    LinearLayout scrollView;
    SwipeRefreshLayout swipe;
    RecyclerView recyclerViewSearch;
    LinearLayoutManager linearLayoutManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        inflateLayout(view);
        setHeaderViews();
     //   setSearchView();
        viewPager();
        runHomeAPI();
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
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void inflateLayout(View view)
    {
        scrollView=(LinearLayout) view.findViewById(R.id.main_fragment);
        recyclerViewTopSearches=(RecyclerView)view.findViewById(R.id.recyclerViewTopSearches);

        swipe=(SwipeRefreshLayout)view.findViewById(R.id.swipe);
        swipe.setEnabled(false);
        gridLayoutManager= new GridLayoutManager(getActivity(),2);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin3);
        recyclerViewTopSearches.addItemDecoration(new GridSpacesItemDecoration(spacingInPixels,true));
        recyclerViewTopSearches.setLayoutManager(gridLayoutManager);


        recyclerViewCategory1=(RecyclerView)view.findViewById(R.id.recyclerViewCategory1);
        recyclerViewCategory2=(RecyclerView)view.findViewById(R.id.recyclerViewCategory2);
        recyclerViewCategory3=(RecyclerView)view.findViewById(R.id.recyclerViewCategory3);
        recyclerViewCategory4=(RecyclerView)view.findViewById(R.id.recyclerViewCategory4);

        linearLayoutManager1=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        linearLayoutManager2=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        linearLayoutManager3=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        linearLayoutManager4=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        headerView1=(View) view.findViewById(R.id.category_home_item1);
        headerView2=(View) view.findViewById(R.id.category_home_item2);
        headerView3=(View) view.findViewById(R.id.category_home_item3);
        headerView4=(View) view.findViewById(R.id.category_home_item4);


        recyclerViewCategory1.setLayoutManager(linearLayoutManager1);
        recyclerViewCategory2.setLayoutManager(linearLayoutManager2);
        recyclerViewCategory3.setLayoutManager(linearLayoutManager3);


        recyclerViewCategory4.setLayoutManager(linearLayoutManager4);



        gsonBuilder = new GsonBuilder();

        gson = gsonBuilder.create();

    }

    public void setHeaderViews()
    {
        headerTextView1=(TextView) headerView1.findViewById(R.id.category_name);
        headerTextView2=(TextView) headerView2.findViewById(R.id.category_name);
        headerTextView3=(TextView) headerView3.findViewById(R.id.category_name);
        headerTextView4=(TextView) headerView4.findViewById(R.id.category_name);

        viewMoreText1=(TextView) headerView1.findViewById(R.id.view_more);
        viewMoreText2=(TextView) headerView2.findViewById(R.id.view_more);
        viewMoreText3=(TextView) headerView3.findViewById(R.id.view_more);
        viewMoreText4=(TextView) headerView4.findViewById(R.id.view_more);
    }

   public void viewMoreClick(int catId,String catName)
   {
    Intent intent=new Intent(getActivity(), ShopListActivity.class);
       intent.putExtra("catId",catId);
       intent.putExtra("catName",catName);
    getActivity().startActivity(intent);
    }

private void runHomeAPI(){
    if(MainActivity_New.homeApiFT=true){
        MainActivity_New.homeApiFT=false;

        getTopRatedCategoryandShops();
    }
}
    public void getTopRatedCategoryandShops() {
        scrollView.setVisibility(View.INVISIBLE);
        try {
            final ProgressDialog pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            swipe.setRefreshing(true);

            String LOGIN_URL=Global.BASE_URL + Global.API_TOP_CATEGORIES_SHOPS;
            Log.e("CLogin URL",LOGIN_URL);
            StringRequest sr = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    swipe.setRefreshing(false);
                    scrollView.setVisibility(View.VISIBLE);
                    Log.e("AA",response);
                    try{
                        JSONObject obj=new JSONObject(response);
                        if(obj.getInt("code")==1){
                            JSONObject data=new JSONObject(obj.getString("data"));

                           setCategoryRecyclerData(data.getString("top_category_view_array"));

                            JSONObject category_shop_array=new JSONObject(data.getString("category_shop_array"));

                            setFirstRecyclerData(category_shop_array.getString("cat_0"));
                            setSecondRecyclerData(category_shop_array.getString("cat_1"));
                            setThirdRecyclerData(category_shop_array.getString("cat_2"));
                            setFourthRecyclerData(category_shop_array.getString("cat_3"));
                        }

                    }catch (Exception e){e.printStackTrace();}
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    scrollView.setVisibility(View.VISIBLE);
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
                    params.put("name", "Androidhive");
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(sr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 private void viewPager(){
     ViewPager vp=(ViewPager)getActivity().findViewById(R.id.mainviewpager);
     vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         @Override
         public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

         }

         @Override
         public void onPageSelected(int position) {
             if(position==0){
                 getTopRatedCategoryandShops();
             }

         }

         @Override
         public void onPageScrollStateChanged(int state) {

         }
     });
 }

    private void setCategoryRecyclerData(String data){

        List<TopCategoryAndShops> shopsList = Arrays.asList(gson.fromJson(data, TopCategoryAndShops[].class));

        try{
            JSONArray arr=new JSONArray(data);
            TopCategoryAndShops category1= gson.fromJson(arr.get(0).toString(), TopCategoryAndShops.class);
            TopCategoryAndShops category2= gson.fromJson(arr.get(1).toString(), TopCategoryAndShops.class);
            TopCategoryAndShops category3= gson.fromJson(arr.get(2).toString(), TopCategoryAndShops.class);

            List<TopCategoryAndShops> shopsList1=new ArrayList<TopCategoryAndShops>();

            shopsList1.add(category1);
            shopsList1.add(category2);
            shopsList1.add(category3);

            TopCategoryAndShops tp=new TopCategoryAndShops();
            tp.setCategory_id(123);
            tp.setCategory_name("View More");
            tp.setCategory_created("aaaa");
            tp.setCategory_icon("http://erajpura.com/assets/img/circle-bar.png");
            tp.setShop_count("");


            shopsList1.add(tp);

            categoryListAdapter =new CategoryListAdapter(getActivity(),shopsList1,this);

            recyclerViewTopSearches.setAdapter(categoryListAdapter);
        }catch(Exception e){e.printStackTrace();}


        if(shopsList.size()>=3){

        }


    }


    private void setFirstRecyclerData(String data){
        try{
            JSONObject catobj=new JSONObject(data);
            TopCategoryAndShops category= gson.fromJson(catobj.getString("category_detail"), TopCategoryAndShops.class);
            List<ShopShortDetail> shopsList = Arrays.asList(gson.fromJson(catobj.getString("shops_detail"), ShopShortDetail[].class));

            final int catId=category.getCategories_id();
            final String catName=category.getCategory_name();

            headerTextView1.setText(category.getCategory_name());

            categoryHomeItemsAdapter1=new CategoryHomeItemsAdapter(getActivity(),shopsList);
            recyclerViewCategory1.setAdapter(categoryHomeItemsAdapter1);

            viewMoreText1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewMoreClick(catId,catName);
                }
            });
        }catch(Exception e){e.printStackTrace();}
    }

    private void setSecondRecyclerData(String data){
        try{
            JSONObject catobj=new JSONObject(data);
            TopCategoryAndShops category= gson.fromJson(catobj.getString("category_detail"), TopCategoryAndShops.class);
            List<ShopShortDetail> shopsList = Arrays.asList(gson.fromJson(catobj.getString("shops_detail"), ShopShortDetail[].class));

            final int catId=category.getCategories_id();
            final String catName=category.getCategory_name();

            headerTextView2.setText(category.getCategory_name());

            categoryHomeItemsAdapter2=new CategoryHomeItemsAdapter(getActivity(),shopsList);
            recyclerViewCategory2.setAdapter(categoryHomeItemsAdapter2);

            viewMoreText2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewMoreClick(catId,catName);
                }
            });
        }
        catch(Exception e){e.printStackTrace();}

    }

    private void setThirdRecyclerData(String data){
        try{
            JSONObject catobj=new JSONObject(data);
            TopCategoryAndShops category= gson.fromJson(catobj.getString("category_detail"), TopCategoryAndShops.class);
            List<ShopShortDetail> shopsList = Arrays.asList(gson.fromJson(catobj.getString("shops_detail"), ShopShortDetail[].class));

            final int catId=category.getCategories_id();
            final String catName=category.getCategory_name();

            headerTextView3.setText(category.getCategory_name());

            categoryHomeItemsAdapter3=new CategoryHomeItemsAdapter(getActivity(),shopsList);
            recyclerViewCategory3.setAdapter(categoryHomeItemsAdapter3);

            viewMoreText3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewMoreClick(catId,catName);
                }
            });
        }catch(Exception e){e.printStackTrace();}
    }

    private void setFourthRecyclerData(String data){
        try{
            JSONObject catobj=new JSONObject(data);
            TopCategoryAndShops category= gson.fromJson(catobj.getString("category_detail"), TopCategoryAndShops.class);
            List<ShopShortDetail> shopsList = Arrays.asList(gson.fromJson(catobj.getString("shops_detail"), ShopShortDetail[].class));


            final int catId=category.getCategories_id();
            final String catName=category.getCategory_name();

            headerTextView4.setText(category.getCategory_name());

            categoryHomeItemsAdapter4=new CategoryHomeItemsAdapter(getActivity(),shopsList);
            recyclerViewCategory4.setAdapter(categoryHomeItemsAdapter4);

            viewMoreText4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewMoreClick(catId,catName);
                }
            });

        }catch(Exception e){e.printStackTrace();}
    }

}
