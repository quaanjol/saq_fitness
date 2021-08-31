package com.kwan.saq.model;

import java.io.Serializable;
import java.util.List;

public class Workout implements Serializable {
    private int id;
    private int user_id;
    private String name;
    private int muscle_part_id;
    private float calo;
    private float time;
    private int total_set;
    private int commentCount;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private List<CustomExerciseItem> exercises;


    public Workout(int id, int user_id, String name, int muscle_part_id, float calo, float time, int total_set, int commentCount, String created_at, String updated_at, String deleted_at, List<CustomExerciseItem> exercises) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.muscle_part_id = muscle_part_id;
        this.calo = calo;
        this.time = time;
        this.total_set = total_set;
        this.commentCount = commentCount;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        this.exercises = exercises;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMuscle_part_id() {
        return muscle_part_id;
    }

    public void setMuscle_part_id(int muscle_part_id) {
        this.muscle_part_id = muscle_part_id;
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

    public int getTotal_set() {
        return total_set;
    }

    public void setTotal_set(int total_set) {
        this.total_set = total_set;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
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

    public List<CustomExerciseItem> getExercises() {
        return exercises;
    }

    public void setExercises(List<CustomExerciseItem> exercises) {
        this.exercises = exercises;
    }
}
