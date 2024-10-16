package org.poo.cb.Command;

import org.poo.cb.CentralApp;

public class ExchangeMoneyCommand implements Command {
    private final CentralApp app;

    public ExchangeMoneyCommand(CentralApp app) {
        this.app = app;
    }

    public void execute(String[] data) {
        app.exchange(data[2], data[3], data[4], Float.parseFloat(data[5]));
    }
}
