package commands;

import command.CommandsEnum;
import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class FilterStartsWithName extends ServerCommand {

    public FilterStartsWithName(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.filterStartsWithName(arg.getArgument()));
    }

    @Override
    public String getHelp() {
        return CommandsEnum.FILTER_STARTS_WITH_NAME.title + " name : " + RB.getString("filterSWN");
    }
}
