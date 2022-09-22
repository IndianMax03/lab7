package commandButtons;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class Exit extends ClientButton {

	public Exit(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.EXIT.title);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Optional<Request> request = clientReceiver.exit();
				notifyCommandListeners(request);
			}
		});
	}

}
