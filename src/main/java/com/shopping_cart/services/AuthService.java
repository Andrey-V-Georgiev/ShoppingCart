package com.shopping_cart.services;

import com.shopping_cart.enums.UserRole;
import com.shopping_cart.models.service_models.BlackTokenServiceModel;

public interface AuthService {

    String createJwtToken(String userId, UserRole userRole);

    Boolean blackTokenExist(String token);

    BlackTokenServiceModel createBlackToken(String userId, String token);
}
