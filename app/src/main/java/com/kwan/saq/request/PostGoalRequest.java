package com.kwan.saq.request;

public class PostGoalRequest {
    private int customerId;
    private String name;
    private String startDate;
    private String endDate;
    private float calo;

    public PostGoalRequest(int customerId, String name, String startDate, String endDate, float calo) {
        this.customerId = customerId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.calo = calo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public float getCalo() {
        return calo;
    }

    public void setCalo(float calo) {
        this.calo = calo;
    }
}
