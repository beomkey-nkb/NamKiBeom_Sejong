package com.seosj.classicbook;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Menu2_testBookChoose extends AppCompatActivity {

    ArrayAdapter<CharSequence> book1,book2;
    TextView stu_name;
    TextView stu_id;
    TextView stu_major;
    TextView stu_date;
    TextView stu_time;
    String choice_select="도서명";
    Spinner spin1;
    Spinner spin2;
    Button gobutton;
    String ISBN = "0";
    String ISBN_sel="1";

    String yoil;
    String tdate;
    String ttime;

    View header;
    TextView textdate;
    TextView textname;

    String url = "http://15.164.113.118:3000/?status=2";
    String urlid ="&id=";
    String urlti ="&title=";
    String urlhak = "&hakgi=2019-1학기";
    String urldate = "&date=";
    String urltime = "&time=";
    String urlday = "&day=";

    /*
    //status = 2 (예약정보 db에 insert)

    //url
    http://15.164.113.118:3000/?status=2&id=14011110&title=총균쇠&hakgi=2017-2학기&date=2018-09-18&time=04:00&day=월

    //변수
    status : 2
    id : 학번
    title : 책이름
    hakgi : 학기
    data : 날짜
    time : 시간
    day : 요일정보

    전부 스트링형태로 db저장됨

    //리턴값

    만약 예약정보가 있으면 {"id" : "0"}
    예약정보가 없으면 {"id" : "1"}  뜨고 데이터 저장함

    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2_test_book_choose);

        stu_name=findViewById(R.id.text_stu_name);
        stu_id=findViewById(R.id.text_stu_id);
        stu_major=findViewById(R.id.text_stu_major);
        stu_date=findViewById(R.id.text_stu_bookdate);
        stu_time=findViewById(R.id.text_stu_booktime);

        spin1=findViewById(R.id.spinner_menu2_1);
        spin2=findViewById(R.id.spinner_menu2_2);

        gobutton = findViewById(R.id.btn_gobook);

        //기본 SharedPreference를 가져옴. (LoginActivity에서 설정한 pref)
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        tdate = sharedPref.getString("Test_Date_Year","null");
        Intent intent = getIntent();
        ttime = intent.getExtras().getString("time");

        JSONParser js = (JSONParser)getApplicationContext();


        //자동으로 불러
        stu_name.setText(js.getStu_name());
        stu_id.setText(js.getStu_num());
        stu_major.setText(js.getMajor());
        //
        stu_date.setText(tdate);
        stu_time.setText(ttime);
        //

        //btn
        book1 = ArrayAdapter.createFromResource(this, R.array.book_cat,R.layout.spinner_settings);

        book1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(book1);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(book1.getItem(i).equals("서양의역사와사상")){
                    choice_select ="도서명";
                    //ISBN 초기화
                    ISBN = "0";
                    //서양도서목록으로 2번스피너 갱신
                    book2= ArrayAdapter.createFromResource(view.getContext(),R.array.book_name_seoyang,R.layout.spinner_settings);
                    book2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(book2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            //저장될 책이름
                            choice_select = book2.getItem(i).toString();
                            //ISBN 저장
                            if(i>0)
                            {
                                ISBN = ISBN_sel;
                            }else{
                                ISBN="0";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            //아무것도 선택안할시
                        }
                    });

                }else if(book1.getItem(i).equals("동양의역사와사상")){
                    choice_select ="도서명";
                    //ISBN 초기화
                    ISBN = "0";
                    //동양도서목록으로 2번스피너 갱신
                    book2= ArrayAdapter.createFromResource(view.getContext(),R.array.book_name_dongyang,R.layout.spinner_settings);
                    book2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(book2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            //저장될 책이름
                            choice_select = book2.getItem(i).toString();
                            //ISBN 저장
                            if(i>0){
                                ISBN =ISBN_sel;
                            }
                            else{
                                ISBN="0";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            //아무것도 선택안할시
                        }
                    });
                }else if(book1.getItem(i).equals("동서양의문학")){
                    choice_select ="도서명";
                    //ISBN 초기화
                    ISBN = "0";
                    //동양도서목록으로 2번스피너 갱신
                    book2= ArrayAdapter.createFromResource(view.getContext(),R.array.book_name_dong_seo,R.layout.spinner_settings);
                    book2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(book2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            //저장될 책이름
                            choice_select = book2.getItem(i).toString();
                            //ISBN 저장
                            if(i>0){
                                ISBN =ISBN_sel;
                            }else{
                                ISBN="0";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            //아무것도 선택안할시
                        }
                    });
                }else if(book1.getItem(i).equals("과학사상")){
                    choice_select ="도서명";
                    //ISBN 초기화
                    ISBN = "0";
                    //동양도서목록으로 2번스피너 갱신
                    book2= ArrayAdapter.createFromResource(view.getContext(),R.array.book_name_science,R.layout.spinner_settings);
                    book2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(book2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            //저장될 책이름
                            choice_select = book2.getItem(i).toString();
                            //ISBN 저장
                            if(i>0){
                                ISBN =ISBN_sel;
                            }else{
                                ISBN="0";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            //아무것도 선택안할시
                        }
                    });
                }
                else if(book1.getItem(i).equals("영역명")){
                    choice_select ="도서명";
                    //ISBN 초기화
                    ISBN = "0";
                    //아무것도없을시
                    book2= ArrayAdapter.createFromResource(view.getContext(),R.array.book_name_default,R.layout.spinner_settings);
                    book2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(book2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            //선택할게없음
                            //ISBN 초기화
                            ISBN = "0";
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            //아무것도 선택안할시
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gobutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(ISBN.equals("0")){
                    //alertDialog띄우
                    new SweetAlertDialog(Menu2_testBookChoose.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("책을 선택해 주세요.")
                            .show();
                }else {
                    yoil = sharedPref.getString("Yo","null");

                    String sendurl = url + urlid + js.getStu_num()
                            +urlti + choice_select + urlhak + urldate + tdate + urltime + ttime
                            +urlday + yoil;

                    TryReserv tryReserv = new TryReserv(sendurl, null);
                    tryReserv.execute();
                }
            }
        });

    }

    public class TryReserv extends AsyncTask<String, Void, String> {

        ProgressDialog asyncDialog = new ProgressDialog(Menu2_testBookChoose.this);

        private String urlz;
        private ContentValues values;

        private TryReserv(String url, ContentValues values) {

            this.urlz = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute(){
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("잠시만 기다려 주세요...");

            //show dialog
            asyncDialog.show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //전송하기 위한 스트링 변수

            String turl = urlz;

            String result;

            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(turl, values);

            return result;
        }

        @Override
        protected void onPostExecute(String result){
            asyncDialog.dismiss();
            super.onPostExecute(result);

            if(result.equals("{\"id\":\"0\"}")){
                new SweetAlertDialog(Menu2_testBookChoose.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("같은 주에 이미 예약하셨습니다. 다시 시도하세요")
                        .show();
                //Toast.makeText(Menu2_testBookChoose.this,"같은 주에 이미 예약하셨습니다. 다시 시도하세요",Toast.LENGTH_SHORT).show();
            }else{
                header = getLayoutInflater().inflate(R.layout.recycle_thisweektest,null,false);
                textdate = header.findViewById(R.id.textView1);
                textname = header.findViewById(R.id.textView2);

                Toast.makeText(Menu2_testBookChoose.this, "예약을 성공하였습니다",Toast.LENGTH_SHORT).show();
                Intent inte = new Intent(getApplicationContext(),MainActivity.class);
                inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(inte);
                finish();
            }
        }
    }





}
