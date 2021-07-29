package com.shopping_cart.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "blacktokens")
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "token", columnDefinition="TEXT", unique = true, nullable = false)
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
