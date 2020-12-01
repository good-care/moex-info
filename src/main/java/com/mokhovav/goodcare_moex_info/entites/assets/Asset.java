package com.mokhovav.goodcare_moex_info.entites;

import javax.persistence.*;

@Entity
@Table(name = "goodcare_assets")
public class Asset extends SimplyEntity{
    @Column(name = "security_id")
    private String securityId;
    @ManyToOne(targetEntity = Currency.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id")
    private Currency currency;
    @ManyToOne(targetEntity = Issuer.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "issuer_id")
    private Issuer issuer;
    @Column(name = "is_trade")
    private boolean isTrade;
    @ManyToOne(targetEntity = AssetType.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "asset_type_id")
    private AssetType assetType;

    public Asset() {
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Issuer getIssuer() {
        return issuer;
    }

    public void setIssuer(Issuer issuer) {
        this.issuer = issuer;
    }

    public boolean isTrade() {
        return isTrade;
    }

    public void setTrade(boolean trade) {
        isTrade = trade;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }
}
