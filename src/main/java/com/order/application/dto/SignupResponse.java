package com.order.application.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class SignupResponse {
    private String message;
    private String email;

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return email;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
