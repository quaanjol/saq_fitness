package com.kwan.saq;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;
import com.jama.carouselview.enums.IndicatorAnimationType;
import com.jama.carouselview.enums.OffsetType;
import com.kwan.saq.adapter.ExerciseItemAdapter;
import com.kwan.saq.adapter.ReportMealItemAdapter;
import com.kwan.saq.adapter.ServiceItemAdapter;
import com.kwan.saq.api.ApiManager;
import com.kwan.saq.model.Customer;
import com.kwan.saq.model.Feed;
import com.kwan.saq.model.Goal;
import com.kwan.saq.model.Note;
import com.kwan.saq.model.Service;
import com.kwan.saq.request.GetWorkoutsRequest;
import com.kwan.saq.response.GetCurrentGoalResponse;
import com.kwan.saq.response.GetFeedsResponse;
import com.kwan.saq.response.GetMealNotesResponse;
import com.kwan.saq.response.GetServicesResponse;
import com.kwan.saq.response.GetWorkoutsResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReportMainActivity extends AppCompatActivity implements View.OnClickListener {

    ServiceItemAdapter serviceItemAdapter;
    ReportMealItemAdapter reportMealItemAdapter;
    List<Service> serviceList;
    List<Note> noteList;
    public List<Feed> feedList;
    Customer currentCustomer;
    Goal currentGoal;
    int noteTake = 3;
    int noteSkip = 0;
    DecimalFormat df;
    DecimalFormat df2;

    // carousel images
    private int[] images = {R.drawable.bg1_32, R.drawable.bg2_32, R.drawable.bg3_32, R.drawable.bg4_32, R.drawable.bg5_32};

    CarouselView carouselView;

    // components
    private RecyclerView rvServices;
    private LinearLayout reportSection;

    private TextView servicesHeader;
    private Button homeBtn;
    private Button trainingBtn;
    private Button nutritionBtn;
    private Button reportBtn;
    private Button meBtn;

    private Button mealNoteCreateBtn;
    private RecyclerView rvMealNotes;
    private Button loadMoreMealNoteBtn, viewAllGoalBtn, createGoalBtn;
    private LinearLayout noGoalSection, currentGoalSection;
    private TextView goalName, goalResult, goalResultProgressBar, goalTime, currentGoalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_main);

        df = new DecimalFormat("#");
        df2 = new DecimalFormat("#.##");

        currentCustomer = _CONST.getCurrentCustomer(this);
        feedList = new ArrayList<>();

        Intent intent = this.getIntent();
        int enrolledService = intent.getIntExtra("enrolledService", 0);
        int newMealNote = intent.getIntExtra("newMealNote", 0);
        int newGoal = intent.getIntExtra("newGoal", 0);
        String newGoalMsg = intent.getStringExtra("newGoalMsg");

        if(enrolledService == 1) {
            Toast toast = Toast.makeText(ReportMainActivity.this, "Thank you for choosing our services. You can access the functions now.", Toast.LENGTH_LONG);
            toast.show();
        }

        if(newMealNote == 1) {
            Toast toast = Toast.makeText(ReportMainActivity.this, "New meal note has been created.", Toast.LENGTH_LONG);
            toast.show();
        }

        if(newGoal == 1) {
            Toast toast = Toast.makeText(ReportMainActivity.this, newGoalMsg, Toast.LENGTH_LONG);
            toast.show();
        }

        // get feeds
        getFeeds();

        // set up view
        initView();

        // set up recycle view
        initRecycleView();
    }

    private void initView() {
        // carousel
        carouselView = findViewById(R.id.carouselView);
        carouselView.setSize(images.length);
        carouselView.setResource(R.layout.center_carousel_item);
        carouselView.setAutoPlay(true);
        carouselView.setIndicatorAnimationType(IndicatorAnimationType.THIN_WORM);
        carouselView.setCarouselOffset(OffsetType.CENTER);
        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                // Example here is setting up a full image carousel
                ImageView imageView = view.findViewById(R.id.imageView);
                imageView.setImageDrawable(getResources().getDrawable(images[position]));
            }
        });

        // After you finish setting up, show the CarouselView
        carouselView.show();


        // get components
        rvServices = findViewById(R.id.rvServices);
        rvMealNotes = findViewById(R.id.rvMealNotes);
        reportSection = findViewById(R.id.reportSection);
        servicesHeader = findViewById(R.id.servicesHeader);
        homeBtn = findViewById(R.id.homeBtn);
        trainingBtn = findViewById(R.id.trainingBtn);
        nutritionBtn = findViewById(R.id.nutritionBtn);
        reportBtn = findViewById(R.id.reportBtn);
        meBtn = findViewById(R.id.meBtn);
        nutritionBtn = findViewById(R.id.nutritionBtn);

        mealNoteCreateBtn = findViewById(R.id.mealNoteCreateBtn);
        rvMealNotes = findViewById(R.id.rvMealNotes);
        loadMoreMealNoteBtn = findViewById(R.id.loadMoreMealNoteBtn);

        viewAllGoalBtn = findViewById(R.id.viewAllGoalBtn);
        createGoalBtn = findViewById(R.id.createGoalBtn);
        noGoalSection = findViewById(R.id.noGoalSection);
        currentGoalSection = findViewById(R.id.currentGoalSection);
        goalName = findViewById(R.id.goalName);
        goalResult = findViewById(R.id.goalResult);
        goalResultProgressBar = findViewById(R.id.goalResultProgressBar);
        goalTime = findViewById(R.id.goalTime);
        currentGoalText = findViewById(R.id.currentGoalText);

//        private Button loadMoreMealNoteBtn, viewAllGoalBtn, createGoalBtn;
//        private LinearLayout noGoalSection, currentGoalSection;
//        private TextView goalName, goalResult, goalResultProgressBar, goalTime;

        homeBtn.setOnClickListener(this);
        trainingBtn.setOnClickListener(this);
        reportBtn.setOnClickListener(this);
        meBtn.setOnClickListener(this);
        nutritionBtn.setOnClickListener(this);

        mealNoteCreateBtn.setOnClickListener(this);
        loadMoreMealNoteBtn.setOnClickListener(this);
    }

    private void initRecycleView() {
        currentCustomer = _CONST.getCurrentCustomer(this);
        if(currentCustomer.getMembership() == 1) {
            // get goal section
            currentGoalText.setVisibility(View.VISIBLE);
            getGoal();

            //
            reportSection.setVisibility(View.VISIBLE);

            //init meal notes recycle view
            rvMealNotes.setVisibility(View.VISIBLE);

            noteList = new ArrayList<>();
            getNotes();

            // adapter
            reportMealItemAdapter = new ReportMealItemAdapter(this, noteList, feedList, currentCustomer);

            // layout manager
            RecyclerView.LayoutManager layoutManagerExercise = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

            rvMealNotes.suppressLayout(true);
            rvMealNotes.setLayoutManager(layoutManagerExercise);
            rvMealNotes.setAdapter(reportMealItemAdapter);
        } else {
            // open alert popup
            // open alert
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Report section is for paid users only. Do you want to register our services to " +
                    "experience the functions provided here?");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "No, thanks",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            builder1.setNegativeButton(
                    "Great, let\'s to this",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

            //init services recycle view
            servicesHeader.setVisibility(View.VISIBLE);

            serviceList = new ArrayList<>();
            getServices();

            // adapter
            serviceItemAdapter = new ServiceItemAdapter(this, serviceList);

            // layout manager
            RecyclerView.LayoutManager layoutManagerExercise = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

            rvServices.suppressLayout(true);
            rvServices.setLayoutManager(layoutManagerExercise);
            rvServices.setAdapter(serviceItemAdapter);

        }
    }

    private void getGoal() {
        Retrofit retrofit = _CONST.createRetrofit();
        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + _CONST.getCurrentToken(this);

        service.getCurrentGoal(headerToken, currentCustomer.getId()).enqueue(new Callback<GetCurrentGoalResponse>() {
            @Override
            public void onResponse(Call<GetCurrentGoalResponse> call, Response<GetCurrentGoalResponse> response) {
                if(response.body() != null) {
                    GetCurrentGoalResponse getCurrentGoalResponse = response.body();
                    currentGoal = getCurrentGoalResponse.getGoal();

                    if(getCurrentGoalResponse.getSuccess() == true) {
                        if(getCurrentGoalResponse.getGoal() != null) {
                            currentGoalSection.setVisibility(View.VISIBLE);
                            viewAllGoalBtn.setVisibility(View.VISIBLE);

                            if(currentGoal.getStatus() != 1) {
                                createGoalBtn.setVisibility(View.VISIBLE);
                                createGoalBtn.setOnClickListener(ReportMainActivity.this::onClick);
                            }

                            viewAllGoalBtn.setOnClickListener(ReportMainActivity.this::onClick);

                            String status = "";
                            if(currentGoal.getStatus() == 0) {
                                status = "Expired";
                            } else {
                                status = "In progress";
                            }

                            goalName.setText(currentGoal.getName() + " (" + df.format(currentGoal.getCalo()) + " calories - Status: " + status + ")");

                            goalResult.setText(df2.format(currentGoal.getResult() * 100) + "% done");

                            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                    0,
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    currentGoal.getResult() * 100
                            );
                            goalResultProgressBar.setLayoutParams(param);

                            // format time
                            String startDateString = currentGoal.getStart_date();
                            String endDateString = currentGoal.getEnd_date();
                            String[] parts1 = startDateString.split("T");
                            String[] parts2 = endDateString.split("T");

                            if(parts1.length > 0 && parts2.length > 0) {
                                goalTime.setText(_CONST.formatTime(parts1[0]) + " - " + _CONST.formatTime(parts2[0]));
                            } else {
                                goalTime.setText("N/A - N/A");
                            }

                            currentGoalSection.setOnClickListener(ReportMainActivity.this::onClick);
                        } else {
                            createGoalBtn.setVisibility(View.VISIBLE);
                            noGoalSection.setVisibility(View.VISIBLE);
                            createGoalBtn.setOnClickListener(ReportMainActivity.this::onClick);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCurrentGoalResponse> call, Throwable t) {
                Log.d("TAG", "Cannot get current goal: " + t);
                Toast toast = Toast.makeText(ReportMainActivity.this, "Cannot get current goal: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void getServices() {
        Retrofit retrofit = _CONST.createRetrofit();
        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + _CONST.getCurrentToken(this);

        service.getServices(headerToken).enqueue(new Callback<GetServicesResponse>() {
            @Override
            public void onResponse(Call<GetServicesResponse> call, Response<GetServicesResponse> response) {
                if(response.body() != null) {
                    GetServicesResponse getServicesResponse = response.body();

                    if(getServicesResponse.getSuccess() == true) {
                        serviceList.addAll(getServicesResponse.getServices());
                        serviceItemAdapter.reloadData(serviceList);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetServicesResponse> call, Throwable t) {
                Log.d("TAG", "Cannot get workouts: " + t);
                Toast toast = Toast.makeText(ReportMainActivity.this, "Cannot get workouts: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void getNotes() {
        Retrofit retrofit = _CONST.createRetrofit();
        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + _CONST.getCurrentToken(this);
        int customer_id = currentCustomer.getId();


        service.getMealNotes(headerToken, customer_id, noteSkip, noteTake).enqueue(new Callback<GetMealNotesResponse>() {
            @Override
            public void onResponse(Call<GetMealNotesResponse> call, Response<GetMealNotesResponse> response) {
                if(response.body() != null) {
                    GetMealNotesResponse getMealNotesResponse = response.body();

                    if(getMealNotesResponse.getSuccess() == true) {
                        if(getMealNotesResponse.getNotes().size() <= 0) {
                            loadMoreMealNoteBtn.setVisibility(View.GONE);
                            Toast toast = Toast.makeText(ReportMainActivity.this, "All notes are loaded", Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            noteList.addAll(getMealNotesResponse.getNotes());
                            reportMealItemAdapter.reloadData(noteList);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMealNotesResponse> call, Throwable t) {
                Log.d("TAG", "Cannot get workouts: " + t);
                Toast toast = Toast.makeText(ReportMainActivity.this, "Cannot get workouts: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void getFeeds() {
        Retrofit retrofit = _CONST.createRetrofit();
        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + _CONST.getCurrentToken(this);

        service.getFeedByCustomerId(headerToken, currentCustomer.getId(), 0, -1).enqueue(new Callback<GetFeedsResponse>() {

            @Override
            public void onResponse(Call<GetFeedsResponse> call, Response<GetFeedsResponse> response) {
                if(response.body() != null) {
                    GetFeedsResponse getFeedsResponse = response.body();

                    feedList.addAll(getFeedsResponse.getFeeds());
                }
            }

            @Override
            public void onFailure(Call<GetFeedsResponse> call, Throwable t) {
                Log.d("TAG", "Cannot get activities: " + t);
                Toast toast = Toast.makeText(ReportMainActivity.this, "Cannot get activities: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == homeBtn) {
            _CONST.goToHomeScreen(this);
        } else if(v == trainingBtn) {
            _CONST.goToTrainingPage(this);
        } else if(v == meBtn) {
            _CONST.goToProfilePage(this);
        } else if(v == reportBtn) {
            _CONST.goToReportPage(this);
        } else if(v == mealNoteCreateBtn) {
            goToCreateNotePage();
        } else if(v == loadMoreMealNoteBtn) {
            noteSkip += noteTake;
            getNotes();
        } else if(v == nutritionBtn){
            _CONST.goToNutritionPage(this);
        } else if(v == createGoalBtn) {
            goToCreateGoalPage();
        } else if(v == currentGoalSection) {
            if(currentGoal != null) {
                Gson gson = new Gson();
                String goalJson = gson.toJson(currentGoal);
                goToGoalDetailPage(goalJson);
            } else {
                Toast toast = Toast.makeText(ReportMainActivity.this, "Current goal does not exist.", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
        } else if(v == viewAllGoalBtn) {
            goToAllGoalsPage();
        }
    }

    private void goToCreateNotePage() {
        Intent createNoteIntent = new Intent(this, ReportCreateMealNote.class);
        startActivity(createNoteIntent);
    }

    private void goToCreateGoalPage() {
        Intent createGoalIntent = new Intent(this, ReportCreateGoal.class);
        startActivity(createGoalIntent);
    }

    private void goToGoalDetailPage(String goalJson) {
        Intent goToDetailIntent = new Intent(this, ReportGoalDetail.class);
        goToDetailIntent.putExtra("goalJson", goalJson);
        startActivity(goToDetailIntent);
    }

    private void goToAllGoalsPage() {
        Intent allGoalsIntent = new Intent(this, ReportAllGoals.class);
        startActivity(allGoalsIntent);
    }
}
