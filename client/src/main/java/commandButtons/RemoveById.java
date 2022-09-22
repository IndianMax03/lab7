package commandButtons;

import client.ClientReceiver;
import command.CommandsEnum;

public class RemoveById extends ClientButton{

	public RemoveById(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.REMOVE_BY_ID.title);
	}

}
