package com.kwan.saq;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReportCreateMealNote extends AppCompatActivity implements View.OnClickListener {


    private EditText mealNumber;
    private EditText mealDescription;
    private Button addMealBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_create_note);

        // set up view
        initView();
    }

    private void initView() {
        mealNumber = findViewById(R.id.mealNumber);
        mealDescription = findViewById(R.id.mealDescription);
        addMealBtn = findViewById(R.id.addMealBtn);

        addMealBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent addMealIntent = new Intent(this, ReportAddMeal.class);
        addMealIntent.putExtra("mealNumber", Integer.parseInt(mealNumber.getText().toString()));
        addMealIntent.putExtra("mealDescription", mealDescription.getText().toString());
        startActivity(addMealIntent);
    }
}
