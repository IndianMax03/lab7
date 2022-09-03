package commands;

import command.CommandsEnum;
import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class PrintDescending extends ServerCommand {

    public PrintDescending(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.printDescending());
    }

    @Override
    public String getHelp() {
        return CommandsEnum.PRINT_DESCENDING.title + " : " + RB.getString("printDesc");
    }
}
