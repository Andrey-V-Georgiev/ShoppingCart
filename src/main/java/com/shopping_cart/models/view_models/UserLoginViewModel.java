package com.shopping_cart.models.view_models;

public class UserLoginViewModel {

    private String username;
    private String token;

    public UserLoginViewModel() {
    }

    public UserLoginViewModel(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
