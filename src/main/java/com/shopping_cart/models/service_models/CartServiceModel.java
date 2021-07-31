package com.shopping_cart.models.service_models;

import com.shopping_cart.models.entities.BaseEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class CartServiceModel extends BaseEntity {

    private UserServiceModel user;
    private Map<String, CartProductServiceModel> cartProducts;
    private BigDecimal totalPriceProducts;
    private BigDecimal totalPriceAfterQuantityDiscount;
    private BigDecimal totalPriceAfterAllSumDiscounts;
    private Double finalDiscountInPercent;
    private BigDecimal finalDiscountInMoney;

    public CartServiceModel() {
        this.setDefault();
    }

    private void setDefault() {
        this.cartProducts = new HashMap<>();
        this.totalPriceProducts = BigDecimal.ZERO;
        this.totalPriceAfterQuantityDiscount = BigDecimal.ZERO;
        this.totalPriceAfterAllSumDiscounts = BigDecimal.ZERO;
        this.finalDiscountInPercent = 0.0;
        this.finalDiscountInMoney = BigDecimal.ZERO;
    }

    public void calculateTotalFields() {

        /* Calculate totalPriceProducts and totalPriceAfterQuantityDiscount */
        for (CartProductServiceModel cartProductServiceModel : cartProducts.values()) {

            this.totalPriceProducts = this.totalPriceProducts
                    .add(cartProductServiceModel.getTotalPrice());

            this.totalPriceAfterQuantityDiscount = this.totalPriceAfterQuantityDiscount
                    .add(cartProductServiceModel.getTotalPriceAfterQuantityDiscount());
        }

        /* If totalPriceAfterQuantityDiscount is more than 100 there is a 10% discount */
        if (this.totalPriceAfterQuantityDiscount.compareTo(BigDecimal.valueOf(100)) > 0) {

            this.totalPriceAfterAllSumDiscounts = this.totalPriceAfterQuantityDiscount
                    .divide(BigDecimal.valueOf(100), RoundingMode.UNNECESSARY).multiply(BigDecimal.valueOf(90));
        } else {
            this.totalPriceAfterAllSumDiscounts = this.totalPriceAfterQuantityDiscount;
        }

        /* Calculate how much is one percent of total product price before any discounts */
        BigDecimal onePercentOfTotalPriceProducts = this.totalPriceProducts
                .divide(BigDecimal.valueOf(100), RoundingMode.UNNECESSARY);

        /* Calculate finalDiscountInMoney */
        this.finalDiscountInMoney = this.totalPriceProducts.subtract(this.totalPriceAfterAllSumDiscounts);

        /* Calculate finalDiscountInPercent */
        this.finalDiscountInPercent = this.finalDiscountInMoney
                .divide(onePercentOfTotalPriceProducts, RoundingMode.UNNECESSARY).doubleValue();
    }

    public void addCartProduct(CartProductServiceModel cartProductServiceModel) {
        this.cartProducts.put(cartProductServiceModel.getProductType(), cartProductServiceModel);
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public Map<String, CartProductServiceModel> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(Map<String, CartProductServiceModel> cartProducts) {
        this.cartProducts = cartProducts;
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

    public BigDecimal getTotalPriceAfterAllSumDiscounts() {
        return totalPriceAfterAllSumDiscounts;
    }

    public void setTotalPriceAfterAllSumDiscounts(BigDecimal totalPriceAfterAllSumDiscounts) {
        this.totalPriceAfterAllSumDiscounts = totalPriceAfterAllSumDiscounts;
    }

    public Double getFinalDiscountInPercent() {
        return finalDiscountInPercent;
    }

    public void setFinalDiscountInPercent(Double finalDiscountInPercent) {
        this.finalDiscountInPercent = finalDiscountInPercent;
    }

    public BigDecimal getFinalDiscountInMoney() {
        return finalDiscountInMoney;
    }

    public void setFinalDiscountInMoney(BigDecimal finalDiscountInMoney) {
        this.finalDiscountInMoney = finalDiscountInMoney;
    }
}
