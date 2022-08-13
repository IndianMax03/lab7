package commands;

import client.ClientReceiver;
import listening.Request;

public class RemoveById extends ClientCommand {

	private final ClientReceiver clientReceiver;

	public RemoveById(ClientReceiver clientReceiver) {
		this.clientReceiver = clientReceiver;
	}


	@Override
	public Request execute(String arg) {
		if (arg == null){
			System.out.println("Команда remove_by_id требует аргумент id типа long.");
			System.out.println("id не может быть отрицательным числом или нулём.");
			return null;
		}
		return clientReceiver.removeById(arg);
	}
}
