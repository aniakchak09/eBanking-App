package org.poo.cb.Command;

import org.poo.cb.CentralApp;

public class AddAccountCommand implements Command {
    private final CentralApp app;

    public AddAccountCommand(CentralApp app) {
        this.app = app;
    }

    public void execute(String[] data) {
        app.addAccount(data[2], data[3]);
    }
}
