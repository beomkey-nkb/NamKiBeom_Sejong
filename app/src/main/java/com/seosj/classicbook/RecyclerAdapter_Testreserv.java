package com.seosj.classicbook;

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

public class RecyclerAdapter_Testreserv extends RecyclerView.Adapter<RecyclerAdapter_Testreserv.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> listData = new ArrayList<>();
    public Context context;
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_reserv_test, parent, false);
        context = parent.getContext();

        SharedPreferences sharedPref1 = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        SharedPreferences.Editor editor = sharedPref1.edit();

        //키의 값을 원하는 string으로 변경
        editor.putString("Test_Date_Year", "null");

        editor.commit();

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView1;
        private TextView textView2;
        private ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.text_test_timedetail);
            textView2 = itemView.findViewById(R.id.text_test_locdetail);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void onBind(Data data) {
            textView1.setText(data.getTitle());
            textView2.setText(data.getContent());
            imageView.setImageResource(data.getResId());

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.recycle_testdatelist:
                    Intent intent = new Intent(v.getContext(),Menu2_testBookChoose.class);
                    intent.putExtra("time",textView1.getText());

                    //기본 SharedPreference를 가져옴. (LoginActivity에서 설정한 pref)
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    //"set_term" 키의 값을 원하는 string으로 변경
                    if(sharedPref.getString("Test_Date_Year","null").equals("null")){
                            //alertDialog띄우
                            new SweetAlertDialog(v.getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("시험을 볼 날짜를 선택해 주세요.")
                                .show();
                    }else {
                        context.startActivity(intent);
                    }
                    break;

            }
        }

    }
}