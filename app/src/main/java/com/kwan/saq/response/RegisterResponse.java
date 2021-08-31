package com.kwan.saq.response;

import com.kwan.saq.model.User;

public class RegisterResponse {
    private Boolean success;
    private String message;
    private User data;

    public RegisterResponse(Boolean success, String message, User data) {
        this.success = success;
        this.message = message;
        this.data = data;
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

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
