package commands;

import listening.Request;

public class Help extends ClientCommand {
    @Override
    public Request execute(String arg) {
        if (arg != null) {
            System.out.println("Команда help не принимает аргументы.");
            return null;
        }
        return new Request("help");
    }
}
