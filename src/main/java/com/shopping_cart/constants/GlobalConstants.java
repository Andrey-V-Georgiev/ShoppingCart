package com.shopping_cart.constants;

public class GlobalConstants {

    /* Auth */

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_PREFIX = "Bearer ";
    public static final String SECRET_KEY = "SuperSecretKey";
    public static final Long TOKEN_EXPIRY_TIME = 6000000L;
    public static final String CLAIMS_JTI = "jti";
    public static final String CLAIMS_AUTHORITIES = "authorities";

    /* Response messages positive */

    public static final String REGISTER_SUCCESS = "Your registration is successful";
    public static final String LOGOUT_SUCCESS = "You are successfully signed out";

    /* Response messages negative */

    public static final String WRONG_USERNAME_OR_PASSWORD = "Wrong username or password";
    public static final String CONFIRMED_PASSWORD_NOT_MATCH = "Confirmed password doesn't match";
    public static final String USERNAME_ALREADY_IN_USE = "This username is already in use";


}
