package com.seosj.classicbook;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu3_searchLibrary extends AppCompatActivity{

    private RecycleAdapter_Search adapter;

    TextView txbookname;
    TextView txbookloc;
    TextView txbooknum;
    TextView txbookdate;
    TextView txbookrent;
    String URL;
    //ISBN토대 책검색
    String URL1 = "http://library.sejong.ac.kr/search/Search.Result.ax?sid=1&q=ISBN%3A";
    String URL2 = "&eq=&mf=true&qt=ISBN%3D";
    String URL3 = "&qf=";
    String URL4 = "&f=&br=&cl=1+2+3+4+5+7+8+9+18+19+20+21+22+23+24+25+26+27+28+29+30+31+32+33+" +
            "34+35+36+37+61+62+34+35+38+39+11+12+13+63+44+45+51+52+42+43+49+50+40+41+57+58+48" +
            "+59+61+62&gr=1+2+3+4+5+6+7+8+9+10+12+13+20&rl=&page=&pageSize=10&s=&st=&h=&cr=&p" +
            "y=&subj=&facet=Y&nd=&vid=0&tabID=";
    //책ID토대 책테이블 검색
    String finURL =  "http://library.sejong.ac.kr/search/ItemDetail.axa?sid=1&cid=";
    String findURLADDID;

    List<String> listStringloc = new ArrayList<>();
    List<String> listStringnum = new ArrayList<>();
    List<String> listStringrent = new ArrayList<>();
    List<String> listStringdate = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu3_search_library);

        txbookname = findViewById(R.id.text_menu_bookname);
        txbookloc = findViewById(R.id.textView_menu3_search2);
        txbooknum = findViewById(R.id.textView_menu3_search4);
        txbookdate = findViewById(R.id.textView_menu3_search5);
        txbookrent = findViewById(R.id.textView_menu3_search6);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_search);
        recyclerView.setLayoutManager(linearLayoutManager);

        //리사이클러뷰 선
        recyclerView.addItemDecoration(new DividerItemDecoration(this,1));
        adapter = new RecycleAdapter_Search();
        recyclerView.setAdapter(adapter);


        Intent intent = getIntent();
        String bookname=intent.getExtras().getString("Book");
        String ISBN=intent.getExtras().getString("ISBN");
        txbookname.setText(bookname);
        Log.d("searchsearch",ISBN);
        URL = URL1 + ISBN + URL2 + ISBN + URL3 + ISBN + URL4;
        SearchTask sc = new SearchTask();
        sc.execute();

    }


    private class SearchTask extends AsyncTask<Void,Void,Void>{
        private Elements element;

        @Override
        protected void onPostExecute(Void result){
            //doinbackground작업끝나고 할작업
            super.onPostExecute(result);

            getData();
        }
        @Override
        protected Void doInBackground(Void... params){
            try{
                Document doc = Jsoup.connect(URL).get();
                //해당 도서 id태그 파싱
                element = doc.select("div.previewListViewHolder");
                String getallid = element.attr("id");
                findURLADDID = getallid.replace("rel_","");
                finURL = finURL+findURLADDID;

                Document doc2 = Jsoup.connect(finURL).get();
                //도서 진짜 대출목록 파싱
                element = doc2.select("tbody");

                //도서 tbody에서 td태그만 추출
                Elements div = element.select("td");

                int j =0;
                for(int i=0;i<div.size();i++){
                    switch(i%7){
                        case 2: listStringloc.add(div.get(i).ownText()); break;
                        case 3: listStringnum.add(div.get(i).ownText()); break;
                        case 4: listStringrent.add(div.get(i).ownText()); break;
                        case 5: listStringdate.add(div.get(i).ownText()); break;
                    }
                    j++;
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }

    private void getData(){
        List<String> listloc = new ArrayList<>();
        List<String> listnum = new ArrayList<>();
        List<String> listdate = new ArrayList<>();
        List<String> listrent = new ArrayList<>();

        listloc.addAll(listStringloc);
        listnum.addAll(listStringnum);
        listdate.addAll(listStringdate);
        listrent.addAll(listStringrent);

        for(int i=0;i<listloc.size();i++){
            Data_Recycle_Search data = new Data_Recycle_Search();
            data.setLoc(listloc.get(i));
            data.setNum(listnum.get(i));
            data.setUntil_date(listdate.get(i));
            data.setIsrent(listrent.get(i));

            adapter.addItem(data);
        }
        adapter.notifyDataSetChanged();

    }

}
