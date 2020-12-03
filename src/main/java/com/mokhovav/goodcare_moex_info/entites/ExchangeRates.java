package com.mokhovav.goodcare_moex_info.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "goodcare_exchange_rates_usd")
public class ExchangeRates extends SimplyEntity {
    @Column(name = "date_time")
    private Timestamp dateAndTime;
    @Column(precision = 19, scale = 6)
    private BigDecimal RUB;
    @Column(precision = 19, scale = 6)
    private BigDecimal EUR;

    public ExchangeRates() {
    }


    public Timestamp getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Timestamp dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public BigDecimal getRUB() {
        return RUB;
    }

    public void setRUB(BigDecimal RUB) {
        this.RUB = RUB;
    }

    public BigDecimal getEUR() {
        return EUR;
    }

    public void setEUR(BigDecimal EUR) {
        this.EUR = EUR;
    }
}
