package commands;

import client.ClientReceiver;
import listening.Request;

public class Update extends ClientCommand {

	private final ClientReceiver clientReceiver;

	public Update(ClientReceiver clientReceiver) {
		this.clientReceiver = clientReceiver;
	}

	@Override
	public Request execute(String arg) {
		if (arg == null){
			System.out.println("Команда update_id требует аргумент id типа long.");
			System.out.println("id не может быть отрицательным числом или нулём.");
			return null;
		}
		return clientReceiver.update(arg);
	}
}
