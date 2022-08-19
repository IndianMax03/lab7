package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

public class RemoveById extends ServerCommand{

	private final ServerReceiver serverReceiver;

	public RemoveById(ServerReceiver serverReceiver){
		this.serverReceiver = serverReceiver;
	}

	@Override
	public Response execute(Request arg) {
		return serverReceiver.removeById(arg.getArgument(), arg.getLogin());
	}
	@Override
	public String getHelp() {
		return "Введите remove_by_id id, чтобы удалить элемент из коллекции по его id";
	}
}
