package com.shopping_cart.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.shopping_cart.constants.EntityMsgConstants.*;

@Entity
@Table(name = "black_tokens")
public class BlackToken extends BaseEntity {

    private User user;
    private String token;
    private LocalDateTime addedOn;

    public BlackToken() {
    }

    @ManyToOne(cascade= CascadeType.ALL)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @NotEmpty(message = TOKEN_NOT_EMPTY)
    @NotNull(message = TOKEN_NOT_NULL)
    @Length(min = 3, message = TOKEN_LENGTH)
    @Column(name = "token", columnDefinition="TEXT")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(name = "added_on", nullable = false)
    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }
}
