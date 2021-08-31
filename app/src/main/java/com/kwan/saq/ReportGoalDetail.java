package com.kwan.saq;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.kwan.saq.model.Feed;
import com.kwan.saq.model.Goal;

import java.text.DecimalFormat;

public class ReportGoalDetail extends AppCompatActivity implements View.OnClickListener {

    Gson gson;
    String goalJson;
    Goal goal;
    DecimalFormat df1;
    DecimalFormat df2;

    private TextView goalName, goalTotalCalories, goalTime, goalStatus, goalCaloriesDone, goalResult, goalActivities;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_goal_detail);

        df1 = new DecimalFormat("#");
        df2 = new DecimalFormat("#.##");

        gson = new Gson();

        Intent thisIntent = this.getIntent();
        goalJson = thisIntent.getStringExtra("goalJson");
        goal = gson.fromJson(goalJson, Goal.class);

        // set up view
        initView();
    }

    private void initView() {
        goalName = findViewById(R.id.goalName);
        goalTotalCalories = findViewById(R.id.goalTotalCalories);
        goalTime = findViewById(R.id.goalTime);
        goalStatus = findViewById(R.id.goalStatus);
        goalCaloriesDone = findViewById(R.id.goalCaloriesDone);
        goalResult = findViewById(R.id.goalResult);
        goalActivities = findViewById(R.id.goalActivities);
        backBtn = findViewById(R.id.backBtn);

        goalName.setText(goal.getName());
        goalTotalCalories.setText(df1.format(goal.getCalo()) + " calories");

        // format time
        String startDateString = goal.getStart_date();
        String endDateString = goal.getEnd_date();
        String[] parts1 = startDateString.split("T");
        String[] parts2 = endDateString.split("T");

        if(parts1.length > 0 && parts2.length > 0) {
            goalTime.setText(_CONST.formatTime(parts1[0]) + " - " + _CONST.formatTime(parts2[0]));
        } else {
            goalTime.setText("N/A - N/A");
        }

        if(goal.getStatus() == 1) {
            goalStatus.setText("In progress");
        } else {
            goalStatus.setText("Expired");
        }

        goalCaloriesDone.setText(df2.format(goal.getCalo_done()) + " calories");
        goalResult.setText(df2.format(goal.getResult() * 100) + "%");

        String activitiesHtml = "";


        if(goal.getGoalFeeds().size() == 0) {
            activitiesHtml += "No activities done.";
            goalActivities.setText(Html.fromHtml(activitiesHtml));
        } else {
            for(Feed feed: goal.getGoalFeeds()) {
                String feedDate = feed.getCreated_at();
                String[] feedTimeParts = feedDate.split("T");
                String feedDateString = "";

                if(feedTimeParts.length > 0 && parts2.length > 0) {
                    feedDateString = _CONST.formatTime(parts1[0]);
                } else {
                    feedDateString = "N/A";
                }
                activitiesHtml += "&#8226; " + feed.getWorkout() + " (<b>" + df1.format(feed.getTime()) + "</b> seconds" + " - <b>" + df1.format(feed.getCalo()) + "</b> calories on " + feedDateString + ")" + "<br/>";
            }
            goalActivities.setText(Html.fromHtml(activitiesHtml));
        }


        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == backBtn) {
            onBackPressed();
        }
    }
}
