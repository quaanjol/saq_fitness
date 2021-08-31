package com.kwan.saq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;


import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.gson.Gson;
import com.kwan.saq.model.MealNoteItem;
import com.kwan.saq.model.Note;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReportMealNoteDetailActivity extends AppCompatActivity implements View.OnClickListener {

    Gson gson;
    Note note;
    String informationFromIntent;
    String currentDate;
    float totalIntake;
    float totalBurned;
    DecimalFormat df;

    private TextView information, noteDescription, intakeCount, burnedCount, mealNoteTime;
//    private BarChart caloChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_meal_note_detail);

        gson = new Gson();
        df = new DecimalFormat("#.#");

        Intent thisIntent = getIntent();

        informationFromIntent = thisIntent.getStringExtra("informationFromIntent");
        note = gson.fromJson(thisIntent.getStringExtra("note"), Note.class);
        totalIntake = thisIntent.getFloatExtra("totalIntake", 0);
        totalBurned = thisIntent.getFloatExtra("totalBurned", 0);
        currentDate = thisIntent.getStringExtra("currentDate");

        // set up view
        initView();

        //
        //initChart();
    }

    private void initView() {
        information = findViewById(R.id.information);
        noteDescription = findViewById(R.id.noteDescription);
        intakeCount = findViewById(R.id.intakeCount);
        burnedCount = findViewById(R.id.burnedCount);
        mealNoteTime = findViewById(R.id.mealNoteTime);
//        caloChart = (BarChart) findViewById(R.id.caloChart);

        mealNoteTime.setText("Insight analysis (" + currentDate + ")");
        information.setText(Html.fromHtml(informationFromIntent));
        noteDescription.setText(note.getDescription());
        intakeCount.setText("Total calories intake: " + df.format(totalIntake) + " calories");
        burnedCount.setText("Total calories burned: " + df.format(totalBurned) + " calories");
    }

//    private void initChart() {
//        caloChart = (BarChart) findViewById(R.id.caloChart);
//        BarData data = new BarData(getXAxisValues(), getDataSet());
//        caloChart.setData(data);
////        caloChart.setDescription(new Description("Calo Chart"));
//        caloChart.animateXY(2000, 2000);
//        caloChart.invalidate();
//
////        BarData data = new BarData(getXAxisValues(), getDataSet());
////        chart.setData(data);
////        chart.setDescription("My Chart");
////        chart.animateXY(2000, 2000);
////        chart.invalidate();
//    }
//
//    private IBarDataSet getDataSet() {
//        ArrayList dataSets = null;
//
//        ArrayList valueSet1 = new ArrayList();
//        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
//        valueSet1.add(v1e1);
//
//
//        ArrayList valueSet2 = new ArrayList();
//        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
//        valueSet2.add(v2e1);
//
//
//
//
//        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Calories intake");
//        barDataSet1.setColor(Color.GREEN);
//        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Calories burned out");
//        barDataSet2.setColors(Color.GREEN);
//
//        dataSets = new ArrayList();
//        dataSets.add(barDataSet1);
//        dataSets.add(barDataSet2);
//        return dataSets;
//    }
//
//    private IBarDataSet getXAxisValues() {
//        ArrayList xAxis = new ArrayList();
//        xAxis.add("Calories intake");
//        xAxis.add("Calories burned out");
//        return xAxis;
//    }

    @Override
    public void onClick(View v) {

    }
}
