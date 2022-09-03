package commands;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.Optional;

public class RemoveLower extends ClientCommand {

    public RemoveLower(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println(CommandsEnum.REMOVE_LOWER + ": " + RB.getString("badCmd"));
            return Optional.empty();
        }
        return clientReceiver.removeLower();
    }
}
