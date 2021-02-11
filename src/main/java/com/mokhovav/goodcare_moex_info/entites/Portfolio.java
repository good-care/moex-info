package com.mokhovav.goodcare_moex_info.entites;

import com.mokhovav.goodcare_moex_info.entites.users.User;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "goodcare_portfolios")
public class Portfolio extends SimplyNameEntity {
    private Currency currency;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(precision = 19, scale = 6)
    private BigDecimal cost;

    public Portfolio() {
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public User getAccount() {
        return user;
    }

    public void setAccount(User user) {
        this.user = user;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
