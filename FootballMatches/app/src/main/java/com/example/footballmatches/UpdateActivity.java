package com.example.footballmatches;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateActivity extends AppCompatActivity {

    ImageButton back;
    TextView title;
    RecyclerView recycler;
    MatchesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        title = (TextView)  findViewById(R.id.title);
        recycler = (RecyclerView) findViewById(R.id.r1);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Match> options =
                new FirebaseRecyclerOptions.Builder<Match>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("matches"), Match.class)
                        .build();

        adapter = new MatchesAdapter(options, 2,UpdateActivity.this);
        recycler.setAdapter(adapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}