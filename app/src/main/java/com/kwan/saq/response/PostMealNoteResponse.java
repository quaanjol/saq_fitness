package com.kwan.saq.response;

import com.kwan.saq.model.Note;

public class PostMealNoteResponse {
    private Boolean success;
    private String message;
    private Note note;

    public PostMealNoteResponse(Boolean success, String message, Note note) {
        this.success = success;
        this.message = message;
        this.note = note;
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

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
