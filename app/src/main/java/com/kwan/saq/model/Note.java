package com.kwan.saq.model;

import java.util.List;

public class Note {
    private int id;
    private int customer_id;
    private String description;
    private List<MealNoteItem> mealNotes;
    private String created_at;
    private String updated_at;
    private String deleted_at;

    public Note(int id, int customer_id, String description, List<MealNoteItem> meal_notes, String created_at, String updated_at, String deleted_at) {
        this.id = id;
        this.customer_id = customer_id;
        this.description = description;
        this.mealNotes = meal_notes;
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

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MealNoteItem> getMeal_notes() {
        return mealNotes;
    }

    public void setMeal_notes(List<MealNoteItem> meal_notes) {
        this.mealNotes = meal_notes;
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
