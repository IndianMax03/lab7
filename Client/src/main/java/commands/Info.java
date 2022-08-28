package commands;

import listening.Request;

public class Info extends ClientCommand {
    @Override
    public Request execute(String arg) {
        if (arg != null) {
            System.out.println("Команда info не принимает аргументы.");
            return null;
        }
        return new Request("info");
    }
}
