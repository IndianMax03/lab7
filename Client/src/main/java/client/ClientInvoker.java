package client;

import commands.*;
import listening.Request;

import java.util.HashMap;
import java.util.Map;

public class ClientInvoker {

    private final Map<String, ClientCommand> commandMap = new HashMap<>();

    public ClientInvoker(ClientReceiver clientReceiver) {

        commandMap.put("add", new Add(clientReceiver));
        commandMap.put("add_if_min", new AddIfMin(clientReceiver));
        commandMap.put("clear", new Clear());
        commandMap.put("execute_script", new ExecuteScript());
        commandMap.put("exit", new Exit());
        commandMap.put("filter_starts_with_name", new FilterStartsWithName());
        commandMap.put("help", new Help());
        commandMap.put("info", new Info());
        commandMap.put("print_descending", new PrintDescending());
        commandMap.put("remove_all_by_government", new RemoveAllByGovernment(clientReceiver));
        commandMap.put("remove_by_id", new RemoveById(clientReceiver));
        commandMap.put("remove_greater", new RemoveGreater(clientReceiver));
        commandMap.put("remove_lower", new RemoveLower(clientReceiver));
        commandMap.put("show", new Show());
        commandMap.put("update", new Update(clientReceiver));
    }

    public Request check(String commandName, String argument) {
        if (this.commandMap.containsKey(commandName))
            return this.commandMap.get(commandName).execute(argument);
        System.out.println("Введённой команды не существует.");
        return null;
    }

}
