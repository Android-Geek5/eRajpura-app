package com.erajpura.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.erajpura.R;

/**
 * Created by erginus_android on 7/4/17.
 */

public class AdvertiseFragmentNew extends Fragment{
    View v;
    Button advertiseButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.advertise_fragment_new, container, false);
        setHasOptionsMenu(true);
        init();
        return v;

    }

    private void init(){
        advertiseButton=(Button)v.findViewById(R.id.advertise_button);
        advertiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getResources().getString(R.string.erajpuraLink);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }

}
