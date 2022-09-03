package commands;

import base.City;
import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.Optional;

public class RemoveById extends ClientCommand {

    public RemoveById(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg == null) {
            System.out.println(CommandsEnum.REMOVE_BY_ID.title + ": " + RB.getString("needArg"));
            System.out.println("id > " + City.getLimitation().get("id"));
            return Optional.empty();
        }
        return clientReceiver.removeById(arg);
    }
}
