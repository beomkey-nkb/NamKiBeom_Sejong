package com.seosj.classicbook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class Menu4Fragment extends Fragment{

    private RecyclerAdapter_Setting adapter;
    private TextView nameTxt;
    public Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_4, container, false);
        context = v.getContext();
        JSONParser js = (JSONParser)context.getApplicationContext();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = v.findViewById(R.id.recycle_menu4);
        nameTxt = v.findViewById(R.id.text_userprofile);

        nameTxt.setText(js.getStu_name()+"님의 프로필");
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter_Setting();
        recyclerView.setAdapter(adapter);


        getData();


        return v;
    }


    private void getData(){

        List<String> listTitle = Arrays.asList(
                "사용자 학번",
                "인증방법 및 절차",
                "인증도서 목록",
                "로그아웃");
        List<Integer> listResId1 = Arrays.asList(
                0,
                R.drawable.ic_settings_1,
                R.drawable.ic_settings_2,
                R.drawable.ic_settings_3
        );

        JSONParser js = (JSONParser)context.getApplicationContext();


        //추후 자동으로 학번 불러와야 함(해결완료)
        List<String> listUserId=Arrays.asList(js.getStu_num(),"","","");
        List<Integer> listResId2 = Arrays.asList(
                0,
                R.drawable.ic_button_clickarrow,
                R.drawable.ic_button_clickarrow,
                R.drawable.ic_button_clickarrow
        );

        for(int i=0;i<listTitle.size();i++) {
            Data_Recycle_Setting data = new Data_Recycle_Setting();
            data.setTitle(listTitle.get(i));
            data.setUser_id(listUserId.get(i));
            data.setResId1(listResId1.get(i));
            data.setResId2(listResId2.get(i));
            adapter.addItem(data);
        }

        adapter.notifyDataSetChanged();

    }

}
