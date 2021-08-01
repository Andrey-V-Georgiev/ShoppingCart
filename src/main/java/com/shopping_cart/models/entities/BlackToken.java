package com.shopping_cart.models.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static com.shopping_cart.constants.ModelsMsgConstants.*;

@Entity
@Table(name = "black_tokens")
public class BlackToken extends BaseEntity {

    private String userId;
    private String token;
    private LocalDateTime addedOn;

    public BlackToken() {
    }

    @NotEmpty(message = ID_NOT_EMPTY)
    @NotNull(message = ID_NOT_NULL)
    @Size(min = 3, message = ID_LENGTH)
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @NotEmpty(message = TOKEN_NOT_EMPTY)
    @NotNull(message = TOKEN_NOT_NULL)
    @Size(min = 3, message = TOKEN_LENGTH)
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
