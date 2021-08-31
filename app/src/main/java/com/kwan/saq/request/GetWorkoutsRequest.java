package com.kwan.saq.request;

public class GetWorkoutsRequest {
    private int user_id;

    public GetWorkoutsRequest(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
