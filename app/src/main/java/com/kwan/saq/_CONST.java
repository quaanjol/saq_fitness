package com.kwan.saq;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kwan.saq.api.ApiManager;
import com.kwan.saq.model.Customer;
import com.kwan.saq.model.Exercise;
import com.kwan.saq.model.ExerciseList;
import com.kwan.saq.model.MusclePart;
import com.kwan.saq.model.MusclePartList;
import com.kwan.saq.model.Nutrition;
import com.kwan.saq.model.NutritionList;
import com.kwan.saq.model.Workout;
import com.kwan.saq.model.WorkoutList;
import com.kwan.saq.request.GetWorkoutsRequest;
import com.kwan.saq.request.LoginRequest;
import com.kwan.saq.response.GetExercisesResponse;
import com.kwan.saq.response.GetMusclePartsResponse;
import com.kwan.saq.response.GetNutritionsResponse;
import com.kwan.saq.response.GetWorkoutsResponse;
import com.kwan.saq.response.LoginResponse;
import com.kwan.saq.response.ValidateTokenResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class _CONST<validateTokenResponse> {
    // remembers vars
    public static final String USER_ID = "User_id";
    public static final String USER_NAME = "User_name";
    public static final String USER_ROLE_ID = "User_role_id";
    public static final String USER_EMAIL = "User_email";
    public static final String USER_PASSWORD = "User_password";
    public static final String USER_TOKEN = "User_token";
    public static final String USER_CUSTOMER = "User_customer";

    // const variables
    public static final int ADMIN_ROLE_ID = 1;
    public static final int SUB_ADMIN_ROLE_ID = 2;
    public static final int CUSTOMER_ROLE_ID = 3;

    public static final String REST_VIDEO_URL = "https://res.cloudinary.com/dmbm8j7te/video/upload/v1626238356/tt67eg4svuofsfkfxbwe.mp4";

    // gender values
    public static final String FEMALE_GENDER = "Female";
    public static final String MALE_GENDER = "Male";

    // training frequencies
    public static final String TRAINING_NEVER = "Almost no training";
    public static final String TRAINING_OFTEN = "Often (1-3 workouts/week)";
    public static final String TRAINING_USUALLY = "Usually (3-5 workouts/week)";
    public static final String TRAINING_HEAVY = "Heavy (5-7 workouts/week)";
    public static final String TRAINING_EXTREME = "Extreme (2 workouts/day)";

    // training time
    public static final String TRAINING_TIME_MORNING = "Morning";
    public static final String TRAINING_TIME_AFTERNOON = "Afternoon";
    public static final String TRAINING_TIME_EVENING = "Evening";


    // format timestamp
    public static String formatTime(String inputTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String res = "";
        Date date1 = null;

        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(inputTime);
            res = formatter.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            res = e.getMessage();
        }

        return res;
    }

    // create retrofit instance
    public static Retrofit createRetrofit() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiManager.DOMAIN).client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    // get login state
    public static boolean getStateLogin(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("Login", MODE_PRIVATE);
        boolean isLogin = preferences.getBoolean("LoginState",false);
        Log.d("TAG", "Current register status is " + isLogin);
        return isLogin;
    }

    public static int getCurrentUserId(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("Login", MODE_PRIVATE);
        int currentUserId = preferences.getInt(_CONST.USER_ID,-1);
        return currentUserId;
    }

    public static String getCurrentToken(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("Login", MODE_PRIVATE);
        String currentToken = preferences.getString(_CONST.USER_TOKEN,"");
        return currentToken;
    }

    public static Customer getCurrentCustomer(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("Login", MODE_PRIVATE);
        String customerJson = preferences.getString(_CONST.USER_CUSTOMER,"");

        Gson gson = new Gson();

        Customer thisCustomer = gson.fromJson(customerJson, Customer.class);

        return thisCustomer;
    }

    public static List<MusclePart> getMusclePartList(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("GeneralData", MODE_PRIVATE);

        Gson gson = new Gson();
        String musclePartsJson = preferences.getString("musclePartList", "");
        //String workoutsJson = preferences.getString("workoutList", "");

        MusclePartList musclePartList = gson.fromJson(musclePartsJson, MusclePartList.class);
        return musclePartList.getMusclePartList();
    }

    public static List<Exercise> getExerciseList(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("GeneralData", MODE_PRIVATE);

        Gson gson = new Gson();
        String exercisesJson = preferences.getString("exerciseList", "");

        ExerciseList exerciseList = gson.fromJson(exercisesJson, ExerciseList.class);
        return exerciseList.getExerciseList();
    }

    public static List<Nutrition> getNutritionList(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("GeneralData", MODE_PRIVATE);

        Gson gson = new Gson();
        String nutritionJson = preferences.getString("nutritionList", "");

        NutritionList nutritionList = gson.fromJson(nutritionJson, NutritionList.class);
        return nutritionList.getNutritionList();
    }


    public static void validateCurrentToken(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("Login", MODE_PRIVATE);
        String currentToken = preferences.getString(_CONST.USER_TOKEN,"");
        String validateTokenHeader = "Bearer " + currentToken;

        // create new retrofit
        Retrofit retrofit = _CONST.createRetrofit();

        ApiManager service = retrofit.create(ApiManager.class);
        service.validateToken(validateTokenHeader).enqueue(new Callback<ValidateTokenResponse>() {
            @Override
            public void onResponse(Call<ValidateTokenResponse> call, Response<ValidateTokenResponse> response) {
                if(response.body() != null) {
                    ValidateTokenResponse validateTokenResponse = response.body();

                    String message = validateTokenResponse.getMessage();

                    if(validateTokenResponse.getSuccess() == true) {
                        Log.d("TAG", "Current token is valid");
                    } else {
                        refreshToken(activity);
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

    public static void refreshToken(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("Login", MODE_PRIVATE);
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
                        editor.commit();
                    } else {
                        Toast toast = Toast.makeText(activity, "Cannot log in to refresh token: " + message, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("TAG", "cannot validate token via api: " + t);
                Toast toast = Toast.makeText(activity, "cannot validate token via api: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }


    // save general data
    public static void getNewExercises(Activity activity) {
        Retrofit retrofit = _CONST.createRetrofit();
        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + getCurrentToken(activity);

        service.getExercises(headerToken).enqueue(new Callback<GetExercisesResponse>() {

            @Override
            public void onResponse(Call<GetExercisesResponse> call, Response<GetExercisesResponse> response) {
                Log.d("TAG", "Get in the get exercises api function");
                if(response.body() != null) {
                    GetExercisesResponse getExercisesResponse = response.body();
                    _CONST.saveGeneralData(activity, getExercisesResponse.getExercises(), null, null);
                }
            }

            @Override
            public void onFailure(Call<GetExercisesResponse> call, Throwable t) {
                Log.d("TAG", "Cannot get exercises: " + t);
                Toast toast = Toast.makeText(activity, "Cannot get exercises: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public static void getNewMuscleParts(Activity activity) {
        Retrofit retrofit = _CONST.createRetrofit();
        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + getCurrentToken(activity);

        service.getMuscleParts(headerToken).enqueue(new Callback<GetMusclePartsResponse>() {
            @Override
            public void onResponse(Call<GetMusclePartsResponse> call, Response<GetMusclePartsResponse> response) {
                if(response.body() != null) {
                    GetMusclePartsResponse getMusclePartsResponse = response.body();
                    _CONST.saveGeneralData(activity, null, getMusclePartsResponse.getMuscle_parts(), null);
                }
            }

            @Override
            public void onFailure(Call<GetMusclePartsResponse> call, Throwable t) {
                Log.d("WEATHER", "Cannot get muscle parts: " + t);
                Toast toast = Toast.makeText(activity, "Cannot get muscle parts: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public static void getNewWorkouts(Activity activity) {
        Retrofit retrofit = _CONST.createRetrofit();
        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + getCurrentToken(activity);
        GetWorkoutsRequest getWorkoutsRequest = new GetWorkoutsRequest(getCurrentUserId(activity));

        service.getWorkouts(headerToken, getCurrentUserId(activity)).enqueue(new Callback<GetWorkoutsResponse>() {
            @Override
            public void onResponse(Call<GetWorkoutsResponse> call, Response<GetWorkoutsResponse> response) {
                if(response.body() != null) {
                    GetWorkoutsResponse getWorkoutsResponse = response.body();
                    _CONST.saveGeneralData(activity, null, null, getWorkoutsResponse.getWorkouts());
                }
            }

            @Override
            public void onFailure(Call<GetWorkoutsResponse> call, Throwable t) {
                Log.d("TAG", "Cannot get workouts: " + t);
                Toast toast = Toast.makeText(activity, "Cannot get workouts: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public static void saveGeneralData(Activity activity, List<Exercise> exerciseList, List<MusclePart> musclePartList, List<Workout> workoutList) {

        SharedPreferences preferences = activity.getSharedPreferences("GeneralData", MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();

        if(exerciseList != null) {
            ExerciseList exerciseList1Model = new ExerciseList(exerciseList);
            String exerciseJson = gson.toJson(exerciseList1Model);
            prefsEditor.putString("exerciseList", exerciseJson);
        }

        if(musclePartList != null) {
            MusclePartList musclePartList1Model = new MusclePartList(musclePartList);
            String musclePartListJson = gson.toJson(musclePartList1Model);
            prefsEditor.putString("musclePartList", musclePartListJson);
        }

        if(workoutList != null) {
            WorkoutList workoutList1Model = new WorkoutList(workoutList);
            String workoutListJson = gson.toJson(workoutList1Model);
            prefsEditor.putString("workoutList", workoutListJson);
        }

        prefsEditor.commit();
    }

    public static void getNutritions(Activity activity) {
        Retrofit retrofit = _CONST.createRetrofit();
        ApiManager service = retrofit.create(ApiManager.class);

        String headerToken = "Bearer " + getCurrentToken(activity);

        service.getNutritions(headerToken).enqueue(new Callback<GetNutritionsResponse>() {
            @Override
            public void onResponse(Call<GetNutritionsResponse> call, Response<GetNutritionsResponse> response) {
                if(response.body() != null) {
                    GetNutritionsResponse getNutritionsResponse = response.body();
                    Gson gson = new Gson();

                    _CONST.saveNutritions(activity, getNutritionsResponse.getNutritions());
                }
            }

            @Override
            public void onFailure(Call<GetNutritionsResponse> call, Throwable t) {
                Log.d("WEATHER", "Cannot get muscle parts: " + t);
                Toast toast = Toast.makeText(activity, "Cannot get muscle parts: " + t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public static void saveNutritions(Activity activity, List<Nutrition> nutritionList) {

        SharedPreferences preferences = activity.getSharedPreferences("GeneralData", MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();

        if(nutritionList != null) {
            NutritionList nutritionListBig = new NutritionList(nutritionList);
            String nutritionJson = gson.toJson(nutritionListBig);
            prefsEditor.putString("nutritionList", nutritionJson);
        }

        prefsEditor.commit();
    }


    // menu buttons navigation
    public static void  goToHomeScreen(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
        activity.finish();
        return;
    }

    public static void goToTrainingPage(Activity activity) {
        Intent intent = new Intent(activity, WorkoutMainActivity.class);
        activity.startActivity(intent);
        activity.finish();
        return;
    }

    public static void goToProfilePage(Activity activity) {
        Intent intent = new Intent(activity, ProfileActivity.class);
        activity.startActivity(intent);
        activity.finish();
        return;
    }

    public static void goToReportPage(Activity activity) {
        Intent intent = new Intent(activity, ReportMainActivity.class);
        activity.startActivity(intent);
        activity.finish();
        return;
    }

    public static void goToNutritionPage(Activity activity) {
        Intent intent = new Intent(activity, NutritionMainActivity.class);
        activity.startActivity(intent);
        activity.finish();
        return;
    }

    public static void goToMainPage(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
        return;
    }

}
