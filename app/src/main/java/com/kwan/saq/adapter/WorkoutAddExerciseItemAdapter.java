package com.kwan.saq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kwan.saq.ExerciseDetailActivity;
import com.kwan.saq.ItemClickListener;
import com.kwan.saq.R;
import com.kwan.saq._CONST;
import com.kwan.saq.model.Exercise;
import com.kwan.saq.model.ExerciseSpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class WorkoutAddExerciseItemAdapter extends RecyclerView.Adapter {

    private Activity activity;
    private ArrayList<Integer> list;

    public WorkoutAddExerciseItemAdapter(Activity activity, ArrayList<Integer> list) {
        this.activity = activity;
        this.list = list;
    }

    public void reloadData(ArrayList<Integer> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.workout_create_add_exercise, parent, false);
        WorkoutAddExerciseItemAdapter.ExerciseItemHolder holder = new WorkoutAddExerciseItemAdapter.ExerciseItemHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WorkoutAddExerciseItemAdapter.ExerciseItemHolder hd = (WorkoutAddExerciseItemAdapter.ExerciseItemHolder) holder;
        int model = list.get(position);

        hd.exerciseCount.setText("Choose exercise " + model);

        List<Exercise> exerciseList = _CONST.getExerciseList(activity);
        List<ExerciseSpinner> exerciseSpinnerArrayList = new ArrayList<>();

        for(Exercise exercise: exerciseList) {
            exerciseSpinnerArrayList.add(new ExerciseSpinner(exercise.getId(), exercise.getName()));
        }

        //fill data in spinner
        ArrayAdapter<ExerciseSpinner> adapter = new ArrayAdapter<ExerciseSpinner>(activity, android.R.layout.simple_spinner_dropdown_item, exerciseSpinnerArrayList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hd.spinnerExercise.setAdapter(adapter);

        hd.spinnerExercise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        hd.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ExerciseItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView exerciseCount;
        public Spinner spinnerExercise;
        public EditText etTime, etRest;

        private ItemClickListener itemClickListener;

        public ExerciseItemHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            exerciseCount = itemView.findViewById(R.id.exerciseCount);
            spinnerExercise = itemView.findViewById(R.id.spinnerExercise);
            etTime = itemView.findViewById(R.id.etTime);
            etRest = itemView.findViewById(R.id.etRest);
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
