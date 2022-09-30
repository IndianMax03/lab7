package commandButtons;

import client.ClientReceiver;
import command.CommandsEnum;

public class ExecuteScript extends ClientButton{

	public ExecuteScript(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.EXECUTE_SCRIPT.title);
	}

}
