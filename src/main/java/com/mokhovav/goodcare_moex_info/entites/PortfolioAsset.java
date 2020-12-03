package com.mokhovav.goodcare_moex_info.entites;

import com.mokhovav.goodcare_moex_info.entites.assets.Asset;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "goodcare_portfolios_assets")
public class PortfolioAsset extends SimplyNameEntity {
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "moex_asset_id")
    private Asset moexAsset;
    @ManyToOne(targetEntity = Portfolio.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    @Column(precision = 19, scale = 6)
    private BigDecimal number;   //BigDecimal is required for currencies
    @Column(precision = 19, scale = 6)
    private BigDecimal cost;     //BigDecimal is required for currencies
    @Column(precision = 19, scale = 6)
    private BigDecimal quotation;

    public PortfolioAsset() {
    }

    public Asset getMoexAsset() {
        return moexAsset;
    }

    public void setMoexAsset(Asset moexAsset) {
        this.moexAsset = moexAsset;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
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

    public BigDecimal getQuotation() {
        return quotation;
    }

    public void setQuotation(BigDecimal quotation) {
        this.quotation = quotation;
    }
}
