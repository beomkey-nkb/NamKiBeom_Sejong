package com.seosj.classicbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Main1_Detail extends AppCompatActivity {

    private RecyclerAdapter_Detail adapter;
    private RecyclerAdapter_Detail adapter2;
    private RecyclerAdapter_Detail adapter3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1__detail);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.rec_det1);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new RecyclerAdapter_Detail();
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        RecyclerView recyclerView2 = findViewById(R.id.rec_det2);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        adapter2 = new RecyclerAdapter_Detail();
        recyclerView2.setAdapter(adapter2);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        RecyclerView recyclerView3 = findViewById(R.id.rec_det3);
        recyclerView3.setLayoutManager(linearLayoutManager3);
        adapter3 = new RecyclerAdapter_Detail();
        recyclerView3.setAdapter(adapter3);


        JSONParser js = (JSONParser)this.getApplicationContext();

        JsonArray testAuthArray = js.getStat_test_auth();
        JsonArray alterAuthArray = js.getStat_alter_auth();
        JsonArray chalAuthArray=js.getStat_challenge_auth();

        List<String> listHakgi = new ArrayList<>();
        List<String> listCat = new ArrayList<>();
        List<String> listTitle = new ArrayList<>();
        List<String> listDate = new ArrayList<>();
        List<String> listScore = new ArrayList<>();
        List<String> listIspass = new ArrayList<>();

        List<String> list_alHakgi = new ArrayList<>();
        List<String> list_alCourse = new ArrayList<>();
        List<String> list_alCat = new ArrayList<>();
        List<String> list_alTitle = new ArrayList<>();
        List<String> list_alPass = new ArrayList<>();

        List<String> list_chHakgi = new ArrayList<>();
        List<String> list_chname = new ArrayList<>();
        List<String> list_chCat = new ArrayList<>();
        List<String> list_chTitle = new ArrayList<>();


        for(int i=0;i<testAuthArray.size();i++){
            JsonObject object = (JsonObject)testAuthArray.get(i);
            /*
                "년도/학기":"2018년도겨울학기",
                "영역명":"동양의역사와사상",
                "도서명":"한비자",
                "응시일자":"2019-02-13",
                "점수":"60",
                "합격여부":"합격"
             */
            if(object.get("년도/학기").getAsString().equals("검색된결과가없습니다.")){
                Data_Recycle_Detail data = new Data_Recycle_Detail();
                data.setThak(" ");
                data.setTcat(" ");
                data.setTtitle("검색된결과가없습니다.");
                data.setTdate(" ");
                data.setTscore(" ");

                adapter.addItem(data);
                adapter.notifyDataSetChanged();

                break;
            }else{
                listHakgi.add(object.get("년도/학기").getAsString());
                listCat.add(object.get("영역명").getAsString());
                listTitle.add(object.get("도서명").getAsString());
                listDate.add(object.get("응시일자").getAsString());
                listScore.add(object.get("점수").getAsString());
                listIspass.add(object.get("합격여부").getAsString());

                Data_Recycle_Detail data = new Data_Recycle_Detail();
                data.setThak(listHakgi.get(i));
                data.setTcat(listCat.get(i));
                data.setTtitle(listTitle.get(i));
                data.setTdate(listDate.get(i));
                data.setTscore(listScore.get(i) + ":  " + listIspass.get(i));
                data.setNoData("0");
                switch(object.get("영역명").getAsString()){
                    case "서양의역사와사상": data.setImgcolor("#CC87F8"); break;
                    case "동양의역사와사상": data.setImgcolor("#F5D648"); break;
                    case "동서양의문학": data.setImgcolor("#53B1BC"); break;
                    case "과학사상": data.setImgcolor("#407F2A"); break;
                }
                adapter.addItem(data);
                adapter.notifyDataSetChanged();

            }
        }

        for(int i=0;i<alterAuthArray.size();i++){
            JsonObject object = (JsonObject)alterAuthArray.get(i);
            /*
                "년도/학기":"2017년도1학기",
                "과목명":"서양고전강독1",
                "영역명":"서양의역사와사상",
                "도서명":"성어거스틴의고백록",
                "이수여부":"이수"
             */
            if(object.get("년도/학기").getAsString().equals("검색된결과가없습니다.")){
                Data_Recycle_Detail data = new Data_Recycle_Detail();
                data.setThak(" ");
                data.setTcat(" ");
                data.setTtitle("검색된결과가없습니다.");
                data.setTdate(" ");
                data.setTscore(" ");

                adapter2.addItem(data);
                adapter2.notifyDataSetChanged();
            }else{
                list_alHakgi.add(object.get("년도/학기").getAsString());
                list_alCourse.add(object.get("과목명").getAsString());
                list_alCat.add(object.get("영역명").getAsString());
                list_alTitle.add(object.get("도서명").getAsString());
                list_alPass.add(object.get("이수여부").getAsString());

                Data_Recycle_Detail data = new Data_Recycle_Detail();
                data.setThak(list_alHakgi.get(i));
                data.setTcat(list_alCourse.get(i));
                data.setTtitle(list_alTitle.get(i));
                data.setTdate(list_alCat.get(i));
                data.setTscore(list_alPass.get(i));
                data.setNoData("0");
                switch(object.get("영역명").getAsString()){
                    case "서양의역사와사상": data.setImgcolor("#CC87F8"); break;
                    case "동양의역사와사상": data.setImgcolor("#F5D648"); break;
                    case "동서양의문학": data.setImgcolor("#53B1BC"); break;
                    case "과학사상": data.setImgcolor("#407F2A"); break;
                }
                adapter2.addItem(data);
                adapter2.notifyDataSetChanged();
            }

        }

        for(int i=0;i<chalAuthArray.size();i++){
            JsonObject object = (JsonObject)chalAuthArray.get(i);
            /*
                "년도/학기":"검색된결과가없습니다.",
                "대회명":"",
                "영역명":"",
                "도서명":""
             */

            if(object.get("년도/학기").getAsString().equals("검색된결과가없습니다.")){
                Data_Recycle_Detail data = new Data_Recycle_Detail();
                data.setThak(" ");
                data.setTcat(" ");
                data.setTtitle("검색된결과가없습니다.");
                data.setTdate(" ");
                data.setTscore(" ");
                data.setImgcolor("#CC87F8");
                data.setNoData("1");
                adapter3.addItem(data);
                adapter3.notifyDataSetChanged();
            }else{
                list_chHakgi.add(object.get("년도/학기").getAsString());
                list_chname.add(object.get("대회명").getAsString());
                list_chCat.add(object.get("영역명").getAsString());
                list_chTitle.add(object.get("도서명").getAsString());

                Data_Recycle_Detail data = new Data_Recycle_Detail();
                data.setThak(list_chHakgi.get(i));
                data.setTcat(list_chCat.get(i));
                data.setTtitle(list_chTitle.get(i));
                data.setTdate(list_chname.get(i));
                data.setTscore(" ");
                data.setNoData("0");
                switch(object.get("영역명").getAsString()){
                    case "서양의역사와사상": data.setImgcolor("#CC87F8"); break;
                    case "동양의역사와사상": data.setImgcolor("#F5D648"); break;
                    case "동서양의문학": data.setImgcolor("#53B1BC"); break;
                    case "과학사상": data.setImgcolor("#407F2A"); break;
                }
                adapter3.addItem(data);
                adapter3.notifyDataSetChanged();
            }
        }
    }
}
