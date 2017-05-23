package com.erajpura.adapter;

/**
 * Created by erginus_android on 17/3/17.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.erajpura.Fragment.AboutUsFragment;
import com.erajpura.Fragment.AdvertiseFragmentNew;
import com.erajpura.Fragment.CategoryFragment;
import com.erajpura.Fragment.HomeFragment;
import com.erajpura.Fragment.SearchFragmentNew;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new HomeFragment();
            case 1:
                return new CategoryFragment();
            case 2:
                return new AboutUsFragment();
            case 3:
                return new AdvertiseFragmentNew();
            case 4:
                return new SearchFragmentNew();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 5;
    }

}
