package com.kwan.saq.model;

import java.util.Date;

public class Customer {
    private int id;
    private String name;
    private int user_id;
    private String img;
    private String phone;
    private int age;
    private int gender;
    private float weight;
    private float height;
    private int training_frequency;
    private float tdee;
    private float ave_intake;
    private float ave_burn;
    private int membership;
    private Date membership_expired;
    private Double total_paid;
    private int preferred_training_time;
    private String created_at;
    private String updated_at;
    private String deleted_at;

    public Customer(int id, String name, int user_id, String img, String phone, int age, int gender, float weight, float height, int training_frequency, float tdee, float ave_intake, float ave_burn, int membership, Date membership_expired, Double total_paid, int preferred_training_time, String created_at, String updated_at, String deleted_at) {
        this.id = id;
        this.name = name;
        this.user_id = user_id;
        this.img = img;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.training_frequency = training_frequency;
        this.tdee = tdee;
        this.ave_intake = ave_intake;
        this.ave_burn = ave_burn;
        this.membership = membership;
        this.membership_expired = membership_expired;
        this.total_paid = total_paid;
        this.preferred_training_time = preferred_training_time;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getTraining_frequency() {
        return training_frequency;
    }

    public void setTraining_frequency(int training_frequency) {
        this.training_frequency = training_frequency;
    }

    public float getTdee() {
        return tdee;
    }

    public void setTdee(float tdee) {
        this.tdee = tdee;
    }

    public float getAve_intake() {
        return ave_intake;
    }

    public void setAve_intake(float ave_intake) {
        this.ave_intake = ave_intake;
    }

    public float getAve_burn() {
        return ave_burn;
    }

    public void setAve_burn(float ave_burn) {
        this.ave_burn = ave_burn;
    }

    public int getMembership() {
        return membership;
    }

    public void setMembership(int membership) {
        this.membership = membership;
    }

    public Date getMembership_expired() {
        return membership_expired;
    }

    public void setMembership_expired(Date membership_expired) {
        this.membership_expired = membership_expired;
    }

    public Double getTotal_paid() {
        return total_paid;
    }

    public void setTotal_paid(Double total_paid) {
        this.total_paid = total_paid;
    }

    public int getPreferred_training_time() {
        return preferred_training_time;
    }

    public void setPreferred_training_time(int preferred_training_time) {
        this.preferred_training_time = preferred_training_time;
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
