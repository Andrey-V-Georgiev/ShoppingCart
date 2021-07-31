package com.shopping_cart.models.binding_models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.shopping_cart.constants.ModelsMsgConstants.*;

public class CartAddBindingModel {

    private String productId;
    private int quantity;

    public CartAddBindingModel() {
    }

    public CartAddBindingModel(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
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

    @NotNull(message = ORDERED_QUANTITY_NOT_NULL)
    @Min(value = 1 , message = ORDERED_QUANTITY_MUST_BE_AT_LEAST_ONE)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
