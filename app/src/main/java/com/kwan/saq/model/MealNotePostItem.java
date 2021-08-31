package com.kwan.saq.model;

public class MealNotePostItem {
    private int nutrition_id;
    private float gram;

    public MealNotePostItem(int nutrition_id, float gram) {
        this.nutrition_id = nutrition_id;
        this.gram = gram;
    }

    public int getNutrition_id() {
        return nutrition_id;
    }

    public void setNutrition_id(int nutrition_id) {
        this.nutrition_id = nutrition_id;
    }

    public float getGram() {
        return gram;
    }

    public void setGram(float gram) {
        this.gram = gram;
    }
}
