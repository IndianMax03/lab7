package commands;

import command.CommandsEnum;
import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class Show extends ServerCommand {

    public Show(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.show());
    }

    @Override
    public String getHelp() {
        return CommandsEnum.SHOW.title + " : " + RB.getString("show");
    }
}
