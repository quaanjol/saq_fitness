package com.kwan.saq.response;

import com.kwan.saq.model.Customer;

public class LoginResponse {
    private Boolean success;
    private String message;
    private String token;
    private int user_id;
    private int user_role_id;
    private Customer customer;

    public LoginResponse(Boolean success, String message, String token, int user_id, int user_role_id, Customer customer) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.user_id = user_id;
        this.user_role_id = user_role_id;
        this.customer = customer;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(int user_role_id) {
        this.user_role_id = user_role_id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
