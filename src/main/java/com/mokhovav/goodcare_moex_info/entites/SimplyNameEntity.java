package com.mokhovav.goodcare_moex_info.entites;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SimplyNameEntity extends SimplyEntity {
    private String name;

    public SimplyNameEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
