package commands;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.Optional;

public class Help extends ClientCommand {
    public Help(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println(CommandsEnum.HELP.title + ": " + RB.getString("badCmd"));
            return Optional.empty();
        }
        return Optional.of(new Request("help"));
    }
}
