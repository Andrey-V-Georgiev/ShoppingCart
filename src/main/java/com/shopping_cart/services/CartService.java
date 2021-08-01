package com.shopping_cart.services;

import com.shopping_cart.enums.RemoveProductFromCart;
import com.shopping_cart.models.service_models.CartServiceModel;
import com.shopping_cart.models.service_models.ProductServiceModel;

public interface CartService {

    ProductServiceModel addProductToCart(String userId, String productId, int quantity);

    CartServiceModel findCartByUserId(String userId);

    RemoveProductFromCart removeProduct(String productId, String userId, int quantity);

    void emptyTheCart(String userId);
}
