package com.kwan.saq.request;

public class PostWorkoutRequest {
    private int userId;
    private String name;
    private int totalSets;
    private int musclePartId;
    private String exerciseItems;

    public PostWorkoutRequest(int userId, String name, int totalSets, int musclePartId, String exerciseItems) {
        this.userId = userId;
        this.name = name;
        this.totalSets = totalSets;
        this.musclePartId = musclePartId;
        this.exerciseItems = exerciseItems;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalSets() {
        return totalSets;
    }

    public void setTotalSets(int totalSets) {
        this.totalSets = totalSets;
    }

    public int getMusclePartId() {
        return musclePartId;
    }

    public void setMusclePartId(int musclePartId) {
        this.musclePartId = musclePartId;
    }

    public String getExerciseItems() {
        return exerciseItems;
    }

    public void setExerciseItems(String exerciseItems) {
        this.exerciseItems = exerciseItems;
    }
}
