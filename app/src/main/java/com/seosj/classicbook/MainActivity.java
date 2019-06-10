package com.seosj.classicbook;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/*
 * ******************************************
 * Copyright 2019. 서성준 all rights reserved.
 * ******************************************
 */
public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    //4개의 메뉴에 들어갈 Fragment들
    private Menu1Fragment menu1Fragment = new Menu1Fragment();
    private Menu2Fragment menu2Fragment = new Menu2Fragment();
    private Menu3Fragment menu3Fragment = new Menu3Fragment();
    private Menu4Fragment menu4Fragment = new Menu4Fragment();
    private MenuRankFragment menuRankFragment = new MenuRankFragment();

    //인증현황 menu_home
    //예약신청 menu_reservation_test
    //도서 검색 menu_search
    //설정 menu_settings
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_1:
                        transaction.replace(R.id.frame_layout,menu1Fragment).commitAllowingStateLoss();
                        return true;
                    case R.id.navigation_2:
                        transaction.replace(R.id.frame_layout,menu2Fragment).commitAllowingStateLoss();
                        return true;
                    case R.id.navigation_3:
                        transaction.replace(R.id.frame_layout,menu3Fragment).commitAllowingStateLoss();
                        return true;
                    case R.id.navigation_4:
                        transaction.replace(R.id.frame_layout,menu4Fragment).commitAllowingStateLoss();
                        return true;
                    case R.id.navigation_rank:
                        transaction.replace(R.id.frame_layout,menuRankFragment).commitAllowingStateLoss();
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        // BottomNavigationView 메뉴를 선택할 때마다 위치가 변하지 않도록
        NavigationHelper.disableShiftMode(navigation);
        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //뒤로가기 버튼을 두번 연속으로 눌러야 종료되게끔 하는 메소드
    private long time= 0;
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            finish();
        }
    }




}
