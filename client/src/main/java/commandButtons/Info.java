package commandButtons;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class Info extends ClientButton{

	public Info(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.INFO.title);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Optional<Request> request = clientReceiver.info();
				notifyCommandListeners(request);
			}
		});
	}

}
