package com.kwan.saq.model;

import java.util.List;

public class Goal {
    private int id;
    private int customer_id;
    private String name;
    private String start_date;
    private String end_date;
    private float calo;
    private float calo_left;
    private float calo_done;
    private int status;
    private float result;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private List<Feed> goalFeeds;

    public Goal(int id, int customer_id, String name, String start_date, String end_date, float calo, float calo_left, float calo_done, int status, float result, String created_at, String updated_at, String deleted_at, List<Feed> goalFeeds) {
        this.id = id;
        this.customer_id = customer_id;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.calo = calo;
        this.calo_left = calo_left;
        this.calo_done = calo_done;
        this.status = status;
        this.result = result;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        this.goalFeeds = goalFeeds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public float getCalo() {
        return calo;
    }

    public void setCalo(float calo) {
        this.calo = calo;
    }

    public float getCalo_left() {
        return calo_left;
    }

    public void setCalo_left(float calo_left) {
        this.calo_left = calo_left;
    }

    public float getCalo_done() {
        return calo_done;
    }

    public void setCalo_done(float calo_done) {
        this.calo_done = calo_done;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
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

    public List<Feed> getGoalFeeds() {
        return goalFeeds;
    }

    public void setGoalFeeds(List<Feed> goalFeeds) {
        this.goalFeeds = goalFeeds;
    }
}
