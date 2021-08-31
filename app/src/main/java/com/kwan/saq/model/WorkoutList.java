package com.kwan.saq.model;

import java.util.List;

public class WorkoutList {
    private List<Workout> workoutList;

    public WorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    public List<Workout> getWorkoutList() {
        return workoutList;
    }

    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }
}
