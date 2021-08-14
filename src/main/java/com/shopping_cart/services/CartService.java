package com.shopping_cart.services;

import com.shopping_cart.enums.RemoveProductFromCart;
import com.shopping_cart.models.service_models.CartProductServiceModel;
import com.shopping_cart.models.service_models.CartServiceModel;
import com.shopping_cart.models.service_models.ProductServiceModel;

import java.util.List;

public interface CartService {

    ProductServiceModel addProductToCart(String userId, String productId, int quantity);

    CartServiceModel findCartByUserId(String userId);

    RemoveProductFromCart decreaseProductCount(String productId, String userId, int quantity);

    void emptyTheCart(String userId);

    boolean checkCartIsEmpty(String userId);

    void removeProductFromCartList(CartProductServiceModel cartProductServiceModel,
                                   List<CartProductServiceModel> cartProducts,
                                   CartServiceModel cartServiceModel);
}
