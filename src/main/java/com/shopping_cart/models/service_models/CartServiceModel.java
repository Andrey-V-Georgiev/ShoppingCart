package com.shopping_cart.models.service_models;

import com.shopping_cart.models.entities.BaseEntity;

import java.math.BigDecimal;
import java.util.Map;

public class CartServiceModel extends BaseEntity {

    private UserServiceModel owner;
    private Map<String, CartProductServiceModel> shoppingCartProducts;
    private BigDecimal totalPriceProducts;
    private BigDecimal totalPriceAfterQuantityDiscount;
    private BigDecimal totalPriceAfterAllSumDiscount;
    private BigDecimal finalDiscountInPercent;
    private BigDecimal finalDiscountInMoney;

    public CartServiceModel() {
    }

    public UserServiceModel getOwner() {
        return owner;
    }

    public void setOwner(UserServiceModel owner) {
        this.owner = owner;
    }

    public Map<String, CartProductServiceModel> getShoppingCartProducts() {
        return shoppingCartProducts;
    }

    public void setShoppingCartProducts(Map<String, CartProductServiceModel> shoppingCartProducts) {
        this.shoppingCartProducts = shoppingCartProducts;
    }

    public BigDecimal getTotalPriceProducts() {
        return totalPriceProducts;
    }

    public void setTotalPriceProducts(BigDecimal totalPriceProducts) {
        this.totalPriceProducts = totalPriceProducts;
    }

    public BigDecimal getTotalPriceAfterQuantityDiscount() {
        return totalPriceAfterQuantityDiscount;
    }

    public void setTotalPriceAfterQuantityDiscount(BigDecimal totalPriceAfterQuantityDiscount) {
        this.totalPriceAfterQuantityDiscount = totalPriceAfterQuantityDiscount;
    }

    public BigDecimal getTotalPriceAfterAllSumDiscount() {
        return totalPriceAfterAllSumDiscount;
    }

    public void setTotalPriceAfterAllSumDiscount(BigDecimal totalPriceAfterAllSumDiscount) {
        this.totalPriceAfterAllSumDiscount = totalPriceAfterAllSumDiscount;
    }

    public BigDecimal getFinalDiscountInPercent() {
        return finalDiscountInPercent;
    }

    public void setFinalDiscountInPercent(BigDecimal finalDiscountInPercent) {
        this.finalDiscountInPercent = finalDiscountInPercent;
    }

    public BigDecimal getFinalDiscountInMoney() {
        return finalDiscountInMoney;
    }

    public void setFinalDiscountInMoney(BigDecimal finalDiscountInMoney) {
        this.finalDiscountInMoney = finalDiscountInMoney;
    }
}
