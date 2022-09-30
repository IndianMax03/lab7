package commandButtons;

import base.Government;
import client.ClientReceiver;
import command.CommandsEnum;
import gui.listeners.FilterListener;
import gui.listeners.GovListener;
import gui.view.FilterView;
import gui.view.RemoveGovView;
import listening.Request;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class RemoveAllByGovernment extends ClientButton{

	public RemoveAllByGovernment(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.REMOVE_ALL_BY_GOVERNMENT.title);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RemoveGovView removeGovView = new RemoveGovView();
				removeGovView.addGovernmentListener(new GovListener() {
					@Override
					public void created(Government government) {
						Optional<Request> request = clientReceiver.removeAllByGovernment(government);
						removeGovView.hide();
						notifyCommandListeners(request);
					}
				});
			}
		});
	}

}
