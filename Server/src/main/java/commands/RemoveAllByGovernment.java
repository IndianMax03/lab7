package commands;

import command.CommandsEnum;
import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class RemoveAllByGovernment extends ServerCommand {

    public RemoveAllByGovernment(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.removeAllByGovernment(arg.getArgument(), arg.getLogin()));
    }

    @Override
    public String getHelp() {
        return CommandsEnum.REMOVE_ALL_BY_GOVERNMENT.title + " government : " + RB.getString("removeByGov");
    }
}
