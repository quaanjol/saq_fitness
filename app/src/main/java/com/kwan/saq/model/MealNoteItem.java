package com.kwan.saq.model;

public class MealNoteItem {
    private String meal;
    private float calo;
    private float gram;

    public MealNoteItem(String meal, float calo, float gram) {
        this.meal = meal;
        this.calo = calo;
        this.gram = gram;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
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
}
