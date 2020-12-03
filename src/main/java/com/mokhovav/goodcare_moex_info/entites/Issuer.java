package com.mokhovav.goodcare_moex_info.entites;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "goodcare_issuers")
public class Issuer extends SimplyNameEntity {
    public Issuer() {
    }
}
