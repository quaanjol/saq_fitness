package com.kwan.saq.response;

import com.kwan.saq.model.NormalFeed;

import java.util.List;

public class PostFeedResponse {
    private Boolean success;
    private String message;
    private NormalFeed feed;

    public PostFeedResponse(Boolean success, String message, NormalFeed feed) {
        this.success = success;
        this.message = message;
        this.feed = feed;
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

    public NormalFeed getFeed() {
        return feed;
    }

    public void setFeed(NormalFeed feed) {
        this.feed = feed;
    }
}
