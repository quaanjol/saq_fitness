package com.kwan.saq.request;

public class PostMealNoteRequest {
    private int customerId;
    private String description;
    private int mealCount;
    private String nutritions;

    public PostMealNoteRequest(int customerId, String description, int mealCount, String nutritions) {
        this.customerId = customerId;
        this.description = description;
        this.mealCount = mealCount;
        this.nutritions = nutritions;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMealCount() {
        return mealCount;
    }

    public void setMealCount(int mealCount) {
        this.mealCount = mealCount;
    }

    public String getNutritions() {
        return nutritions;
    }

    public void setNutritions(String nutritions) {
        this.nutritions = nutritions;
    }
}
