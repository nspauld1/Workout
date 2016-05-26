package com.example.nathanspaulding.workoutapp;

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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nathan Spaulding on 5/25/2016.
 * This is the class for timing deadbugs. It will be passed an intent from
 * the dead bugs class.
 */
public class T_Deadbugs extends AppCompatActivity {

    public boolean dataSent;
    int interval, totalSets;
    Button startButton, pauseButton, finshButton;

    final String s = "Switch";
    ListView listView;
    private long startTime = 0L;
    private TextView timerValue;
    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updateTime = 0L;

    int num2 = 0, i=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_deadbugs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        interval = intent.getIntExtra("Interval", 0);

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
                        // Send back data to DeadBug_Fragment
                        dataSent = true;
                        Bundle bundle = new Bundle();
                        bundle.putString("Timer", timerValue.getText().toString());
                        bundle.putInt("Interval", interval);
                        bundle.putInt("Sets", totalSets / 2);
                        DeadBugs_Fragment deadBugs_fragment = new DeadBugs_Fragment();
                        deadBugs_fragment.setArguments(bundle);
                        deadBugs_fragment.recieveData();
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
