package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

public class RemoveGreater extends ServerCommand{

	private final ServerReceiver serverReceiver;

	public RemoveGreater(ServerReceiver serverReceiver){
		this.serverReceiver = serverReceiver;
	}

	@Override
	public Response execute(Request arg) {
		return serverReceiver.removeGreater(arg.getCity(), arg.getLogin());
	}

	@Override
	public String getHelp() {
		return "Введите remove_greater {element}, чтобы удалить из коллекции все элементы, превышающие заданный";
	}
}
