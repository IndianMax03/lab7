package commandButtons;

import client.ClientReceiver;
import client.Main;
import gui.listeners.CommandListener;
import listening.Request;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class ClientButton extends JButton {

	protected ClientReceiver clientReceiver;
	private final List<CommandListener> commandListeners = new ArrayList<>();
	protected static ResourceBundle RB = ResourceBundle.getBundle("commandsButtons", Main.locale);

	protected ClientButton(ClientReceiver clientReceiver, String name) {
		this.clientReceiver = clientReceiver;
		setText(name);
		setName(name);
	}

	public void addCommandListener(CommandListener commandListener) {
		commandListeners.add(commandListener);
	}

	protected void notifyCommandListeners(Optional<Request> request) {
		for (CommandListener cl : commandListeners) {
			cl.created(request);
		}
	}

	public static void refreshRB() {
		RB = ResourceBundle.getBundle("commandsButtons", Main.locale);
	}

}
