package com.shopping_cart.utils.impl;

import com.shopping_cart.utils.IdUtil;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdUtilImpl implements IdUtil {
    @Override
    public String generateID() {
        String id = UUID.randomUUID().toString();
        return id;
    }
}
