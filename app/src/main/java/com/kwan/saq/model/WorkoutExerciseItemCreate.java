package com.kwan.saq.model;

public class WorkoutExerciseItemCreate {
    private int exercise_id;
    private int order;
    private float time;
    private float rest;

    public WorkoutExerciseItemCreate(int exercise_id, int order, float time, float rest) {
        this.exercise_id = exercise_id;
        this.order = order;
        this.time = time;
        this.rest = rest;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
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

    public float getRest() {
        return rest;
    }

    public void setRest(float rest) {
        this.rest = rest;
    }
}
