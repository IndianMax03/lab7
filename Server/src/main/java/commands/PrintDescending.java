package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

public class PrintDescending extends ServerCommand{

	private final ServerReceiver serverReceiver;

	public PrintDescending(ServerReceiver serverReceiver){
		this.serverReceiver = serverReceiver;
	}

	@Override
	public Response execute(Request arg) {
		return serverReceiver.printDescending();
	}

	@Override
	public String getHelp() {
			return "Введите print_descending, чтобы вывести элементы коллекции в порядке убывания";
	}
}
