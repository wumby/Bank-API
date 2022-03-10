package com.revature.model;

import java.util.Objects;


//Create our account
public class Account {
    private int id;
    private String account_name;
    private double balance;
    private int client_id;

    public Account(int id, String account_name, double balance, int client_id) {
        this.id = id;
        this.account_name = account_name;
        this.balance = balance;
        this.client_id = client_id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && Double.compare(account.balance, balance) == 0 && client_id == account.client_id && account_name.equals(account.account_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account_name, balance, client_id);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                "account_name='" + account_name + '\'' +
                ", balance=" + balance +
                ", client_id=" + client_id +
                '}';
    }
}
