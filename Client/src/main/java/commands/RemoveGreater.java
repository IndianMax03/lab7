package commands;

import client.ClientReceiver;
import listening.Request;

public class RemoveGreater extends ClientCommand {

    private final ClientReceiver clientReceiver;

    public RemoveGreater(ClientReceiver clientReceiver) {
        this.clientReceiver = clientReceiver;
    }

    @Override
    public Request execute(String arg) {
        if (arg != null) {
            System.out.println("Команда remove_greater не принимает аргумент.");
            return null;
        }
        return clientReceiver.removeGreater();
    }
}
