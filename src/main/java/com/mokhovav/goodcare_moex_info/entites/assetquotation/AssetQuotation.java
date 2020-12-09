package com.mokhovav.goodcare_moex_info.entites.assetquotation;

import com.mokhovav.goodcare_moex_info.entites.QuotationType;
import com.mokhovav.goodcare_moex_info.entites.SimplyEntity;
import com.mokhovav.goodcare_moex_info.entites.assets.Asset;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "goodcare_assets_quotations")
public class AssetQuotation extends SimplyEntity {

    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "moex_asset_id")
    private Asset moexAsset;
    @Column(precision = 19, scale = 6)
    private BigDecimal quotation;   //BigDecimal is required for currencies
    @Column(name = "date_time")
    private Timestamp dateAndTime;
    private QuotationType quotationType;

    public AssetQuotation() {
    }

    public Asset getMoexAsset() {
        return moexAsset;
    }

    public void setMoexAsset(Asset moexAsset) {
        this.moexAsset = moexAsset;
    }

    public BigDecimal getQuotation() {
        return quotation;
    }

    public void setQuotation(BigDecimal quotation) {
        this.quotation = quotation;
    }

    public Timestamp getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Timestamp dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public QuotationType getQuotationType() {
        return quotationType;
    }

    public void setQuotationType(QuotationType quotationType) {
        this.quotationType = quotationType;
    }
}
