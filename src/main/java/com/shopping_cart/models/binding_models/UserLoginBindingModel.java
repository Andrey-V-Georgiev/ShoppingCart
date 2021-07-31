package com.shopping_cart.models.binding_models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.shopping_cart.constants.ModelsMsgConstants.*;

public class UserLoginBindingModel {

    private String username;
    private String password;

    public UserLoginBindingModel() {
    }

    public UserLoginBindingModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @NotEmpty(message = USERNAME_NOT_EMPTY)
    @NotNull(message = USERNAME_NOT_NULL)
    @Size(min = 3, max = 50, message = USERNAME_LENGTH)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = PASSWORD_NOT_EMPTY)
    @NotNull(message = PASSWORD_NOT_NULL)
    @Size(min = 3, message =  PASSWORD_LENGTH)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
