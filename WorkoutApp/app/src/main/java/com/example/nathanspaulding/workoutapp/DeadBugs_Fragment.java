package com.example.nathanspaulding.workoutapp;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Nathan Spaulding on 5/25/2016.
 */
public class DeadBugs_Fragment extends android.support.v4.app.Fragment {

    View myView;
    int duration, interval;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        myView = inflater.inflate(R.layout.deadbug_fragment, container, false);

        FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNew();            }
        });

        return myView;
    }

    public void CreateNew() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LinearLayout myLayout = new LinearLayout(getActivity());
        myLayout.setOrientation(LinearLayout.VERTICAL);

        builder.setIcon(R.drawable.ic_create_builder);
        builder.setTitle("Custom Dead Bugs\n");

        final EditText txtInterval = new EditText(getActivity());
        txtInterval.setHint("Interval (seconds)");
        txtInterval.setInputType(3);
        myLayout.addView(txtInterval);

        final TextView txtValidation = new TextView(getActivity());
        txtValidation.setText(" Please enter an integer only!");
        txtValidation.setTextColor(Color.RED);
        myLayout.addView(txtValidation);

        builder.setView(myLayout);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //User Clicked Create
                interval = Integer.parseInt(txtInterval.getText().toString());

                Intent intent = new Intent(getContext(), T_Deadbugs.class);
                intent.putExtra("Interval", interval);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //User clicked Cancel
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
    }
}
