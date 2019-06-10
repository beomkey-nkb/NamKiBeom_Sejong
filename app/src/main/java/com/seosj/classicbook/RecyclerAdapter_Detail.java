package com.seosj.classicbook;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter_Detail extends RecyclerView.Adapter<RecyclerAdapter_Detail.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Data_Recycle_Detail> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerAdapter_Detail.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_detail, parent, false);
        return new RecyclerAdapter_Detail.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter_Detail.ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Data_Recycle_Detail data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView txhak;
        private TextView txdate;
        private TextView txtitle;
        private TextView txcat;
        private TextView txscore;
        private ImageView imgic;

        ItemViewHolder(View itemView) {
            super(itemView);
            txhak = itemView.findViewById(R.id.t_hakgi);
            txdate = itemView.findViewById(R.id.t_date);
            txtitle = itemView.findViewById(R.id.td_title);
            txcat = itemView.findViewById(R.id.td_cat);
            txscore = itemView.findViewById(R.id.td_score);
            imgic = itemView.findViewById(R.id.img_det);

        }

        void onBind(Data_Recycle_Detail data) {
            txhak.setText(data.getThak());
            txdate.setText(data.getTdate());
            txtitle.setText(data.getTtitle());
            txcat.setText(data.getTcat());
            txscore.setText(data.getTscore());
            if(data.getNoData().equals("1")){
                imgic.setVisibility(View.INVISIBLE);
            }else{
                imgic.setVisibility(View.VISIBLE);
            }
            GradientDrawable bgs = (GradientDrawable) imgic.getBackground();
            bgs.setColor(Color.parseColor(data.getImgcolor()));

        }
    }
}