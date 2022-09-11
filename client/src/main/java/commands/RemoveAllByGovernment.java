package commands;

import base.Government;
import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.Optional;

public class RemoveAllByGovernment extends ClientCommand {
    public RemoveAllByGovernment(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg == null) {
            System.out.println(CommandsEnum.REMOVE_ALL_BY_GOVERNMENT.title + ": " + RB.getString("needArg"));
            byte i = 1;
            for (Government government : Government.values()) {
                System.out.println(i + ") " + government.toString());
            }
            return Optional.empty();
        }
        return clientReceiver.removeAllByGovernment(arg);
    }
}
