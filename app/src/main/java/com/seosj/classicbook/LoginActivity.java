package com.seosj.classicbook;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gun0912.tedpermission.PermissionListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/*
* ******************************************
* Copyright 2019. 서성준 all rights reserved.
* ******************************************
 */
public class LoginActivity extends AppCompatActivity {

    public String loginURL = "http://15.164.113.118:3000/?status=1&";
    public String loginID = "id=";
    public String loginPW = "&password=";
    //public JsonObject json = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText text_id = findViewById(R.id.textID);
        EditText text_pw = findViewById(R.id.textPW);
        Button button_login=findViewById(R.id.buttonLogin);
        CheckBox autoLogin = findViewById(R.id.autologin);

        //자동로그인 정보 저장
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        String auID = pref.getString("ID","null");
        String auPW = pref.getString("PW","null");

        //자동로그인시 바로 로그인 시도
        if(pref.getString("auto_login","0").equals("1")){
            autoLogin.setChecked(true);

            System.out.println("login info : " + auID + auPW);

            text_id.setText(auID);
            text_pw.setText(auPW);

            String url = loginURL + loginID + auID + loginPW + auPW;

            LoginTask loginTask = new LoginTask(url,null);
            loginTask.execute();
        }

        /*권한 얻기
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("권한 설정")
                .setDeniedMessage("거부하셨습니다.\n어플리케이션을 원활히 이용하기 위해서는\n" +
                        "[설정]->[어플리케이션]->[권한]에서 권한을 허용해 주시기 바랍니다.")
                .setPermissions()
        */

        //다음화면 넘기기
        button_login.setOnClickListener((View v) -> {

            if ( text_id.getText().toString().length() == 0 ) {
                //공백일 때 처리할 내용
                new SweetAlertDialog(this)
                        .setTitleText("ID를 입력해 주세요")
                        .show();
            } else if(text_pw.getText().toString().length() == 0){
                //공백일 때 처리할 내용
                new SweetAlertDialog(this)
                        .setTitleText("PW를 입력해 주세요")
                        .show();
            }else {
                String uID;
                String uPW;
                uID = text_id.getText().toString();
                uPW = text_pw.getText().toString();
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("ID",uID);
                editor.putString("PW",uPW);
                editor.commit();

                if(autoLogin.isChecked()){
                    //TODO
                    editor.putString("auto_login","1");
                    editor.commit();
                }else{
                    //TODO
                    editor.putString("auto_login","0");
                    editor.commit();
                }

                String url = loginURL + loginID + uID + loginPW + uPW;

                LoginTask loginTask = new LoginTask(url,null);
                loginTask.execute();
            }

        });

    }
    public class LoginTask extends AsyncTask<String, Void, String>{

        SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.PROGRESS_TYPE);
        //ProgressDialog asyncDialog = new ProgressDialog(LoginActivity.this);

        private String url;
        private ContentValues values;

        private LoginTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute(){
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("로그인 중입니다...");
            pDialog.setCancelable(false);
            //show dialog
            pDialog.show();


           // asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
           // asyncDialog.setMessage("잠시만 기다려 주세요...");

           // asyncDialog.show();

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
        protected void onPostExecute(String result){
            pDialog.dismiss();
            //asyncDialog.dismiss();
            super.onPostExecute(result);

            //파싱
            try {
                JsonParser Parser = new JsonParser();
                JsonObject jsonObj = (JsonObject) Parser.parse(result);
                //전역변수에 저장선언
                JSONParser jsglobal = (JSONParser)getApplicationContext();

                jsglobal.setStat(jsonObj.get("status").getAsString());
                jsglobal.setMajor(jsonObj.get("학과").getAsString());
                jsglobal.setStu_num(jsonObj.get("학번").getAsString());
                jsglobal.setStu_name(jsonObj.get("이름").getAsString());

                jsglobal.setStat_auth((JsonArray) jsonObj.get("인증현황"));

                for(int i = 0;i<jsglobal.getStat_auth().size();i++){
                    JsonObject object = (JsonObject)jsglobal.getStat_auth().get(i);
                    jsglobal.setStat_auth_seo(object.get("서양의 역사와 사상").getAsString());//서양 -> ~권
                    jsglobal.setStat_auth_dong(object.get("동양의 역사와 사상").getAsString());//동양 -> ~권
                    jsglobal.setStat_auth_dongseo(object.get("동서양의 문학").getAsString());//동서양
                    jsglobal.setStat_auth_science(object.get("과학 사상").getAsString());//과학
                    jsglobal.setStat_auth_tot(object.get("합계").getAsString());//합계

                }

                jsglobal.setStat_test_auth((JsonArray) jsonObj.get("시험인증현황"));
                jsglobal.setStat_alter_auth((JsonArray) jsonObj.get("대체과목현황"));
                jsglobal.setStat_challenge_auth((JsonArray) jsonObj.get("대회인증현황"));

                if(jsglobal.getStat().equals("0")){
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("오류")
                            .setContentText("ID와 PW를 확인해 주세요")
                            .show();
                    //               Toast.makeText(LoginActivity.this,"ID와 PW를 확인해 주세요",Toast.LENGTH_SHORT).show();

                }else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }




            }catch (NullPointerException e){
                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("오류")
                        .setContentText("서버와의 통신이 원활하지 않습니다. \n다시 시도해 주세요")
                        .show();
            }

        }

    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(LoginActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(LoginActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };


}
