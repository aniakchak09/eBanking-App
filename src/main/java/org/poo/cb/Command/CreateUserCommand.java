package org.poo.cb.Command;

import org.poo.cb.*;

public class CreateUserCommand implements Command {
    private final CentralApp app;

    public CreateUserCommand(CentralApp app) {
        this.app = app;
    }

    public void execute(String[] data) {
        app.createUser(data);
    }
}
