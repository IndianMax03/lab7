package commandButtons;

import base.City;
import client.ClientReceiver;
import command.CommandsEnum;
import gui.input.Typer;
import gui.listeners.CityListener;
import listening.Request;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class RemoveLower extends ClientButton{

	public RemoveLower(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.REMOVE_LOWER.title);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Typer typer = new Typer();
				typer.setTitle("removeLower");
				typer.addCityListener(new CityListener() {
					@Override
					public void created(City city) {
						Optional<Request> request = clientReceiver.removeLower(city);
						typer.hide();
						notifyCommandListeners(request);
					}
				});

			}
		});
	}

}
