package com.example.sassydesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lib.kingja.switchbutton.SwitchMultiButton;

public class HandAddActivity extends AppCompatActivity {

    Button dateButton;
    Date selectedDate;
    Button itemDetailAddButton;
    SwitchMultiButton switchInOutButton;
    SwitchMultiButton switchCCButton;
    String inOrOut="";
    String CashCard="";
    String title="";
    EditText itemDetailTitle;
    Button completeButton;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hand_add);

        //제목 확인
        itemDetailTitle = findViewById(R.id.itemDetailTitle);
        title = itemDetailTitle.getText().toString();

        //수입 지출 확인

        switchInOutButton = findViewById(R.id.switchInOutButton);
        switchInOutButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                inOrOut = tabText;
            }
        });

        //카드 현금 확인
        switchCCButton = findViewById(R.id.switchCCButton);
        switchCCButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                CashCard = tabText;
            }
        });

        //날짜 확인
        //selectedDate를 데베에 넣음
        dateButton = findViewById(R.id.dateButton);
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



        //리싸이클러뷰
        RecyclerView recyclerView = findViewById(R.id.handAddRecyclerView);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ItemDetailAdapter adapter = new ItemDetailAdapter();

        adapter.addItem(new ItemDetail("과자", "1500", "1","식비"));
        adapter.addItem(new ItemDetail("음료수", "2500","2","식비"));
        adapter.addItem(new ItemDetail("커피", "3000", "3","식비"));


        recyclerView.setAdapter(adapter);
        
        //완료버튼 누르면 데이터베이스에 전송
        completeButton = findViewById(R.id.completeButton);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i< adapter.getItemCount(); i++) {
                    ItemDetail tmp = adapter.parsingData(i);
                    //이렇게 받은거로 데베에 넣기
                    String productName = tmp.getProductName();
                    String productCost = tmp.getProductCost();
                    String productQuantity = tmp.getQuantity();
                    String selectedCategory =  tmp.getCategory();
                }

                //수입지출, 카드현금, 날짜, 제목 데베에 넣기


            }
        });
        

        //+버튼 누르면 추가
        itemDetailAddButton = findViewById(R.id.itemDetailAddButton);
        itemDetailAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDetail itemDetail = null;
                itemDetail = new ItemDetail("", "", "1", "");

                adapter.addItem(itemDetail);
                recyclerView.setAdapter(adapter);
            }
        });


        //날짜버튼 누르면 날짜선택
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


}