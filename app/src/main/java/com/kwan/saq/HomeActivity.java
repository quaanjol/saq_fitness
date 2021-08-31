package com.kwan.saq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;
import com.jama.carouselview.enums.IndicatorAnimationType;
import com.jama.carouselview.enums.OffsetType;
import com.kwan.saq.adapter.ExerciseItemAdapter;
import com.kwan.saq.api.ApiManager;
import com.kwan.saq.model.Exercise;
import com.kwan.saq.model.ExerciseList;
import com.kwan.saq.model.MusclePart;
import com.kwan.saq.model.MusclePartList;
import com.kwan.saq.model.Workout;
import com.kwan.saq.model.WorkoutList;
import com.kwan.saq.request.GetWorkoutsRequest;
import com.kwan.saq.response.GetExercisesResponse;
import com.kwan.saq.response.GetMusclePartsResponse;
import com.kwan.saq.response.GetWorkoutsResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    GetWorkoutsResponse getWorkoutsResponse;
    GetMusclePartsResponse getMusclePartsResponse;

    // token
    String token = "";

    // data lists
    List<Exercise> exerciseList;

    // carousel images
    private int[] images = {R.drawable.bg1_32, R.drawable.bg2_32, R.drawable.bg3_32, R.drawable.bg4_32, R.drawable.bg5_32};

    CarouselView carouselView;

    // recycle view, adapter
    RecyclerView rvExerciseList;
    ExerciseItemAdapter exerciseItemAdapter;

    // components
    private Button homeBtn;
    private Button trainingBtn;
    private Button meBtn;
    private Button reportBtn;
    private Button nutritionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Log.d("TAG", "email: " + this.getSharedPreferences("LoginState", MODE_PRIVATE).getString(_CONST.USER_EMAIL, ""));

        token = getCurrentToken();

        Intent thisIntent = getIntent();
        int openToast = thisIntent.getIntExtra("openToast", 0);
        int getExercisesAgain = thisIntent.getIntExtra("getExercisesAgain", 0);

        if(openToast == 1) {
            Toast toast = Toast.makeText(HomeActivity.this, "Login successfully!", Toast.LENGTH_LONG);
            toast.show();
        }

        Log.d("TAG", "current token is: " + token);

        // set up view
        initView();


        // init recycle view
        initRecycleView(getExercisesAgain);

        getMuscleParts();
        getWorkouts();
        _CONST.getNutritions(this);
    }

    private void initRecycleView(int getExercisesAgain) {
        // get data source

        if(getExercisesAgain == 1 ) {
            getData();
            exerciseList = new ArrayList<>();
        } else {
            SharedPreferences preferences = getSharedPreferences("GeneralData", MODE_PRIVATE);

            Gson gson = new Gson();
            String exercisesListJson = preferences.getString("exerciseList", "");

            if(!exercisesListJson.equals("")) {
                exerciseList = gson.fromJson(exercisesListJson, ExerciseList.class).getExerciseList();
            } else {
                getData();
                exerciseList = new ArrayList<>();
            }
        }


        // adapter
        exerciseItemAdapter = new ExerciseItemAdapter(HomeActivity.this, exerciseList);

        // layout manager
        RecyclerView.LayoutManager layoutManagerExercise = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rvExerciseList = findViewById(R.id.rvExerciseList);
        rvExerciseList.suppressLayout(true);
        rvExerciseList.setLayoutManager(layoutManagerExercise);
        rvExerciseList.setAdapter(exerciseItemAdapter);
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

        // buttons
        homeBtn = findViewById(R.id.homeBtn);
        trainingBtn = findViewById(R.id.trainingBtn);
        meBtn = findViewById(R.id.meBtn);
        reportBtn = findViewById(R.id.reportBtn);
        nutritionBtn = findViewById(R.id.nutritionBtn);

        homeBtn.setOnClickListener(this);
        trainingBtn.setOnClickListener(this);
        meBtn.setOnClickListener(this);
        reportBtn.setOnClickListener(this);
        nutritionBtn.setOnClickListener(this);
    }

    public String getCurrentToken() {
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        String currentToken = preferences.getString(_CONST.USER_TOKEN,"");
        return currentToken;
    }

    public int getCurrentUserId() {
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        int currentUserId = preferences.getInt(_CONST.USER_ID,-1);
        return currentUserId;
    }

    private void getData() {
        Retrofit retrofit = _CONST.createRetrofit();
        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + getCurrentToken();

        service.getExercises(headerToken).enqueue(new Callback<GetExercisesResponse>() {

            @Override
            public void onResponse(Call<GetExercisesResponse> call, Response<GetExercisesResponse> response) {
                Log.d("TAG", "Get in the get exercises api function");
                if(response.body() != null) {
                    GetExercisesResponse getExercisesResponse = response.body();
                    exerciseItemAdapter.reloadData(getExercisesResponse.getExercises());
                    saveGeneralData(getExercisesResponse.getExercises(), null, null);
                    Log.d("TAG", "First exercise: " + getExercisesResponse.getExercises().get(0).getName());
                }
            }

            @Override
            public void onFailure(Call<GetExercisesResponse> call, Throwable t) {
                Log.d("TAG", "Cannot get exercises: " + t);
                Toast toast = Toast.makeText(HomeActivity.this, "Cannot get exercises: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public void getMuscleParts() {
        Retrofit retrofit = _CONST.createRetrofit();
        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + getCurrentToken();

        service.getMuscleParts(headerToken).enqueue(new Callback<GetMusclePartsResponse>() {
            @Override
            public void onResponse(Call<GetMusclePartsResponse> call, Response<GetMusclePartsResponse> response) {
                if(response.body() != null) {
                    getMusclePartsResponse = response.body();
                    saveGeneralData(null, getMusclePartsResponse.getMuscle_parts(), null);
                }
            }

            @Override
            public void onFailure(Call<GetMusclePartsResponse> call, Throwable t) {
                Log.d("WEATHER", "Cannot get muscle parts: " + t);
                Toast toast = Toast.makeText(HomeActivity.this, "Cannot get muscle parts: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void getWorkouts() {
        Retrofit retrofit = _CONST.createRetrofit();
        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + getCurrentToken();
        GetWorkoutsRequest getWorkoutsRequest = new GetWorkoutsRequest(getCurrentUserId());

        Log.d("TAG", "Current user id: " + getCurrentUserId());

        service.getWorkouts(headerToken, getCurrentUserId()).enqueue(new Callback<GetWorkoutsResponse>() {
            @Override
            public void onResponse(Call<GetWorkoutsResponse> call, Response<GetWorkoutsResponse> response) {
                if(response.body() != null) {
                    getWorkoutsResponse = response.body();
                    saveGeneralData(null, null, getWorkoutsResponse.getWorkouts());
                    Log.d("TAG", "1st workout: " + getWorkoutsResponse.getWorkouts().get(0).getCommentCount());
                }
            }

            @Override
            public void onFailure(Call<GetWorkoutsResponse> call, Throwable t) {
                Log.d("TAG", "Cannot get workouts: " + t);
                Toast toast = Toast.makeText(HomeActivity.this, "Cannot get workouts: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void saveGeneralData(List<Exercise> exerciseList, List<MusclePart> musclePartList, List<Workout> workoutList) {

        SharedPreferences preferences = getSharedPreferences("GeneralData", MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();

        if(exerciseList != null) {
            ExerciseList exerciseList1Model = new ExerciseList(exerciseList);
            String exerciseJson = gson.toJson(exerciseList1Model);
            prefsEditor.putString("exerciseList", exerciseJson);
        }

        if(musclePartList != null) {
            MusclePartList musclePartList1Model = new MusclePartList(musclePartList);
            String musclePartListJson = gson.toJson(musclePartList1Model);
            prefsEditor.putString("musclePartList", musclePartListJson);
        }

        if(workoutList != null) {
            WorkoutList workoutList1Model = new WorkoutList(workoutList);
            String workoutListJson = gson.toJson(workoutList1Model);
            prefsEditor.putString("workoutList", workoutListJson);
        }

        prefsEditor.commit();
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
        } else if(v == nutritionBtn) {
            _CONST.goToNutritionPage(this);
        }
    }
}
