package com.kwan.saq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kwan.saq.api.ApiManager;
import com.kwan.saq.model.Exercise;
import com.kwan.saq.model.MusclePart;
import com.kwan.saq.model.Workout;
import com.kwan.saq.request.LoginRequest;
import com.kwan.saq.response.LoginResponse;
import com.kwan.saq.response.ValidateTokenResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ValidateTokenResponse validateTokenResponse;

    // view components
    private Button startedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        Intent thisIntent = getIntent();
        int isThisAdmin = thisIntent.getIntExtra("isThisAdmin", 0);

        if(isThisAdmin == 1) {
            Toast toast = Toast.makeText(MainActivity.this, "This account is an admin account. Please go to the website instead.", Toast.LENGTH_LONG);
            toast.show();
        }

//        if(getStateLogin() && !getCurrentToken().equals("")) {
//            goHomePage();
//        }
        validateCurrentToken();
    }

    private void initView() {
        startedBtn = findViewById(R.id.startedBtn);
        startedBtn.setOnClickListener(this);
    }

    // go home page
    private void goHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("openToast", 0);
        intent.putExtra("getExercisesAgain", 1);
        startActivity(intent);
        finish();
        MainActivity.this.finish();
    }

    @Override
    public void onClick(View v) {
        if(v == startedBtn) {
            onGetStarted();
        }
    }

    private void onGetStarted() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        Log.d("onGetStarted", "clicked");
        startActivity(intent);
        MainActivity.this.finish();
    }

    // get login state
    public boolean getStateLogin() {
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        boolean isLogin = preferences.getBoolean("LoginState",false);
        Log.d("TAG", "Current register status is " + isLogin);
        return isLogin;
    }

    public String getCurrentToken() {
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        String currentToken = preferences.getString(_CONST.USER_TOKEN,"");
        return currentToken;
    }

    private void validateCurrentToken() {
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        String currentToken = preferences.getString(_CONST.USER_TOKEN,"");

        Log.d("TAG", "Current email: " + preferences.getString(_CONST.USER_EMAIL,""));

        String validateTokenHeader = "Bearer " + currentToken;

        // create new retrofit
        Retrofit retrofit = _CONST.createRetrofit();

        ApiManager service = retrofit.create(ApiManager.class);
        service.validateToken(validateTokenHeader).enqueue(new Callback<ValidateTokenResponse>() {
            @Override
            public void onResponse(Call<ValidateTokenResponse> call, Response<ValidateTokenResponse> response) {
                if(response.body() != null) {
                    validateTokenResponse = response.body();

                    String message = validateTokenResponse.getMessage();

                    if(validateTokenResponse.getSuccess() == true) {
                        Log.d("TAG", "Current token is valid");

                        if(getStateLogin() && !getCurrentToken().equals("")) {
                            goHomePage();
                        }
                    } else {
                        refreshToken();
                        Log.d("TAG", "Current token is invalid");

                    }
                }
            }

            @Override
            public void onFailure(Call<ValidateTokenResponse> call, Throwable t) {
                Log.d("TAG", "cannot validate token via api: " + t);
            }
        });
    }

    private void refreshToken() {
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String userEmail = preferences.getString(_CONST.USER_EMAIL, "");
        String userPassword = preferences.getString(_CONST.USER_PASSWORD, "");

        LoginRequest loginRequest = new LoginRequest(userEmail, userPassword);

        // create new retrofit
        Retrofit retrofit = _CONST.createRetrofit();

        ApiManager service = retrofit.create(ApiManager.class);
        service.loginPost(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    String message = loginResponse.getMessage();

                    if(loginResponse.getSuccess() == true) {
                        Log.d("TAG", "Current token is valid");
                        editor.putBoolean("LoginState", true);
                        editor.putString(_CONST.USER_TOKEN, loginResponse.getToken());

                        // save customer
                        Gson gson = new Gson();
                        String customerJson = gson.toJson(loginResponse.getCustomer());
                        editor.putString(_CONST.USER_CUSTOMER, customerJson);

                        editor.commit();
                        goHomePage();
                    } else {
                        Toast toast = Toast.makeText(MainActivity.this, "Cannot log in to refresh token: " + message, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("TAG", "cannot validate token via api: " + t);
                Toast toast = Toast.makeText(MainActivity.this, "cannot validate token via api: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}