package com.example.sassydesign;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lib.kingja.switchbutton.SwitchMultiButton;

//사용자가 손으로 직접 추가하는 화면
//hand_add.xml
public class HandAddActivity extends AppCompatActivity {

    Button dateButton;
    Date selectedDate;
    Button itemDetailAddButton;
    SwitchMultiButton switchInOutButton;
    SwitchMultiButton switchCCButton;
    String inOrOut="지출";
    String cashCard ="카드";
    String title="";
    EditText itemDetailTitle;
    Button completeButton;
    ItemDetailAdapter adapter;
    Spinner category;

    public static Activity handAddActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hand_add);

        handAddActivity = HandAddActivity.this;


        //리싸이클러뷰 선언, 초기화, 어댑터 초기화
        RecyclerView recyclerView = findViewById(R.id.handAddRecyclerView);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ItemDetailAdapter();

        category = findViewById(R.id.categorySpinner);
        //예시로 넣은 상세 품목들
        adapter.addItem(new ItemDetail("과자", "1500", "1", null));
        adapter.addItem(new ItemDetail("음료수", "2500","2",null));
        adapter.addItem(new ItemDetail("커피", "3000", "3",null));

        recyclerView.setAdapter(adapter);


        //+버튼 누르면 상세 품목 작성 칸 추가
        itemDetailAddButton = findViewById(R.id.itemDetailAddButton);
        itemDetailAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDetail itemDetail = null;
                //기본 수량을 1로 설정
                itemDetail = new ItemDetail("", "", "1", null);

                adapter.addItem(itemDetail);
                recyclerView.setAdapter(adapter);
            }
        });


        //날짜버튼 누르면 날짜선택(이 아래부터 건들면 안 됨)
        dateButton = findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (year_string + "/" + month_string+"/"+day_string);
        dateButton.setText(dateMessage);

    }

    //완료버튼 누르면 데이터베이스에 전송
    public void OnClickCompleteButton(View view) {
        boolean CheckBlank = false;

        //사용자가 입력한 제목 title로 받아오기
        itemDetailTitle = findViewById(R.id.itemDetailTitle);
        title = itemDetailTitle.getText().toString();


        //사용자가 선택한 수입/지출 inOrOut으로 받아오기
        switchInOutButton = findViewById(R.id.switchInOutButton);
        if(switchInOutButton.getSelectedTab() == 0){
            inOrOut = "수입";
        }
        else if (switchInOutButton.getSelectedTab() == 1){
            inOrOut = "지출";
        }
//
        

        //사용자가 선택한 현금/카드 cashCard로 받아오기
        switchCCButton = findViewById(R.id.switchCCButton);
        if(switchCCButton.getSelectedTab() == 0){
            cashCard = "카드";
        }
        else if (switchCCButton.getSelectedTab() == 1){
            cashCard = "현금";
        }
        else if (switchCCButton.getSelectedTab() == 2) {
            cashCard = "기타";
        }

        //날짜 선택하는 버튼 누르면 선택된 날짜 selectedDate에 받아오기
        //selectedDate를 데베에 넣음
        if (!((dateButton.getText().toString()).equals("날짜"))) {
            Date selectedDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/mm/dd");
            String dateMsg = dateButton.getText().toString();
            try {
                selectedDate = simpleDateFormat.parse(dateMsg);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        for(int i=0; i< adapter.getItemCount(); i++) {
            //상세 품목 객체 하나를 tmp에 받아옴
            ItemDetail tmp = adapter.getItem(i);


            //tmp의 get함수로 받아서 데베에 넣기

            String productName = "";
            String productCost = "";
            productName = tmp.getProductName();
            productCost = tmp.getProductCost();
            Spinner category = tmp.getCategory();
            String productQuantity = tmp.getProductQuantity();
            //String selectedCategory = category.getSelectedItem().toString();
            String selectedCategory =  tmp.getProductCategory();
            Toast.makeText(getApplicationContext(), "제목: " + title + " 수입/지출: " + inOrOut + " 카드/현금:" + cashCard, Toast.LENGTH_LONG).show();

            Toast.makeText(getApplicationContext(), "상품명:" + productName + " 가격:" + productCost + " 수량:" + productQuantity + "카테고리:" + selectedCategory , Toast.LENGTH_LONG).show();
            if (title.equals("") || productName.equals("") || productCost.equals("")){
                CheckBlank = false;

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("빈칸이 있습니다").setMessage("빈칸을 다 채워주세요");

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else{
                CheckBlank = true;
            }

        }

        //위에서 받아온 수입지출, 카드현금, 날짜, 제목 데베에 넣기




        //액티비티 종료(건들면 안 됨)
        if (CheckBlank == true) {
            HandAddActivity HA = (HandAddActivity) HandAddActivity.handAddActivity;
            HA.finish();
        }
    }

    public void checkSwitch(int position) {
        if (position == 0) {
            inOrOut = "수입";
        }
        else{
            inOrOut = "지출";
        }
    }


}