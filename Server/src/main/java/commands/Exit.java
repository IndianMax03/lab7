package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class Exit extends ServerCommand {
    public Exit(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.empty();
    }

    @Override
    public String getHelp() {
        return "Введите exit, чтобы завершить программу (без сохранения в файл).";
    }
}
