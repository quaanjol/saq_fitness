package com.kwan.saq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kwan.saq.adapter.CommentItemAdapter;
import com.kwan.saq.adapter.ExerciseItemAdapter;
import com.kwan.saq.api.ApiManager;
import com.kwan.saq.model.ExerciseList;
import com.kwan.saq.model.Workout;
import com.kwan.saq.model.WorkoutComment;
import com.kwan.saq.request.PostCommentRequest;
import com.kwan.saq.response.GetCommentsResponse;
import com.kwan.saq.response.GetExercisesResponse;
import com.kwan.saq.response.PostCommentResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkoutCommentsActivity extends AppCompatActivity implements View.OnClickListener {

    GetCommentsResponse getCommentsResponse;
    CommentItemAdapter commentItemAdapter;
    int workoutId;
    int querySkip;
    Boolean allLoaded = false;
    List<WorkoutComment> commentList;

    private NestedScrollView commentScroll;
    private RecyclerView rvComments;
    private ProgressBar loadingBar;
    private EditText etComment;
    private Button commentBtn;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_comment_post);

        allLoaded = false;
        querySkip = 0;

        Intent intent = getIntent();
        workoutId = intent.getIntExtra("workoutId", -1);

        // set up view
        initView();
        initRecycleView();

        // get comments data
        getComments();
    }

    private void initView() {
        etComment = findViewById(R.id.etComment);
        commentBtn = findViewById(R.id.commentBtn);
        loadingBar = findViewById(R.id.loadingBar);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(this);
        commentBtn.setOnClickListener(this);

        //
        commentScroll = findViewById(R.id.commentScroll);
    }

    private void initRecycleView() {
        // get data source
        commentList = new ArrayList<>();


        // adapter
        commentItemAdapter = new CommentItemAdapter(WorkoutCommentsActivity.this, commentList);

        // layout manager
        RecyclerView.LayoutManager layoutManagerExercise = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rvComments = findViewById(R.id.rvComments);
        rvComments.suppressLayout(true);
        rvComments.setLayoutManager(layoutManagerExercise);
        rvComments.setAdapter(commentItemAdapter);

        //
        commentScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY >= v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    querySkip += 8;
                    loadingBar.setVisibility(View.VISIBLE);
                    getComments();
                }
            }

        });

    }

    private void getComments() {
        Retrofit retrofit = _CONST.createRetrofit();

        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + _CONST.getCurrentToken(this);

        service.getCommentsByWorkoutId(headerToken, workoutId, querySkip).enqueue(new Callback<GetCommentsResponse>() {

            @Override
            public void onResponse(Call<GetCommentsResponse> call, Response<GetCommentsResponse> response) {
                if(response.body() != null) {
                    getCommentsResponse = response.body();
                    if(getCommentsResponse.getComments().size() > 0) {
                        commentList.addAll(getCommentsResponse.getComments());
                        commentItemAdapter.reloadData(commentList);
                    } else {
                        Toast toast = Toast.makeText(WorkoutCommentsActivity.this, "All comments are loaded!", Toast.LENGTH_SHORT);
                        toast.show();
                        allLoaded = true;
                    }
                    WorkoutCommentsActivity.this.loadingBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GetCommentsResponse> call, Throwable t) {
                Log.d("TAG", "Cannot get comments: " + t);
                Toast toast = Toast.makeText(WorkoutCommentsActivity.this, "Cannot get comments: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v == backBtn) {
            onBackPressed();
        } else if(v == commentBtn) {
            postComment();
        }
    }

    private void postComment() {
        Retrofit retrofit = _CONST.createRetrofit();

        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + _CONST.getCurrentToken(this);

        PostCommentRequest postCommentRequest = new PostCommentRequest(workoutId, _CONST.getCurrentCustomer(this).getId(), etComment.getText().toString());

        service.postComments(headerToken, postCommentRequest).enqueue(new Callback<PostCommentResponse>() {

            @Override
            public void onResponse(Call<PostCommentResponse> call, Response<PostCommentResponse> response) {
                if(response.body() != null) {
                    PostCommentResponse postCommentResponse = response.body();
                    if(postCommentResponse.getSuccess() != false) {
                        _CONST.getNewWorkouts(WorkoutCommentsActivity.this);
                        finish();
                        startActivity(getIntent());
                    }
                }
            }

            @Override
            public void onFailure(Call<PostCommentResponse> call, Throwable t) {
                Log.d("TAG", "Cannot post comment: " + t);
                Toast toast = Toast.makeText(WorkoutCommentsActivity.this, "Cannot post comment: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
