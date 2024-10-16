package org.poo.cb.Command;

import org.poo.cb.CentralApp;

public class AddMoneyCommand implements Command {
    private final CentralApp app;

    public AddMoneyCommand(CentralApp app) {
        this.app = app;
    }

    public void execute(String[] data) {
        app.addMoney(data[2], data[3], Integer.parseInt(data[4]));
    }
}
