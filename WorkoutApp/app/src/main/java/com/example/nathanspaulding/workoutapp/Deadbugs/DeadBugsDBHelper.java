package com.example.nathanspaulding.workoutapp.Deadbugs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nathan Spaulding on 6/1/2016.
 */
public class DeadBugsDBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "deadbugs.db";


    public DeadBugsDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_DEADBUGS_TABLE = "CREATE TABLE " + DeadBugs.TABLE  + "("
                + DeadBugs.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + DeadBugs.KEY_timer + " TEXT, "
                + DeadBugs.KEY_interval + " INTEGER, "
                + DeadBugs.KEY_sets + " INTEGER, "
                + DeadBugs.KEY_date + " DATE )";

        db.execSQL(CREATE_DEADBUGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + DeadBugs.TABLE);

        // Create tables again
        onCreate(db);

    }

}
