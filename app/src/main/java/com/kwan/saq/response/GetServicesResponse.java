package com.kwan.saq.response;

import com.kwan.saq.model.Service;

import java.util.List;

public class GetServicesResponse {
    private Boolean success;
    private String message;
    private List<Service> services;

    public GetServicesResponse(Boolean success, String message, List<Service> services) {
        this.success = success;
        this.message = message;
        this.services = services;
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

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
