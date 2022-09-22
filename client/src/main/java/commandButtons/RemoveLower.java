package commandButtons;

import client.ClientReceiver;
import command.CommandsEnum;

public class RemoveLower extends ClientButton{

	public RemoveLower(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.REMOVE_LOWER.title);
	}

}
