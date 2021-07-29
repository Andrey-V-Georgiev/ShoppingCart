package com.shopping_cart.services;

import com.shopping_cart.models.binding_models.UserRegisterBindingModel;
import com.shopping_cart.models.service_models.UserServiceModel;

public interface UserService {

    UserServiceModel registerUser(UserRegisterBindingModel userRegisterBindingModel);

    UserServiceModel findUserByUsernameAndPassword(String username, String password);

    void updateUserToken(String newNakedToken, String userId);

    UserServiceModel findUserById(String userId);
}
