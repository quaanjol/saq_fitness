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
import com.kwan.saq.ItemClickListener;
import com.kwan.saq.R;
import com.kwan.saq._CONST;
import com.kwan.saq.model.Exercise;
import com.kwan.saq.model.WorkoutComment;

import java.text.DecimalFormat;
import java.util.List;

public class CommentItemAdapter extends RecyclerView.Adapter {
    Intent exerciseDetailIntent;

    private Activity activity;
    private List<WorkoutComment> list;

    public CommentItemAdapter(Activity activity, List<WorkoutComment> list) {
        this.activity = activity;
        this.list = list;
    }

    public void reloadData(List<WorkoutComment> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.workout_comment_item, parent, false);
        CommentItemAdapter.CommentItemHolder holder = new CommentItemAdapter.CommentItemHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommentItemAdapter.CommentItemHolder hd = (CommentItemAdapter.CommentItemHolder) holder;
        WorkoutComment model = list.get(position);

        hd.customerName.setText(model.getCustomerName());
        hd.commentContent.setText(model.getContent());

        // format time
        String originalTime = model.getCreated_at();
        String[] parts = originalTime.split("T");

        if(parts.length > 0) {
            hd.commentTime.setText(_CONST.formatTime(parts[0]));
        } else {
            hd.commentTime.setText("N/As");
        }

        Glide.with(activity).load(model.getCustomerImg()).into(hd.customerAvatar);

        hd.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick) {

                } else {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class CommentItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView customerName, commentContent, commentTime;
        ImageView customerAvatar;

        private ItemClickListener itemClickListener;

        public CommentItemHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            customerName = itemView.findViewById(R.id.customerName);
            commentContent = itemView.findViewById(R.id.commentContent);
            commentTime = itemView.findViewById(R.id.commentTime);
            customerAvatar = itemView.findViewById(R.id.customerAvatar);
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
