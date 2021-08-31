package com.kwan.saq.response;

import com.kwan.saq.model.Payment;

import java.util.List;

public class GetPaymentsResponse {
    private Boolean success;
    private String message;
    private List<Payment> payments;

    public GetPaymentsResponse(Boolean success, String message, List<Payment> payments) {
        this.success = success;
        this.message = message;
        this.payments = payments;
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

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
