package org.poo.cb.Command;

import org.poo.cb.CentralApp;

public class ListPortfolioCommand implements Command {
    private final CentralApp app;

    public ListPortfolioCommand(CentralApp app) {
        this.app = app;
    }

    public void execute(String[] data) {
        app.listPortfolio(data[2]);
    }
}
