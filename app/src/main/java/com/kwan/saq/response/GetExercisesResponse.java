package com.kwan.saq.response;

import com.kwan.saq.model.Exercise;

import java.util.List;

public class GetExercisesResponse {
    private Boolean success;
    private String message;
    private List<Exercise> exercises;

    public GetExercisesResponse(Boolean success, String message, List<Exercise> exercises) {
        this.success = success;
        this.message = message;
        this.exercises = exercises;
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

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
