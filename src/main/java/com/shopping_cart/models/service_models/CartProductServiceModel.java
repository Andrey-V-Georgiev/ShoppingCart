package com.shopping_cart.models.service_models;

import java.math.BigDecimal;

public class CartProductServiceModel extends ProductServiceModel {

    private ProductServiceModel productServiceModel;
    private Integer quantity;
    private BigDecimal totalPrice;
    private BigDecimal totalPriceAfterQuantityDiscount;

    public CartProductServiceModel() {
    }

}
