package com.kwan.saq.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kwan.saq.ItemClickListener;
import com.kwan.saq.R;
import com.kwan.saq._CONST;
import com.kwan.saq.model.Customer;
import com.kwan.saq.model.Feed;
import com.kwan.saq.model.Nutrition;

import java.text.DecimalFormat;
import java.util.List;

public class NutritionItemAdapter extends RecyclerView.Adapter {

    private Activity activity;
    private List<Nutrition> list;

    public NutritionItemAdapter(Activity activity, List<Nutrition> list) {

        this.activity = activity;
        this.list = list;
    }

    public void reloadData(List<Nutrition> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.nutritio_item, parent, false);
        NutritionItemAdapter.NutritionItemHolder holder = new NutritionItemAdapter.NutritionItemHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        NutritionItemAdapter.NutritionItemHolder hd = (NutritionItemAdapter.NutritionItemHolder) holder;
        Nutrition model = list.get(position);

        DecimalFormat df = new DecimalFormat("#.#");

        hd.nutritionName.setText(model.getName());
        hd.nutritionCategory.setText(model.getNutrition_category_name());
        hd.nutritionServing.setText("" + df.format(model.getServing()));
        hd.nutritionCalo.setText("" + df.format(model.getCalo()));

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


    public class NutritionItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView nutritionName, nutritionCategory, nutritionServing, nutritionCalo;

        private ItemClickListener itemClickListener;

        public NutritionItemHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            nutritionName = itemView.findViewById(R.id.nutritionName);
            nutritionCategory = itemView.findViewById(R.id.nutritionCategory);
            nutritionServing = itemView.findViewById(R.id.nutritionServing);
            nutritionCalo = itemView.findViewById(R.id.nutritionCalo);
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
