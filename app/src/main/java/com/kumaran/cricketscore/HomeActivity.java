package com.kumaran.cricketscore;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.sql.Array;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ImageView team1_logo;
    ImageView team2_logo;
    TextView team1_name;
    TextView team2_name;
    TextView team1_score;
    TextView team2_score;

    TabLayout tabLayout;
    ViewPager2 viewPager;

    ArrayList<String> teamnamesshort = new ArrayList<>();
    ArrayList<String> teamNames;
    ArrayList<Long> scores;
    ArrayList<Long> wickets;
    ArrayList<String> overs;


    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private ScoreboardAdapter scoreboardAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        team1_logo = findViewById(R.id.team1_logo);
        team2_logo = findViewById(R.id.team2_logo);
        team1_name = findViewById(R.id.team1_name);
        team2_name = findViewById(R.id.team2_name);
        team1_score = findViewById(R.id.team1_score);
        team2_score = findViewById(R.id.team2_score);

        tabLayout = findViewById(R.id.team_tab);
        viewPager = findViewById(R.id.team_pager);

        scoreboardAdapter = new ScoreboardAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager.setAdapter(scoreboardAdapter);

        firestore.collection("live_match").document("teams").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                teamnamesshort = (ArrayList<String>) documentSnapshot.get("abbr");
                new TabLayoutMediator(tabLayout, viewPager,
                        (tab, position) -> tab.setText(teamnamesshort.get(position))).attach();
            }
        });

        firestore.collection("live_match").document("teams").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                teamNames = (ArrayList<String>) documentSnapshot.get("name");
            }
        });

        firestore.collection("live_match").document("teams").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                teamNames = (ArrayList<String>) documentSnapshot.get("name");
                team1_name.setText(teamNames.get(0));
                team2_name.setText(teamNames.get(1));
            }
        });

        firestore.collection("live_match").document("teams").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                scores = (ArrayList<Long>) value.get("score");
                wickets = (ArrayList<Long>) value.get("wickets");
                overs = (ArrayList<String>) value.get("overs");

                team1_score.setText(scores.get(0).toString()+"/"+wickets.get(0)+" ("+overs.get(0)+")");
                team2_score.setText(scores.get(1).toString()+"/"+wickets.get(1)+" ("+overs.get(1)+")");

            }
        });












        //firestore.collection("live_match").document("team1").get().



    }
}