package org.poo.cb.Command;

import org.poo.cb.*;

public class ListUserCommand implements Command {
    private final CentralApp app;

    public ListUserCommand(CentralApp app) {
        this.app = app;
    }

    public void execute(String[] data) {
        app.listUser(data[2]);
    }
}
