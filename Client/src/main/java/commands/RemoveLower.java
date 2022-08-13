package commands;

import client.ClientReceiver;
import listening.Request;

public class RemoveLower extends ClientCommand{

	private final ClientReceiver clientReceiver;

	public RemoveLower(ClientReceiver clientReceiver) {
		this.clientReceiver = clientReceiver;
	}

	@Override
	public Request execute(String arg) {
		if (arg != null){
			System.out.println("Команда remove_lower не принимает аргумент.");
			return null;
		}
		return clientReceiver.removeLower();
	}
}
