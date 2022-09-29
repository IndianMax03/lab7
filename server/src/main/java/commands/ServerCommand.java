package commands;

import command.AbstractCommand;
import listening.Request;
import listening.Response;
import server.Main;
import server.ServerReceiver;

import java.util.ResourceBundle;

public abstract class ServerCommand extends AbstractCommand<Request, Response> {

    public static ResourceBundle RB;
    protected ServerReceiver serverReceiver;

    public ServerCommand(ServerReceiver serverReceiver) {
        this.serverReceiver = serverReceiver;
    }

}
