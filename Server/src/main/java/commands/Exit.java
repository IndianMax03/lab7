package commands;

import listening.Request;
import listening.Response;

public class Exit extends ServerCommand {
    @Override
    public Response execute(Request arg) {
        return null;
    }

    @Override
    public String getHelp() {
        return "Введите exit, чтобы завершить программу (без сохранения в файл).";
    }
}
