package com.mokhovav.goodcare_moex_info.entites.users;

import com.mokhovav.goodcare_moex_info.entites.Currency;
import com.mokhovav.goodcare_moex_info.entites.SimplyEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "goodcare_user_data")
public class UserData extends SimplyEntity {

    @OneToOne (mappedBy = "userData")
    private User user;
    private Currency currency;
    @Column(precision = 19, scale = 6)
    private BigDecimal cost;
    private String name;

    public UserData() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
