package com.kwan.saq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.kwan.saq.api.ApiManager;
import com.kwan.saq.model.Customer;
import com.kwan.saq.request.LoginRequest;
import com.kwan.saq.request.RegisterRequest;
import com.kwan.saq.response.LoginResponse;
import com.kwan.saq.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    LoginResponse loginResponse;

    private EditText etEmail;
    private EditText etPassword;

    private Button loginBtn;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();

        // make a toast if register status is true
        if(getStateRegister()) {
            Toast.makeText(this, "An account has been successfully created. Try login now", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView () {
        etEmail = findViewById((R.id.etEmail));
        etPassword = findViewById((R.id.etPassword));

        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == registerBtn) {
            onRegister();
        } else if(v == loginBtn) {
            onLogin();
        }
    }

    private void onRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    // register state
    public boolean getStateRegister(){
        SharedPreferences preferences = getSharedPreferences("Register", MODE_PRIVATE);
        boolean isRegister = preferences.getBoolean("RegisterState",false);
        Log.d("TAG", "Current register status is " + isRegister);
        return isRegister;
    }

    // login function
    private void onLogin() {
        boolean validate = true;

        if(etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
            validate = false;
            Toast.makeText(this, "Please enter all the information.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(validate) {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            LoginRequest loginRequest = new LoginRequest(email, password);
            onLoginApiPost(loginRequest);
        }
    }

    private void onLoginApiPost(LoginRequest loginRequest) {
        Log.d("TAG", "get in loginApiPost function");
        Log.d("TAG", "Email input is: " + loginRequest.getEmail());
        Log.d("TAG", "Password input is: " + loginRequest.getPassword());
        Retrofit retrofit = _CONST.createRetrofit();

        ApiManager service = retrofit.create(ApiManager.class);
        service.loginPost(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body() != null) {
                    loginResponse = response.body();

                    String message = loginResponse.getMessage();

                    Log.d("TAG", "role id ss: " + loginResponse.getUser_role_id());

                    if(loginResponse.getSuccess() == true) {
                        if(loginResponse.getUser_role_id() == _CONST.ADMIN_ROLE_ID || loginResponse.getUser_role_id() == _CONST.SUB_ADMIN_ROLE_ID) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("isThisAdmin", 1);
                            startActivity(intent);
                            LoginActivity.this.finish();
                            return;
                        } else {
                            saveStateLogin(loginResponse.getToken(), loginResponse.getUser_id(), loginResponse.getUser_role_id(), loginResponse.getCustomer());
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("openToast", 1);
                            startActivity(intent);
                            LoginActivity.this.finish();
                            return;
                        }
                    } else {
                        Toast toast = Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG);
                        toast.show();
                    }


                    Log.d("TAG", "status: " + loginResponse.getSuccess());
                    Log.d("TAG", "message: " + message);
                    Log.d("TAG", "token: " + loginResponse.getToken());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("TAG", "cannot register via api: " + t);
                Toast toast = Toast.makeText(LoginActivity.this, "Cannot register: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }



    // login state
    public boolean getStateLogin() {
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        boolean isLogin = preferences.getBoolean("LoginState",false);
        Log.d("TAG", "Current register status is " + isLogin);
        return isLogin;
    }

    public void saveStateLogin(String token, int userId, int userRoleId, Customer customer){
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("LoginState", true);
        editor.putString(_CONST.USER_EMAIL, etEmail.getText().toString());
        editor.putString(_CONST.USER_PASSWORD, etPassword.getText().toString());
        editor.putString(_CONST.USER_TOKEN, token);
        editor.putInt(_CONST.USER_ID, userId);
        editor.putInt(_CONST.USER_ROLE_ID, userRoleId);

        // save customer
        Gson gson = new Gson();
        String customerJson = gson.toJson(customer);
        editor.putString(_CONST.USER_CUSTOMER, customerJson);
        editor.commit();
    }
}
