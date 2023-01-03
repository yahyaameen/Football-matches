package com.example.footballmatches;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private EditText first,second,city;
    private Button add,editDate;
    private ImageButton back;
    private int day,month,year;
    private DatePickerDialog.OnDateSetListener datePicker;
    Boolean check;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        first = (EditText) findViewById(R.id.homeTeam);
        second = (EditText) findViewById(R.id.awayTeam);
        city = (EditText) findViewById(R.id.editCity);
        editDate = (Button) findViewById(R.id.editDate);
        back = (ImageButton) findViewById(R.id.back);
        add = (Button) findViewById(R.id.add);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                DatePickerDialog d = new DatePickerDialog(AddActivity.this, android.R.style.Theme_Dialog,
                        datePicker, year,month,day);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.show();
            }
        });

        datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year_, int month_, int day_) {
                month_ = month_ + 1;
                Log.d("AddActivity", "onDateSet: mm/dd/yyy: " + month_ + "/" + day_ + "/" + year_);


                String date_ = day_ + "/" +  month_ + "/" + year_;
                day = day_;
                month = month_ - 1;
                year = year_;
                editDate.setText(date_);
            }
        };

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("matches");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstTeam = first.getText().toString().trim();
                String secondTeam = second.getText().toString().trim();
                String location = city.getText().toString().trim();

                if(TextUtils.isEmpty(firstTeam) || TextUtils.isEmpty(secondTeam) || TextUtils.isEmpty(location))
                    check = false;
                else
                    check = true;
                if(!check || editDate.getText().toString().compareTo("select date") == 0){

                    Toast.makeText(AddActivity.this, "Please fill all fields to add match.", Toast.LENGTH_LONG).show();
                    return;

                }

                String city_ = city.getText().toString();
                Date date_ = new Date(year,month,day);
                String first_ = first.getText().toString();
                String second_ = second.getText().toString();;
                Match m = new Match(city_,date_,first_,second_);
                DatabaseReference d = myRef.push();
                String key = d.getKey();
                m.setKey(key);
                d.setValue(m);
                Toast.makeText(AddActivity.this, "match added successfully.", Toast.LENGTH_LONG).show();
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
