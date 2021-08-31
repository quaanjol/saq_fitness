package com.kwan.saq;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kwan.saq.adapter.NutritionItemAdapter;
import com.kwan.saq.model.Nutrition;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class NutritionMainActivity extends AppCompatActivity implements View.OnClickListener {

    List<Nutrition> nutritionList;
    NutritionItemAdapter nutritionItemAdapter;

    private RecyclerView rvNutritions;
    private Button homeBtn;
    private Button trainingBtn;
    private Button nutritionBtn;
    private Button reportBtn;
    private Button meBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_main);

        nutritionList = _CONST.getNutritionList(this);

        initView();
    }

    private void initView() {
        rvNutritions = findViewById(R.id.rvNutritions);
        homeBtn = findViewById(R.id.homeBtn);
        trainingBtn = findViewById(R.id.trainingBtn);
        nutritionBtn = findViewById(R.id.nutritionBtn);
        reportBtn = findViewById(R.id.reportBtn);
        meBtn = findViewById(R.id.meBtn);

        homeBtn.setOnClickListener(this);
        trainingBtn.setOnClickListener(this);
        nutritionBtn.setOnClickListener(this);
        reportBtn.setOnClickListener(this);
        meBtn.setOnClickListener(this);

        // init recycle view
        initRecycleView();
    }

    private void initRecycleView() {
        nutritionItemAdapter = new NutritionItemAdapter(this, nutritionList);

        // layout manager
        RecyclerView.LayoutManager layoutManagerExercise = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rvNutritions.suppressLayout(true);
        rvNutritions.setLayoutManager(layoutManagerExercise);
        rvNutritions.setAdapter(nutritionItemAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v == homeBtn) {
            _CONST.goToHomeScreen(this);
        } else if(v == trainingBtn) {
            _CONST.goToTrainingPage(this);
        } else if(v == nutritionBtn) {
            _CONST.goToNutritionPage(this);
        } else if(v == reportBtn) {
            _CONST.goToReportPage(this);
        } else if(v == meBtn) {
            _CONST.goToProfilePage(this);
        }
    }
}
