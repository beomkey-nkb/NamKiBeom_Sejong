package com.seosj.classicbook;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Webview_setting_1 extends AppCompatActivity {

    private WebView mwv;//mobile webview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_setting_1);

        Intent intent = getIntent();
        String op = intent.getStringExtra("case");

        mwv=findViewById(R.id.webview_setting_1);

        WebSettings mws = mwv.getSettings();
        mws.setJavaScriptEnabled(true);//자바스크립트 허용
        mws.setLoadWithOverviewMode(true);//컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정

        mwv.setWebViewClient(new WebViewClient(){
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                final Uri uri = request.getUrl();
                final String host = uri.getHost();
                final String scheme = uri.getScheme();
                view.loadUrl(uri.toString());
                return true;
            }

        });
        if(op.equals("1")) {
            mwv.loadUrl("http://classic.sejong.ac.kr/info/MAIN_02_02.do");
        }else if(op.equals("2")){
            mwv.loadUrl("http://classic.sejong.ac.kr/info/MAIN_02_03.do");
        }
    }
    //   추가전에 뒤로가기 이벤트 호출시 홈으로 돌아갔으나, 이젠 일반적인 뒤로가기 기능 활성화
    @Override
    public void onPause() {
        super.onPause();
        mwv.onPause();
    }
}
