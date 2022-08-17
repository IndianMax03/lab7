package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

public class Show extends ServerCommand{

	private final ServerReceiver serverReceiver;

	public Show(ServerReceiver serverReceiver){
		this.serverReceiver = serverReceiver;
	}


	@Override
	public Response execute(Request arg) {
		return serverReceiver.show();
	}

	@Override
	public String getHelp() {
		return "Введите clear, чтобы очистить коллекцию";
	}
}
