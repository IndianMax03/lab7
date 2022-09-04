package commands;

import command.CommandsEnum;
import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class ExecuteScript extends ServerCommand {
    public ExecuteScript(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.empty();
    }

    @Override
    public String getHelp() {
        return CommandsEnum.EXECUTE_SCRIPT.title + " filename : " + RB.getString("execScr");
    }
}
