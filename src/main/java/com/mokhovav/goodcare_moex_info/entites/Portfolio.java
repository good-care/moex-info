package com.mokhovav.goodcare_moex_info.entites;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "goodcare_portfolios")
public class Portfolio extends SimplyNameEntity {
    private Currency currency;
    @ManyToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
