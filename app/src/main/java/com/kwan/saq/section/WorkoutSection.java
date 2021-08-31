package com.kwan.saq.section;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kwan.saq.ExerciseDetailActivity;
import com.kwan.saq.ItemClickListener;
import com.kwan.saq.R;
import com.kwan.saq.WorkoutCommentsActivity;
import com.kwan.saq.WorkoutDetailActivity;
import com.kwan.saq.WorkoutMainActivity;
import com.kwan.saq._CONST;
import com.kwan.saq.model.ExerciseList;
import com.kwan.saq.model.Workout;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class WorkoutSection extends Section {
    Intent workoutDetailIntent;
    Intent commentsIntent;

    private String sectionTitle;
    private List<Workout> workoutList;
    private Activity activity;

    public WorkoutSection(String sectionTitle, List<Workout> workoutList, Activity activity) {
        // call constructor with layout resources for this Section header and items
        super(SectionParameters.builder()
                .itemResourceId(R.layout.section_item)
                .headerResourceId(R.layout.section_header)
                .build());
        this.sectionTitle = sectionTitle;
        this.workoutList = workoutList;
        this.activity = activity;
    }

    @Override
    public int getContentItemsTotal() {
        return workoutList.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new MyItemViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        // return an empty instance of ViewHolder for the headers of this section
        return new MyHeaderViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyItemViewHolder itemHolder = (MyItemViewHolder) holder;

        // bind your view here
        Workout model = workoutList.get(position);

        // format timestamp
        String originalTime = model.getCreated_at();
        String[] parts = originalTime.split("T");

        if(parts.length > 0) {
            itemHolder.workoutCreatedAt.setText(_CONST.formatTime(parts[0]));
        } else {
            itemHolder.workoutCreatedAt.setText("N/A");
        }

        itemHolder.workoutName.setText(model.getName());
        itemHolder.workoutMusclePart.setText(sectionTitle);
        itemHolder.workoutCommentBtn.setText(" " + model.getCommentCount());
        Log.d("TAG", model.getName() +" comments count: " + model.getCommentCount());

        if(model.getId() % 5 == 1) {
            itemHolder.container.setBackground(AppCompatResources.getDrawable(activity, R.drawable.workout_banner_1_scale));
        } else if(model.getId() % 5 == 2) {
            itemHolder.container.setBackground(AppCompatResources.getDrawable(activity, R.drawable.workout_banner_2_scale));
        } else if(model.getId() % 5 == 3) {
            itemHolder.container.setBackground(AppCompatResources.getDrawable(activity, R.drawable.workout_banner_3_scale));
        } else if(model.getId() % 5 == 4) {
            itemHolder.container.setBackground(AppCompatResources.getDrawable(activity, R.drawable.workout_banner_4_scale));
        } else if(model.getId() % 5 == 0) {
            itemHolder.container.setBackground(AppCompatResources.getDrawable(activity, R.drawable.workout_banner_5_scale));
        }

        itemHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                    Toast.makeText(activity, "You long clicked on " + model.getName() + " workout", Toast.LENGTH_SHORT).show();
                else
                    if(view == itemHolder.workoutCommentBtn) {
                        commentsIntent = new Intent(activity, WorkoutCommentsActivity.class);
                        commentsIntent.putExtra("workoutId", model.getId());
                        activity.startActivity(commentsIntent);
                        return;
                    } else {
                        workoutDetailIntent = new Intent(activity, WorkoutDetailActivity.class);
                        Gson gson = new Gson();

                        String workoutJson = "";
                        if(model != null) {
                            workoutJson = gson.toJson(model);
                        }

                        workoutDetailIntent.putExtra("workoutJson", workoutJson);
                        activity.startActivity(workoutDetailIntent);

                        Toast.makeText(activity, "You clicked on " + model.getName() + " workout", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    @Override
    public void onBindHeaderViewHolder(final RecyclerView.ViewHolder holder) {
        final MyHeaderViewHolder headerHolder = (MyHeaderViewHolder) holder;

        headerHolder.headerText.setText(sectionTitle);
    }

    public class MyItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView workoutCreatedAt;
        private TextView workoutName;
        private TextView workoutMusclePart;
        private Button workoutCommentBtn;
        private LinearLayout container;

        private ItemClickListener itemClickListener;

        public MyItemViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            workoutCreatedAt = (TextView) itemView.findViewById(R.id.workoutCreatedAt);
            workoutName = (TextView) itemView.findViewById(R.id.workoutName);
            workoutMusclePart = (TextView) itemView.findViewById(R.id.workoutMusclePart);
            workoutCommentBtn = (Button) itemView.findViewById(R.id.workoutCommentBtn);
            container = itemView.findViewById(R.id.container);
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

    public class MyHeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView headerText;

        public MyHeaderViewHolder(View itemView) {
            super(itemView);

            headerText = (TextView) itemView.findViewById(R.id.headerText);
        }
    }
}
