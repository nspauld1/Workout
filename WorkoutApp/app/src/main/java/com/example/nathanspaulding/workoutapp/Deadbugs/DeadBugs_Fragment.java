package com.example.nathanspaulding.workoutapp.Deadbugs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nathanspaulding.workoutapp.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Nathan Spaulding on 5/25/2016.
 */
public class DeadBugs_Fragment extends android.support.v4.app.Fragment {

    public static Context mContext;
    View myView;
    int  interval;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = getContext();
        myView = inflater.inflate(R.layout.deadbug_fragment, container, false);

        mRecyclerView = (RecyclerView)myView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mSwipeRefreshLayout = (SwipeRefreshLayout)myView.findViewById(R.id.swipeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        refreshItems();

        FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNew();
            }
        });

        return myView;
    }

    public void refreshItems(){
        DeadBugsRepo repo = new DeadBugsRepo(getContext());
        ArrayList<HashMap<String, String>> deadBugList = repo.getDeadBugList();

        if (deadBugList.size() != 0) {

            mAdapter = new DeadBugs_Adapter(deadBugList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Toast.makeText(getContext(), "No DeadBugs!", Toast.LENGTH_SHORT).show();
        }

        onItemsLoadComplete();
    }
    public void onItemsLoadComplete(){
        mSwipeRefreshLayout.setRefreshing(false);
    }


    public void CreateNew(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mContext = getActivity();
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
                intent.putExtra("deadbug_Id", 0);
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
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

    }
}
