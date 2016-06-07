package com.example.nathanspaulding.workoutapp.Deadbugs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nathan Spaulding on 6/5/2016.
 */
public class DeadBugsRepo {

    private DeadBugsDBHelper deadBugsDBHandler;

    public DeadBugsRepo(Context context){
        deadBugsDBHandler = new DeadBugsDBHelper(context);
    }

    public int addDeadBug (DeadBugs deadBugs) {

        //Open connection to write data
        SQLiteDatabase db = deadBugsDBHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DeadBugs.KEY_date, deadBugs.date);
        values.put(DeadBugs.KEY_sets, deadBugs.sets);
        values.put(DeadBugs.KEY_interval,deadBugs.interval);
        values.put(DeadBugs.KEY_timer, deadBugs.timerVal);

        // Inserting Row
        long deadBug_Id = db.insert(DeadBugs.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) deadBug_Id;
    }



    public void deleteDeadBug(int deadbug_Id){

        SQLiteDatabase db = deadBugsDBHandler.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(DeadBugs.TABLE, DeadBugs.KEY_ID + "= ?", new String[] { String.valueOf(deadbug_Id) });
        db.close(); // Closing database connection
    }

    public void updateEntry(DeadBugs deadBugs){

        SQLiteDatabase db = deadBugsDBHandler.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DeadBugs.KEY_date, deadBugs.date);
        values.put(DeadBugs.KEY_sets, deadBugs.sets);
        values.put(DeadBugs.KEY_interval,deadBugs.interval);
        values.put(DeadBugs.KEY_timer, deadBugs.timerVal);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(DeadBugs.TABLE, values, DeadBugs.KEY_ID + "= ?", new String[] { String.valueOf(deadBugs.deadbug_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getDeadBugList(){

        //Open connection to read only
        SQLiteDatabase db = deadBugsDBHandler.getReadableDatabase();
        String selectQuery =  "SELECT " +
                DeadBugs.KEY_ID + "," +
                DeadBugs.KEY_timer + "," +
                DeadBugs.KEY_interval + "," +
                DeadBugs.KEY_sets + "," +
                DeadBugs.KEY_date +
                " FROM " + DeadBugs.TABLE;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> deadbugList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> deadbug = new HashMap<String, String>();
                deadbug.put("id", cursor.getString(cursor.getColumnIndex(DeadBugs.KEY_ID)));
                deadbug.put("timer", cursor.getString(cursor.getColumnIndex(DeadBugs.KEY_timer)));
                deadbug.put("interval",cursor.getString(cursor.getColumnIndex(DeadBugs.KEY_interval)));
                deadbug.put("sets",cursor.getString(cursor.getColumnIndex(DeadBugs.KEY_sets)));
                deadbug.put("date",cursor.getString(cursor.getColumnIndex(DeadBugs.KEY_date)));
                deadbugList.add(deadbug);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return deadbugList;
    }

    public DeadBugs getDeadBugById (int Id){
        SQLiteDatabase db = deadBugsDBHandler.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DeadBugs.KEY_ID + "," +
                DeadBugs.KEY_timer + "," +
                DeadBugs.KEY_interval + "," +
                DeadBugs.KEY_sets +"," +
                DeadBugs.KEY_date +
                " FROM " + DeadBugs.TABLE
                + " WHERE " +
                DeadBugs.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        DeadBugs deadBugs = new DeadBugs();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                deadBugs.deadbug_ID = cursor.getInt(cursor.getColumnIndex(DeadBugs.KEY_ID));
                deadBugs.timerVal = cursor.getString(cursor.getColumnIndex(DeadBugs.KEY_timer));
                deadBugs.interval  = cursor.getInt(cursor.getColumnIndex(DeadBugs.KEY_interval));
                deadBugs.sets = cursor.getInt(cursor.getColumnIndex(DeadBugs.KEY_sets));
                deadBugs.date = cursor.getString(cursor.getColumnIndex(DeadBugs.KEY_date));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return deadBugs;

    }
}
