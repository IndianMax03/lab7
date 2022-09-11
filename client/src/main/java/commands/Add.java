package commands;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.Optional;

public class Add extends ClientCommand {

    public Add(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println(CommandsEnum.ADD.title + ": " + RB.getString("badCmd"));
            return Optional.empty();
        }
        return clientReceiver.add();
    }
}
