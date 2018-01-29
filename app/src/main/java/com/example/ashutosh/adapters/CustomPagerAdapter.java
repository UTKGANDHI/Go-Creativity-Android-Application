package com.example.ashutosh.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ashutosh.fragments.HomeFragment;
import com.example.ashutosh.fragments.LogoFragment;
import com.example.ashutosh.fragments.VideoFragment;

/**
 * Created by Admin on 12/20/2016.
 */

public class CustomPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public CustomPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                HomeFragment tab1 = new HomeFragment();
                return tab1;
            case 1:
                LogoFragment tab2 = new LogoFragment();
                return tab2;
            case 2:
                VideoFragment tab3 = new VideoFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
