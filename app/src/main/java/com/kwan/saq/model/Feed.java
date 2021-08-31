package com.kwan.saq.model;

public class Feed {
    private int id;
    private int customer_id;
    private int workout_id;
    private String customer_name;
    private String workout_name;
    private Double calo;
    private Double time;
    private float result;
    private String img;
    private String created_at;
    private String updated_at;
    private String deleted_at;

    public Feed(int id, int customer_id, int workout_id, String customer, String workout, Double calo, Double time, float result, String img, String created_at, String updated_at, String deleted_at) {
        this.id = id;
        this.customer_id = customer_id;
        this.workout_id = workout_id;
        this.customer_name = customer;
        this.workout_name = workout;
        this.calo = calo;
        this.time = time;
        this.result = result;
        this.img = img;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCustomer() {
        return customer_name;
    }

    public void setCustomer(String customer) {
        this.customer_name = customer;
    }

    public String getWorkout() {
        return workout_name;
    }

    public void setWorkout(String workout) {
        this.workout_name = workout;
    }

    public Double getCalo() {
        return calo;
    }

    public void setCalo(Double calo) {
        this.calo = calo;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}
