package com.kwan.saq.response;

import com.kwan.saq.model.WorkoutComment;

import java.util.List;

public class GetCommentsResponse {
    private Boolean success;
    private String message;
    private List<WorkoutComment> comments;

    public GetCommentsResponse(Boolean success, String message, List<WorkoutComment> comments) {
        this.success = success;
        this.message = message;
        this.comments = comments;
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

    public List<WorkoutComment> getComments() {
        return comments;
    }

    public void setComments(List<WorkoutComment> comments) {
        this.comments = comments;
    }
}
