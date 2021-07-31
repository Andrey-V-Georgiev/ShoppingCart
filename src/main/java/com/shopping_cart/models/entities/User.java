package com.shopping_cart.models.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static com.shopping_cart.constants.ModelsMsgConstants.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String username;
    private Cart cart;
    private String email;
    private String password;
    private String token;
    private String role;
    private LocalDateTime registrationDate;

    public User() {
        super();
    }

    @OneToOne(targetEntity=Cart.class,cascade=CascadeType.ALL)
    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @NotEmpty(message = USERNAME_NOT_EMPTY)
    @NotNull(message = USERNAME_NOT_NULL)
    @Size(min = 3, max = 50, message = USERNAME_LENGTH)
    @Column(name = "username", unique = true )
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = USER_EMAIL_NOT_EMPTY)
    @NotNull(message = USER_EMAIL_NOT_NULL)
    @Pattern(regexp = USER_EMAIL_REGEX, message = USER_EMAIL_REGEX_MSG)
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = PASSWORD_NOT_EMPTY)
    @NotNull(message = PASSWORD_NOT_NULL)
    @Size(min = 3, message =  PASSWORD_LENGTH)
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "token", columnDefinition="TEXT")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @NotEmpty(message = USER_ROLE_NOT_EMPTY)
    @NotNull(message = USER_ROLE_NOT_NULL)
    @Pattern(regexp = USER_ROLE_REGEX, message = USER_ROLE_REGEX_MSG)
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(name = "registration_date", nullable = false)
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime addDate) {
        this.registrationDate = addDate;
    }
}
