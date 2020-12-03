package com.mokhovav.goodcare_moex_info.entites;

import com.mokhovav.goodcare_moex_info.entites.assets.Asset;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "goodcare_events")
public class Event extends SimplyEntity {

    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "moex_asset_id")
    private Asset moexAsset;
    private EventType eventType;
    @Column(precision = 19, scale = 6)
    private BigDecimal number;   //BigDecimal is required for currencies
    @Column(precision = 19, scale = 6)
    private BigDecimal cost;     //BigDecimal is required for currencies
    @ManyToOne(targetEntity = Portfolio.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    @Column(name = "date_time")
    private Timestamp dateAndTime;

    public Event() {
    }

    public Asset getMoexAsset() {
        return moexAsset;
    }

    public void setMoexAsset(Asset moexAsset) {
        this.moexAsset = moexAsset;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Timestamp getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Timestamp dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
