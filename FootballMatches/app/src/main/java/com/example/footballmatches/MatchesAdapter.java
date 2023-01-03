package com.example.footballmatches;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MatchesAdapter extends FirebaseRecyclerAdapter<Match,MatchesAdapter.MatchView> {
    int op;
    Context context;

    public MatchesAdapter(@NonNull FirebaseRecyclerOptions<Match> data,int op, Context context) {
        super(data);
        this.op = op;
        this.context =  context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MatchView mv, int i, @NonNull Match m) {

        mv.home.setText(m.getTeamAlower());
        mv.away.setText(m.getTeamBlower());
        mv.city.setText(m.getCity());
        mv.key.setVisibility(View.INVISIBLE);
        mv.key.setText(m.getKey());
        mv.date.setText(m.getSdate());
    }

    @NonNull
    @Override
    public MatchView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_layout,parent,false);

        if(op == 1){  // delete operation
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog myQuittingDialogBox = new AlertDialog.Builder(parent.getContext())
                            // set message, title, and icon
                            .setTitle("Match Delete")
                            .setMessage("Do you want to Delete this match ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //your deleting code
                                    TextView key = (TextView ) view.findViewById(R.id.key);
                                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                                    DatabaseReference ref =  db.getReference("matches").child(key.getText().toString());

                                    ref.setValue(null);
                                    dialog.dismiss();
                                }

                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            })
                            .create();
                    myQuittingDialogBox.show();
                }
            });
        }

        MatchView m = new MatchView(view);
        if(op == 2){  // update operation
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Update.class);
                    // pass all the fields of the selected game in the update operation activity
                    intent.putExtra("firstTeam",m.home.getText().toString());
                    intent.putExtra("secondTeam",m.away.getText().toString());
                    intent.putExtra("city",m.city.getText().toString());
                    intent.putExtra("date",m.date.getText().toString());
                    intent.putExtra("key",m.key.getText().toString());
                    context.startActivity(intent);  // move to update operation activity
                }
            });
        }

        return m;
    }

    public static class MatchView extends RecyclerView.ViewHolder{  // a single game view content

        TextView home, away,key,date,city;
        public MatchView(@NonNull View itemView) {
            super(itemView);
            key = (TextView)itemView.findViewById(R.id.key);
            home = (TextView)itemView.findViewById(R.id.homeTeam);
            away = (TextView)itemView.findViewById(R.id.awayTeam);
            date = (TextView)itemView.findViewById(R.id.editDate);
            city = (TextView)itemView.findViewById(R.id.editCity);
        }
    }
}
