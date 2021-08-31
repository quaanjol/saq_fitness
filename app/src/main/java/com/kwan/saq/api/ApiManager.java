package com.kwan.saq.api;

import com.kwan.saq.request.LoginRequest;
import com.kwan.saq.request.PostCommentRequest;
import com.kwan.saq.request.PostFeedRequest;
import com.kwan.saq.request.PostGoalRequest;
import com.kwan.saq.request.PostMealNoteRequest;
import com.kwan.saq.request.PostPaymentRequest;
import com.kwan.saq.request.PostWorkoutRequest;
import com.kwan.saq.request.RegisterRequest;
import com.kwan.saq.response.GetCommentsResponse;
import com.kwan.saq.response.GetCurrentGoalResponse;
import com.kwan.saq.response.GetCustomerResponse;
import com.kwan.saq.response.GetExercisesResponse;
import com.kwan.saq.response.GetFeedsResponse;
import com.kwan.saq.response.GetGoalsResponse;
import com.kwan.saq.response.GetMealNotesResponse;
import com.kwan.saq.response.GetMusclePartsResponse;
import com.kwan.saq.response.GetNutritionsResponse;
import com.kwan.saq.response.GetServicesResponse;
import com.kwan.saq.response.GetWorkoutsResponse;
import com.kwan.saq.response.LoginResponse;
import com.kwan.saq.response.PostCommentResponse;
import com.kwan.saq.response.PostFeedResponse;
import com.kwan.saq.response.PostGoalResponse;
import com.kwan.saq.response.PostMealNoteResponse;
import com.kwan.saq.response.PostPaymentResponse;
import com.kwan.saq.response.PostWorkoutResponse;
import com.kwan.saq.response.RegisterResponse;
import com.kwan.saq.response.ValidateTokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiManager {

    String DOMAIN = "http://saq.herokuapp.com";

    @POST("/api/validate")
    Call<ValidateTokenResponse> validateToken(@Header("Authorization") String token);

    @POST("/api/register")
    Call<RegisterResponse> registerPost(@Body RegisterRequest registerRequest);

    @POST("/api/login")
    Call<LoginResponse> loginPost(@Body LoginRequest loginRequest);

    // get customer by id
    @GET("/api/customer")
    Call<GetCustomerResponse> getCustomer(@Header("Authorization") String token, @Query("id") int id);

    // get exercises
    @GET("/api/exercises")
    Call<GetExercisesResponse> getExercises(@Header("Authorization") String token);

    // get muscle parts
    @GET("/api/muscleParts")
    Call<GetMusclePartsResponse> getMuscleParts(@Header("Authorization") String token);

    // get workouts
    @GET("/api/workouts")
    Call<GetWorkoutsResponse> getWorkouts(@Header("Authorization") String token, @Query("user_id") int user_id);

    @POST("/api/workout")
    Call<PostWorkoutResponse> postWorkout(@Header("Authorization") String token, @Body PostWorkoutRequest postWorkoutRequest);

    // workout comments
    @GET("/api/workout_comments")
    Call<GetCommentsResponse> getCommentsByWorkoutId(@Header("Authorization") String token, @Query("workout_id") int workout_id, @Query("skip") int skip);

    @POST("/api/workout_comment")
    Call<PostCommentResponse> postComments(@Header("Authorization") String token, @Body PostCommentRequest postCommentRequest);

    //feeds
    @GET("/api/feeds")
    Call<GetFeedsResponse> getFeedByCustomerId(@Header("Authorization") String token, @Query("customer_id") int customer_id, @Query("skip") int skip, @Query("take") int take);

    @GET("/api/feeds")
    Call<GetFeedsResponse> getFeedByDate(@Header("Authorization") String token, @Query("customer_id") int customer_id, @Query("skip") String queryDate);

    @POST("/api/feed")
    Call<PostFeedResponse> postFeed(@Header("Authorization") String token, @Body PostFeedRequest postFeedRequest);

    //services
    @GET("/api/services")
    Call<GetServicesResponse> getServices(@Header("Authorization") String token);

    //nutritions
    @GET("/api/nutritions")
    Call<GetNutritionsResponse> getNutritions(@Header("Authorization") String token);

    @POST("/api/mealnote")
    Call<PostMealNoteResponse> postMealNote(@Header("Authorization") String token, @Body PostMealNoteRequest postMealNoterequest);

    @GET("/api/notes")
    Call<GetMealNotesResponse> getMealNotes(@Header("Authorization") String token, @Query("customer_id") int customer_id, @Query("skip") int skip, @Query("take") int take);

    //payment
    @POST("/api/payment")
    Call<PostPaymentResponse> postPayment(@Header("Authorization") String token, @Body PostPaymentRequest postPaymentRequest);

    //goal
    @GET("/api/goal")
    Call<GetCurrentGoalResponse> getCurrentGoal(@Header("Authorization") String token, @Query("customerId") int customerId);

    @GET("/api/goals")
    Call<GetGoalsResponse> getGoals(@Header("Authorization") String token, @Query("customerId") int customerId, @Query("skip") int skip, @Query("take") int take);

    @POST("/api/goal-create")
    Call<PostGoalResponse> postGoal(@Header("Authorization") String token, @Body PostGoalRequest postGoalRequest);
}
