package com.kwan.saq.response;

import com.kwan.saq.model.Payment;

public class PostPaymentResponse {
    private Boolean success;
    private String message;
    private Payment payment;

    public PostPaymentResponse(Boolean success, String message, Payment payment) {
        this.success = success;
        this.message = message;
        this.payment = payment;
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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
