package com.shopping_cart.constants;

import com.google.gson.Gson;

public class ResponseMsgConstants {

    public static final Gson g = new Gson();

    /* Positive messages */

    public static final String REGISTER_SUCCESS = g.toJson("Your registration is successful");
    public static final String LOGOUT_SUCCESS = g.toJson("You are successfully logged out");

    public static final String PRODUCT_QUANTITY_DECREASED = g.toJson("Quantity of this product was decreased");
    public static final String PRODUCT_REMOVED_FROM_CART = g.toJson("Product was removed from cart");
    public static final String PRODUCT_REMOVED = g.toJson("Product was successfully removed");

    public static final String CART_EMPTY_SUCCESS = g.toJson("All products are successfully removed from cart");
    public static final String CART_ALREADY_EMPTY = g.toJson("Your cart is already empty");

    /* Negative messages */

    public static final String WRONG_USERNAME_OR_PASSWORD = g.toJson("Wrong username or password");
    public static final String CONFIRMED_PASSWORD_NOT_MATCH = g.toJson("Confirmed password doesn't match");
    public static final String USERNAME_ALREADY_IN_USE = g.toJson("This username is already in use");

    public static final String PRODUCT_NOT_FOUND = g.toJson("No such a product with this id");
    public static final String PRODUCTS_NOT_FOUND = g.toJson("No products found");
    public static final String PRODUCT_ALREADY_EXISTS = g.toJson("Product with the same name already exists");

    public static final String NO_SUCH_PRODUCT_IN_CART = g.toJson("No such a product in cart");
    public static final String QUANTITY_MORE_THAN_AVAILABLE = g.toJson("You are trying to decrease with more then the available quantity");
    public static final String CART_NOT_FOUND = g.toJson("Cart not found");

}
