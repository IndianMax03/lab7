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

public class Update extends ClientButton{

	public Update(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.UPDATE.title);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RemoveIdView removeIdView = new RemoveIdView();
				removeIdView.addIdListener(new IdListener() {
					@Override
					public void created(Integer id) {
						removeIdView.hide();
						Typer typer = new Typer();
						typer.setTitle("cityToUpdate");
						typer.addCityListener(new CityListener() {
							@Override
							public void created(City city) {
								Optional<Request> request = clientReceiver.update(id, city);
								typer.hide();
								notifyCommandListeners(request);
							}
						});
					}
				});
			}
		});
	}
}
