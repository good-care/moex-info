package com.mokhovav.goodcare_moex_info.entites;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "goodcare_accounts")
public class Account extends AccountData {
    private String login;
    private String password;

    public Account() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
