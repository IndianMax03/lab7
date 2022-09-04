package commands;

import command.AbstractCommand;
import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.ResourceBundle;

public abstract class ServerCommand extends AbstractCommand<Request, Response> {

    protected final ResourceBundle RB = ResourceBundle.getBundle("commands");
    protected ServerReceiver serverReceiver;

    public ServerCommand(ServerReceiver serverReceiver) {
        this.serverReceiver = serverReceiver;
    }

}
