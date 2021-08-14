package com.shopping_cart.utils;

import org.springframework.validation.ObjectError;

import java.util.List;

public interface ValidationMsgUtil {

    String parse(List<ObjectError> validationErrors);
}
