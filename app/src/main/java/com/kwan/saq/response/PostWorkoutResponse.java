package com.kwan.saq.response;

import com.kwan.saq.model.Workout;

import java.util.List;

public class PostWorkoutResponse {
    private Boolean success;
    private String message;
    private Workout workouts;

    public PostWorkoutResponse(Boolean success, String message, Workout workouts) {
        this.success = success;
        this.message = message;
        this.workouts = workouts;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Workout getWorkouts() {
        return workouts;
    }

    public void setWorkouts(Workout workouts) {
        this.workouts = workouts;
    }
}
