package com.shopping_cart.constants;

public class ResponseMsgConstants {

    /* Positive messages */

    public static final String REGISTER_SUCCESS = "Your registration is successful";
    public static final String LOGOUT_SUCCESS = "You are successfully signed out";

    public static final String PRODUCT_QUANTITY_DECREASED = "Quantity of this product was decreased";
    public static final String PRODUCT_REMOVED_FROM_CART = "Product was removed from cart";
    public static final String PRODUCT_REMOVED = "Product was successfully removed";

    /* Negative messages */

    public static final String WRONG_USERNAME_OR_PASSWORD = "Wrong username or password";
    public static final String CONFIRMED_PASSWORD_NOT_MATCH = "Confirmed password doesn't match";
    public static final String USERNAME_ALREADY_IN_USE = "This username is already in use";

    public static final String PRODUCT_NOT_FOUND = "No such a product with this id";
    public static final String PRODUCTS_NOT_FOUND = "No products found";
    public static final String PRODUCT_ALREADY_EXISTS = "Product with the same name already exists";

    public static final String NO_SUCH_PRODUCT_IN_CART = "No such a product in cart";
    public static final String QUANTITY_MORE_THAN_AVAILABLE = "You are trying to decrease with more then the available quantity";
    public static final String CART_NOT_FOUND = "Cart not found";

}
