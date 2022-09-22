package commandButtons;

import base.City;
import client.ClientReceiver;
import command.CommandsEnum;
import gui.listeners.CityListener;
import gui.input.Typer;
import listening.Request;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class AddIfMin extends ClientButton {

	public AddIfMin(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.ADD_IF_MIN.title);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Typer typer = new Typer();
				typer.addCityListener(new CityListener() {
					@Override
					public void created(City city) {
						Optional<Request> request = clientReceiver.addIfMin(city);
						typer.hide();
						notifyCommandListeners(request);
					}
				});
			}
		});
	}

}
