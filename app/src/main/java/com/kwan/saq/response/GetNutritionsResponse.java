package com.kwan.saq.response;

import com.kwan.saq.model.Nutrition;

import java.util.List;

public class GetNutritionsResponse {
    private Boolean success;
    private String message;
    private List<Nutrition> nutritions;

    public GetNutritionsResponse(Boolean success, String message, List<Nutrition> nutritions) {
        this.success = success;
        this.message = message;
        this.nutritions = nutritions;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Nutrition> getNutritions() {
        return nutritions;
    }

    public void setNutritions(List<Nutrition> nutritions) {
        this.nutritions = nutritions;
    }
}
