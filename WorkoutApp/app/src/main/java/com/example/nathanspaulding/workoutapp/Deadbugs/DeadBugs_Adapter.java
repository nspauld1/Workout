package com.example.nathanspaulding.workoutapp.Deadbugs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nathanspaulding.workoutapp.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nathan Spaulding on 5/25/2016.
 */
public class DeadBugs_Adapter extends RecyclerView.Adapter<DeadBugs_Adapter.ViewHolder> {

    private ArrayList<HashMap<String,String>> dataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTimerVal;
        public TextView mInterval;
        public TextView mSets;

        public ViewHolder (View view){
            super(view);
            this.mTimerVal = (TextView)view.findViewById(R.id.timerValue);
            this.mInterval = (TextView)view.findViewById(R.id.interval);
            this.mSets = (TextView)view.findViewById(R.id.sets);
        }
    }


    public DeadBugs_Adapter(ArrayList<HashMap<String, String>> data){
        this.dataSet = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deadbug_row_layout, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        HashMap<String,String> e = dataSet.get(position);
        holder.mTimerVal.setText(e.get("timer"));
        holder.mInterval.setText(e.get("interval"));
        holder.mSets.setText(e.get("sets"));

        

    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

}
