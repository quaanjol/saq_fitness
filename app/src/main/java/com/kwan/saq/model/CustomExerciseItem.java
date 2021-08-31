package com.kwan.saq.model;

public class CustomExerciseItem {
    private int order;
    private String name;
    private float calo;
    private float time;
    private float rest;

    public CustomExerciseItem(int order, String name, float calo, float time, float rest) {
        this.order = order;
        this.name = name;
        this.calo = calo;
        this.time = time;
        this.rest = rest;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public float getRest() {
        return rest;
    }

    public void setRest(float rest) {
        this.rest = rest;
    }
}
