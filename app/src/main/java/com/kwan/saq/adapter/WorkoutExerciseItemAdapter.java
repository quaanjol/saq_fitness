package com.kwan.saq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kwan.saq.ExerciseDetailActivity;
import com.kwan.saq.ItemClickListener;
import com.kwan.saq.R;
import com.kwan.saq.model.CustomExerciseItem;
import com.kwan.saq.model.Exercise;
import com.kwan.saq.model.ExerciseItem;
import com.kwan.saq.model.ExerciseList;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class WorkoutExerciseItemAdapter extends RecyclerView.Adapter {

    LayoutInflater inflater;

    private Activity activity;
    private List<CustomExerciseItem> exerciseItemList;

    public WorkoutExerciseItemAdapter(Activity activity, List<CustomExerciseItem> exerciseItemList) {
        this.activity = activity;
        this.exerciseItemList = exerciseItemList;
    }

    public void reloadData(List<CustomExerciseItem> exerciseItemList) {
        this.exerciseItemList = exerciseItemList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.workout_exercise_item, parent, false);
        WorkoutExerciseItemAdapter.ExerciseItemHolder holder = new WorkoutExerciseItemAdapter.ExerciseItemHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WorkoutExerciseItemAdapter.ExerciseItemHolder hd = (WorkoutExerciseItemAdapter.ExerciseItemHolder) holder;
        CustomExerciseItem model = exerciseItemList.get(position);

        DecimalFormat df = new DecimalFormat("#");

        hd.exerciseTitle.setText(model.getName());
        hd.exerciseCompactInfo.setText(df.format(model.getTime()) + " seconds - rest: " + df.format(model.getRest()) + " seconds");

        List<Exercise> exerciseList;
        String thisImg = "";
        String thisVideo = "";
        SharedPreferences preferences = activity.getSharedPreferences("GeneralData", MODE_PRIVATE);

        Gson gson = new Gson();
        String exercisesListJson = preferences.getString("exerciseList", "");
        //Log.d("TAG", "exercise items: " + exercisesListJson);

        if(!exercisesListJson.equals("")) {
            exerciseList = gson.fromJson(exercisesListJson, ExerciseList.class).getExerciseList();

            for(Exercise exercise: exerciseList) {
                if(exercise.getName().equals(model.getName())) {
                    thisImg = exercise.getImg();
                    thisVideo = exercise.getVideo();
                    break;
                }
            }

        }

        Glide.with(activity).load(thisImg).into(hd.exerciseImg);

        String finalThisVideo = thisVideo;
        hd.setItemClickListener(new ItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                    Toast.makeText(activity, "You long clicked on " + model.getName() + " exercise", Toast.LENGTH_SHORT).show();
                else

                    inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
                    View layout = inflater.inflate(R.layout.exercise_item_popup, null);



                    PopupWindow popupWindow = new PopupWindow(layout, LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, true);

                    TextView popupText = (TextView) layout.findViewById(R.id.exerciseTitle);

                    popupText.setText(model.getName());


                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


                    VideoView exerciseVideo = layout.findViewById(R.id.exerciseVideo);
                    Button playVideoBtn = layout.findViewById(R.id.playVideoBtn);
                    Button closeBtn = layout.findViewById(R.id.closeBtn);

                    exerciseVideo.setVideoPath(finalThisVideo);
                    exerciseVideo.start();

                    // play video again
                    playVideoBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            exerciseVideo.setVideoPath(finalThisVideo);
                            exerciseVideo.seekTo(0);
                            exerciseVideo.start();
                        }
                    });

                    // close popup
                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });

                    Toast.makeText(activity, "You clicked on " + model.getName() + " exercise", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return exerciseItemList.size();
    }

    public class ExerciseItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView exerciseTitle, exerciseCompactInfo;
        ImageView exerciseImg;

        private ItemClickListener itemClickListener;

        public ExerciseItemHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            exerciseTitle = itemView.findViewById(R.id.exerciseTitle);
            exerciseCompactInfo = itemView.findViewById(R.id.exerciseCompactInfo);
            exerciseImg = itemView.findViewById(R.id.exerciseImg);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v , getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(),true);
            return true;
        }
    }
}
