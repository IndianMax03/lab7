package commandButtons;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class Clear extends ClientButton{

	public Clear(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.CLEAR.title);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Optional<Request> request = clientReceiver.clear();
				notifyCommandListeners(request);
			}
		});
	}
}
