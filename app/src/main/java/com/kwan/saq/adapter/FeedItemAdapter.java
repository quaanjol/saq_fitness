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
import com.google.gson.Gson;
import com.kwan.saq.ExerciseDetailActivity;
import com.kwan.saq.ItemClickListener;
import com.kwan.saq.R;
import com.kwan.saq.WorkoutDetailActivity;
import com.kwan.saq._CONST;
import com.kwan.saq.model.Customer;
import com.kwan.saq.model.Exercise;
import com.kwan.saq.model.Feed;

import java.text.DecimalFormat;
import java.util.List;

public class FeedItemAdapter extends RecyclerView.Adapter {
    Customer currentCustomer;

    private Activity activity;
    private List<Feed> list;

    public FeedItemAdapter(Activity activity, List<Feed> list) {
        currentCustomer = _CONST.getCurrentCustomer(activity);

        this.activity = activity;
        this.list = list;
    }

    public void reloadData(List<Feed> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.customer_profile_feed_item, parent, false);
        FeedItemAdapter.FeedItemHolder holder = new FeedItemAdapter.FeedItemHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        FeedItemAdapter.FeedItemHolder hd = (FeedItemAdapter.FeedItemHolder) holder;
        Feed model = list.get(position);

        DecimalFormat df = new DecimalFormat("#.#");

        hd.customerName.setText(model.getCustomer());

        // format time
        String originalTime = model.getCreated_at();
        String[] parts = originalTime.split("T");

        if(parts.length > 0) {
            hd.feedTime.setText(_CONST.formatTime(parts[0]));
        } else {
            hd.feedTime.setText("N/A");
        }


        Glide.with(activity).load(currentCustomer.getImg()).into(hd.customerAvatar);
        Glide.with(activity).load(model.getImg()).into(hd.feedImg);

        hd.workoutName.setText(model.getWorkout());
        hd.workoutCompactInfo.setText(df.format(model.getCalo()) + " calories in " + df.format(model.getTime()) + " seconds");

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


    public class FeedItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView customerName, feedTime, workoutName, workoutCompactInfo;
        ImageView customerAvatar, feedImg;

        private ItemClickListener itemClickListener;

        public FeedItemHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            customerName = itemView.findViewById(R.id.customerName);
            feedTime = itemView.findViewById(R.id.feedTime);
            workoutName = itemView.findViewById(R.id.workoutName);
            workoutCompactInfo = itemView.findViewById(R.id.workoutCompactInfo);
            customerAvatar = itemView.findViewById(R.id.customerAvatar);
            feedImg = itemView.findViewById(R.id.feedImg);
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
