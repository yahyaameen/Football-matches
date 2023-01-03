package com.example.footballmatches;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchActivityByTeam extends AppCompatActivity {

    private EditText team;
    private Button search;
    private ImageButton back;
    DatabaseReference myRef;
    RecyclerView rv,rv2;
    MatchesAdapter ma,ma2;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_team);
        team = (EditText) findViewById(R.id.editTeam);
        search = (Button) findViewById(R.id.search_btn);
        back = (ImageButton) findViewById(R.id.back);
        rv = (RecyclerView) findViewById(R.id.r1);
        rv2 = (RecyclerView) findViewById(R.id.r2);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("matches");
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv2.setLayoutManager(new LinearLayoutManager(this));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query home,away;
                String name = team.getText().toString();
                    if (TextUtils.isEmpty(name.trim())) {
                        home = myRef.orderByChild("teamA").startAt("").endAt("");
                        away = myRef.orderByChild("teamB").startAt("").endAt("");
                        emptyQuery(home,away,ma,ma2,rv,rv2);
                        Toast.makeText(SearchActivityByTeam.this, "Please enter a team name", Toast.LENGTH_LONG).show();
                        return;
                    }
                    searchMatches(name);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    private void emptyQuery(Query home, Query away, MatchesAdapter ma, MatchesAdapter ma2, RecyclerView rv, RecyclerView rv2) {

        FirebaseRecyclerOptions<Match> options =
                new FirebaseRecyclerOptions.Builder<Match>()
                        .setQuery(home, Match.class)
                        .build();

        ma = new MatchesAdapter(options, 0, SearchActivityByTeam.this);
        ma.startListening();
        rv.setAdapter(ma);

        FirebaseRecyclerOptions<Match> options2 =
                new FirebaseRecyclerOptions.Builder<Match>()
                        .setQuery(away, Match.class)
                        .build();

        ma2 = new MatchesAdapter(options2, 0, SearchActivityByTeam.this);
        ma2.startListening();
        rv2.setAdapter(ma2);
    }

    private void searchMatches(String teamName) {

            Query query1, query2;

            query1 = myRef.orderByChild("teamAlower").startAt(teamName.toLowerCase()).endAt(teamName.toLowerCase() + "\uf8ff");
            query2 = myRef.orderByChild("teamBlower").startAt(teamName.toLowerCase()).endAt(teamName.toLowerCase() + "\uf8ff");

            FirebaseRecyclerOptions<Match> homeData =
                    new FirebaseRecyclerOptions.Builder<Match>()
                            .setQuery(query1, Match.class)
                            .build();
            ma = new MatchesAdapter(homeData, 0, SearchActivityByTeam.this);
            ma.startListening();
            rv.setAdapter(ma);

            FirebaseRecyclerOptions<Match> awayData =
                    new FirebaseRecyclerOptions.Builder<Match>()
                            .setQuery(query2, Match.class)
                            .build();
            ma2 = new MatchesAdapter(awayData, 0, SearchActivityByTeam.this);
            ma2.startListening();
            rv2.setAdapter(ma2);
    }

}