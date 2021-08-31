package com.kwan.saq.model;

public class Nutrition {
    private int id;
    private String nutrition_category_name;
    private int nutrition_category_id;
    private String name;
    private float serving;
    private float calo;
    private float fat;
    private float carb;
    private float protein;
    private float sugar;
    private String created_at;
    private String updated_at;
    private String deleted_at;

    public Nutrition(int id, String nutrition_category_name, int nutrition_category_id, String name, float serving, float calo, float fat, float carb, float protein, float sugar, String created_at, String updated_at, String deleted_at) {
        this.id = id;
        this.nutrition_category_name = nutrition_category_name;
        this.nutrition_category_id = nutrition_category_id;
        this.name = name;
        this.serving = serving;
        this.calo = calo;
        this.fat = fat;
        this.carb = carb;
        this.protein = protein;
        this.sugar = sugar;
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

    public String getNutrition_category_name() {
        return nutrition_category_name;
    }

    public void setNutrition_category_name(String nutrition_category_name) {
        this.nutrition_category_name = nutrition_category_name;
    }

    public int getNutrition_category_id() {
        return nutrition_category_id;
    }

    public void setNutrition_category_id(int nutrition_category_id) {
        this.nutrition_category_id = nutrition_category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getServing() {
        return serving;
    }

    public void setServing(float serving) {
        this.serving = serving;
    }

    public float getCalo() {
        return calo;
    }

    public void setCalo(float calo) {
        this.calo = calo;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getCarb() {
        return carb;
    }

    public void setCarb(float carb) {
        this.carb = carb;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getSugar() {
        return sugar;
    }

    public void setSugar(float sugar) {
        this.sugar = sugar;
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
