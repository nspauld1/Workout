package com.example.nathanspaulding.workoutapp.Deadbugs;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nathanspaulding.workoutapp.R;

/**
 * Created by Nathan Spaulding on 5/25/2016.
 * This is the class for timing deadbugs. It will be passed an intent from
 * the dead bugs class.
 */
public class T_Deadbugs extends AppCompatActivity {

    //public boolean dataSent;
    int interval, totalSets;
    Button startButton, pauseButton, finshButton;


    //final String s = "Switch";
    private long startTime = 0L;
    private TextView timerValue;
    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updateTime = 0L;

    int num2 = 0, i=0;
    private int _DeadBugs_Id = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_deadbugs);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        _DeadBugs_Id = 0;
        Intent intent = getIntent();
        _DeadBugs_Id = intent.getIntExtra("deadBug_Id", 0);
        interval = intent.getIntExtra("Interval", 0);
        DeadBugsRepo repo = new DeadBugsRepo(this);
        DeadBugs deadBugs = new DeadBugs();
        deadBugs = repo.getDeadBugById(_DeadBugs_Id);


        timerValue = (TextView)findViewById(R.id.timerValue);

        num2 = interval;

        startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startButton.setClickable(false);
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);

            }
        });

        pauseButton = (Button) findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                startButton.setClickable(true);
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);

            }
        });

        finshButton = (Button) findViewById(R.id.finshButton);
        finshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass all info back
                AlertDialog.Builder builder = new AlertDialog.Builder(T_Deadbugs.this);
                LinearLayout myLayout = new LinearLayout(T_Deadbugs.this);

                myLayout.setOrientation(LinearLayout.VERTICAL);
                builder.setIcon(R.drawable.ic_create_complete);

                builder.setTitle("Congratulations! Work out Complete!");
                final TextView finshedTime = new TextView(T_Deadbugs.this);
                finshedTime.setTextSize(24);
                finshedTime.setText("You lasted for: " + timerValue.getText().toString() + ".\nAt intervals of: "
                        + interval + " seconds" + "\nYou completed " + totalSets / 2 + " complete sets!");
                myLayout.addView(finshedTime);

                builder.setView(myLayout);
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        totalSets = totalSets / 2;

                        DeadBugsRepo repo1 = new DeadBugsRepo(T_Deadbugs.this);
                        DeadBugs deadBugs1 = new DeadBugs();
                        deadBugs1.timerVal = timerValue.getText().toString();
                        deadBugs1.interval = interval;
                        deadBugs1.sets = totalSets;
                        deadBugs1.deadbug_ID = _DeadBugs_Id;

                        if(_DeadBugs_Id == 0){
                            _DeadBugs_Id = repo1.addDeadBug(deadBugs1);
                        }
                        else {
                            // Not implemented
                            repo1.updateEntry(deadBugs1);
                        }
                        finish();
                    }
                });

                builder.create();
                builder.show();
            }
        });
    }

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMilliseconds;

            int sec = (int) (updateTime / 1000);
            int min = sec / 60;
            sec = sec % 60;
            int millisec = (int) (updateTime % 1000);
            timerValue.setText("" + min + ":"
                    + String.format("%02d", sec) + "."
                    + String.format("%03d", millisec));
            int totalSeconds = (min *60) + sec;
            totalSets = totalSeconds / interval;
            customHandler.postDelayed(this, 0);

            if ( sec == num2){
                Toast.makeText(T_Deadbugs.this, "Switch", Toast.LENGTH_SHORT).show();
                ToneGenerator beep = new ToneGenerator(AudioManager.STREAM_ALARM, 200);
                beep.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 500);
                i += interval;
                num2 += interval;
                while (num2 >= 60){
                    if (num2 >= 60) {
                        num2 -= 60;
                    }
                }
            }
        }
    };

}
