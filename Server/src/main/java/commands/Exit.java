package commands;

import listening.Request;
import listening.Response;

import java.util.Optional;

public class Exit extends ServerCommand {
    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.empty();
    }

    @Override
    public String getHelp() {
        return "Введите exit, чтобы завершить программу (без сохранения в файл).";
    }
}
