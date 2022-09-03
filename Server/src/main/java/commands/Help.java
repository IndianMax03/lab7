package commands;

import command.CommandsEnum;
import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.HashMap;
import java.util.Optional;

public class Help extends ServerCommand {
    private final HashMap<String, ServerCommand> commandMap;

    public Help(ServerReceiver serverReceiver, HashMap<String, ServerCommand> commandMap) {
        super(serverReceiver);
        this.commandMap = commandMap;
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.help(commandMap));
    }

    @Override
    public String getHelp() {
        return CommandsEnum.HELP.title + " : " + RB.getString("help");
    }

}
