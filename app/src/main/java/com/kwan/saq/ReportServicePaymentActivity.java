package com.kwan.saq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.kwan.saq.api.ApiManager;
import com.kwan.saq.model.Customer;
import com.kwan.saq.model.Service;
import com.kwan.saq.request.PostPaymentRequest;
import com.kwan.saq.response.GetCustomerResponse;
import com.kwan.saq.response.PostPaymentResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReportServicePaymentActivity extends AppCompatActivity implements View.OnClickListener {

    Gson gson;
    Service thisService;
    Customer currentCustomer;

    // components
    private TextView serviceName;
    private EditText etNameOnCard;
    private EditText etCardNumber;
    private EditText etCardCvc;
    private EditText etCardExpireMonth;
    private EditText etCardExpireYear;
    private Button backBtn;
    private Button payBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_service_payment);

        gson = new Gson();
        Intent intent = getIntent();
        String serviceJson = intent.getStringExtra("serviceJson");

        thisService = gson.fromJson(serviceJson, Service.class);

        currentCustomer = _CONST.getCurrentCustomer(this);

        // set up view
        initView();
    }

    private void initView() {
        serviceName = findViewById(R.id.serviceName);
        etNameOnCard = findViewById(R.id.etNameOnCard);
        etCardNumber = findViewById(R.id.etCardNumber);
        etCardCvc = findViewById(R.id.etCardCvc);
        etCardExpireMonth = findViewById(R.id.etCardExpireMonth);
        etCardExpireYear = findViewById(R.id.etCardExpireYear);
        backBtn = findViewById(R.id.backBtn);
        payBtn = findViewById(R.id.payBtn);

        serviceName.setText(thisService.getName());
        payBtn.setText("Pay $" + thisService.getPrice());

        //
        backBtn.setOnClickListener(this);
        payBtn.setOnClickListener(this);
    }

    private void postpayment() {
        Retrofit retrofit =  _CONST.createRetrofit();

        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + _CONST.getCurrentToken(this);
        String cardNumber = etCardNumber.getText().toString();
        String nameOnCard = etNameOnCard.getText().toString();
        int cardCvc = Integer.parseInt(etCardCvc.getText().toString());
        int cardExpiredMonth = Integer.parseInt(etCardExpireMonth.getText().toString());
        int cardExpiredYear = Integer.parseInt(etCardExpireYear.getText().toString());

        PostPaymentRequest postPaymentRequest = new PostPaymentRequest(thisService.getId(),
                currentCustomer.getId(), thisService.getPrice(),
                nameOnCard, cardNumber, cardExpiredMonth, cardExpiredYear, cardCvc);

        Log.d("TAG", "postPaymentRequest: " + gson.toJson(postPaymentRequest));


        service.postPayment(headerToken, postPaymentRequest).enqueue(new Callback<PostPaymentResponse>() {
            @Override
            public void onResponse(Call<PostPaymentResponse> call, Response<PostPaymentResponse> response) {
                if(response.body() != null) {
                    PostPaymentResponse postPaymentResponse = response.body();

                    String message = postPaymentResponse.getMessage();


                    if(postPaymentResponse.getSuccess() == true) {
                        updateCustomerAndGoBacktoReportMainPage();
                    } else {
                        Toast toast = Toast.makeText(ReportServicePaymentActivity.this, "Cannot post payment: " + message, Toast.LENGTH_LONG);
                        toast.show();
                    }
                    return;
                }
            }

            @Override
            public void onFailure(Call<PostPaymentResponse> call, Throwable t) {
                Toast toast = Toast.makeText(ReportServicePaymentActivity.this, "Cannot post payment: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void updateCustomerAndGoBacktoReportMainPage() {
        Retrofit retrofit =  _CONST.createRetrofit();

        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + _CONST.getCurrentToken(this);

        service.getCustomer(headerToken, currentCustomer.getId()).enqueue(new Callback<GetCustomerResponse>() {
            @Override
            public void onResponse(Call<GetCustomerResponse> call, Response<GetCustomerResponse> response) {
                if(response.body() != null) {
                    GetCustomerResponse getCustomerResponse = response.body();

                    String message = getCustomerResponse.getMessage();

                    if(getCustomerResponse.getSuccess() == true) {
                        // update customer data
                        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        String customerJson = gson.toJson(getCustomerResponse.getCustomer());
                        editor.putString(_CONST.USER_CUSTOMER, customerJson);
                        editor.commit();

                        Intent intent = new Intent(ReportServicePaymentActivity.this, ReportMainActivity.class);
                        intent.putExtra("enrolledService", 1);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast toast = Toast.makeText(ReportServicePaymentActivity.this, "Cannot get customer: " +message, Toast.LENGTH_LONG);
                        toast.show();
                    }
                    return;
                }
            }

            @Override
            public void onFailure(Call<GetCustomerResponse> call, Throwable t) {
                Toast toast = Toast.makeText(ReportServicePaymentActivity.this, "Cannot get customer to update: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == backBtn) {
            onBackPressed();
        } else if(v == payBtn) {
            postpayment();
        }
    }
}
