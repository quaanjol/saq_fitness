package com.kwan.saq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kwan.saq.adapter.ReportMealInputAdapter;
import com.kwan.saq.api.ApiManager;
import com.kwan.saq.model.Customer;
import com.kwan.saq.model.MealNotePostItem;
import com.kwan.saq.model.Nutrition;
import com.kwan.saq.request.PostMealNoteRequest;
import com.kwan.saq.request.PostWorkoutRequest;
import com.kwan.saq.response.PostMealNoteResponse;
import com.kwan.saq.response.PostWorkoutResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReportAddMeal extends AppCompatActivity implements View.OnClickListener {

    int mealNumberFromIntent;
    String mealDescriptionFromIntent;
    ReportMealInputAdapter reportMealInputAdapter;
    ArrayList<Integer> setCount;
    List<Nutrition> nutritionList;
    List<MealNotePostItem> mealNotePostItems;
    Customer currentCustomer;
    Gson gson;


    private TextView mealNumber;
    private TextView mealDescription;
    private RecyclerView rvMealInputs;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_add_meal);

        nutritionList = _CONST.getNutritionList(this);
        mealNotePostItems = new ArrayList<>();

        currentCustomer = _CONST.getCurrentCustomer(this);
        gson = new Gson();

        Intent thisIntent = this.getIntent();

        mealNumberFromIntent = thisIntent.getIntExtra("mealNumber", 0);
        mealDescriptionFromIntent = thisIntent.getStringExtra("mealDescription");

        setCount = new ArrayList();
        if(mealNumberFromIntent > 0) {
            for(int i = 0; i < mealNumberFromIntent; i++) {
                setCount.add(i + 1);
            }
        }

        // set up view
        initView();
    }

    private void initView() {
        mealNumber = findViewById(R.id.mealNumber);
        mealDescription = findViewById(R.id.mealDescription);
        rvMealInputs = findViewById(R.id.rvMealInputs);
        submitBtn = findViewById(R.id.submitBtn);

        mealNumber.setText("" + mealNumberFromIntent);
        mealDescription.setText(mealDescriptionFromIntent);

        submitBtn.setOnClickListener(this);

        // init recycle view
        initRecycleView();
    }

    private void initRecycleView() {
        // adapter
        reportMealInputAdapter = new ReportMealInputAdapter(this, setCount);

        // layout manager
        RecyclerView.LayoutManager layoutManagerExercise = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rvMealInputs.suppressLayout(true);
        rvMealInputs.setLayoutManager(layoutManagerExercise);
        rvMealInputs.setAdapter(reportMealInputAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v == submitBtn) {
            createmealNote();
        }
    }

    private void createmealNote() {
        Boolean check = true;

        int nutritionIdtemp = -1;
        float gramTemp = -1;

        for(int i = 0; i < mealNumberFromIntent; i++) {
            ReportMealInputAdapter.MealItemHolder holder = (ReportMealInputAdapter.MealItemHolder) rvMealInputs.findViewHolderForAdapterPosition(i);

            if(holder.spinnerNutrition.getSelectedItem().toString().equals("") ||
                    holder.nutritionGram.getText().toString().equals("")) {
                check = false;
                break;
            }

            for(Nutrition nutrition: nutritionList) {
                if(nutrition.getName().equals(holder.spinnerNutrition.getSelectedItem().toString())) {
                    nutritionIdtemp = nutrition.getId();
                    break;
                }
            }

            gramTemp = Float.parseFloat(holder.nutritionGram.getText().toString());

            mealNotePostItems.add(new MealNotePostItem(nutritionIdtemp, gramTemp));

            Log.d("TAG", "Meal note " + (i + 1) + ": " + String.valueOf(holder.spinnerNutrition.getSelectedItem().toString()));
            nutritionIdtemp = -1;
            gramTemp = -1;
        }

        if(!check) {
            Toast toast = Toast.makeText(this, "Please fill in all the information to continue", Toast.LENGTH_SHORT);
            toast.show();
            return;
        } else {
            PostMealNoteRequest postMealNoteRequest = new PostMealNoteRequest(currentCustomer.getId(),
                    mealDescriptionFromIntent, mealNumberFromIntent, gson.toJson(mealNotePostItems));

            Log.d("TAG", "mealNotePostItems json: " + gson.toJson(mealNotePostItems));
            saveMealNote(postMealNoteRequest);
        }
    }

    private void saveMealNote(PostMealNoteRequest postMealNoteRequest) {
        Retrofit retrofit = _CONST.createRetrofit();
        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + _CONST.getCurrentToken(this);

        service.postMealNote(headerToken, postMealNoteRequest).enqueue(new Callback<PostMealNoteResponse>() {

            @Override
            public void onResponse(Call<PostMealNoteResponse> call, Response<PostMealNoteResponse> response) {
                if(response.body() != null) {
                    PostMealNoteResponse postMealNoteResponse = response.body();

                    String message = postMealNoteResponse.getMessage();

                    if(postMealNoteResponse.getSuccess()) {
                        // go back to report main
                        goBackToReportMainPage();

                    } else {
                        Toast toast = Toast.makeText(ReportAddMeal.this, "Cannot post meal note: " + message, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostMealNoteResponse> call, Throwable t) {
                Log.d("TAG", "Cannot post workout: " + t);
                Toast toast = Toast.makeText(ReportAddMeal.this, "Cannot post workout: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void goBackToReportMainPage() {
        Intent reportMainIntent = new Intent(this, ReportMainActivity.class);

        reportMainIntent.putExtra("newMealNote", 1);
        startActivity(reportMainIntent);
        finish();
    }
}
