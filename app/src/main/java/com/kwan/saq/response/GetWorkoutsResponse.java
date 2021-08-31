package com.kwan.saq.response;

import com.kwan.saq.model.Workout;

import java.util.List;

public class GetWorkoutsResponse {
    private Boolean success;
    private String message;
    private List<Workout> workouts;

    public GetWorkoutsResponse(Boolean success, String message, List<Workout> workouts) {
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

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }
}
