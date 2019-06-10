package com.seosj.classicbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RecyclerAdapter_Setting extends RecyclerView.Adapter<RecyclerAdapter_Setting.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Data_Recycle_Setting> listData1 = new ArrayList<>();
    public Context context;
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_settings, parent, false);
        context = parent.getContext();

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData1.get(position));

    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData1.size();
    }

    void addItem(Data_Recycle_Setting data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData1.add(data);
    }
    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView1;
        private TextView textView1;
        private TextView textView2;
        private ImageView imageView2;
        private Data_Recycle_Setting data;
        ItemViewHolder(View itemView) {
            super(itemView);

            imageView1 = itemView.findViewById(R.id.image_setting_menu);
            textView1 = itemView.findViewById(R.id.text_setting_menu);
            textView2 = itemView.findViewById(R.id.text_setting_userid);
            imageView2 = itemView.findViewById(R.id.image_setting_arrow);
        }

        void onBind(Data_Recycle_Setting data) {
            this.data = data;
            imageView1.setImageResource(data.getResId1());
            textView1.setText(data.getTitle());
            textView2.setText(data.getUser_id());
            imageView2.setImageResource(data.getResId2());

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.setting_linearitembox:
                    if(data.getTitle().equals("인증방법 및 절차")){
                        Intent intent = new Intent(v.getContext(),Webview_setting_1.class);
                        intent.putExtra("case","1");
                        v.getContext().startActivity(intent);
                    }else if(data.getTitle().equals("인증도서 목록")){
                        Intent intent = new Intent(v.getContext(),Webview_setting_1.class);
                        intent.putExtra("case","2");
                        v.getContext().startActivity(intent);
                    }else if(data.getTitle().equals("로그아웃")){
                        new SweetAlertDialog(v.getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("로그아웃시 알림설정이 전부 삭제됩니다.")
                                .setContentText("계속하시겠습니까?")
                                .setConfirmText("로그아웃")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        //기본 SharedPreference를 가져옴. (LoginActivity에서 설정한 pref)
                                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                                        SharedPreferences.Editor editor = sharedPref.edit();

                                        editor.clear();

                                        editor.commit();

                                        Intent inte = new Intent(v.getContext(),LoginActivity.class);
                                        inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        v.getContext().startActivity(inte);
                                        ((Activity)context).finish();

                                    }
                                })
                                .setCancelButton("뒤로가기", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();

                    }
                    break;
            }
        }

    }

}