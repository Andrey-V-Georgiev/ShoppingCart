package com.shopping_cart.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.shopping_cart.constants.EntityMsgConstants.*;

@Entity
@Table(name = "black_tokens")
public class BlackToken extends BaseEntity {

    private String userId;
    private String token;
    private LocalDateTime addDate;

    public BlackToken() {
    }

    public BlackToken(String userId, String token, LocalDateTime addDate) {
        this.userId = userId;
        this.token = token;
        this.addDate = addDate;
    }

    @Column(name = "user_id", nullable = false)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @NotEmpty(message = TOKEN_NOT_EMPTY)
    @NotNull(message = TOKEN_NOT_NULL)
    @Length(min = 3, message =  TOKEN_LENGTH)
    @Column(name = "token", columnDefinition="TEXT")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }
}
