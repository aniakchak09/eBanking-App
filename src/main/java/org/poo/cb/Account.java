package org.poo.cb;

public class Account extends Portfolio {
    private final String curencyType;
    private float balance;

    public String getCurencyType() {
        return curencyType;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Account(String curencyType) {
        this.curencyType = curencyType;
        this.balance = 0;
    }

    public boolean isAccount() {
        return true;
    }
    public String list() {
        return  "{\"currencyName\":\"" + this.getCurencyType() + "\",\"amount\":\"" + String.format("%.2f", this.getBalance()) + "\"},";
    }
}
