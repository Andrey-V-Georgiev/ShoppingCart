package com.shopping_cart.models.entities;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String username;
    private String email;
    private String password;
    private String token;
    private String role;
    private LocalDateTime registrationDate;

    public User() {
        super();
    }

    public User(String username, String email, String password, String token, String role, LocalDateTime registrationDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.token = token;
        this.role = role;
        this.registrationDate = registrationDate;
    }

    @Length(min = 3, max = 20, message = "Username must be between 3 an 20 symbols")
    @Column(name = "username", unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    @Column(name = "role", nullable = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime addDate) {
        this.registrationDate = addDate;
    }
}
