package com.erajpura.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.erajpura.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AboutUsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AboutUsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutUsFragment extends Fragment implements ScreenShotable, View.OnClickListener {
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
    LinearLayout calllayout,emaillayout;
    private Bitmap bitmap;
    View v;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutUsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutUsFragment newInstance(String param1, String param2) {
        AboutUsFragment fragment = new AboutUsFragment();
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
        setHasOptionsMenu(true);
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
        v= inflater.inflate(R.layout.fragment_about_us, container, false);
        intit();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
private void intit(){
    calllayout=(LinearLayout)v.findViewById(R.id.call2_layout);
    emaillayout=(LinearLayout)v.findViewById(R.id.email_layout);

    calllayout.setOnClickListener(this);
    emaillayout.setOnClickListener(this);
}
    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
    }
    @Override
    public void takeScreenShot() {
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void onClick(View v) {
        if(v==calllayout){
            dialog(false,"9779135856");
        }
        else if(v==emaillayout){
            dialog(true,"hi@erajpura.com");
        }

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void dialog(boolean mailOrNot, final String detail)
    {
        final Dialog dialog = new Dialog(getActivity());
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
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }
}
