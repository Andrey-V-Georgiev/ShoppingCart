package com.shopping_cart.models.service_models;

import com.shopping_cart.enums.UserRole;

import java.time.LocalDateTime;

public class UserServiceModel extends BaseServiceModel {

    private String username;
    private String email;
    private String password;
    private String token;
    private UserRole role;
    private LocalDateTime addDate;

    public UserServiceModel() {
    }

    public UserServiceModel(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UserServiceModel(String username, String email, String password, String token, UserRole role, LocalDateTime addDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.token = token;
        this.role = role;
        this.addDate = addDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }
}
