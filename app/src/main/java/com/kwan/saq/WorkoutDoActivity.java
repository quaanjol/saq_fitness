package com.kwan.saq;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kwan.saq.api.ApiManager;
import com.kwan.saq.model.CustomExerciseItem;
import com.kwan.saq.model.Exercise;
import com.kwan.saq.model.ExerciseList;
import com.kwan.saq.model.Workout;
import com.kwan.saq.request.PostFeedRequest;
import com.kwan.saq.response.LoginResponse;
import com.kwan.saq.response.PostFeedResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkoutDoActivity extends AppCompatActivity implements View.OnClickListener {

    Boolean stopped = false;

    Handler handler;
    Runnable runnable;
    Handler restHandler;
    Runnable restRunnable;

    DecimalFormat df;
    DecimalFormat df2;
    List<Exercise> exerciseList;
    CountDownTimer timer;

    private Workout workout;
    private List<CustomExerciseItem> exerciseItemList;
    private String workoutJson;
    private int currentExerciseIndex;

    // components
    private TextView currentTime;
    private VideoView currentExerciseVideo;
    private TextView currentExerciseTime;
    private TextView currentExerciseTitle;
    private ImageView nextExerciseImg;
    private TextView nextExerciseTitle;
    private TextView nextExerciseCompactInfo;
    private Button xBtn;
    private Button skipBtn;



    //Declare a variable to hold CountDownTimer remaining time

    CustomExerciseItem currentExerciseItem;

    private float totalTime = 0;
    private float totalCalo = 0;
    private float tempTime = 0;
    private float tempTimeDown = 0;
    private float tempTimeRest = 0;
    private float tempTimeRestDown = 0;
    private float finalResult = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_do);

        df = new DecimalFormat("#");
        df2 = new DecimalFormat("#.##");

        // set up current exercise index
        currentExerciseIndex = 0;

        // get all exercises
        SharedPreferences preferences = getSharedPreferences("GeneralData", MODE_PRIVATE);

        Gson gson = new Gson();

        String exercisesListJson = preferences.getString("exerciseList", "");
        //Log.d("TAG", "exercise items: " + exercisesListJson);

        if(!exercisesListJson.equals("")) {
            exerciseList = gson.fromJson(exercisesListJson, ExerciseList.class).getExerciseList();
        } else {
            exerciseList = null;
        }

        workoutJson = getIntent().getStringExtra("workout");


        if(!workoutJson.equals("")) {
            workout = gson.fromJson(workoutJson, Workout.class);
        } else {
            workout = null;
        }

        // set up view
        initView();

    }

    private void initView() {
        List<CustomExerciseItem> exerciseListTemp = workout.getExercises();
        exerciseItemList = new ArrayList<>();

        for(int i = 0; i < workout.getTotal_set(); i++) {
            exerciseItemList.addAll(workout.getExercises());
        }

        Log.d("TAG", "all exercise items size: " + exerciseItemList.size());


        Gson gson = new Gson();
        Log.d("TAG", "all exercise items: " + gson.toJson(exerciseItemList));

        //init components
        currentExerciseItem = exerciseItemList.get(currentExerciseIndex);

        currentTime = findViewById(R.id.currentTime);

        currentExerciseVideo = findViewById(R.id.currentExerciseVideo);

        currentExerciseTime = findViewById(R.id.currentExerciseTime);
        currentExerciseTitle = findViewById(R.id.currentExerciseTitle);

        nextExerciseImg = findViewById(R.id.nextExerciseImg);

        nextExerciseTitle = findViewById(R.id.nextExerciseTitle);
        nextExerciseCompactInfo = findViewById(R.id.nextExerciseCompactInfo);

        xBtn = findViewById(R.id.xBtn);
        skipBtn = findViewById(R.id.skipBtn);

        xBtn.setOnClickListener(this);
        skipBtn.setOnClickListener(this);

        // map value
        currentTime.setText(df.format(currentExerciseItem.getTime()) + " seconds");
        currentExerciseTime.setText(df.format(currentExerciseItem.getTime()) + " seconds");
        currentExerciseTitle.setText(currentExerciseItem.getName());

        // set video
        currentExerciseVideo.setVideoPath(findCurrentExerciseVideo(currentExerciseIndex));
        currentExerciseVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        currentExerciseVideo.start();

        // map next exercise's image and info
        if(currentExerciseIndex + 1 < exerciseItemList.size()) {
            String thisImg = "";

            Glide.with(WorkoutDoActivity.this).load(findNextExerciseImg(currentExerciseIndex + 1)).into(nextExerciseImg);
            nextExerciseTitle.setText(exerciseItemList.get(currentExerciseIndex + 1).getName());
            nextExerciseCompactInfo.setText(df.format(exerciseItemList.get(currentExerciseIndex + 1).getTime()) + " seconds - rest: " + df.format(exerciseItemList.get(currentExerciseIndex + 1).getRest()) + " seconds");
        } else {
            nextExerciseImg.setImageResource(R.drawable.logo);
            nextExerciseTitle.setText("No more exercise");
            nextExerciseCompactInfo.setText("End of Workout");
        }

        // init timer
        startTimer(currentExerciseIndex, 0);
    }

    private void startTimer(int currentExerciseIndex, float pauseTempTime) {
        stopped = false;

        currentExerciseItem = exerciseItemList.get(currentExerciseIndex);

        //

        currentExerciseTime.setText(df.format(currentExerciseItem.getTime()) + " seconds - rest " + df.format(currentExerciseItem.getRest()) + " seconds");
        currentExerciseTitle.setText(currentExerciseItem.getName());

        currentExerciseVideo.setVideoPath(findCurrentExerciseVideo(currentExerciseIndex));
        currentExerciseVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        currentExerciseVideo.start();

        // map next exercise's image and info
        if(currentExerciseIndex + 1 < exerciseItemList.size()) {
            String thisImg = "";

            Glide.with(WorkoutDoActivity.this).load(findNextExerciseImg(currentExerciseIndex + 1)).into(nextExerciseImg);
            nextExerciseTitle.setText(exerciseItemList.get(currentExerciseIndex + 1).getName());
            nextExerciseCompactInfo.setText(df.format(exerciseItemList.get(currentExerciseIndex + 1).getTime()) + " seconds - rest: " + df.format(exerciseItemList.get(currentExerciseIndex + 1).getRest()) + " seconds");
        } else {
            nextExerciseImg.setImageResource(R.drawable.logo);
            nextExerciseTitle.setText("No more exercise");
            nextExerciseCompactInfo.setText("End of Workout");
        }

        //

        if(pauseTempTime == 0) {
            tempTimeDown = currentExerciseItem.getTime();
        } else {
            tempTimeDown = pauseTempTime;
        }

        float realTime = currentExerciseItem.getTime();
        float caloTemp = currentExerciseItem.getCalo();

        currentTime.setText(df.format(tempTimeDown) + "s");

        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                if(tempTimeDown > 0) {
                    tempTimeDown--;
                    currentTime.setText(df.format(tempTimeDown) + "s");

                    totalTime++;
                    totalCalo += caloTemp / realTime;
                } else {
                    tempTimeDown = 0;
                    handler.removeCallbacks(this);
                    stopped = true;
                    tempTime = 0;

                    WorkoutDoActivity.this.currentExerciseVideo.stopPlayback();

                    if(WorkoutDoActivity.this.currentExerciseIndex == exerciseItemList.size() - 1) {
                        currentTime.setText("Total time: " + totalTime + " - total calo: " + totalCalo);
                        saveFeed();
                        return;
                    } else {
                        startRest(WorkoutDoActivity.this.currentExerciseIndex, 0);
                    }

                    Log.d("TAG", "current total time " + totalTime);
                    Log.d("TAG", "current total calo " + totalCalo);
                    Log.d("TAG", "current time temp " + tempTime);
                    return;
                }

                if(!stopped) {
                    handler.postDelayed(this, 1000); // Optional, to repeat the task.
                }
            }
        };

        handler.postDelayed(runnable, 1000);

    }

    private void startRest(int currentExerciseIndex, float pauseTempTime) {
        stopped = false;

        currentExerciseItem = exerciseItemList.get(currentExerciseIndex);


        if(pauseTempTime == 0) {
            tempTimeRestDown = currentExerciseItem.getRest();
        } else {
            tempTimeRestDown = pauseTempTime;
        }
        currentTime.setText(df.format(tempTimeRestDown) + "s");


        currentExerciseTime.setText(df.format(currentExerciseItem.getRest()) + " seconds");
        currentExerciseTitle.setText("Rest");

        currentExerciseVideo.setVideoPath(_CONST.REST_VIDEO_URL);
        currentExerciseVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        currentExerciseVideo.start();

        restHandler = new Handler(Looper.getMainLooper());
        restRunnable = new Runnable() {
            @Override
            public void run() {
                if(tempTimeRestDown > 0) {
                    tempTimeRestDown--;
                    currentTime.setText(df.format(tempTimeRestDown) + "s");

                    totalTime++;
                } else {
                    tempTimeRestDown = 0;
                    restHandler.removeCallbacks(this);
                    stopped = true;
                    WorkoutDoActivity.this.currentExerciseVideo.stopPlayback();
                    if(WorkoutDoActivity.this.currentExerciseIndex + 1 < exerciseItemList.size()) {

                        WorkoutDoActivity.this.currentExerciseIndex++;
                        startTimer(WorkoutDoActivity.this.currentExerciseIndex, 0);
                        return;

                    } else {
                        currentTime.setText("Total time: " + totalTime + " - total calo: " + totalCalo);
                    }
                }

                if(!stopped) {
                    restHandler.postDelayed(this, 1000); // Optional, to repeat the task.
                }
            }
        };

        restHandler.postDelayed(restRunnable, 1000);
    }



    private String findNextExerciseImg(int exerciseIndexToFind) {
        String thisImg = "";

        if(exerciseList != null) {
            for(Exercise exercise: exerciseList) {
                if(exercise.getName().equals(exerciseItemList.get(exerciseIndexToFind).getName())) {
                    thisImg = exercise.getImg();
                    break;
                }
            }
        }

        return thisImg;
    }

    public String findCurrentExerciseVideo(int exerciseIndexToFind) {
        String thisVideo = "";

        if(exerciseList != null) {

            for(Exercise exercise: exerciseList) {
                if(exercise.getName().equals(exerciseItemList.get(exerciseIndexToFind).getName())) {
                    thisVideo = exercise.getVideo();
                    break;
                }
            }

        }

        return thisVideo;
    }



    @Override
    public void onClick(View v) {
        if(v == xBtn) {
            openPauseModal();
        } else if(v == skipBtn) {
            skipCurrentExercise();
        }
    }

    private void skipCurrentExercise() {
        WorkoutDoActivity.this.currentExerciseVideo.stopPlayback();
        if(currentExerciseIndex == (exerciseItemList.size() - 1)) {
            skipBtn.setEnabled(false);

            stopped = true;
            if(restRunnable != null) {
                restHandler.removeCallbacks(restRunnable);
            }

            if(runnable != null) {
                handler.removeCallbacks(runnable);
            }

            currentTime.setText("Total time: " + totalTime + " - total calo: " + totalCalo);
            saveFeed();
            return;
        }

        if(tempTimeDown > 0) {
            stopped = true;
            handler.removeCallbacks(runnable);
            tempTimeDown = 0;

            if(restRunnable != null) {
                restHandler.removeCallbacks(restRunnable);
            }

            WorkoutDoActivity.this.currentExerciseVideo.stopPlayback();
            startRest(currentExerciseIndex, 0);
        } else if(tempTimeRestDown > 0) {
            stopped = true;
            restHandler.removeCallbacks(restRunnable);
            tempTimeRestDown = 0;

            if(runnable != null) {
                handler.removeCallbacks(runnable);
            }

            WorkoutDoActivity.this.currentExerciseVideo.stopPlayback();
            currentExerciseIndex++;
            startTimer(currentExerciseIndex, 0);
        }
    }

    private void saveFeed() {

        Retrofit retrofit = _CONST.createRetrofit();

        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + _CONST.getCurrentToken(this);

        PostFeedRequest postFeedRequest = new PostFeedRequest(_CONST.getCurrentCustomer(this).getId(), workout.getId(), totalCalo, totalTime, totalCalo / workout.getCalo());

        Gson gson = new Gson();
        Log.d("TAG", "postFeedRequest: " + gson.toJson(postFeedRequest));

        service.postFeed(headerToken, postFeedRequest).enqueue(new Callback<PostFeedResponse>() {
            @Override
            public void onResponse(Call<PostFeedResponse> call, Response<PostFeedResponse> response) {
                if(response.body() != null) {
                    PostFeedResponse postFeedResponse = response.body();

                    String message = postFeedResponse.getMessage();


                    if(postFeedResponse.getSuccess() == true) {
                        Intent intent = new Intent(WorkoutDoActivity.this, WorkoutMainActivity.class);
                        intent.putExtra("feedCreated", 1);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast toast = Toast.makeText(WorkoutDoActivity.this, message, Toast.LENGTH_LONG);
                        toast.show();
                    }
                    return;
                }
            }

            @Override
            public void onFailure(Call<PostFeedResponse> call, Throwable t) {
                Log.d("TAG", "cannot register via api: " + t);
                Toast toast = Toast.makeText(WorkoutDoActivity.this, "Cannot save feed: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        openPauseModal();
    }

    private void openPauseModal() {
        // stop video
        WorkoutDoActivity.this.currentExerciseVideo.stopPlayback();

        // stop counting time
        stopped = true;
        if(runnable != null) {
            handler.removeCallbacks(runnable);
        }

        if(restRunnable != null) {
            restHandler.removeCallbacks(restRunnable);
        }

        stopped = true;

        // open alert
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Do you want to discard and qui workout?");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Quit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        WorkoutDoActivity.this.finish();
                        dialog.cancel();
                        return;
                    }
                });

        builder1.setNegativeButton(
                "Resume",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if(tempTimeDown > 0) {
                            startTimer(WorkoutDoActivity.this.currentExerciseIndex, tempTimeDown);
                        }

                        if(tempTimeRestDown > 0) {
                            startRest(WorkoutDoActivity.this.currentExerciseIndex, tempTimeRestDown);
                        }

                        WorkoutDoActivity.this.currentExerciseVideo.start();

                        dialog.cancel();
                        return;
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}
