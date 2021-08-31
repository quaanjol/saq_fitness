package com.kwan.saq;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.kwan.saq.model.Service;
import com.kwan.saq.model.Workout;

import java.text.DecimalFormat;

public class ReportServiceDetailActivity extends AppCompatActivity implements View.OnClickListener {

    DecimalFormat timeDf = new DecimalFormat("#");
    Service service;
    Gson gson;

    // components
    private TextView serviceNameHeader;
    private TextView serviceName;
    private TextView serviceTime;
    private TextView servicePrice;
    private TextView serviceDescription;
    private Button backBtn;
    private Button payBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_service_detail);

        gson = new Gson();
        Intent intent = getIntent();
        String serviceJson = intent.getStringExtra("serviceJson");

        service = gson.fromJson(serviceJson, Service.class);

        // set up view
        initView();
    }

    private void initView() {
        serviceNameHeader = findViewById(R.id.serviceNameHeader);
        serviceName = findViewById(R.id.serviceName);
        serviceTime = findViewById(R.id.serviceTime);
        servicePrice = findViewById(R.id.servicePrice);
        serviceDescription = findViewById(R.id.serviceDescription);

        serviceNameHeader.setText(service.getName());
        serviceName.setText(service.getName());
        serviceTime.setText("Expires in " + timeDf.format(service.getTime()) + " " + service.getPeriod() + "(s)");
        servicePrice.setText("Only $" + timeDf.format(service.getPrice()) + " for a good fit");
        serviceDescription.setText(Html.fromHtml(service.getDescription()));


        //
        backBtn = findViewById(R.id.backBtn);
        payBtn = findViewById(R.id.payBtn);

        backBtn.setOnClickListener(this);
        payBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == backBtn) {
            onBackPressed();
        } else if (v == payBtn) {
            goToPayServicePage();
        }
    }

    private void goToPayServicePage() {
        Intent servicePaymentIntent = new Intent(this, ReportServicePaymentActivity.class);
        servicePaymentIntent.putExtra("serviceJson", this.getIntent().getStringExtra("serviceJson"));
        this.startActivity(servicePaymentIntent);
    }
}
