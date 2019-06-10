package com.seosj.classicbook;


import android.app.Application;

import com.google.gson.JsonArray;


public class JSONParser extends Application {

    public String stat;//status 0 -> 로긴 실패 status 1 ->로긴 성공
    public String major;//학과
    public String stu_num;//학번
    public String stu_name;//이름

    public JsonArray stat_auth;//인증현황
    public String stat_auth_seo;//서양 -> ~권
    public String stat_auth_dong;//동양
    public String stat_auth_dongseo;//동서양
    public String stat_auth_science;//과학
    public String stat_auth_tot;//합계

    public JsonArray stat_test_auth;//시험인증현황

    public JsonArray stat_alter_auth;//대체과목현황

    public JsonArray stat_challenge_auth;//대회인증현황


    //-------------------getter-------------------//
    public String getStat() {
        return stat;
    }
    public String getMajor() {
        return major;
    }
    public String getStu_num() {
        return stu_num;
    }
    public String getStu_name() {
        return stu_name;
    }
    public JsonArray getStat_auth() {
        return stat_auth;
    }
    public String getStat_auth_seo() {
        return stat_auth_seo;
    }
    public String getStat_auth_dong() {
        return stat_auth_dong;
    }
    public String getStat_auth_dongseo() {
        return stat_auth_dongseo;
    }
    public String getStat_auth_science() {
        return stat_auth_science;
    }
    public String getStat_auth_tot() {
        return stat_auth_tot;
    }
    public JsonArray getStat_test_auth() {
        return stat_test_auth;
    }
    public JsonArray getStat_alter_auth() {
        return stat_alter_auth;
    }
    public JsonArray getStat_challenge_auth() {
        return stat_challenge_auth;
    }
    //--------------------setter------------------------//
    public void setStat(String stat) {
        this.stat = stat;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public void setStu_num(String stu_num) {
        this.stu_num = stu_num;
    }
    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }
    public void setStat_auth(JsonArray stat_auth) {
        this.stat_auth = stat_auth;
    }
    public void setStat_auth_seo(String stat_auth_seo) {
        this.stat_auth_seo = stat_auth_seo;
    }
    public void setStat_auth_dong(String stat_auth_dong) {
        this.stat_auth_dong = stat_auth_dong;
    }
    public void setStat_auth_dongseo(String stat_auth_dongseo) {
        this.stat_auth_dongseo = stat_auth_dongseo;
    }
    public void setStat_auth_science(String stat_auth_science) {
        this.stat_auth_science = stat_auth_science;
    }
    public void setStat_auth_tot(String stat_auth_tot) {
        this.stat_auth_tot = stat_auth_tot;
    }
    public void setStat_test_auth(JsonArray stat_test_auth) {
        this.stat_test_auth = stat_test_auth;
    }
    public void setStat_alter_auth(JsonArray stat_alter_auth) {
        this.stat_alter_auth = stat_alter_auth;
    }
    public void setStat_challenge_auth(JsonArray stat_challenge_auth) {
        this.stat_challenge_auth = stat_challenge_auth;
    }
    //-------------------------------------------//


    @Override
    public void onCreate(){
        super.onCreate();
    }
    @Override
    public void onTerminate(){
        super.onTerminate();
    }


}
