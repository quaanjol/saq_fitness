package com.kwan.saq.model;

import java.io.Serializable;
import java.util.List;

public class Exercise implements Serializable {
    private int id;
    private String name;
    private float calo_per_hour;
    private String img;
    private String video;
    private String description;
    private int muscle_part_id;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private String muscle_part;
    private List<String> muscle_parts;

    public Exercise(int id, String name, float calo_per_hour, String img, String video, String description, int muscle_part_id, String created_at, String updated_at, String deleted_at, String muscle_part, List<String> muscle_parts) {
        this.id = id;
        this.name = name;
        this.calo_per_hour = calo_per_hour;
        this.img = img;
        this.video = video;
        this.description = description;
        this.muscle_part_id = muscle_part_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        this.muscle_part = muscle_part;
        this.muscle_parts = muscle_parts;
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

    public float getCalo_per_hour() {
        return calo_per_hour;
    }

    public void setCalo_per_hour(float calo_per_hour) {
        this.calo_per_hour = calo_per_hour;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMuscle_part_id() {
        return muscle_part_id;
    }

    public void setMuscle_part_id(int muscle_part_id) {
        this.muscle_part_id = muscle_part_id;
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

    public String getMuscle_part() {
        return muscle_part;
    }

    public void setMuscle_part(String muscle_part) {
        this.muscle_part = muscle_part;
    }

    public List<String> getMuscle_parts() {
        return muscle_parts;
    }

    public void setMuscle_parts(List<String> muscle_parts) {
        this.muscle_parts = muscle_parts;
    }
}
