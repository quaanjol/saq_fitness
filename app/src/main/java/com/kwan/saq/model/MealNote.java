package com.kwan.saq.model;

public class MealNote {
    private int id;
    private int note_id;
    private int nutrition_id;
    private float calo;
    private float gram;
    private String created_at;
    private String updated_at;
    private String deleted_at;

    public MealNote(int id, int note_id, int nutrition_id, float calo, float gram, String created_at, String updated_at, String deleted_at) {
        this.id = id;
        this.note_id = note_id;
        this.nutrition_id = nutrition_id;
        this.calo = calo;
        this.gram = gram;
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

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public int getNutrition_id() {
        return nutrition_id;
    }

    public void setNutrition_id(int nutrition_id) {
        this.nutrition_id = nutrition_id;
    }

    public float getCalo() {
        return calo;
    }

    public void setCalo(float calo) {
        this.calo = calo;
    }

    public float getGram() {
        return gram;
    }

    public void setGram(float gram) {
        this.gram = gram;
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
