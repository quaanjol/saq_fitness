package com.kwan.saq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kwan.saq.adapter.ExerciseItemAdapter;
import com.kwan.saq.adapter.WorkoutAddExerciseItemAdapter;
import com.kwan.saq.api.ApiManager;
import com.kwan.saq.model.Exercise;
import com.kwan.saq.model.ExerciseList;
import com.kwan.saq.model.ExerciseSpinner;
import com.kwan.saq.model.MusclePart;
import com.kwan.saq.model.MusclePartSpinner;
import com.kwan.saq.model.PostExerciseItem;
import com.kwan.saq.model.PostExerciseItemList;
import com.kwan.saq.model.WorkoutExerciseItemCreate;
import com.kwan.saq.request.PostWorkoutRequest;
import com.kwan.saq.response.GetFeedsResponse;
import com.kwan.saq.response.PostWorkoutResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkoutCreateAddExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    Gson gson = new Gson();
    WorkoutAddExerciseItemAdapter workoutAddExerciseItemAdapter;
    List<Exercise> exerciseList;
    List<MusclePart> musclePartList;
    List<PostExerciseItem> postExerciseItemList;
    PostExerciseItemList postExerciseItemListBig;

    String workoutNameFromIntent;
    String mainMuscleFromIntent;
    int totalSetsFromIntent;
    ArrayList setCount;

    private TextView workoutName;
    private TextView totalSets, mainMuscle;
    private RecyclerView rvExerciseInputs;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_create_save);

        postExerciseItemList = new ArrayList<>();
        exerciseList = _CONST.getExerciseList(this);
        musclePartList = _CONST.getMusclePartList(this);

        Intent intent = this.getIntent();
        workoutNameFromIntent = intent.getStringExtra("workoutName");
        totalSetsFromIntent = intent.getIntExtra("totalSets", -1);
        mainMuscleFromIntent = intent.getStringExtra("mainMuscle");

        setCount = new ArrayList();
        if(totalSetsFromIntent > 0) {
            for(int i = 0; i < totalSetsFromIntent; i++) {
                setCount.add(i + 1);
            }
        }

        initView();
    }

    private void initView() {
        workoutName = findViewById(R.id.workoutName);
        mainMuscle = findViewById(R.id.mainMuscle);
        totalSets = findViewById(R.id.totalSets);
        rvExerciseInputs = findViewById(R.id.rvExerciseInputs);
        submitBtn = findViewById(R.id.submitBtn);

        workoutName.setText(workoutNameFromIntent);
        totalSets.setText("" + totalSetsFromIntent);
        mainMuscle.setText(mainMuscleFromIntent);

        //
        initRecycleView();

        //
        submitBtn.setOnClickListener(this);
    }

    private void initRecycleView() {
        // adapter
        workoutAddExerciseItemAdapter = new WorkoutAddExerciseItemAdapter(this, setCount);

        // layout manager
        RecyclerView.LayoutManager layoutManagerExercise = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rvExerciseInputs.suppressLayout(true);
        rvExerciseInputs.setLayoutManager(layoutManagerExercise);
        rvExerciseInputs.setAdapter(workoutAddExerciseItemAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v == submitBtn) {
            postWorkout();
        }
    }

    private void postWorkout() {
        Boolean check = true;
        int exerciseIdTemp = -1;
        float timeTemp = -1;
        float restTemp = -1;
        String exerciseNameTemp = "";

        for(int i = 0; i < totalSetsFromIntent; i++) {
            WorkoutAddExerciseItemAdapter.ExerciseItemHolder holder = (WorkoutAddExerciseItemAdapter.ExerciseItemHolder) rvExerciseInputs.findViewHolderForAdapterPosition(i);

            if(holder.spinnerExercise.getSelectedItem().toString().equals("") ||
                    holder.etTime.getText().toString().equals("") ||
                    holder.etRest.getText().toString().equals("")) {
                check = false;
                break;
            }

            exerciseNameTemp = String.valueOf(holder.spinnerExercise.getSelectedItem().toString());
            timeTemp = Float.parseFloat(holder.etTime.getText().toString());
            restTemp = Float.parseFloat(holder.etRest.getText().toString());

            for(Exercise exercise: exerciseList) {
                if(exercise.getName().equals(exerciseNameTemp)) {
                    exerciseIdTemp = exercise.getId();
                }
            }

            postExerciseItemList.add(new PostExerciseItem(exerciseIdTemp, i + 1, timeTemp, restTemp));

            Log.d("TAG", "Exercise " + (i + 1) + ": " + String.valueOf(holder.spinnerExercise.getSelectedItem().toString()));
            exerciseIdTemp = -1;
            timeTemp = -1;
            restTemp = -1;
            exerciseNameTemp = "";
        }

        if(!check) {
            Toast toast = Toast.makeText(this, "Please fill in all the information to continue", Toast.LENGTH_SHORT);
            toast.show();
            return;
        } else {
            int musclePartId = -1;
            for(MusclePart musclePart: musclePartList) {
                if(musclePart.getName().equals(mainMuscleFromIntent)) {
                    musclePartId = musclePart.getId();
                }
            }

            int currentUserId = _CONST.getCurrentUserId(this);

            PostWorkoutRequest postWorkoutRequest = new PostWorkoutRequest(currentUserId, workoutNameFromIntent,
                    totalSetsFromIntent, musclePartId, gson.toJson(postExerciseItemList));

            Log.d("TAG", "postExerciseItemList json: " + gson.toJson(postExerciseItemList));
            Log.d("TAG", "postExerciseRequest json: " + postWorkoutRequest);
            saveWorkout(postWorkoutRequest);
        }
    }

    private void saveWorkout(PostWorkoutRequest postWorkoutRequest) {
        Retrofit retrofit = _CONST.createRetrofit();
        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + _CONST.getCurrentToken(this);

        service.postWorkout(headerToken, postWorkoutRequest).enqueue(new Callback<PostWorkoutResponse>() {

            @Override
            public void onResponse(Call<PostWorkoutResponse> call, Response<PostWorkoutResponse> response) {
                if(response.body() != null) {
                    PostWorkoutResponse postWorkoutResponse = response.body();

                    String message = postWorkoutResponse.getMessage();

                    if(postWorkoutResponse.getSuccess()) {
                        // update workout list
                        _CONST.getNewWorkouts(WorkoutCreateAddExerciseActivity.this);

                        // go back to workout main
                        goBackToWorkoutMainPage();

                    } else {
                        Toast toast = Toast.makeText(WorkoutCreateAddExerciseActivity.this, "Cannot post workout: " + message, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostWorkoutResponse> call, Throwable t) {
                Log.d("TAG", "Cannot post workout: " + t);
                Toast toast = Toast.makeText(WorkoutCreateAddExerciseActivity.this, "Cannot post workout: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void goBackToWorkoutMainPage() {
        Intent workoutMainIntent = new Intent(this, WorkoutMainActivity.class);

        workoutMainIntent.putExtra("newWorkout", 1);
        startActivity(workoutMainIntent);
        finish();
    }
}
