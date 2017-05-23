package com.erajpura.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.erajpura.Model.TopCategoryAndShops;
import com.erajpura.R;
import com.erajpura.adapter.CategoryListAdapter;
import com.erajpura.common.AppController;
import com.erajpura.common.Global;
import com.erajpura.common.GridSpacesItemDecoration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment implements ScreenShotable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View containerView;
    protected int res;
    private Bitmap bitmap;
    RecyclerView recyclerViewCategory;
    GridLayoutManager gridLayoutManager;
    CategoryListAdapter categoryListAdapter;
    SwipeRefreshLayout swipe;


    GsonBuilder gsonBuilder;
    private Gson gson;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
        View view= inflater.inflate(R.layout.fragment_category, container, false);
        inflateLayout(view);
        viewPager();
        //setSearchView();
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
        /*if (context instanceof OnFragmentInteractionListener) {
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
       /* Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                CategoryFragment.this.bitmap = bitmap;
            }
        };

        thread.start();*/

    }
    private void viewPager(){
        ViewPager vp=(ViewPager)getActivity().findViewById(R.id.mainviewpager);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1){
                    getCategoryList();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        recyclerViewCategory=(RecyclerView)view.findViewById(R.id.recyclerViewCategoryList);
        gridLayoutManager= new GridLayoutManager(getActivity(),2);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin5);
        recyclerViewCategory.addItemDecoration(new GridSpacesItemDecoration(spacingInPixels,true));
        swipe=(SwipeRefreshLayout)view.findViewById(R.id.swipe);
        swipe.setEnabled(false);
        recyclerViewCategory.setLayoutManager(gridLayoutManager);



        gsonBuilder = new GsonBuilder();

        gson = gsonBuilder.create();
    }

    public void getCategoryList() {
        try {
           swipe.setRefreshing(true);
            String LOGIN_URL= Global.BASE_URL + Global.API_ALL_CATEGORIES;
            StringRequest sr = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    swipe.setRefreshing(false);
                    try{
                        JSONObject obj=new JSONObject(response);
                        if(obj.getInt("code")==1){

                            List<TopCategoryAndShops> shopsList = Arrays.asList(gson.fromJson(obj.getString("data"), TopCategoryAndShops[].class));
                            categoryListAdapter =new CategoryListAdapter(getActivity(),shopsList,CategoryFragment.this);
                            recyclerViewCategory.setAdapter(categoryListAdapter);
                        }
                    }catch (Exception e){e.printStackTrace();}
                }}
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    swipe.setRefreshing(false);
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(getActivity(), "No Internet Connection",
                                Toast.LENGTH_LONG).show();
                    }{
                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
                    }}
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", "Androidhive");
                    return params;
                }};
            AppController.getInstance().addToRequestQueue(sr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
