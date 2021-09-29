package com.shopping_cart.models.binding_models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.shopping_cart.constants.ModelsMsgConstants.*;

public class CartProductBindingModel {

    private String productId;

    public CartProductBindingModel() {
    }

    public CartProductBindingModel(String productId) {
        this.productId = productId;
    }

    @NotEmpty(message = ID_NOT_EMPTY)
    @NotNull(message = ID_NOT_NULL)
    @Size(min = 3, message =  ID_LENGTH)
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
