package com.shopping_cart.models.view_models;

public class UserViewModel {

    private String username;
    private String email;
    private String token;
    private String role;

    public UserViewModel() {
    }

    public UserViewModel(String username, String email, String token, String role) {
        this.username = username;
        this.email = email;
        this.token = token;
        this.role = role;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
