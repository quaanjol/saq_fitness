package com.kwan.saq.model;

public class ExerciseItem {
    private String name;
    private int order;
    private float time;
    private  float calo;
    private float rest;

    public ExerciseItem(String name, int order, float time, float calo, float rest) {
        this.name = name;
        this.order = order;
        this.time = time;
        this.calo = calo;
        this.rest = rest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getCalo() {
        return calo;
    }

    public void setCalo(float calo) {
        this.calo = calo;
    }

    public float getRest() {
        return rest;
    }

    public void setRest(float rest) {
        this.rest = rest;
    }
}
