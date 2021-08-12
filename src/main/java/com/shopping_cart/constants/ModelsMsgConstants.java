package com.shopping_cart.constants;

public class ModelsMsgConstants {

    /* Id */

    public static final String ID_NOT_EMPTY = "Id cannot be empty";
    public static final String ID_NOT_NULL = "Id cannot be null";
    public static final String ID_LENGTH = "Id length must be at least 3 symbols";

    /* CartProduct */

    public static final String ORDERED_QUANTITY_CANNOT_BE_NEGATIVE = "Ordered quantity cannot be negative number";
    public static final String ORDERED_QUANTITY_NOT_NULL = "Ordered quantity cannot be null";

    /* Product */

    public static final String PRODUCT_NAME_LENGTH = "Product name must be between 3 and 50 symbols";
    public static final String PRODUCT_NAME_NOT_EMPTY = "Product name cannot be empty field";
    public static final String PRODUCT_NAME_NOT_NULL = "Product name cannot be null";

    public static final String PRODUCT_DESCRIPTION_LENGTH = "Product description must be at least 3 symbols";
    public static final String PRODUCT_DESCRIPTION_NOT_EMPTY = "Product description cannot be empty field";
    public static final String PRODUCT_DESCRIPTION_NOT_NULL = "Product description cannot be null";

    public static final String PRODUCT_PICTURE_URL_LENGTH = "Product picture url must be at least 3 symbols";
    public static final String PRODUCT_PICTURE_URL_NOT_EMPTY = "Product picture url cannot be empty field";
    public static final String PRODUCT_PICTURE_URL_NOT_NULL = "Product picture url cannot be null";

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

    /* Contact */

    public static final String CITY_NAME_NOT_EMPTY = "City name cannot be empty field";
    public static final String CITY_NAME_NOT_NULL = "City name cannot be null";
    public static final String CITY_NAME_LENGTH = "City name must be at least 3 symbols";

    public static final String IFRAME_URL_NOT_EMPTY = "Iframe url cannot be empty field";
    public static final String IFRAME_URL_NOT_NULL = "Iframe url cannot be null";
    public static final String IFRAME_URL_LENGTH = "Iframe url must be at least 3 symbols";

    public static final String CONTACT_ADDRESS_NOT_EMPTY = "Contact address cannot be empty field";
    public static final String CONTACT_ADDRESS_NOT_NULL = "Contact address cannot be null";
    public static final String CONTACT_ADDRESS_LENGTH = "Contact address must be at least 3 symbols";

    public static final String CONTACT_TEL_NOT_EMPTY = "Contact tel cannot be empty field";
    public static final String CONTACT_TEL_NOT_NULL = "Contact tel cannot be null";
    public static final String CONTACT_TEL_LENGTH = "Contact tel must be at least 3 symbols";

    public static final String CONTACT_EMAIL_NOT_EMPTY = "Contact email cannot be empty field";
    public static final String CONTACT_EMAIL_NOT_NULL = "Contact email cannot be null";
    public static final String CONTACT_EMAIL_REGEX_MSG = "Not valid email";

    public static final String WH_WEEK_NOT_EMPTY = "Working hours cannot be empty field";
    public static final String WH_WEEK_NOT_NULL = "Working hours cannot be null";
    public static final String WH_WEEK_LENGTH = "Working hours must be at least 3 symbols";

    public static final String WH_WEEKEND_NOT_EMPTY = "Weekend working hours cannot be empty field";
    public static final String WH_WEEKEND_NOT_NULL = "Weekend working hours cannot be null";
    public static final String WH_WEEKEND_LENGTH = "Weekend working hours must be at least 3 symbols";

}
