package com.seosj.classicbook;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu1Fragment extends Fragment{

    //메인화면
    //
    //
    //
    public static Context mContext;
    private RecyclerAdapter_testinfo adapter;
    private TextView textView_name;
    private TextView textView_stat_tot;
    private com.github.lzyzsd.circleprogress.ArcProgress arcProgress;
    private com.seosj.classicbook.CustomCategory catseo;
    private com.seosj.classicbook.CustomCategory catdong;
    private com.seosj.classicbook.CustomCategory catdongseo;
    private com.seosj.classicbook.CustomCategory catscience;
    private LinearLayout lin;

    public String furl;
    public String url1 = "http://15.164.113.118:3000/?status=4&id=";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_1, container, false);

        textView_name = v.findViewById(R.id.text_hellouser);
        textView_stat_tot = v.findViewById(R.id.text_stat_tot);
        arcProgress = v.findViewById(R.id.arc_progress);
        catseo = v.findViewById(R.id.customcat1);
        catdong = v.findViewById(R.id.customcat2);
        catdongseo = v.findViewById(R.id.customcat3);
        catscience = v.findViewById(R.id.customcat4);
        lin = v.findViewById(R.id.det);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter_testinfo();
        recyclerView.setAdapter(adapter);
        mContext= v.getContext();
        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(v.getContext(),Main1_Detail.class));
            }
        });
        setData();
        getData();
        return v;
    }

    //로그인 정보에 맞게 메인화면 세팅하기
    //서양 4권 동양 2권 동서양 3권 과학 1권
    @SuppressLint("SetTextI18n")
    private void setData(){
        JSONParser js = (JSONParser)mContext.getApplicationContext();

        textView_name.setText(js.getStu_name() + "님,\n안녕하세요");

        switch (js.getStat_auth_tot()){
            case "0권": textView_stat_tot.setText("0/10권"); arcProgress.setProgress(0); break;
            case "1권": textView_stat_tot.setText("1/10권"); arcProgress.setProgress(10); break;
            case "2권": textView_stat_tot.setText("2/10권"); arcProgress.setProgress(20); break;
            case "3권": textView_stat_tot.setText("3/10권"); arcProgress.setProgress(30); break;
            case "4권": textView_stat_tot.setText("4/10권"); arcProgress.setProgress(40); break;
            case "5권": textView_stat_tot.setText("5/10권"); arcProgress.setProgress(50); break;
            case "6권": textView_stat_tot.setText("6/10권"); arcProgress.setProgress(60); break;
            case "7권": textView_stat_tot.setText("7/10권"); arcProgress.setProgress(70); break;
            case "8권": textView_stat_tot.setText("8/10권"); arcProgress.setProgress(80); break;
            case "9권": textView_stat_tot.setText("9/10권"); arcProgress.setProgress(90); break;
            case "10권": textView_stat_tot.setText("10/10권"); arcProgress.setProgress(100); break;
        }

        switch(js.getStat_auth_seo()){
            case "0권": catseo.symbol2.setText("0/4권"); break;
            case "1권": catseo.symbol2.setText("1/4권"); break;
            case "2권": catseo.symbol2.setText("2/4권"); break;
            case "3권": catseo.symbol2.setText("3/4권"); break;
            case "4권": catseo.symbol2.setText("4/4권"); break;
        }
        switch(js.getStat_auth_dong()){
            case "0권": catdong.symbol2.setText("0/2권"); break;
            case "1권": catdong.symbol2.setText("1/2권"); break;
            case "2권": catdong.symbol2.setText("2/2권"); break;
        }
        switch(js.getStat_auth_dongseo()){
            case "0권": catdongseo.symbol2.setText("0/3권"); break;
            case "1권": catdongseo.symbol2.setText("1/3권"); break;
            case "2권": catdongseo.symbol2.setText("2/3권"); break;
            case "3권": catdongseo.symbol2.setText("3/3권"); break;
        }
        switch(js.getStat_auth_science()){
            case "0권": catscience.symbol2.setText("0/1권"); break;
            case "1권": catscience.symbol2.setText("1/1권"); break;
        }

    }




    private void getData(){

        JSONParser js = (JSONParser) mContext.getApplicationContext();
        furl = url1 + js.getStu_num();

        LoadtIn loadtIn = new LoadtIn(furl,null);
        loadtIn.execute();
    }


    public class LoadtIn extends AsyncTask<String, Void, String> {

        ProgressDialog asyncDialog = new ProgressDialog(Menu1Fragment.mContext);

        private String url;
        private ContentValues values;

        private LoadtIn(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("잠시만 기다려 주세요...");

            //show dialog
            asyncDialog.show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //전송하기 위한 스트링 변수
            String turl = url;

            String result;

            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(turl, values);

            System.out.println(result);

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);

            //파싱
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = (JsonArray) jsonParser.parse(result);
            JsonObject object = (JsonObject) jsonArray.get(0);
            String tbid = object.get("id").getAsString();//학번
            String tbname = object.get("title").getAsString();//책이름
            String tbdate = object.get("date").getAsString();//날짜
            String tbtime = object.get("time").getAsString();//시간

            List<String> listTitle = new ArrayList<>();
            List<String> listContent = new ArrayList<>();

            if (tbid.equals("1")) {
                listTitle = Arrays.asList("예약한 시험이 없습니다.");
                listContent = Arrays.asList("-");

                List<Integer> listResId = Arrays.asList(R.drawable.ic_button_clickarrow);

                Data data = new Data();
                data.setTitle(listTitle.get(0));
                data.setContent(listContent.get(0));
                data.setResId(listResId.get(0));
                adapter.addItem(data);

                adapter.notifyDataSetChanged();

            } else {
                String t = tbdate + "\n" + tbtime;
                String n = "광108B\n" + "도서명: " + tbname;
                listTitle = Arrays.asList(t);
                listContent = Arrays.asList(n);

                List<Integer> listResId = Arrays.asList(R.drawable.ic_button_clickarrow);

                Data data = new Data();
                data.setTitle(listTitle.get(0));
                data.setContent(listContent.get(0));
                data.setResId(listResId.get(0));
                adapter.addItem(data);

                adapter.notifyDataSetChanged();
            }
        }

    }


}
