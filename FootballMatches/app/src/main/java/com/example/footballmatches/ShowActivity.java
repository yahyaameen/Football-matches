package com.example.footballmatches;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowActivity extends AppCompatActivity {

    private ImageButton back;
    RecyclerView r;
    MatchesAdapter adapter;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        r = (RecyclerView) findViewById(R.id.r1);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        r.setLayoutManager(new LinearLayoutManager(this));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("matches");
        FirebaseRecyclerOptions<Match> allMatches =
                new FirebaseRecyclerOptions.Builder<Match>()
                        .setQuery(ref, Match.class)
                        .build();

        adapter = new MatchesAdapter(allMatches,0,ShowActivity.this);
        r.setAdapter(adapter);
    }
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

}