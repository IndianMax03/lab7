package commandButtons;

import client.ClientReceiver;
import command.CommandsEnum;

public class RemoveGreater extends ClientButton{

	public RemoveGreater(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.REMOVE_GREATER.title);
	}

}
