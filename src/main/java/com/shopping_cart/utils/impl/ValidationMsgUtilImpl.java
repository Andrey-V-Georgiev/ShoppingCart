package com.shopping_cart.utils.impl;

import com.shopping_cart.utils.ValidationMsgUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import java.util.List;

@Component
public class ValidationMsgUtilImpl implements ValidationMsgUtil {

    @Override
    public String parse(List<ObjectError> validationErrors) {

        StringBuilder sb = new StringBuilder();
        for (ObjectError validationErr : validationErrors) {
            sb.append(validationErr.getDefaultMessage()).append(", ");
        }
        String rawMsg = sb.toString();

        if (rawMsg.length() > 79) {
            String shortMsg = sb.substring(0, 80);
            return String.format("Errors: %s ...", shortMsg);
        } else {
            String fullMsg = sb.substring(0, rawMsg.length() - 2);
            return String.format("Errors: %s", fullMsg);
        }
    }
}
