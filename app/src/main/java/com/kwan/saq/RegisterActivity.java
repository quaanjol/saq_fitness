package com.kwan.saq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kwan.saq.api.ApiManager;
import com.kwan.saq.request.RegisterRequest;
import com.kwan.saq.response.RegisterResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    RegisterResponse registerResponse;
    Boolean registerStatus = false;
    String apiMessage = "";

    //
    private EditText etName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etAge;
    private EditText etWeight;
    private EditText etHeight;
    private EditText etPassword;
    private EditText etPassword2;

    private Spinner spinnerGender;
    private Spinner trainingFrequencySpinner;
    private Spinner preferredTrainingTimeSpinner;

    private Button registerBtn;

    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        initView();
    }

    private void initView() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAge = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        registerBtn = findViewById(R.id.registerBtn);

        //
        registerBtn.setOnClickListener(this);

        // set up for gender
        spinnerGender = (Spinner) findViewById(R.id.spinnerGender);

        String[] genders = {_CONST.MALE_GENDER, _CONST.FEMALE_GENDER};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // set up for training preferred time
        preferredTrainingTimeSpinner = (Spinner) findViewById(R.id.spinnerPreferredTrainingTime);

        String[] trainingTimes = {_CONST.TRAINING_TIME_MORNING, _CONST.TRAINING_TIME_AFTERNOON, _CONST.TRAINING_TIME_EVENING};
        ArrayAdapter<String> adapterTrainingTime = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, trainingTimes);
        adapterTrainingTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preferredTrainingTimeSpinner.setAdapter(adapterTrainingTime);

        preferredTrainingTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // set up for training frequency
        trainingFrequencySpinner = (Spinner) findViewById(R.id.spinnerTrainingFrequency);

        String[] trainingFrequencies = {_CONST.TRAINING_NEVER, _CONST.TRAINING_OFTEN, _CONST.TRAINING_USUALLY, _CONST.TRAINING_HEAVY, _CONST.TRAINING_EXTREME};
        ArrayAdapter<String> adapterTrainingFrequency = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, trainingFrequencies);
        adapterTrainingFrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trainingFrequencySpinner.setAdapter(adapterTrainingFrequency);

        trainingFrequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == registerBtn) {
            onRegister();
        }
    }

    private void onRegister() {
        boolean validate = true;

        if(etName.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty() ||
        etPhone.getText().toString().isEmpty() || etWeight.getText().toString().isEmpty() || etHeight.getText().toString().isEmpty() ||
        etAge.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty() || etPassword2.getText().toString().isEmpty() ||
        spinnerGender.getSelectedItem().toString().isEmpty() || preferredTrainingTimeSpinner.getSelectedItem().toString().isEmpty() ||
        trainingFrequencySpinner.getSelectedItem().toString().isEmpty()) {

            validate = false;
            Toast.makeText(this, "Please enter all the information.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(validate) {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();
            int age = Integer.parseInt(etAge.getText().toString());
            String gender = spinnerGender.getSelectedItem().toString();
            float weight = Float.parseFloat(etWeight.getText().toString());
            float height = Float.parseFloat(etHeight.getText().toString());
            String training_frequency = trainingFrequencySpinner.getSelectedItem().toString();
            String preferred_training_time = preferredTrainingTimeSpinner.getSelectedItem().toString();
            String password = etPassword.getText().toString();
            String password2 = etPassword2.getText().toString();

            if(!password.equals(password2)) {
                Toast.makeText(this, "Passwords do not match. Please try again.", Toast.LENGTH_SHORT).show();
                return;
            }

            // re-format
            int genderInt = 1;
            int trainingTimeInt = 1;
            int trainingFrequencyInt = 1;

            if(gender == _CONST.FEMALE_GENDER) {
                genderInt = 2;
            } else if(gender == _CONST.MALE_GENDER) {
                genderInt = 1;
            }

            if(training_frequency == _CONST.TRAINING_NEVER) {
                trainingFrequencyInt = 1;
            } else if(training_frequency == _CONST.TRAINING_OFTEN) {
                trainingFrequencyInt = 2;
            } else if(training_frequency == _CONST.TRAINING_USUALLY) {
                trainingFrequencyInt = 3;
            } else if(training_frequency == _CONST.TRAINING_HEAVY) {
                trainingFrequencyInt = 4;
            } else if(training_frequency == _CONST.TRAINING_EXTREME) {
                trainingFrequencyInt = 5;
            }

            if(preferred_training_time == _CONST.TRAINING_TIME_MORNING) {
                trainingTimeInt = 1;
            } else if(preferred_training_time == _CONST.TRAINING_TIME_AFTERNOON) {
                trainingTimeInt = 2;
            } else if(preferred_training_time == _CONST.TRAINING_TIME_EVENING) {
                trainingTimeInt = 3;
            }

            RegisterRequest registerRequest = new RegisterRequest(name, email, phone, age, genderInt, weight, height, trainingFrequencyInt, trainingTimeInt, password, password2);

            onRegisterApiPost(registerRequest);
        }


    }

    private void onRegisterApiPost(RegisterRequest registerRequest) {
        Retrofit retrofit = _CONST.createRetrofit();

        ApiManager service = retrofit.create(ApiManager.class);
        service.registerPost(registerRequest).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.d("TAG", "response body is: " + response.body().toString());
                if(response.body() != null) {
                    registerResponse = response.body();

                    apiMessage = registerResponse.getMessage();

                    if(registerResponse.getSuccess() == true) {
                        registerStatus = true;
                        saveStateRegister();
                    }

                    if(registerStatus) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        RegisterActivity.this.finish();
                    } else {
                        Toast toast = Toast.makeText(RegisterActivity.this, apiMessage, Toast.LENGTH_LONG);
                        toast.show();
                    }

                    Log.d("TAG", "status: " + registerResponse.getSuccess());
                    Log.d("TAG", "message: " + apiMessage);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d("TAG", "cannot register via api: " + t);
                Toast toast = Toast.makeText(RegisterActivity.this, "Cannot register: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public void saveStateRegister(){
        SharedPreferences preferences = getSharedPreferences("Register", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("RegisterState", true);
        editor.commit();
    }
}
