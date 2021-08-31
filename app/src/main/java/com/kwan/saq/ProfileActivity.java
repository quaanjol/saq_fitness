package com.kwan.saq;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kwan.saq.adapter.ExerciseItemAdapter;
import com.kwan.saq.adapter.FeedItemAdapter;
import com.kwan.saq.api.ApiManager;
import com.kwan.saq.model.Customer;
import com.kwan.saq.model.ExerciseList;
import com.kwan.saq.model.Feed;
import com.kwan.saq.response.GetExercisesResponse;
import com.kwan.saq.response.GetFeedsResponse;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    FeedItemAdapter feedItemAdapter;
    List<Feed> feedList;
    GetFeedsResponse getFeedsResponse;
    Customer currentCustomer;
    DecimalFormat df;
    int take = 3;
    int skip = 0;

    private ImageView customerAvatar;
    private TextView customerName;
    private TextView customerTdee;
    private TextView customerWeightHeight;
    private TextView customerFeedCount;
    private TextView customerPaymentStatus;

    private Button homeBtn;
    private Button meBtn;
    private Button trainingBtn;
    private Button reportBtn;
    private Button nutritionBtn;
    private Button loadMoreBtn;
    private Button logoutBtn;

    private RecyclerView rvFeeds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_profile);

        df = new DecimalFormat("#.#");
        feedList = new ArrayList<>();

        initView();
    }

    private void initView() {
        customerAvatar = findViewById(R.id.customerAvatar);
        customerName = findViewById(R.id.customerName);
        customerTdee = findViewById(R.id.customerTdee);
        customerWeightHeight = findViewById(R.id.customerWeightHeight);
        customerFeedCount = findViewById(R.id.customerFeedCount);
        customerPaymentStatus = findViewById(R.id.customerPaymentStatus);

        homeBtn = findViewById(R.id.homeBtn);
        meBtn = findViewById(R.id.meBtn);
        trainingBtn = findViewById(R.id.trainingBtn);
        reportBtn = findViewById(R.id.reportBtn);
        nutritionBtn = findViewById(R.id.nutritionBtn);
        loadMoreBtn = findViewById(R.id.loadMoreBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        homeBtn.setOnClickListener(this);
        trainingBtn.setOnClickListener(this);
        meBtn.setOnClickListener(this);
        nutritionBtn.setOnClickListener(this);
        reportBtn.setOnClickListener(this);
        loadMoreBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

        rvFeeds = findViewById(R.id.rvFeeds);

        currentCustomer = _CONST.getCurrentCustomer(this);

        // map components' values
        Glide.with(this).load(currentCustomer.getImg()).into(customerAvatar);
        customerName.setText(currentCustomer.getName());
        customerTdee.setText(" " +df.format(currentCustomer.getTdee()) + " Kcal");
        customerWeightHeight.setText(" " + df.format(currentCustomer.getWeight()) + " Kg - " + df.format(currentCustomer.getHeight()) + " cm");

        if(currentCustomer.getMembership() == 1) {
            String pattern = "dd MMMM yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            String membership_expired = simpleDateFormat.format(currentCustomer.getMembership_expired());

            customerPaymentStatus.setText(" Payment status: active (expired on " + membership_expired + ")");
        } else {
            customerPaymentStatus.setText(" No active payments");
        }

        // init recycle view
        initRecycleView();
    }

    private void initRecycleView() {
        getFeeds();
        feedList = new ArrayList<>();


        // adapter
        feedItemAdapter = new FeedItemAdapter(this, feedList);

        // layout manager
        RecyclerView.LayoutManager layoutManagerExercise = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);


        rvFeeds.suppressLayout(true);
        rvFeeds.setLayoutManager(layoutManagerExercise);
        rvFeeds.setAdapter(feedItemAdapter);
    }

    private void getFeeds() {
        Retrofit retrofit = _CONST.createRetrofit();
        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + _CONST.getCurrentToken(this);

        service.getFeedByCustomerId(headerToken, currentCustomer.getId(), skip, take).enqueue(new Callback<GetFeedsResponse>() {

            @Override
            public void onResponse(Call<GetFeedsResponse> call, Response<GetFeedsResponse> response) {
                if(response.body() != null) {
                    getFeedsResponse = response.body();

                    if(getFeedsResponse.getFeeds().size() <= 0) {
                        ProfileActivity.this.loadMoreBtn.setVisibility(View.GONE);
                        Toast toast = Toast.makeText(ProfileActivity.this, "Loaded all activities", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        feedList.addAll(getFeedsResponse.getFeeds());
                        ProfileActivity.this.customerFeedCount.setText(" " + getFeedsResponse.getTotalFeeds() + " activities");
                        feedItemAdapter.reloadData(feedList);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetFeedsResponse> call, Throwable t) {
                Log.d("TAG", "Cannot get activities: " + t);
                Toast toast = Toast.makeText(ProfileActivity.this, "Cannot get activities: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == trainingBtn) {
            _CONST.goToTrainingPage(this);
        } else if(v == meBtn) {
            _CONST.goToProfilePage(this);
        } else if(v == homeBtn) {
            _CONST.goToHomeScreen(this);
        } else if(v == reportBtn) {
            _CONST.goToReportPage(this);
        } else if(v == loadMoreBtn) {
            skip += take;
            getFeeds();
        } else if(v == nutritionBtn) {
            _CONST.goToNutritionPage(this);
        } else if(v == logoutBtn) {
            // open alert
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Do you want to logout?");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "Logout",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            logout();
                            dialog.cancel();
                            return;
                        }
                    });

            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            return;
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    private void logout() {
        SharedPreferences preference = getSharedPreferences("LoginState", MODE_PRIVATE);
        SharedPreferences preference1 = getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences preference2 = getSharedPreferences("GeneralData", MODE_PRIVATE);
        SharedPreferences preference3 = getSharedPreferences("Register", MODE_PRIVATE);


        Log.d("TAG", "On logout clicked");
        Log.d("TAG", "email: " + preference1.getString(_CONST.USER_EMAIL, ""));
        Log.d("TAG", "Current email: " + preference1.getString(_CONST.USER_EMAIL,""));

        preference1.edit().putString(_CONST.USER_EMAIL, null);
        preference1.edit().putString(_CONST.USER_PASSWORD, null);
        preference1.edit().putString(_CONST.USER_TOKEN, null);
        preference1.edit().putInt(_CONST.USER_ID, -1);
        preference1.edit().putInt(_CONST.USER_ROLE_ID, -1);
        preference1.edit().putString(_CONST.USER_CUSTOMER, null);
        preference.edit().putBoolean("LoginState", false);

        preference2.edit().putString("exerciseList", null);
        preference2.edit().putString("musclePartList", null);
        preference2.edit().putString("workoutList", null);
        preference2.edit().putString("nutritionList", null);

        preference3.edit().putBoolean("RegisterState", false);

        preference1.edit().commit();
        preference2.edit().commit();
        preference3.edit().commit();

        preference1.edit().clear().commit();
        preference2.edit().clear().commit();
        preference3.edit().clear().commit();

        Log.d("TAG", "email: " + preference1.getString(_CONST.USER_EMAIL, ""));

        _CONST.goToMainPage(this);
    }
}
