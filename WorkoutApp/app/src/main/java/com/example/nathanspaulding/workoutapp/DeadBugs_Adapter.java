package com.example.nathanspaulding.workoutapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Nathan Spaulding on 5/25/2016.
 */
public class DeadBugs_Adapter extends RecyclerView.Adapter<DeadBugs_Adapter.ViewHolder> {

    private ArrayList<DeadBugs_DataModel> dataSet;

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

    public DeadBugs_Adapter(ArrayList<DeadBugs_DataModel> data){
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

        String time = dataSet.get(position).getTimerVal();
        int interval = dataSet.get(position).getInterval();
        int sets = dataSet.get(position).getSets();

        holder.mTimerVal.setText(time);
        holder.mInterval.setText(String.valueOf(interval));
        holder.mSets.setText(String.valueOf(sets));
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }




}
