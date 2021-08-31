package com.kwan.saq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kwan.saq.ExerciseDetailActivity;
import com.kwan.saq.HomeActivity;
import com.kwan.saq.ItemClickListener;
import com.kwan.saq.R;
import com.kwan.saq.model.Exercise;

import java.text.DecimalFormat;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP;


public class ExerciseItemAdapter extends RecyclerView.Adapter {
    Intent exerciseDetailIntent;

    private Activity activity;
    private List<Exercise> list;

    public ExerciseItemAdapter(Activity activity, List<Exercise> list) {
        this.activity = activity;
        this.list = list;
    }

    public void reloadData(List<Exercise> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.exercise_item, parent, false);
        ExerciseItemAdapter.ExerciseItemHolder holder = new ExerciseItemAdapter.ExerciseItemHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ExerciseItemAdapter.ExerciseItemHolder hd = (ExerciseItemAdapter.ExerciseItemHolder) holder;
        Exercise model = list.get(position);

        DecimalFormat df = new DecimalFormat("#.#");

        hd.exerciseTitle.setText(model.getName());
        hd.exerciseCompactInfo.setText(df.format(model.getCalo_per_hour()) + " calories burned per hour - Main muscle: " + model.getMuscle_part());

        Glide.with(activity).load(model.getImg()).into(hd.exerciseImg);

        hd.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                    Toast.makeText(activity, "You long clicked on " + list.get(position).getName() + " exercise", Toast.LENGTH_SHORT).show();
                else
                    exerciseDetailIntent = new Intent(activity, ExerciseDetailActivity.class);
                    exerciseDetailIntent.putExtra("exercise", model);
//                    activity.startActivityForResult(exerciseDetailIntent, FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                    activity.startActivity(exerciseDetailIntent);
                    Toast.makeText(activity, "You clicked on " + list.get(position).getName() + " exercise", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
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
