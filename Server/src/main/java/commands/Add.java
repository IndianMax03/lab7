package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

public class Add extends ServerCommand{

	private final ServerReceiver serverReceiver;

	public Add(ServerReceiver serverReceiver){
		this.serverReceiver = serverReceiver;
	}


	@Override
	public Response execute(Request request){
		return serverReceiver.add(request.getCity(), request.getLogin());
	}

	@Override
	public String getHelp() {
		return "Введите add {element}, чтобы добавить новый элемент в коллекцию";
	}
}