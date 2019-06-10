package com.seosj.classicbook;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Menu1_testinfo extends AppCompatActivity {

    TextView txname;
    TextView tx1;
    TextView tx2;
    TextView tx3;
    TextView txAlarm;
    TextView txAlarmlist;
    Button bt_cancel;
    Button bt_alarm;
    public String isal;
    public String adate;
    public String atime;
    public String[] ldate;
    public String[] ltime;
    public String furl;
    public String url1 = "http://15.164.113.118:3000/?status=4&id=";

    public String curl;
    public String crul2 = "http://15.164.113.118:3000/?status=3&id=";
    /*
    //status = 4 (예약 확인)
    //url
    http://15.164.113.118:3000/?status=4&id=14011110
    //변수
    status : 4
    id : 학번
    //리턴값
    예약 정보가 있으면(예시)
    [{"id":"14011110","title":"총균쇠","hakgi":"2017-2학기","date":"2018-09-18","time":"04:00","day":"월"}]
    예약 정보가 없으면
    [{"id":"1","title":"","hakgi":"","date":"","time":"","day":""}]

    id가 1인지 아닌지 판별해야함
     */

    //테스트 예약 정보부분
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1_testinfo);

        txname = findViewById(R.id.text_menu_test_bookname_tt);
        tx1 = findViewById(R.id.text_menu_test_1_detail);
        tx2 = findViewById(R.id.text_menu_test_2_detail);
        tx3 = findViewById(R.id.text_menu_test_3_detail);
        txAlarm = findViewById(R.id.text_alarmlist);
        txAlarmlist = findViewById(R.id.text_alarmlist2);

        bt_cancel = findViewById(R.id.btn_cancel);

        bt_alarm = findViewById(R.id.btn_setalarm);

        JSONParser js = (JSONParser) this.getApplicationContext();

        furl = url1 + js.getStu_num();
        curl = crul2 + js.getStu_num();


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isal = sharedPref.getString("Alarm","null");

        LoadInfo loadInfo = new LoadInfo(furl, null);
        loadInfo.execute();

        tx1.setText("2019년도 1학기");




        AlarmHATT ah = new AlarmHATT(getApplicationContext());



        bt_alarm.setOnClickListener((View v) -> {
            if(txAlarm.getText().toString().equals("현재 설정된 알림이 없습니다.")) {
                SharedPreferences.Editor ed = sharedPref.edit();
                ed.putString("Alarm","1");
                ed.commit();
                ah.Alarm();
                txAlarmlist.setText("예약시간 :  \t"+ adate +" "+(Integer.parseInt(ltime[0])-1) + ":" + ltime[1]);
                txAlarmlist.setVisibility(View.VISIBLE);
                txAlarm.setVisibility(View.GONE);
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("알림설정이 완료되었습니다.")
                        .show();

            }else{
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("이미 예약이 설정되어있습니다.")
                        .show();
            }

        });

        bt_cancel.setOnClickListener((View v) -> {
            //시험 정보 삭제
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("예약 취소")
                    .setContentText("예약을 취소하겠습니까?")
                    .setConfirmText("예약취소")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor ed = sharedPref.edit();
                            ed.remove("Alarm");
                            ed.commit();
                            Cancelinfo cancelinfo = new Cancelinfo(curl, null);
                            cancelinfo.execute();
                        }
                    })
                    .setCancelButton("뒤로가기", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        });

        txAlarmlist.setOnClickListener((View v) -> {
            //알림 정보 삭제
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("알림 취소")
                    .setContentText("알림을 취소하겠습니까?")
                    .setConfirmText("알림취소")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            SharedPreferences.Editor ed = sharedPref.edit();
                            ed.putString("Alarm","0");
                            ed.commit();
                            txAlarmlist.setVisibility(View.GONE);
                            txAlarm.setVisibility(View.VISIBLE);
                            ah.releaseAlarm(Menu1_testinfo.this,0);

                        }
                    })
                    .setCancelButton("뒤로가기", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();

        });
    }


    //알람 설정
    public class AlarmHATT {
        private Context context;

        public AlarmHATT(Context context) {
            this.context = context;
        }
        // 알람 추가 메소드
        public void Alarm() {
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(Menu1_testinfo.this, BroadcastD.class);
            intent.setAction(BroadcastD.ACTION_RESTART_SERVICE);
            PendingIntent sender = PendingIntent.getBroadcast(
                    getApplicationContext(), 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기

            int year=Integer.parseInt(ldate[0]);
            int month=Integer.parseInt(ldate[1]);
            int day=Integer.parseInt(ldate[2]);
            int ho = Integer.parseInt(ltime[0]);
            int min = Integer.parseInt(ltime[1]);

            calendar.set(year, month-1, day, ho-1, min, 0);

            AlarmManager.AlarmClockInfo ac = new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(),sender);
            am.setAlarmClock(ac, sender);
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

        }
        //알람 취소
        public void releaseAlarm(Context context, int requestCode){
            AlarmManager fiveToHourAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

            Intent fiveIntent = new Intent(context, BroadcastD.class);
            fiveIntent.setAction(BroadcastD.ACTION_RESTART_SERVICE);
            PendingIntent fiveSender = PendingIntent.getBroadcast(context, requestCode, fiveIntent, 0);

            fiveToHourAlarmManager.cancel(fiveSender);

            Log.d("NotiTEST", "AlarmUtil Canel");
        }


    }


    public class LoadInfo extends AsyncTask<String, Void, String> {

        ProgressDialog asyncDialog = new ProgressDialog(Menu1_testinfo.this);

        private String url;
        private ContentValues values;

        private LoadInfo(String url, ContentValues values) {

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
            String tbname = object.get("title").getAsString();
            String tbdate = object.get("date").getAsString();
            String tbtime = object.get("time").getAsString();

            adate = tbdate;
            atime = tbtime;
            ldate = adate.split("-");
            ltime = atime.split(":");
            if(isal.equals("1")){
                txAlarmlist.setText("예약시간 :  \t"+ adate +" "+(Integer.parseInt(ltime[0])-1) + ":" + ltime[1] + "\t");
                txAlarmlist.setVisibility(View.VISIBLE);
                txAlarm.setVisibility(View.GONE);
            }else{
                txAlarmlist.setVisibility(View.GONE);
                txAlarm.setVisibility(View.VISIBLE);
            }
            if (tbname.equals("1")) {

            } else {
                txname.setText(tbname);
                tx2.setText(tbdate);
                tx3.setText(tbtime + "부터 20분동안");
            }
        }

    }

    public class Cancelinfo extends AsyncTask<String, Void, String> {

        ProgressDialog asyncDialog = new ProgressDialog(Menu1_testinfo.this);

        private String url;
        private ContentValues values;

        private Cancelinfo(String url, ContentValues values) {

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

            //기본 SharedPreference를 가져옴. (LoginActivity에서 설정한 pref)
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor ed = sharedPref.edit();
            ed.remove("info_date");
            ed.remove("info_name");
            ed.commit();
            Toast.makeText(getApplicationContext(), "예약을 취소하였습니다", Toast.LENGTH_SHORT).show();
            Intent inte = new Intent(getApplicationContext(), MainActivity.class);
            inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(inte);
            finish();

        }
    }
}