package com.kwan.saq.response;

import com.kwan.saq.model.Goal;

public class GetCurrentGoalResponse {
    private Boolean success;
    private String message;
    private Goal goal;

    public GetCurrentGoalResponse(Boolean success, String message, Goal goal) {
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

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}
