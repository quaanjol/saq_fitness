package com.kwan.saq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kwan.saq.ItemClickListener;
import com.kwan.saq.R;
import com.kwan.saq.ReportGoalDetail;
import com.kwan.saq._CONST;
import com.kwan.saq.model.Goal;
import com.kwan.saq.model.Nutrition;

import java.text.DecimalFormat;
import java.util.List;

public class ReportGoalItemAdapter extends RecyclerView.Adapter {
    Intent goalDetailIntent;
    Gson gson;

    private Activity activity;
    private List<Goal> list;

    public ReportGoalItemAdapter(Activity activity, List<Goal> list) {
        gson = new Gson();

        this.activity = activity;
        this.list = list;
    }

    public void reloadData(List<Goal> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.report_goal_item, parent, false);
        ReportGoalItemAdapter.GoalItemHolder holder = new ReportGoalItemAdapter.GoalItemHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ReportGoalItemAdapter.GoalItemHolder hd = (ReportGoalItemAdapter.GoalItemHolder) holder;
        Goal model = list.get(position);

        DecimalFormat df1 = new DecimalFormat("#");
        DecimalFormat df2 = new DecimalFormat("#.##");

        String status = "";
        if(model.getStatus() == 0) {
            status = "Expired";
        } else {
            status = "In progress";
        }

        hd.goalName.setText(model.getName() + " (" + df1.format(model.getCalo()) + " calories - Status: " + status + ")");
        hd.goalResult.setText(df2.format(model.getResult() * 100) + "%");

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT,
                model.getResult() * 100
        );
        hd.goalResultProgressBar.setLayoutParams(param);

        // format time
        String startDateString = model.getStart_date();
        String endDateString = model.getEnd_date();
        String[] parts1 = startDateString.split("T");
        String[] parts2 = endDateString.split("T");

        if(parts1.length > 0 && parts2.length > 0) {
            hd.goalTime.setText(_CONST.formatTime(parts1[0]) + " - " + _CONST.formatTime(parts2[0]));
        } else {
            hd.goalTime.setText("N/A - N/A");
        }

        hd.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick) {

                } else {
                    goalDetailIntent = new Intent(activity, ReportGoalDetail.class);
                    goalDetailIntent.putExtra("goalJson", gson.toJson(model));
                    activity.startActivity(goalDetailIntent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class GoalItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView goalName, goalResult, goalResultProgressBar, goalTime;

        private ItemClickListener itemClickListener;

        public GoalItemHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            goalName = itemView.findViewById(R.id.goalName);
            goalResult = itemView.findViewById(R.id.goalResult);
            goalResultProgressBar = itemView.findViewById(R.id.goalResultProgressBar);
            goalTime = itemView.findViewById(R.id.goalTime);
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
