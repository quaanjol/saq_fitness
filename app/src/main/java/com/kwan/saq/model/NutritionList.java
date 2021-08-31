package com.kwan.saq.model;

import java.util.List;

public class NutritionList {
    private List<Nutrition> nutritionList;

    public NutritionList(List<Nutrition> nutritionList) {
        this.nutritionList = nutritionList;
    }

    public List<Nutrition> getNutritionList() {
        return nutritionList;
    }

    public void setNutritionList(List<Nutrition> nutritionList) {
        this.nutritionList = nutritionList;
    }
}
