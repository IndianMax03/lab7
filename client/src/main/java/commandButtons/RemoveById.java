package commandButtons;

import base.City;
import client.ClientReceiver;
import command.CommandsEnum;
import gui.input.Typer;
import gui.listeners.CityListener;
import gui.listeners.IdListener;
import gui.view.RemoveIdView;
import listening.Request;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class RemoveById extends ClientButton{

	public RemoveById(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.REMOVE_BY_ID.title);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RemoveIdView removeIdView = new RemoveIdView();
				removeIdView.addIdListener(new IdListener() {
					@Override
					public void created(Integer id) {
						Optional<Request> request = clientReceiver.removeById(id);
						removeIdView.hide();
						notifyCommandListeners(request);
					}
				});
			}
		});
	}

}
