package com.kwan.saq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kwan.saq.ExerciseDetailActivity;
import com.kwan.saq.ItemClickListener;
import com.kwan.saq.R;
import com.kwan.saq.ReportMainActivity;
import com.kwan.saq.ReportMealNoteDetailActivity;
import com.kwan.saq._CONST;
import com.kwan.saq.api.ApiManager;
import com.kwan.saq.model.Customer;
import com.kwan.saq.model.Exercise;
import com.kwan.saq.model.Feed;
import com.kwan.saq.model.MealNoteItem;
import com.kwan.saq.model.Note;
import com.kwan.saq.response.GetMealNotesResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReportMealItemAdapter extends RecyclerView.Adapter {
    Gson gson = new Gson();
    Intent mealNoteDetailIntent;

    private Activity activity;
    private List<Note> list;
    List<Feed> feedList;
    Customer currentCustomer;

    public ReportMealItemAdapter(Activity activity, List<Note> list, List<Feed> passedFeedList, Customer passedCurrentCustomer) {
        this.activity = activity;
        this.list = list;
        feedList = passedFeedList;
        currentCustomer = passedCurrentCustomer;
    }

    public void reloadData(List<Note> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.report_meal_note_item, parent, false);
        ReportMealItemAdapter.NoteItemHolder holder = new ReportMealItemAdapter.NoteItemHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NoteItemHolder hd = (NoteItemHolder) holder;
        Note model = list.get(position);

        DecimalFormat df = new DecimalFormat("#.#");

        // format time
        String originalTime = model.getCreated_at();
        String[] parts = originalTime.split("T");

        if(parts.length > 0) {
            hd.mealNoteTime.setText("Health recap on " + _CONST.formatTime(parts[0]));
        } else {
            hd.mealNoteTime.setText("Health recap on N/A");
        }

        float totalCalo = 0;
        int mealCount = 0;

        for(MealNoteItem mealNoteItem: model.getMeal_notes()) {
            totalCalo += mealNoteItem.getCalo();
            mealCount++;
        }

        hd.mealCount.setText("You took: " + mealCount + " meals (" + df.format(totalCalo) + " calories in total)");
//        hd.noteDescription.setText(model.getDescription());

        // compact information
        String compactInformation = "<i>Meals taken:</i> <br/>";

        for(MealNoteItem mealNoteItem: model.getMeal_notes()) {
            compactInformation += "&#8226; " + mealNoteItem.getMeal() + " (" + df.format(mealNoteItem.getGram()) + "g" + " - " + df.format(mealNoteItem.getCalo()) + " calories)" + "<br/>";
        }

        ArrayList<Feed> thoseFeeds = new ArrayList<>();
        for(Feed feed: feedList) {
            if(feed.getCreated_at().split("T")[0].equals(parts[0])) {
                thoseFeeds.add(feed);
            }
        }

        compactInformation += "<i>Workouts done:</i> <br/>";
        float totalWorkoutCalo = 0;

        if(thoseFeeds.size() <= 0) {
            compactInformation += "No workouts done <br/>";

            hd.caloriesCount.setText("You burned 0 calories by exercises");
        } else {
            for(Feed thisFeed: thoseFeeds) {
                totalWorkoutCalo += thisFeed.getCalo();
                compactInformation += "&#8226; " + thisFeed.getWorkout() + " (" + df.format(thisFeed.getTime()) + " seconds" + " - " + df.format(thisFeed.getCalo()) + " calories)" + "<br/>";
            }

            hd.caloriesCount.setText("You burned " + df.format(totalWorkoutCalo) +" calories by doing " + thoseFeeds.size() + " workouts.");
        }

        float caloriesDiff = currentCustomer.getAve_burn() - (totalCalo - totalWorkoutCalo);

        if(caloriesDiff <= 0) {
            compactInformation += "<i>Calories analysis:</i> <br/>";
            compactInformation += "You should take in an extra amount of " + df.format(caloriesDiff * -1) + " calories to maintain your fitness, "
            + "and a further " + 300 + " calories to gain weight.";
        } else {
            compactInformation += "<i>Calories analysis:</i> <br/>";
            compactInformation += "You should burn out an extra amount of " + df.format(caloriesDiff * 1) + " calories to maintain your fitness, "
            + "and a further 300 calories to lose weight";
        }


        String finalCompactInformation = compactInformation;
        float finalTotalCalo = totalCalo;
        float finalTotalWorkoutCalo = totalWorkoutCalo;
        hd.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick) {

                } else {
                    mealNoteDetailIntent = new Intent(activity, ReportMealNoteDetailActivity.class);
                    mealNoteDetailIntent.putExtra("note", gson.toJson(model));
                    mealNoteDetailIntent.putExtra("informationFromIntent", finalCompactInformation);
                    mealNoteDetailIntent.putExtra("totalIntake", finalTotalCalo);
                    mealNoteDetailIntent.putExtra("totalBurned", finalTotalWorkoutCalo);
                    mealNoteDetailIntent.putExtra("currentDate", parts[0]);

                    activity.startActivity(mealNoteDetailIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class NoteItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView mealNoteTime, mealCount, caloriesCount;

        private ItemClickListener itemClickListener;

        public NoteItemHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            mealNoteTime = itemView.findViewById(R.id.mealNoteTime);
            mealCount = itemView.findViewById(R.id.mealCount);
            caloriesCount = itemView.findViewById(R.id.caloriesCount);
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
