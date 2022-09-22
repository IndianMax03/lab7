package client;

import base.City;
import command.CommandsEnum;
import commandButtons.*;
import gui.util.CitiesTable;
import listening.Request;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientInvoker {

    private final Map<String, ClientButton> commandMap = new HashMap<>();

    private void register(String commandName, ClientButton commandButton) {
        commandMap.put(commandName, commandButton);
    }

    public ClientInvoker(ClientReceiver clientReceiver) {
        for (CommandsEnum command : CommandsEnum.values()) {
            Optional<ClientButton> optional = create(clientReceiver, command);
            optional.ifPresent(clientCommand -> register(command.title, clientCommand));
        }
    }

    public void execute(Request request, CitiesTable table, City city) {
        String commandName = request.getCommandName();
        ClientButton command = commandMap.get(commandName);
        command.executeCommand(request.getArgument(), city, table);
    }

    public Map<String, ClientButton> getCommandMap() {
        return this.commandMap;
    }

    private Optional<ClientButton> create(ClientReceiver clientReceiver, CommandsEnum command) {
        switch (command) {
            case ADD:
                return Optional.of(new Add(clientReceiver));
            case ADD_IF_MIN:
                return Optional.of(new AddIfMin(clientReceiver));
            case CLEAR:
                return Optional.of(new Clear(clientReceiver));
            case EXECUTE_SCRIPT:
                return Optional.of(new ExecuteScript(clientReceiver));
            case EXIT:
                return Optional.of(new Exit(clientReceiver));
            case FILTER_STARTS_WITH_NAME:
                return Optional.of(new FilterStartsWithName(clientReceiver));
            case HELP:
                return Optional.of(new Help(clientReceiver));
            case INFO:
                return Optional.of(new Info(clientReceiver));
            case REMOVE_ALL_BY_GOVERNMENT:
                return Optional.of(new RemoveAllByGovernment(clientReceiver));
            case REMOVE_BY_ID:
                return Optional.of(new RemoveById(clientReceiver));
            case REMOVE_GREATER:
                return Optional.of(new RemoveGreater(clientReceiver));
            case REMOVE_LOWER:
                return Optional.of(new RemoveLower(clientReceiver));
            case UPDATE:
                return Optional.of(new Update(clientReceiver));
            case PRINT_DESCENDING:
            case SHOW:
            case AUTHORIZATION:
            default:
                return Optional.empty();
        }
    }

}
