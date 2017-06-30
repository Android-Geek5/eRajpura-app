package com.erajpura.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.erajpura.R;

/**
 * Created by erginus_android on 7/4/17.
 */

public class AdvertiseFragmentNew extends Fragment{
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.advertise_fragment_new, container, false);
        setHasOptionsMenu(true);
        return v;

    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }

}
