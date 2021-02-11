package com.mokhovav.goodcare_moex_info.entites.users;

import com.mokhovav.goodcare_moex_info.entites.SimplyEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "goodcare_user_tokens")
public class UserToken extends SimplyEntity {
    @OneToOne(mappedBy = "token")
    private User user;
    @Column(length = 511)
    private String token;
    private Timestamp expires;

    public UserToken() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getExpires() {
        return expires;
    }

    public void setExpires(Timestamp expires) {
        this.expires = expires;
    }
}
