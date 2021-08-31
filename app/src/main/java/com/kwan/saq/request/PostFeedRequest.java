package com.kwan.saq.request;

public class PostFeedRequest {
    private int customer_id;
    private int workout_id;
    private float calo;
    private float time;
    private float result;

    public PostFeedRequest(int customer_id, int workout_id, float calo, float time, float result) {
        this.customer_id = customer_id;
        this.workout_id = workout_id;
        this.calo = calo;
        this.time = time;
        this.result = result;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(int workout_id) {
        this.workout_id = workout_id;
    }

    public float getCalo() {
        return calo;
    }

    public void setCalo(float calo) {
        this.calo = calo;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }
}
