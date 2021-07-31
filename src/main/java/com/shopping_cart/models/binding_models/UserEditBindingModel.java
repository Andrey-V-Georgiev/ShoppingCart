package com.shopping_cart.models.binding_models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.shopping_cart.constants.ModelsMsgConstants.*;

public class UserEditBindingModel {

    private String username;
    private String email;
    private String password;

    public UserEditBindingModel() {
    }

    public UserEditBindingModel(String username, String email, String password) {
        this.username = username;
        this.email = email;
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

    @NotEmpty(message = USER_EMAIL_NOT_EMPTY)
    @NotNull(message = USER_EMAIL_NOT_NULL)
    @Pattern(regexp = USER_EMAIL_REGEX, message = USER_EMAIL_REGEX_MSG)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
