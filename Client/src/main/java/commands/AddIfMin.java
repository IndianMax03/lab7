package commands;

import client.ClientReceiver;
import listening.Request;

public class AddIfMin extends ClientCommand {

    private final ClientReceiver clientReceiver;

    public AddIfMin(ClientReceiver clientReceiver) {
        this.clientReceiver = clientReceiver;
    }

    @Override
    public Request execute(String arg) {
        if (arg != null) {
            System.out.println("Команда add_if_min не принимает аргументы.");
            return null;
        }
        return clientReceiver.addIfMin();
    }
}
