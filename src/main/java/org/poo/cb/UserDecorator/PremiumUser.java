package org.poo.cb.UserDecorator;

import org.poo.cb.*;
import org.poo.cb.Exchange.ExchangeStrategy;

import java.util.ArrayList;

public class PremiumUser extends UserDecorator {
    public PremiumUser(User decoratedUser) {
        super(decoratedUser);
    }

    @Override
    public ArrayList<Portfolio> getPortfolio() {
        return super.getPortfolio();
    }

    public ArrayList<User> getFriends() {
        return decoratedUser.getFriends();
    }

    public String printFriends() {
        return decoratedUser.printFriends();
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

        account.setBalance((float) (account.getBalance() - amount * 0.95f * stock.getLastTenDaysValues().get(9)));  //pays 5% less than the current value
        stock.setAmount(stock.getAmount() + (int) amount);

        if (stock.getAmount() == amount)
            user.getPortfolio().add(stock);
    }
}
