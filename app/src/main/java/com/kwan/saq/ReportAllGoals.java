package com.kwan.saq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kwan.saq.adapter.ReportGoalItemAdapter;
import com.kwan.saq.adapter.ServiceItemAdapter;
import com.kwan.saq.api.ApiManager;
import com.kwan.saq.model.Customer;
import com.kwan.saq.model.Goal;
import com.kwan.saq.response.GetGoalsResponse;
import com.kwan.saq.response.PostMealNoteResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReportAllGoals extends AppCompatActivity implements View.OnClickListener {

    ReportGoalItemAdapter reportGoalItemAdapter;
    Customer currentCustomer;
    List<Goal> goals;
    int take = 5;
    int skip = 0;

    private RecyclerView rvGoals;
    private Button loadMoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_goal_list);

        currentCustomer = _CONST.getCurrentCustomer(this);

        // set up view
        initView();
    }

    private void initView() {
        rvGoals = findViewById(R.id.rvGoals);
        loadMoreBtn = findViewById(R.id.loadMoreBtn);

        loadMoreBtn.setOnClickListener(this);

        // init recycle view
        initRecycleView();
    }

    private void initRecycleView() {
        goals = new ArrayList<>();

        getGoals();

        // adapter
        reportGoalItemAdapter = new ReportGoalItemAdapter(this, goals);

        // layout manager
        RecyclerView.LayoutManager layoutManagerExercise = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rvGoals.suppressLayout(true);
        rvGoals.setLayoutManager(layoutManagerExercise);
        rvGoals.setAdapter(reportGoalItemAdapter);
    }

    private void getGoals() {
        Retrofit retrofit = _CONST.createRetrofit();
        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + _CONST.getCurrentToken(this);

        service.getGoals(headerToken, currentCustomer.getId(), skip, take).enqueue(new Callback<GetGoalsResponse>() {

            @Override
            public void onResponse(Call<GetGoalsResponse> call, Response<GetGoalsResponse> response) {
                if(response.body() != null) {
                    GetGoalsResponse getGoalsResponse = response.body();

                    String message = getGoalsResponse.getMessage();

                    if(getGoalsResponse.getSuccess()) {
                        // go back to report main
                        if(getGoalsResponse.getGoals() != null) {
                            if(getGoalsResponse.getGoals().size() == 0) {
                                loadMoreBtn.setVisibility(View.GONE);
                                Toast toast = Toast.makeText(ReportAllGoals.this, "All goals are loaded", Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                goals.addAll(getGoalsResponse.getGoals());
                                reportGoalItemAdapter.reloadData(goals);
                            }
                        } else {
                            loadMoreBtn.setVisibility(View.GONE);
                            Toast toast = Toast.makeText(ReportAllGoals.this, "All goals are loaded", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    } else {
                        Toast toast = Toast.makeText(ReportAllGoals.this, "Cannot get goals: " + message, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetGoalsResponse> call, Throwable t) {
                Log.d("TAG", "Cannot get goals: " + t);
                Toast toast = Toast.makeText(ReportAllGoals.this, "Cannot get goals: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == loadMoreBtn) {
            skip += take;
            getGoals();
        }
    }
}
