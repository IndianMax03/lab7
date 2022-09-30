package commandButtons;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class Help extends ClientButton{

	public Help(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.HELP.title);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Optional<Request> request = clientReceiver.help();
				notifyCommandListeners(request);
			}
		});
	}

}
