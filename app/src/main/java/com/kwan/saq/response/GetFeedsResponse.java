package com.kwan.saq.response;

import com.kwan.saq.model.Feed;

import java.util.List;

public class GetFeedsResponse {
    private Boolean success;
    private String message;
    private int totalFeeds;
    private List<Feed> feeds;

    public GetFeedsResponse(Boolean success, String message, int totalFeeds, List<Feed> feeds) {
        this.success = success;
        this.message = message;
        this.totalFeeds = totalFeeds;
        this.feeds = feeds;
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

    public int getTotalFeeds() {
        return totalFeeds;
    }

    public void setTotalFeeds(int totalFeeds) {
        this.totalFeeds = totalFeeds;
    }

    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }
}
