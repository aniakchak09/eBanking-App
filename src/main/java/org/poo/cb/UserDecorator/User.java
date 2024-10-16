package org.poo.cb.UserDecorator;

import org.poo.cb.*;

import java.util.ArrayList;

public interface User {
    String getEmail();

    String getLastName();

    String getFirstName();

    String getAdress();

    ArrayList<Portfolio> getPortfolio();

    ArrayList<User> getFriends();

    String printFriends() ;

    void exchangeMoney(User user, String currency1, String currency2, float amount, CentralApp app);

    void buySomeStocks(User user, String symbol, float quantity, CentralApp app);
}
