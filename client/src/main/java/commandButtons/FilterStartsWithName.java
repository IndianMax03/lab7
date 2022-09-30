package commandButtons;

import client.ClientReceiver;
import command.CommandsEnum;
import gui.listeners.FilterListener;
import gui.view.FilterView;
import listening.Request;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class FilterStartsWithName extends ClientButton{

	public FilterStartsWithName(ClientReceiver clientReceiver) {
		super(clientReceiver, CommandsEnum.FILTER_STARTS_WITH_NAME.title);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FilterView filterView = new FilterView();
				filterView.addFilterListener(new FilterListener() {
					@Override
					public void created(String line) {
						Optional<Request> request = clientReceiver.filterStartsWithName(line);
						filterView.hide();
						notifyCommandListeners(request);
					}
				});
			}
		});
	}

}
