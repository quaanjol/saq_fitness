package com.kwan.saq.response;

import com.kwan.saq.model.PostGoal;

public class PostGoalResponse {
    private Boolean success;
    private String message;
    private PostGoal goal;

    public PostGoalResponse(Boolean success, String message, PostGoal goal) {
        this.success = success;
        this.message = message;
        this.goal = goal;
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

    public PostGoal getGoal() {
        return goal;
    }

    public void setGoal(PostGoal goal) {
        this.goal = goal;
    }
}
