package commandButtons;

import client.ClientReceiver;
import command.CommandsEnum;

public class Update extends ClientButton{

	public Update(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.UPDATE.title);
	}

}
