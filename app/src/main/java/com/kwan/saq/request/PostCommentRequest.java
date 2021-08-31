package com.kwan.saq.request;

public class PostCommentRequest {
    private int workout_id;
    private int customer_id;
    private String content;

    public PostCommentRequest(int workout_id, int customer_id, String content) {
        this.workout_id = workout_id;
        this.customer_id = customer_id;
        this.content = content;
    }

    public int getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(int workout_id) {
        this.workout_id = workout_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
