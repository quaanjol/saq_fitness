package com.kwan.saq.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwan.saq.ItemClickListener;
import com.kwan.saq.R;
import com.kwan.saq._CONST;
import com.kwan.saq.model.Exercise;
import com.kwan.saq.model.ExerciseSpinner;
import com.kwan.saq.model.Nutrition;

import java.util.ArrayList;
import java.util.List;

public class ReportMealInputAdapter extends RecyclerView.Adapter {

    List<Nutrition> nutritionList;

    private Activity activity;
    private ArrayList<Integer> list;

    public ReportMealInputAdapter(Activity activity, ArrayList<Integer> list) {
        this.activity = activity;
        this.list = list;

        nutritionList = _CONST.getNutritionList(activity);
    }

    public void reloadData(ArrayList<Integer> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.report_add_meal_item, parent, false);
        ReportMealInputAdapter.MealItemHolder holder = new ReportMealInputAdapter.MealItemHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReportMealInputAdapter.MealItemHolder hd = (ReportMealInputAdapter.MealItemHolder) holder;
        int model = list.get(position);

        hd.mealCount.setText("Meal note " + model);

        //nutritionList
        ArrayList<String> nutritionSpinnerArrayList = new ArrayList<>();

        for(Nutrition nutrition: nutritionList) {
            nutritionSpinnerArrayList.add(nutrition.getName());
        }

        //fill data in spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, nutritionSpinnerArrayList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hd.spinnerNutrition.setAdapter(adapter);

        hd.spinnerNutrition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    public class MealItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public Spinner spinnerNutrition;
        public EditText nutritionGram;
        private TextView mealCount;

        private ItemClickListener itemClickListener;

        public MealItemHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            spinnerNutrition = itemView.findViewById(R.id.spinnerNutrition);
            nutritionGram = itemView.findViewById(R.id.nutritionGram);
            mealCount = itemView.findViewById(R.id.mealCount);
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
