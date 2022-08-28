package commands;

import client.ClientReceiver;
import listening.Request;

public class FilterStartsWithName extends ClientCommand {

    @Override
    public Request execute(String arg) {
        if (arg == null) {
            System.out.println("Команда filter_starts_with_name принимает требует аргумент name.");
            return null;
        }
        return new Request("filter_starts_with_name", arg);
    }
}
