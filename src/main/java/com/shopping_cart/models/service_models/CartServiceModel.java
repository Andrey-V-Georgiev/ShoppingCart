package com.shopping_cart.models.service_models;

import com.shopping_cart.models.entities.BaseEntity;

import java.math.BigDecimal;
import java.util.Map;

public class CartServiceModel extends BaseEntity {

    private String ownerID;
    private BigDecimal totalPriceProducts;
    private BigDecimal totalPriceAfterQuantityDiscount;
    private BigDecimal totalPriceAfterAllSumDiscount;
    private BigDecimal finalDiscountInPercent;
    private BigDecimal finalDiscountInMoney;
    private Map<String, CartProductServiceModel> shoppingCartProducts;

    public CartServiceModel() {
    }

}
