package com.shopping_cart.constants;

public class ModelsMsgConstants {

    /* CartProduct */

    public static final String ORDERED_QUANTITY_MUST_BE_AT_LEAST_ONE = "Ordered quantity must be at least one";
    public static final String ORDERED_QUANTITY_NOT_NULL = "Ordered quantity cannot be null";

    /* Product */

    public static final String PRODUCT_NAME_LENGTH = "Product name must be between 3 and 50 symbols";
    public static final String PRODUCT_NAME_NOT_EMPTY = "Product name cannot be empty field";
    public static final String PRODUCT_NAME_NOT_NULL = "Product name cannot be null";

    public static final String PRODUCT_DESCRIPTION_LENGTH = "Product description must be between 3 and 500 symbols";
    public static final String PRODUCT_DESCRIPTION_NOT_EMPTY = "Product description cannot be empty field";
    public static final String PRODUCT_DESCRIPTION_NOT_NULL = "Product description cannot be null";

    public static final String PRODUCT_PRICE = "Product price must be at least one";
    public static final String PRODUCT_PRICE_NOT_NULL = "Product price cannot be null";

    /* User */

    public static final String USERNAME_NOT_EMPTY = "Username name cannot be empty field";
    public static final String USERNAME_NOT_NULL = "Username name cannot be null";
    public static final String USERNAME_LENGTH = "Username must be between 3 and 50 symbols";

    public static final String USER_EMAIL_NOT_EMPTY = "User email cannot be empty field";
    public static final String USER_EMAIL_NOT_NULL = "User email cannot be null";
    public static final String USER_EMAIL_REGEX_MSG = "Not valid email";
    public static final String USER_EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+\\/=?`{|}~^.-]{3,}@[a-zA-Z0-9.-]{3,}\\.[a-zA-Z0-9.-]{2,}$";

    public static final String PASSWORD_NOT_EMPTY = "Password cannot be empty field";
    public static final String PASSWORD_NOT_NULL = "Password cannot be null";
    public static final String PASSWORD_LENGTH = "Password must be at least 3 symbols";

    public static final String TOKEN_NOT_EMPTY = "Token cannot be empty field";
    public static final String TOKEN_NOT_NULL = "Token cannot be null";
    public static final String TOKEN_LENGTH = "Token must be at least 3 symbols";

    public static final String USER_ROLE_NOT_EMPTY = "User role cannot be empty field";
    public static final String USER_ROLE_NOT_NULL = "User role cannot be null";
    public static final String USER_ROLE_REGEX_MSG = "Not valid user role";
    public static final String USER_ROLE_REGEX = "^(ROLE_ADMIN||ROLE_USER)$";

}
