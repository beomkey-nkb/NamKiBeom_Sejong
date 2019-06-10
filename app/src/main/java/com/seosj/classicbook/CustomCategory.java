package com.seosj.classicbook;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CustomCategory extends LinearLayout {

    //메인화면 세부카테고리 커스텀위한 코드
    LinearLayout bg;
    TextView symbol1;
    TextView symbol2;

    public CustomCategory(Context context){
        super(context);
        initView();
    }
    public CustomCategory(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }
    public CustomCategory(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView(){
        String infService=Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_category, this, false);
        addView(v);

        bg = findViewById(R.id.bg);
        symbol1 = findViewById(R.id.text_symbol1);
        symbol2 = findViewById(R.id.text_symbol2);

    }

    private void getAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomCat);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.CustomCat, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray){
        int bg_resID = typedArray.getResourceId(R.styleable.CustomCat_bg,R.color.color_cat_1);
        bg.setBackgroundResource(bg_resID);

        String text_string1= typedArray.getString(R.styleable.CustomCat_symbol1);
        symbol1.setText(text_string1);

        String text_string2= typedArray.getString(R.styleable.CustomCat_symbol2);
        symbol2.setText(text_string2);

        typedArray.recycle();
    }


}
