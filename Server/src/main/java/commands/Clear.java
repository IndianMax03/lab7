package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

public class Clear extends ServerCommand{

	private final ServerReceiver serverReceiver;

	public Clear(ServerReceiver serverReceiver){
		this.serverReceiver = serverReceiver;
	}

	@Override
	public Response execute(Request arg) {
		return serverReceiver.clear(arg.getLogin());
	}

	public String getHelp(){
		return "Введите clear, чтобы очистить коллекцию";
	}
}
