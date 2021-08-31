package com.kwan.saq.response;

import com.kwan.saq.model.WorkoutComment;

public class PostCommentResponse {
    private Boolean success;
    private String message;
    private WorkoutComment comment;

    public PostCommentResponse(Boolean success, String message, WorkoutComment comment) {
        this.success = success;
        this.message = message;
        this.comment = comment;
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

    public WorkoutComment getComment() {
        return comment;
    }

    public void setComment(WorkoutComment comment) {
        this.comment = comment;
    }
}
