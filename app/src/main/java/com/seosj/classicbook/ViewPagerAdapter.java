package com.seosj.classicbook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int mPageCount;


    public ViewPagerAdapter(FragmentManager fm, int pageCount){
        super(fm);
        this.mPageCount=pageCount;

    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0: return new PageOneFragment();
            case 1:
                return new PageTwoFragment();
            case 2:
                return new PageThreeFragment();
            case 3:
                return new PageFourFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {

        return mPageCount;

    }


}
