package org.poo.cb.Command;

import org.poo.cb.CentralApp;

public class RecommendStocksCommand implements Command {
    private final CentralApp app;

    public RecommendStocksCommand(CentralApp app) {
        this.app = app;
    }

    public void execute(String[] args) {
        app.recommendStocks();
    }
}
