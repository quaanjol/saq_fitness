package com.kwan.saq.response;

import com.kwan.saq.model.Goal;

import java.util.List;

public class GetGoalsResponse {
    private Boolean success;
    private String message;
    private List<Goal> goals;

    public GetGoalsResponse(Boolean success, String message, List<Goal> goals) {
        this.success = success;
        this.message = message;
        this.goals = goals;
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

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }
}
