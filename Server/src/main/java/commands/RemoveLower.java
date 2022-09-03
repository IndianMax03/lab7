package commands;

import command.CommandsEnum;
import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class RemoveLower extends ServerCommand {

    public RemoveLower(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.removeLower(arg.getCity(), arg.getLogin()));
    }

    @Override
    public String getHelp() {
        return CommandsEnum.REMOVE_LOWER.title + " {element} : " + RB.getString("removeLower");
    }
}
