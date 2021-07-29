package com.shopping_cart.models.service_models;

import java.time.LocalDateTime;

public class BlackTokenServiceModel extends BaseServiceModel {

    private String userId;
    private String token;
    private LocalDateTime addDate;

    public BlackTokenServiceModel() {
    }

    public BlackTokenServiceModel(String userId, String token) {
        this.userId = userId;
        this.token = token;
        this.addDate = LocalDateTime.now();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }
}
