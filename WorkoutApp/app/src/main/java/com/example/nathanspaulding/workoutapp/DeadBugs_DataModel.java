package com.example.nathanspaulding.workoutapp;

/**
 * Created by Nathan Spaulding on 5/26/2016.
 */
public class DeadBugs_DataModel {

    String timerVal;
    int sets, interval;

    public DeadBugs_DataModel(String timerVal, int sets, int interval){
        this.timerVal = timerVal;
        this.sets = sets;
        this.interval = interval;
    }

    public String getTimerVal() {
        return timerVal;
    }

    public void setTimerVal(String timerVal) {
        this.timerVal = timerVal;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
}
