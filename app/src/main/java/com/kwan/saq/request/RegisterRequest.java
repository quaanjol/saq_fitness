package com.kwan.saq.request;

public class RegisterRequest {
    private String name;
    private String email;
    private String phone;
    private int age;
    private int gender;
    private float weight;
    private float height;
    private int training_frequency;
    private int preferred_training_time;
    private String password;
    private String password2;

    public RegisterRequest(String name, String email, String phone, int age, int gender, float weight, float height, int training_frequency, int preferred_training_time, String password, String password2) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.training_frequency = training_frequency;
        this.preferred_training_time = preferred_training_time;
        this.password = password;
        this.password2 = password2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getPreferred_training_time() {
        return preferred_training_time;
    }

    public void setPreferred_training_time(int preferred_training_time) {
        this.preferred_training_time = preferred_training_time;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
