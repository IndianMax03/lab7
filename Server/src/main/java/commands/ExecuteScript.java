package commands;

import listening.Request;
import listening.Response;

public class ExecuteScript extends ServerCommand {
    @Override
    public Response execute(Request arg) {
        return null;
    }

    @Override
    public String getHelp() {
        return "Введите execute_script file_name, чтобы считать и исполнить скрипт из указанного файла.";
    }
}
