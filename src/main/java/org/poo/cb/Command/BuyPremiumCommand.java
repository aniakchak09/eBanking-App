package org.poo.cb.Command;

import org.poo.cb.CentralApp;

public class BuyPremiumCommand implements Command {
    private final CentralApp app;

    public BuyPremiumCommand(CentralApp app) {
        this.app = app;
    }

    public void execute(String[] data) {
        app.buyPremium(data[2]);
    }
}
