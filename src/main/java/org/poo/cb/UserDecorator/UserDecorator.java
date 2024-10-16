package org.poo.cb.UserDecorator;

import org.poo.cb.*;

import java.util.ArrayList;

public abstract class UserDecorator implements User {
    protected User decoratedUser;

    public UserDecorator(User decoratedUser) {
        this.decoratedUser = decoratedUser;
    }

    public String getEmail() {
        return decoratedUser.getEmail();
    }

    public String getLastName() {
        return decoratedUser.getLastName();
    }

    public String getFirstName() {
        return decoratedUser.getFirstName();
    }

    public String getAdress() {
        return decoratedUser.getAdress();
    }

    public ArrayList<Portfolio> getPortfolio() {
        return decoratedUser.getPortfolio();
    }

    public ArrayList<User> getFriends() {
        return null;
    }
}
