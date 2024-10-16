package org.poo.cb;

import java.util.ArrayList;

public class StockMarket extends Portfolio {
    private final String companyName;
    private final ArrayList<Double> lastTenDaysValues;
    private int amount;

    public String getCompanyName() {
        return companyName;
    }

    public ArrayList<Double> getLastTenDaysValues() {
        return lastTenDaysValues;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public StockMarket(String companyName, ArrayList<Double> lastTenDaysValues) {
        this.companyName = companyName;
        this.lastTenDaysValues = lastTenDaysValues;
        this.amount = 0;
    }

    public boolean isStock() {
        return true;
    }

    public String list() {
        return "{\"stockName\":\"" + companyName + "\",\"amount\":" + amount + "},";
    }
}
