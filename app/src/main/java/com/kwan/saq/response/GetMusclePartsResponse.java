package com.kwan.saq.response;

import com.kwan.saq.model.MusclePart;

import java.util.List;

public class GetMusclePartsResponse {
    private Boolean success;
    private String message;
    private List<MusclePart> muscle_parts;

    public GetMusclePartsResponse(Boolean success, String message, List<MusclePart> muscle_parts) {
        this.success = success;
        this.message = message;
        this.muscle_parts = muscle_parts;
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

    public List<MusclePart> getMuscle_parts() {
        return muscle_parts;
    }

    public void setMuscle_parts(List<MusclePart> muscle_parts) {
        this.muscle_parts = muscle_parts;
    }
}
