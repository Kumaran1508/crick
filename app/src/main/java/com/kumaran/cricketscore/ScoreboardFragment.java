package com.kumaran.cricketscore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardFragment extends Fragment {
    private int page;
    private RecyclerView scoreRecycler;
    private PlayerAdapter playerAdapter;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Map<String,Object> players = null;

    public ScoreboardFragment(int page) {
        this.page = page;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.scoreboard, container, false);

        scoreRecycler = view.findViewById(R.id.score_recycler);
        scoreRecycler.setLayoutManager(new LinearLayoutManager(getContext()));





        firestore.collection("live_match").document("team"+(page+1)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                players = value.getData();
                playerAdapter = new PlayerAdapter(players,getContext());
                scoreRecycler.setAdapter(playerAdapter);
                playerAdapter.notifyDataSetChanged();
            }
        });







        return view;
    }
}
