package com.seosj.classicbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Menu3Fragment extends Fragment{
    ArrayAdapter<CharSequence> book1,book2;
    String choice_do="";
    String choice_select="도서명";//도서명 판별
    Spinner spin1;
    Spinner spin2;
    ImageView searchbutton;
    String ISBN = "0";
    String[] ISBN_seo = {"9788930606233","9788991290280","9788930606240","9788908062238","9788951105630","9788972914440","9788935652921","9788949705293","9788972914280","9788901110837","9788970136226","9788930610407","9788964545638","9788957330241","9788937460944","9788931000443","9788935601950", "9788972914297","9788931000023","9788935651733","9788970556826","9788932471389","9788974531881","9788935651740","9788935664153","9788970840659","9788930031363","9788930088572","9788949714318","9788978000468","9788941991120","9788935652860","9788927806615","9788955592115","9788949703879","9788956441160","9788935656615","9788972910251"};
    String[] ISBN_dong = {"9788970656038","9788932471723","9788937603488","9788935656592","9788970650340","9788970650357","9788970650364","9788971393185","9788932307565","9788937425967","9788931004137","9788932452456","9791186558102","9788982640599","9788970300153","9788974187699","9788936471712","9788937423185","9788935914012"};
    String[] ISBN_dongseo = {"9788932911892","9788931001839","9788936603373","9788931006643","9788949704111","8991290035","9788986045536","9788949706481","9788937462672","9788993800593","9788934405184","9788970126937", "9788937460043","9788970962665","9788949706719","9788935912629","9788935911028","9788937426865","9788932015705","9788937462122","9788949714066","9788932015149","9788931004786","9788983920775","9788972883906","9788932317960","9788937462757","9788937484018","9788954613934","9788932015552","9788952237781","9791187192459"};
    String[] ISBN_science = {"9788981721312","9788949705132","9788942389056","9788972912569","8980730705","9788983711540","9788970128856","9788986698824","9788955591484","9788972911395"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_3, container, false);

        spin1=v.findViewById(R.id.spinner_menu3_1);
        spin2=v.findViewById(R.id.spinner_menu3_2);
        searchbutton=v.findViewById(R.id.button_menu3_search);

        //btn
        book1 = ArrayAdapter.createFromResource(v.getContext(), R.array.book_cat,R.layout.spinner_settings);

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
                    book2= ArrayAdapter.createFromResource(v.getContext(),R.array.book_name_seoyang,R.layout.spinner_settings);
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
                                ISBN = ISBN_seo[i-1];
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
                    book2= ArrayAdapter.createFromResource(v.getContext(),R.array.book_name_dongyang,R.layout.spinner_settings);
                    book2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(book2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            //저장될 책이름
                            choice_select = book2.getItem(i).toString();
                            //ISBN 저장
                            if(i>0){
                                ISBN =ISBN_dong[i-1];
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
                    book2= ArrayAdapter.createFromResource(v.getContext(),R.array.book_name_dong_seo,R.layout.spinner_settings);
                    book2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(book2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            //저장될 책이름
                            choice_select = book2.getItem(i).toString();
                            //ISBN 저장
                            if(i>0){
                                ISBN =ISBN_dongseo[i-1];
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
                    book2= ArrayAdapter.createFromResource(v.getContext(),R.array.book_name_science,R.layout.spinner_settings);
                    book2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(book2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            //저장될 책이름
                            choice_select = book2.getItem(i).toString();
                            //ISBN 저장
                            if(i>0){
                                ISBN =ISBN_science[i-1];
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
                    book2= ArrayAdapter.createFromResource(v.getContext(),R.array.book_name_default,R.layout.spinner_settings);
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

        searchbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(ISBN.equals("0")){
                 //alertDialog띄우
                    new SweetAlertDialog(v.getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("책을 선택해 주세요.")
                            .show();

                }else {

                    Intent intent = new Intent(v.getContext(), Menu3_searchLibrary.class);
                    intent.putExtra("ISBN",ISBN);
                    intent.putExtra("Book",choice_select);
                    startActivity(intent);
                }
            }
        });


        return v;
    }

}
