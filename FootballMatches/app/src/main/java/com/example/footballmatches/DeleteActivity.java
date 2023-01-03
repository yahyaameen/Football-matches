package com.example.footballmatches;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteActivity extends AppCompatActivity {

    private ImageButton back;
    DatabaseReference ref;
    RecyclerView r;
    MatchesAdapter adapter;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        title = (TextView)  findViewById(R.id.title);
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
        adapter = new MatchesAdapter(allMatches, 1, DeleteActivity.this);

        r.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}