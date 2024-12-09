package com.order.application.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class SignupRequest {

    private String firstName;
    private String lastName;
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public @Email(message = "Invalid email address") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid email address") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") String password) {
        this.password = password;
    }
}

