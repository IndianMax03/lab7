package commands;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.Optional;

public class PrintDescending extends ClientCommand {
    public PrintDescending(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println(CommandsEnum.PRINT_DESCENDING.title + ": " + RB.getString("badCmd"));
            return Optional.empty();
        }
        return Optional.of(new Request("print_descending"));
    }
}
