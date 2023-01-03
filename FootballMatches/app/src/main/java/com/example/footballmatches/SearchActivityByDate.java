package com.example.footballmatches;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import java.util.Calendar;

public class SearchActivityByDate extends AppCompatActivity {

    private Button date,search;
    private ImageButton back;
    int day,month,year;
    private DatePickerDialog.OnDateSetListener datePicker;
    DatabaseReference myRef;
    RecyclerView rv;
    MatchesAdapter ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_date);
        date = (Button) findViewById(R.id.editDate);
        search = (Button) findViewById(R.id.search_btn);
        back = (ImageButton) findViewById(R.id.back);
        rv = (RecyclerView) findViewById(R.id.r1);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("matches");
        rv.setLayoutManager(new LinearLayoutManager(this));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SearchActivityByDate.this, android.R.style.Theme_Dialog,
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
                month = month_ - 1;
                year = year_;
                date.setText(date_);
            }
        };

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchData = date.getText().toString();
                    if (searchData.compareTo("select date") == 0){
                        Toast.makeText(SearchActivityByDate.this, "Please choose a date to search ", Toast.LENGTH_LONG).show();
                        return;
                    }
                Query q;
                q = myRef.orderByChild("sdate").startAt(searchData).endAt(searchData + "\uf8ff");
                FirebaseRecyclerOptions<Match> matchesByDate =
                        new FirebaseRecyclerOptions.Builder<Match>()
                                .setQuery(q, Match.class)
                                .build();
                ma = new MatchesAdapter(matchesByDate, 0, SearchActivityByDate.this);
                ma.startListening();
                rv.setAdapter(ma);
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