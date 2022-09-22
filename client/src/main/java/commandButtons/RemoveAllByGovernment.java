package commandButtons;

import client.ClientReceiver;
import command.CommandsEnum;

public class RemoveAllByGovernment extends ClientButton{

	public RemoveAllByGovernment(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.REMOVE_ALL_BY_GOVERNMENT.title);
	}

}
