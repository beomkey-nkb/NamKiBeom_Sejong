package com.seosj.classicbook;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Menu2Fragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private RecyclerAdapter_Testreserv adapter;
    private Calendar pickedDate = Calendar.getInstance();
    private Calendar minDate = Calendar.getInstance();//다음날
    private Calendar maxDate = Calendar.getInstance();//
    TextView DatePickerText;
    public Context context;

    //시험 예약화면
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_2, container, false);
        context = v.getContext();
        DatePickerText = v.findViewById(R.id.text_menu2_datepicker);

        //최소/최대 날짜 선택 가능 지정
        minDate.add(Calendar.DATE, 1);
        maxDate.add(Calendar.DATE, 30);

        DatePickerText.setOnClickListener(v1 -> setDate());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView_testdate);
        recyclerView.setLayoutManager(linearLayoutManager);
        //리사이클러뷰 선
        recyclerView.addItemDecoration(new DividerItemDecoration(v.getContext(),1));
        adapter = new RecyclerAdapter_Testreserv();
        recyclerView.setAdapter(adapter);


        getData();


        return v;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        DatePickerText.setText(date);

        //기본 SharedPreference를 가져옴. (LoginActivity에서 설정한 pref)
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        //Preference 자료 수정을 위하여 editor 생성
        SharedPreferences.Editor edit = sharedPref.edit();

        String datee = Integer.toString(year)+Integer.toString((monthOfYear+1))+Integer.toString(dayOfMonth);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMdd");

        try {
            Date nDate = dateFormat.parse(datee);
            Calendar cal = Calendar.getInstance();
            cal.setTime(nDate);
            int dayNum = cal.get(Calendar.DAY_OF_WEEK);
            switch (dayNum){
                case 1: edit.putString("Yo","일"); break;
                case 2: edit.putString("Yo","월"); break;
                case 3: edit.putString("Yo","화"); break;
                case 4: edit.putString("Yo","수"); break;
                case 5: edit.putString("Yo","목"); break;
                case 6: edit.putString("Yo","금"); break;
                case 7: edit.putString("Yo","토"); break;
            }

            edit.putString("Test_Date_Year", DatePickerText.getText().toString());
            edit.commit();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void setDate(){

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                pickedDate.get(Calendar.YEAR),
                pickedDate.get(Calendar.MONTH),
                pickedDate.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMinDate(minDate);
        dpd.setMaxDate(maxDate);

        //주말 안되게
        Calendar saturday;
        Calendar sunday;
        List<Calendar> weekends = new ArrayList<>();
        int weeks = 5;

        for (int i = 0; i < (weeks * 7) ; i = i + 7) {
            sunday = Calendar.getInstance();
            sunday.add(Calendar.DAY_OF_YEAR, (Calendar.SUNDAY - sunday.get(Calendar.DAY_OF_WEEK) + 7 + i));
            saturday = Calendar.getInstance();
            saturday.add(Calendar.DAY_OF_YEAR, (Calendar.SATURDAY - saturday.get(Calendar.DAY_OF_WEEK) + i));
            weekends.add(saturday);
            weekends.add(sunday);
        }
        Calendar[] disabledDays = weekends.toArray(new Calendar[weekends.size()]);
        dpd.setDisabledDays(disabledDays);

        dpd.show(this.getActivity().getFragmentManager(),"Datepickerdialog");

    }

    private void getData(){

        List<String> listTitle = Arrays.asList(
                getString(R.string.text_time1000), getString(R.string.text_time1020), getString(R.string.text_time1040), getString(R.string.text_time1100), getString(R.string.text_time1120), getString(R.string.text_time1140),
                getString(R.string.text_time1400), getString(R.string.text_time1420), getString(R.string.text_time1440), getString(R.string.text_time1500), getString(R.string.text_time1520),
                getString(R.string.text_time1540), getString(R.string.text_time1600), getString(R.string.text_time1620), getString(R.string.text_time1640)
        );
       List<String> listContent = Arrays.asList(
               getString(R.string.text_test_locaion)
       );
        List<Integer> listResId = Arrays.asList(R.drawable.ic_button_clickarrow);

        for(int i=0;i<listTitle.size();i++) {
            Data data = new Data();
            data.setTitle(listTitle.get(i));
            data.setContent(listContent.get(0));
            data.setResId(listResId.get(0));
            adapter.addItem(data);
        }
        adapter.notifyDataSetChanged();

    }


}
