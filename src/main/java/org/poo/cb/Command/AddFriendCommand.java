package org.poo.cb.Command;

import org.poo.cb.CentralApp;

public class AddFriendCommand implements Command {
    private final CentralApp app;

    public AddFriendCommand(CentralApp app) {
        this.app = app;
    }

    public void execute(String[] data) {
        app.addFriend(data[2], data[3]);
    }
}
