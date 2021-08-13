package com.shopping_cart.models.service_models;

import com.shopping_cart.models.entities.BaseEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.shopping_cart.constants.DiscountConstants.DISCOUNT_OVER_2000;

public class CartServiceModel extends BaseEntity {

    private UserServiceModel user;
    private List<CartProductServiceModel> cartProducts;
    private BigDecimal totalPriceProducts;
    private BigDecimal totalPriceAfterQuantityDiscount;
    private BigDecimal totalPriceAfterAllSumDiscounts;
    private Double finalDiscountInPercent;
    private BigDecimal finalDiscountInMoney;

    public CartServiceModel() {
        this.setDefault();
    }

    private void setDefault() {
        this.cartProducts = new ArrayList<>();
        this.totalPriceProducts = BigDecimal.ZERO;
        this.totalPriceAfterQuantityDiscount = BigDecimal.ZERO;
        this.totalPriceAfterAllSumDiscounts = BigDecimal.ZERO;
        this.finalDiscountInPercent = 0.0;
        this.finalDiscountInMoney = BigDecimal.ZERO;
    }

    public void calculateTotalFields() {

        for (CartProductServiceModel cartProduct : cartProducts) {

            /* Calculate totalPriceProducts */
            this.totalPriceProducts = this.totalPriceProducts.add(cartProduct.getTotalPrice());

            /* Calculate totalPriceAfterQuantityDiscount */
            this.totalPriceAfterQuantityDiscount = this.totalPriceAfterQuantityDiscount
                    .add(cartProduct.getTotalPriceAfterQuantityDiscount());
        }

        /* If totalPriceAfterQuantityDiscount is more than 2000 there is a 10% discount */
        if (this.totalPriceAfterQuantityDiscount.compareTo(BigDecimal.valueOf(DISCOUNT_OVER_2000)) > 0) {

            this.totalPriceAfterAllSumDiscounts = this.totalPriceAfterQuantityDiscount
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_DOWN).multiply(BigDecimal.valueOf(90));
        } else {
            this.totalPriceAfterAllSumDiscounts = this.totalPriceAfterQuantityDiscount;
        }

        /* Calculate how much is one percent of total product price before any discounts */
        BigDecimal onePercentOfTotalPriceProducts = this.totalPriceProducts
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_DOWN);

        /* Calculate finalDiscountInMoney */
        this.finalDiscountInMoney = this.totalPriceProducts.subtract(this.totalPriceAfterAllSumDiscounts);

        /* Calculate finalDiscountInPercent */
        this.finalDiscountInPercent = this.finalDiscountInMoney
                .divide(onePercentOfTotalPriceProducts, RoundingMode.HALF_DOWN).doubleValue();
    }

    public void addCartProduct(CartProductServiceModel cartProductServiceModel) {
        this.cartProducts.add(cartProductServiceModel);
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public List<CartProductServiceModel> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProductServiceModel> cartProducts) {
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
