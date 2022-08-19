package server;

import commands.*;
import commands.ServerCommand;
import listening.Request;
import listening.Response;

import java.util.HashMap;

public class ServerInvoker {

	private final HashMap<String, ServerCommand> commandMap = new HashMap<>();

	public void register(String commandName, ServerCommand command){
		commandMap.put(commandName, command);
	}

	public ServerInvoker(ServerReceiver serverReceiver){
		commandMap.put("help", new Help(serverReceiver, commandMap));
		commandMap.put("add", new Add(serverReceiver));
		commandMap.put("clear", new Clear(serverReceiver));
		commandMap.put("show", new Show(serverReceiver));
		commandMap.put("authorization", new Authorization(serverReceiver));
		commandMap.put("add_if_min", new AddIfMin(serverReceiver));
		commandMap.put("filter_starts_with_name", new FilterStartsWithName(serverReceiver));
		commandMap.put("info", new Info(serverReceiver));
		commandMap.put("print_descending", new PrintDescending(serverReceiver));
		commandMap.put("remove_all_by_government", new RemoveAllByGovernment(serverReceiver));
		commandMap.put("remove_by_id", new RemoveById(serverReceiver));
		/*commandMap.put("save", new Save(serverReceiver));
		commandMap.put("execute_script", new ExecuteScript(serverReceiver));
		commandMap.put("remove_greater", new RemoveGreater(serverReceiver));
		commandMap.put("remove_lower", new RemoveLower(serverReceiver));
		commandMap.put("update", new Update(serverReceiver));
		commandMap.put("exit", new Exit());*/
	}

	public Response execute(Request request){
		String commandName = request.getCommandName();
		return this.commandMap.get(commandName).execute(request);
	}

	public HashMap<String, ServerCommand> getCommandMap(){
		return this.commandMap;
	}

}
