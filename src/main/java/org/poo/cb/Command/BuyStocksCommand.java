package org.poo.cb.Command;

import org.poo.cb.CentralApp;

public class BuyStocksCommand implements Command {
    private final CentralApp app;

    public BuyStocksCommand(CentralApp app) {
        this.app = app;
    }

    public void execute(String[] args) {
        app.buyStocks(args[2], args[3], Integer.parseInt(args[4]));
    }
}
