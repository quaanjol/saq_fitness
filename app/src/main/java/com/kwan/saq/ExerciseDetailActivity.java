package com.kwan.saq;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kwan.saq.model.Exercise;

public class ExerciseDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Exercise exercise;

    private ImageView exerciseImg;
    private Button backBtn, playVideoBtn;
    private TextView exerciseMusclePart, exerciseTitle, exerciseDescription, exerciseCompactInfo;
    private VideoView exerciseVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_detail);

        exercise = (Exercise) getIntent().getSerializableExtra("exercise");

        // set up view
        initView();

    }

    private void initView() {
        exerciseImg = findViewById(R.id.exerciseImg);
        backBtn = findViewById(R.id.backBtn);
        playVideoBtn = findViewById(R.id.playVideoBtn);
        exerciseMusclePart = findViewById(R.id.exerciseMusclePart);
        exerciseTitle = findViewById(R.id.exerciseTitle);
        exerciseDescription = findViewById(R.id.exerciseDescription);
        exerciseCompactInfo = findViewById(R.id.exerciseCompactInfo);
        exerciseVideo = findViewById(R.id.exerciseVideo);

        // add click listener
        playVideoBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);

        // set up content view
        //Glide.with(this).load(exercise.getImg()).into(exerciseImg);
        exerciseVideo.setVideoPath(exercise.getVideo());
        exerciseVideo.start();

        exerciseTitle.setText(exercise.getName());
        exerciseDescription.setText(Html.fromHtml(exercise.getDescription()));
        exerciseMusclePart.setText(exercise.getMuscle_part());

        // compact information
        String compactInformation = "<b>Muscle groups:</b> <br/>";

        for(String name: exercise.getMuscle_parts()) {
            compactInformation += "&#8226; " + name + "<br/>";
        }

        compactInformation += "<br/><b>Total calories burned per hour:</b> " + exercise.getCalo_per_hour() + " Kcal";

        exerciseCompactInfo.setText(Html.fromHtml((compactInformation)));
    }


    @Override
    public void onClick(View v) {
        if(v == playVideoBtn) {
            playVideo();
        } else if(v == backBtn) {
            goToPreviousActivity();
        }
    }

    private void playVideo() {
        exerciseVideo.setVideoPath(exercise.getVideo());
        exerciseVideo.seekTo(0);
        exerciseVideo.start();
    }

    public void goToPreviousActivity() {
        Log.d("TAG", "Go back to previous activity please.");
        this.finish();
    }
}
