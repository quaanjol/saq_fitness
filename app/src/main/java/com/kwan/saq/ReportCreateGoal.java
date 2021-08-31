package com.kwan.saq;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kwan.saq.api.ApiManager;
import com.kwan.saq.model.Customer;
import com.kwan.saq.request.PostGoalRequest;
import com.kwan.saq.response.PostGoalResponse;
import com.kwan.saq.response.RegisterResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReportCreateGoal extends AppCompatActivity implements View.OnClickListener {
    DatePickerDialog picker;
    Customer currentCustomer;

    private EditText etGoalName, etTotalCalories;
    private EditText etStartDate, etEndDate;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_create_goal);

        currentCustomer = _CONST.getCurrentCustomer(this);

        // set up view
        initView();
    }

    private void initView() {
        etGoalName = findViewById(R.id.etGoalName);
        etTotalCalories = findViewById(R.id.etTotalCalories);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        submitBtn = findViewById(R.id.submitBtn);

        etStartDate.setInputType(InputType.TYPE_NULL);
        etEndDate.setInputType(InputType.TYPE_NULL);

        submitBtn.setOnClickListener(this);
        etStartDate.setOnClickListener(this);
        etEndDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == submitBtn) {
            if(etGoalName.getText().toString().equals("") || etTotalCalories.getText().toString().equals("")
            || etStartDate.getText().toString().equals("") || etEndDate.getText().toString().equals("")) {
                Toast toast = Toast.makeText(this, "Please fill in all the information.", Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            String startDate = etStartDate.getText().toString();
            String endDate = etEndDate.getText().toString();

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

            Date dateStartDate = null;
            Date dateEndDate = null;

            try {
                dateStartDate = dateFormatter.parse(startDate);
                dateEndDate = dateFormatter.parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(this, "Error: " + e.getMessage(),  Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            if (dateStartDate.compareTo(dateEndDate) >= 0) {
                Toast toast = Toast.makeText(this, "Start date cannot be later than end date. Please check this again.",  Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            saveGoal();
        } else if(v == etStartDate) {
            chooseGoalDate(etStartDate);
        } else if(v == etEndDate) {
            chooseGoalDate(etEndDate);
        }
    }

    private void saveGoal() {
        Retrofit retrofit = _CONST.createRetrofit();

        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + _CONST.getCurrentToken(this);
        PostGoalRequest postGoalRequest = new PostGoalRequest(currentCustomer.getId(),
                etGoalName.getText().toString(),
                etStartDate.getText().toString(),
                etEndDate.getText().toString(),
                Float.parseFloat(etTotalCalories.getText().toString()));

        service.postGoal(headerToken, postGoalRequest).enqueue(new Callback<PostGoalResponse>() {
            @Override
            public void onResponse(Call<PostGoalResponse> call, Response<PostGoalResponse> response) {
                Log.d("TAG", "response body is: " + response.body().toString());
                if(response.body() != null) {
                    PostGoalResponse postGoalResponse = response.body();

                    String message = postGoalResponse.getMessage();

                    if(postGoalResponse.getSuccess() == true) {
                        goBackToReportPage(message);
                    } else {
                        Toast toast = Toast.makeText(ReportCreateGoal.this, "Cannot create goal: " + message, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostGoalResponse> call, Throwable t) {
                Log.d("TAG", "cannot create new goal: " + t);
                Toast toast = Toast.makeText(ReportCreateGoal.this, "Cannot create goal: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void goBackToReportPage(String message) {
        Intent reportMainIntent = new Intent(this, ReportMainActivity.class);
        reportMainIntent.putExtra("newGoal", 1);
        reportMainIntent.putExtra("newGoalMsg", message);
        startActivity(reportMainIntent);
        finish();
    }

    private void chooseGoalDate(TextView v) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(ReportCreateGoal.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        v.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
        picker.show();
    }
}
