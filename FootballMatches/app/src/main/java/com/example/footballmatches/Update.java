package com.example.footballmatches;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
import java.util.Date;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Update extends AppCompatActivity {

    private EditText first,second,city;
    private Button update,editDate;
    private ImageButton back;
    private int day,month,year;
    private DatePickerDialog.OnDateSetListener datePicker;
    Boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update2);
        first = (EditText) findViewById(R.id.homeTeam);
        second = (EditText) findViewById(R.id.awayTeam);
        city = (EditText) findViewById(R.id.editCity);
        editDate = (Button) findViewById(R.id.editDate);
        back = (ImageButton) findViewById(R.id.back);
        update = (Button) findViewById(R.id.update);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Update.this, android.R.style.Theme_Dialog,
                        datePicker, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year_, int month_, int day_) {
                month_ = month_ + 1;
                String date_ = day_ + "/" +  month_ + "/" + year_;
                day = day_;
                month = month_- 1;
                year = year_;
                editDate.setText(date_);
            }
        };

        Intent intent = getIntent();
        String scity = intent.getStringExtra("city");
        String sdate = intent.getStringExtra("date");
        String team1 = intent.getStringExtra("firstTeam");
        String team2 = intent.getStringExtra("secondTeam");
        int dateLength = sdate.length() - 1;

        int day_ = 0, month_ = 0 , year_ = 0;
        year_ = (sdate.charAt(dateLength) - '0') + (sdate.charAt(dateLength-1) - '0')*10 + (sdate.charAt(dateLength-2) - '0')*100 + (sdate.charAt(dateLength-3) - '0')*1000;
        dateLength -= 5;
        month_ += (sdate.charAt(dateLength) - '0');
        dateLength--;
        if(sdate.charAt(dateLength) != '/') {
            month_ += (sdate.charAt(dateLength) - '0')  * 10;
            dateLength-=2;
        }
        else
            dateLength--;
        day_ += (sdate.charAt(dateLength) - '0');
        dateLength--;
        if(dateLength == 0)
            day_ += (sdate.charAt(dateLength) - '0') * 10;

        day = day_;
        month = month_-1;
        year = year_;
        String key =  intent.getStringExtra("key");
        first.setText(team1);
        second.setText(team2);
        city.setText(scity);
        editDate.setText(sdate);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("matches").child(key);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstTeam = first.getText().toString().trim();
                String secondTeam = second.getText().toString().trim();
                String location = city.getText().toString().trim();

                if(TextUtils.isEmpty(firstTeam) || TextUtils.isEmpty(secondTeam) || TextUtils.isEmpty(location))
                    check = false;
                else
                    check = true;

                if(!check){
                    Toast.makeText(Update.this, "Please fill all fields to update match.", Toast.LENGTH_LONG).show();
                    return;
                }

                Date date_ = new Date(year,month,day);
                String city_ = city.getText().toString();
                String teamA_ = first.getText().toString();
                String teamB_ = second.getText().toString();;
                Match g = new Match(city_,date_,teamA_,teamB_);
                g.setKey(key);
                myRef.setValue(g);
                Toast.makeText(Update.this, "match was updated successfully.", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }
}