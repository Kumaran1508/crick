package com.kumaran.cricketscore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private Map<String,Object> players;
    private Context context;

    public PlayerAdapter(Map<String, Object> players, Context context) {
        this.players = players;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layuout = inflater.inflate(R.layout.score_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(layuout);
        return viewHolder;

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{


            Map<String,Object> player = (Map) players.get("player"+(position+1));
            holder.name.setText(player.get("name").toString());
            holder.runs.setText(player.get("runs").toString());
            holder.balls.setText(player.get("balls").toString());
            holder.fours.setText(player.get("fours").toString());
            holder.sixes.setText(player.get("sixes").toString());
            holder.strikerate.setText(player.get("strike_rate").toString());
            holder.status.setText(player.get("status").toString());

            if(holder.status.getText().toString().contentEquals("OUT")) holder.status.setTextColor(R.color.red);
        }
        catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        return players.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView runs;
        TextView balls;
        TextView fours;
        TextView sixes;
        TextView strikerate;
        TextView status;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.player_name);
            runs = itemView.findViewById(R.id.player_runs);
            balls = itemView.findViewById(R.id.player_balls);
            fours = itemView.findViewById(R.id.player_fours);
            sixes = itemView.findViewById(R.id.player_sixes);
            strikerate = itemView.findViewById(R.id.player_strike_rate);
            status = itemView.findViewById(R.id.player_status);

        }
    }
}
