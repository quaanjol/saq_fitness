package com.kwan.saq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;
import com.jama.carouselview.enums.IndicatorAnimationType;
import com.jama.carouselview.enums.OffsetType;
import com.kwan.saq.adapter.ExerciseItemAdapter;
import com.kwan.saq.api.ApiManager;
import com.kwan.saq.model.Exercise;
import com.kwan.saq.model.MusclePart;
import com.kwan.saq.model.MusclePartList;
import com.kwan.saq.model.Workout;
import com.kwan.saq.model.WorkoutList;
import com.kwan.saq.request.GetWorkoutsRequest;
import com.kwan.saq.response.GetExercisesResponse;
import com.kwan.saq.response.GetMusclePartsResponse;
import com.kwan.saq.response.GetWorkoutsResponse;
import com.kwan.saq.section.WorkoutSection;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkoutMainActivity extends AppCompatActivity implements View.OnClickListener {
    SectionedRecyclerViewAdapter sectionAdapter;

    GetMusclePartsResponse getMusclePartsResponse;
    GetWorkoutsResponse getWorkoutsResponse;

    private MusclePartList musclePartList;
    private WorkoutList workoutList;

    // token
    String token = "";

    // carousel images
    private int[] images = {R.drawable.bg1_32, R.drawable.bg2_32, R.drawable.bg3_32, R.drawable.bg4_32, R.drawable.bg5_32};

    CarouselView carouselView;

    // components
    private RecyclerView rvWorkoutSectioned;
    private Button createWorkoutBtn;
    private Button homeBtn;
    private Button trainingBtn;
    private Button meBtn;
    private Button reportBtn;
    private Button nutritionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workouts_main_layout);

        token = getCurrentToken();

        Intent thisIntent = getIntent();
        int feedCreated = thisIntent.getIntExtra("feedCreated", 0);
        int newWorkout = thisIntent.getIntExtra("newWorkout", 0);

        if(feedCreated == 1) {
            Toast toast = Toast.makeText(WorkoutMainActivity.this, "Workout completed. Please check your feed.", Toast.LENGTH_LONG);
            toast.show();
        }

        if(newWorkout == 1) {
            Toast toast = Toast.makeText(WorkoutMainActivity.this, "New custom workout created successfully.", Toast.LENGTH_LONG);
            toast.show();
        }

        Log.d("TAG", "current token is: " + token);

        // get list muscle parts and list workouts
        getGeneralData();

        // set up view
        initView();

        // set up recycle view
        initRecycleView();
    }

    private void getGeneralData() {
        SharedPreferences preferences = getSharedPreferences("GeneralData", MODE_PRIVATE);

        Gson gson = new Gson();
        String musclePartsJson = preferences.getString("musclePartList", "");
        String workoutsJson = preferences.getString("workoutList", "");

        if(!musclePartsJson.equals("") && !workoutsJson.equals("")) {
            musclePartList = gson.fromJson(musclePartsJson, MusclePartList.class);
            workoutList = gson.fromJson(workoutsJson, WorkoutList.class);
        }

        //Log.d("TAG", "musclePartsJson is: " + musclePartsJson);
        //Log.d("TAG", "workoutsJson is: " + workoutsJson);
    }

    private void initView() {
        // carousel
        carouselView = findViewById(R.id.carouselView);
        carouselView.setSize(images.length);
        carouselView.setResource(R.layout.center_carousel_item);
        carouselView.setAutoPlay(true);
        carouselView.setIndicatorAnimationType(IndicatorAnimationType.THIN_WORM);
        carouselView.setCarouselOffset(OffsetType.CENTER);
        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                // Example here is setting up a full image carousel
                ImageView imageView = view.findViewById(R.id.imageView);
                imageView.setImageDrawable(getResources().getDrawable(images[position]));
            }
        });

        // After you finish setting up, show the CarouselView
        carouselView.show();

        //
        createWorkoutBtn = findViewById(R.id.createWorkoutBtn);
        homeBtn = findViewById(R.id.homeBtn);
        trainingBtn = findViewById(R.id.trainingBtn);
        meBtn = findViewById(R.id.meBtn);
        reportBtn = findViewById(R.id.reportBtn);
        nutritionBtn = findViewById(R.id.nutritionBtn);

        createWorkoutBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        trainingBtn.setOnClickListener(this);
        meBtn.setOnClickListener(this);
        reportBtn.setOnClickListener(this);
        nutritionBtn.setOnClickListener(this);
    }

    private void initRecycleView() {
        // set up adapter
        sectionAdapter = new SectionedRecyclerViewAdapter();

        List<MusclePart> muscleParts = musclePartList.getMusclePartList();
        List<Workout> workouts = workoutList.getWorkoutList();

        for(MusclePart musclePart: muscleParts) {
            List<Workout> chosenWorkout = new ArrayList<>();

            for(Workout workout: workouts) {
                if(workout.getMuscle_part_id() == musclePart.getId()) {
                    chosenWorkout.add(workout);
                }
            }

            if(chosenWorkout.size() > 0) {
                // Add your Sections
                sectionAdapter.addSection(new WorkoutSection(musclePart.getName(), chosenWorkout, WorkoutMainActivity.this));
            }
        }

        // recycle view
        rvWorkoutSectioned = (RecyclerView) findViewById(R.id.rvWorkoutSectioned);

        // Set up your RecyclerView with the SectionedRecyclerViewAdapter
        rvWorkoutSectioned.setLayoutManager(new LinearLayoutManager(this));
        rvWorkoutSectioned.setAdapter(sectionAdapter);
    }

    public String getCurrentToken() {
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        String currentToken = preferences.getString(_CONST.USER_TOKEN,"");
        return currentToken;
    }

    @Override
    public void onClick(View v) {
        if(v == trainingBtn) {
            _CONST.goToTrainingPage(this);
        } else if(v == meBtn) {
            _CONST.goToProfilePage(this);
        } else if(v == homeBtn) {
            _CONST.goToHomeScreen(this);
        } else if(v == reportBtn) {
            _CONST.goToReportPage(this);
        } else if(v == createWorkoutBtn) {
            goToCreateWorkoutPage();
        } else if(v == nutritionBtn) {
            _CONST.goToNutritionPage(this);
        }
    }

    private void goToCreateWorkoutPage() {
        Intent workoutCreateIntent = new Intent(this, WorkoutCreateActivity.class);
        startActivity(workoutCreateIntent);
    }
}
