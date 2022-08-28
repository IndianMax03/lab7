package commands;

import client.ClientReceiver;
import listening.Request;

public class Add extends ClientCommand {

    private final ClientReceiver clientReceiver;

    public Add(ClientReceiver clientReceiver) {
        this.clientReceiver = clientReceiver;
    }

    @Override
    public Request execute(String arg) {
        if (arg != null) {
            System.out.println("Команда add не принимает аргументы.");
            return null;
        }
        return clientReceiver.add();
    }
}
