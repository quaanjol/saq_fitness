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
import com.kwan.saq.ItemClickListener;
import com.kwan.saq.R;
import com.kwan.saq.ReportServiceDetailActivity;
import com.kwan.saq.WorkoutCommentsActivity;
import com.kwan.saq.WorkoutDetailActivity;
import com.kwan.saq._CONST;
import com.kwan.saq.model.Customer;
import com.kwan.saq.model.Feed;
import com.kwan.saq.model.Service;

import java.text.DecimalFormat;
import java.util.List;

public class ServiceItemAdapter extends RecyclerView.Adapter {
    Customer currentCustomer;
    Intent serviceDetailIntent;

    private Activity activity;
    private List<Service> list;

    public ServiceItemAdapter(Activity activity, List<Service> list) {

        this.activity = activity;
        this.list = list;
    }

    public void reloadData(List<Service> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.report_servce_item, parent, false);
        ServiceItemAdapter.ServiceItemHolder holder = new ServiceItemAdapter.ServiceItemHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ServiceItemAdapter.ServiceItemHolder hd = (ServiceItemAdapter.ServiceItemHolder) holder;
        Service model = list.get(position);

        DecimalFormat timeDf = new DecimalFormat("#");
        DecimalFormat moneyDf = new DecimalFormat("#.##");

        if(model.getTime() > 1) {
            hd.serviceTime.setText(timeDf.format(model.getTime()) + " " + model.getPeriod() + "s");
        } else {
            hd.serviceTime.setText(timeDf.format(model.getTime()) + " " + model.getPeriod());
        }

        hd.serviceName.setText(model.getName());
        hd.servicePrice.setText("Price: $" + moneyDf.format(model.getPrice()));


        hd.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick) {

                } else {
                    serviceDetailIntent = new Intent(activity, ReportServiceDetailActivity.class);
                    Gson gson = new Gson();

                    String serviceJson = "";
                    if(model != null) {
                        serviceJson = gson.toJson(model);
                    }

                    serviceDetailIntent.putExtra("serviceJson", serviceJson);
                    activity.startActivity(serviceDetailIntent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ServiceItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView serviceTime, serviceName, servicePrice;

        private ItemClickListener itemClickListener;

        public ServiceItemHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            serviceTime = itemView.findViewById(R.id.serviceTime);
            serviceName = itemView.findViewById(R.id.serviceName);
            servicePrice = itemView.findViewById(R.id.servicePrice);
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
