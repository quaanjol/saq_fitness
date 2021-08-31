package com.kwan.saq.model;

import java.util.List;

public class ExerciseList {
    private List<Exercise> exerciseList;

    public ExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }
}
