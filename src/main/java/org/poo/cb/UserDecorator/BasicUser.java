package org.poo.cb.UserDecorator;

import org.poo.cb.Account;
import org.poo.cb.CentralApp;
import org.poo.cb.Exchange.ExchangeStrategy;
import org.poo.cb.Portfolio;
import org.poo.cb.StockMarket;

import java.util.ArrayList;

public class BasicUser implements User {
    private final String email;
    private final String lastName;
    private final String firstName;
    private String adress;
    private final ArrayList<Portfolio> portfolio;    //I will be trying to use generics here
    private final ArrayList<User> friends;

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getAdress() {
        return adress;
    }

    public ArrayList<Portfolio> getPortfolio() {
        return portfolio;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public BasicUser(String[] data) {
        this.email = data[2];
        this.lastName = data[4];
        this.firstName = data[3];
        this.adress = "";

        for (int i = 5; i < data.length; i++) {
            this.adress += " " + data[i];
        }

        this.portfolio = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    public String printFriends() {
        StringBuilder result = new StringBuilder();

        for(User friend : friends) {
            result.append("\"").append(friend.getEmail()).append("\"");
        }

        result.toString().replaceAll("\"\"", "\",\"");

        return result.toString();
    }

    public void exchangeMoney(User user, String currency1, String currency2, float amount, CentralApp app) {
        Account account1 = app.findAccount(user.getEmail(), currency1);
        Account account2 = app.findAccount(user.getEmail(), currency2);

        ExchangeStrategy strategy = app.exchangeStrategies.get(currency2 + "->" + currency1);

        float exchangedAmount = strategy.exchange(amount);

        if (exchangedAmount > account1.getBalance()) {
            System.out.println("Insufficient amount in account " + currency1 + " for exchange");
            return;
        }

        if (exchangedAmount > account1.getBalance() / 2)
            exchangedAmount += exchangedAmount * 0.01;

        account1.setBalance(account1.getBalance() - exchangedAmount);
        account2.setBalance(account2.getBalance() + amount);
    }

    public void buySomeStocks(User user, String companyName, float amount, CentralApp app) {
        StockMarket stock = app.findStock(companyName, user);

        Account account = app.findAccount(user.getEmail(), "USD");

        if (account == null)
            return;

        if (amount * stock.getLastTenDaysValues().get(9) > account.getBalance()) {
            System.out.println("Insufficient amount in account for buying stock");
            return;
        }

        account.setBalance((float) (account.getBalance() - amount * stock.getLastTenDaysValues().get(9)));
        stock.setAmount(stock.getAmount() + (int) amount);

        if (stock.getAmount() == amount)
            user.getPortfolio().add(stock);
    }
}
