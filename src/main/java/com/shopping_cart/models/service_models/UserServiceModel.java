package com.shopping_cart.models.service_models;


import java.time.LocalDateTime;

public class UserServiceModel extends BaseServiceModel {

    private String username;
    private String email;
    private String password;
    private String token;
    private String role;
    private LocalDateTime registrationDate;
    private CartServiceModel cart;

    public UserServiceModel() {
    }

    public UserServiceModel(String username, String email) {
        this.username = username;
        this.email = email;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public CartServiceModel getCart() {
        return cart;
    }

    public void setCart(CartServiceModel cart) {
        this.cart = cart;
    }
}
