package com.shopping_cart.models.service_models;


import com.google.gson.annotations.Expose;

public abstract class BaseServiceModel {

    @Expose
    private String id;

    public BaseServiceModel() {
    }

    public BaseServiceModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

