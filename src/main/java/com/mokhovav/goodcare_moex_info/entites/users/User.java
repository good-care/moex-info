package com.mokhovav.goodcare_moex_info.entites.users;

import com.mokhovav.goodcare_moex_info.entites.Portfolio;
import com.mokhovav.goodcare_moex_info.entites.SimplyEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "goodcare_users")
public class User extends SimplyEntity {
    @Column(unique = true)
    private String login;
    private String password;
    @Column(unique = true)
    private String email;
    @Column(name = "registered")
    private Timestamp registered;
    private UserStatus status;
    @OneToOne(targetEntity = UserData.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_data_id", referencedColumnName = "id")
    private UserData userData;
    @OneToOne(targetEntity = UserToken.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "token_id", referencedColumnName = "id")
    private UserToken token;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Portfolio> portfolios;

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getRegistered() {
        return registered;
    }

    public void setRegistered(Timestamp registered) {
        this.registered = registered;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public List<Portfolio> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(List<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }

    public UserToken getToken() {
        return token;
    }

    public void setToken(UserToken token) {
        this.token = token;
    }
}
