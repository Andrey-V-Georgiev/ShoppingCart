package com.shopping_cart.services;

import com.shopping_cart.enums.RemoveProductFromCart;
import com.shopping_cart.models.service_models.CartProductServiceModel;
import com.shopping_cart.models.service_models.CartServiceModel;
import com.shopping_cart.models.service_models.ProductServiceModel;


public interface CartService {

    CartServiceModel findCartByUserId(String userId);

    ProductServiceModel addProductToCart(String userId, String productId);

    RemoveProductFromCart decreaseProductCount(String productId, String userId);

    void removeProductFromCartList(CartProductServiceModel cartProductServiceModel, CartServiceModel cartServiceModel);

    void emptyTheCart(String userId);

    boolean checkCartIsEmpty(String userId);
}
