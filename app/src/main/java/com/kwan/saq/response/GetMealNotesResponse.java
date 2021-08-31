package com.kwan.saq.response;

import com.kwan.saq.model.Note;

import java.util.List;

public class GetMealNotesResponse {
    private Boolean success;
    private String message;
    private List<Note> notes;

    public GetMealNotesResponse(Boolean success, String message, List<Note> notes) {
        this.success = success;
        this.message = message;
        this.notes = notes;
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

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
