package com.kumaran.cricketscore;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ScoreboardAdapter extends FragmentStateAdapter {


    public ScoreboardAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        ScoreboardFragment scoreboardFragment = new ScoreboardFragment(position);
        return  scoreboardFragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
