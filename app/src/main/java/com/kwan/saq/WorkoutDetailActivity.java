package com.kwan.saq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kwan.saq.adapter.ExerciseItemAdapter;
import com.kwan.saq.adapter.WorkoutExerciseItemAdapter;
import com.kwan.saq.model.Exercise;
import com.kwan.saq.model.MusclePart;
import com.kwan.saq.model.MusclePartList;
import com.kwan.saq.model.Workout;
import com.kwan.saq.model.WorkoutList;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDetailActivity extends AppCompatActivity implements View.OnClickListener {
    List<MusclePart> musclePartList;

    WorkoutExerciseItemAdapter workoutExerciseItemAdapter;

    private Workout workout;
    private String workoutJson;

    private Button backBtn;
    private TextView workoutName;
    private TextView workoutMusclePart;
    private TextView workoutTotalSet;
    private Button workoutCommentBtn;
    private Button startWorkoutBtn;

    private RecyclerView rvExerciseItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_detail);

        workoutJson = getIntent().getStringExtra("workoutJson");

        Gson gson = new Gson();

        if(!workoutJson.equals("")) {
            workout = gson.fromJson(workoutJson, Workout.class);
        } else {
            workout = null;
        }

        getMuscleParts();

        // set up view
        initView();

    }

    private void initView() {

        backBtn = findViewById(R.id.backBtn);
        startWorkoutBtn = findViewById(R.id.startWorkoutBtn);
        workoutCommentBtn = findViewById(R.id.workoutCommentBtn);

        backBtn.setOnClickListener(this);
        startWorkoutBtn.setOnClickListener(this);
        workoutCommentBtn.setOnClickListener(this);

        workoutName = findViewById(R.id.workoutName);
        workoutMusclePart = findViewById(R.id.workoutMusclePart);
        workoutTotalSet = findViewById(R.id.workoutTotalSet);

        workoutName.setText(workout.getName());

        if(musclePartList.size() > 0) {
            for(MusclePart musclePart: musclePartList) {
                if(musclePart.getId() == workout.getMuscle_part_id()) {
                    workoutMusclePart.setText(musclePart.getName());
                    break;
                }
            }
        } else {
            workoutMusclePart.setText(("N/A"));
        }

        workoutTotalSet.setText(workout.getTotal_set() + " sets");
        workoutCommentBtn.setText(" " + workout.getCommentCount() + " comments");


        // init recycle view
        rvExerciseItemList = (RecyclerView) findViewById(R.id.rvExerciseItemList);

        // adapter
        workoutExerciseItemAdapter = new WorkoutExerciseItemAdapter(WorkoutDetailActivity.this, workout.getExercises());

        // layout manager
        RecyclerView.LayoutManager layoutManagerExercise = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rvExerciseItemList.suppressLayout(true);
        rvExerciseItemList.setLayoutManager(layoutManagerExercise);
        rvExerciseItemList.setAdapter(workoutExerciseItemAdapter);
    }

    private void getMuscleParts() {
        SharedPreferences preferences = getSharedPreferences("GeneralData", MODE_PRIVATE);

        Gson gson = new Gson();
        String musclePartsJson = preferences.getString("musclePartList", "");

        if(!musclePartsJson.equals("")) {
            musclePartList = gson.fromJson(musclePartsJson, MusclePartList.class).getMusclePartList();
        } else {
            musclePartList = new ArrayList<>();
        }
    }


    @Override
    public void onClick(View v) {
        if(v == backBtn) {
            goToPreviousActivity();
        } else if(v == startWorkoutBtn) {
            goToDoWorkoutActivity();
        } else if(v == workoutCommentBtn) {
            Intent commentsIntent = new Intent(this, WorkoutCommentsActivity.class);
            commentsIntent.putExtra("workoutId", workout.getId());
            startActivity(commentsIntent);
            return;
        }
    }


    public void goToPreviousActivity() {
        Log.d("TAG", "Go back to previous activity please.");
        this.finish();
    }

    private void goToDoWorkoutActivity() {
        Gson gson = new Gson();

        Intent intent = new Intent(WorkoutDetailActivity.this, WorkoutDoActivity.class);
        intent.putExtra("workout", gson.toJson(workout));
        startActivity(intent);
        return;
    }
}
