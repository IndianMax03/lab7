package commands;

import listening.Request;
import listening.Response;

import java.util.Optional;

public class ExecuteScript extends ServerCommand {
    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.empty();
    }

    @Override
    public String getHelp() {
        return "Введите execute_script file_name, чтобы считать и исполнить скрипт из указанного файла.";
    }
}
