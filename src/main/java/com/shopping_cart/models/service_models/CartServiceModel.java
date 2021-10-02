package com.shopping_cart.models.service_models;

import com.shopping_cart.models.entities.BaseEntity;
import java.util.List;

public class CartServiceModel extends BaseEntity {

    private UserServiceModel user;
    private List<CartProductServiceModel> cartProducts;

    public CartServiceModel() {

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
}
