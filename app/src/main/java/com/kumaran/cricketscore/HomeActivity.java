package com.kumaran.cricketscore;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    private ImageView team1_logo;
    private ImageView team2_logo;
    private TextView team1_name;
    private TextView team2_name;
    private TextView team1_score;
    private TextView team2_score;
    private LottieAnimationView lottieAnimationView;

    TabLayout tabLayout;
    ViewPager2 viewPager;

    ArrayList<String> teamnamesshort = new ArrayList<>();
    ArrayList<String> teamNames;
    ArrayList<Long> scores;
    ArrayList<Long> wickets;
    ArrayList<String> overs;


    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private ScoreboardAdapter scoreboardAdapter;
    private TimerTask timerTask;


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
        lottieAnimationView = findViewById(R.id.animation_view);

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



        firestore.collection("live_match").document("lastrun").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                long run = (long) value.get("run");
                switch((int) run){
                    case 0:
                        lottieAnimationView.setAnimation(R.raw.zero);
                        break;
                    case 1:
                        lottieAnimationView.setAnimation(R.raw.one);
                        break;
                    case 2:
                        lottieAnimationView.setAnimation(R.raw.two);
                        break;
                    case 3:
                        lottieAnimationView.setAnimation(R.raw.three);
                        break;
                    case 4:
                        lottieAnimationView.setAnimation(R.raw.four);
                        break;
                    case 6:
                        lottieAnimationView.setAnimation(R.raw.six);
                }
                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
                Timer timer = new Timer();
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lottieAnimationView.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                };
                timer.schedule(timerTask,3500);


            }
        });










    }
}