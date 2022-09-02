package commands;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.Optional;

public class Clear extends ClientCommand {

    public Clear(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println(CommandsEnum.CLEAR.title + ": " + RB.getString("badCmd"));
            return Optional.empty();
        }
        return Optional.of(new Request("clear"));
    }

}
