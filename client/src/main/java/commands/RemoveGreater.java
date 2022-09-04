package commands;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.Optional;

public class RemoveGreater extends ClientCommand {

    public RemoveGreater(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println(CommandsEnum.REMOVE_GREATER + ": " + RB.getString("badCmd"));
            return Optional.empty();
        }
        return clientReceiver.removeGreater();
    }
}
