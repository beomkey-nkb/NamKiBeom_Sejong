package com.seosj.classicbook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MenuRankFragment extends Fragment {

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerApapter;
    private TabLayout mTabLayout;

    public Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rank, container, false);
        context = v.getContext();

        mTabLayout = v.findViewById(R.id.tbl);
        mViewPager = v.findViewById(R.id.viewPG);
        mViewPagerApapter = new ViewPagerAdapter(
                getChildFragmentManager(), 4);

        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(mViewPagerApapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPagerApapter.notifyDataSetChanged();


        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        final int pos = 0;
        mViewPager.postDelayed(new Runnable() {

            @Override
            public void run() {
                mViewPager.setCurrentItem(pos);
            }
        }, 100);
    }


}
