package commands;

import base.City;
import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.Optional;

public class Update extends ClientCommand {

    public Update(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg == null) {
            System.out.println(CommandsEnum.UPDATE.title + ": " + RB.getString("needArg"));
            System.out.println("id > " + City.getLimitation().get("id"));
            return Optional.empty();
        }
        return clientReceiver.update(arg);
    }
}
