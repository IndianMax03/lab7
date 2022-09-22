package gui.view;

import client.Client;
import client.ClientInvoker;
import client.ClientReceiver;
import commandButtons.*;
import gui.listeners.CommandListener;
import gui.util.CitiesTable;
import gui.util.MainFrame;
import listening.Request;
import listening.Response;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Optional;

public class AccountView {
	private Font font = MainFrame.getFont();
	private JFrame frame = MainFrame.getFrame();
	private JPanel mainPanel = new JPanel();
	private JTable table = new JTable(new CitiesTable());

	private final String login;
	private final String password;
	private final ClientReceiver CR = new ClientReceiver();


	public AccountView(String login, String passord, Client client) {
		this.login=login;
		this.password=passord;

		frame.setTitle("Account");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainPanel.setLayout(new BorderLayout());
		frame.getContentPane().add(mainPanel);

		JLabel infoLabel = new JLabel("Вы авторизованы под именем: " + login);
		infoLabel.setFont(font);
		JPanel southPanel = new JPanel();
		infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		southPanel.add(infoLabel);
		mainPanel.add(southPanel, BorderLayout.NORTH);

		CitiesTable.init(table);
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel commandPanel = new JPanel();
		GridLayout layout = new GridLayout();
		layout.setColumns(1);
		layout.setRows(20);
		commandPanel.setLayout(layout);
		JLabel comLabel = new JLabel("Команды");
		comLabel.setFont(font);
		commandPanel.add(comLabel);

		ClientInvoker clientInvoker = new ClientInvoker(CR);

		Map<String, ClientButton> buttons = clientInvoker.getCommandMap();

		for (Map.Entry<String, ClientButton> entry : buttons.entrySet()) {
			ClientButton button = entry.getValue();
			commandPanel.add(button);
			button.addCommandListener(new CommandListener() {
				@Override
				public void created(Optional<Request> request) {
					if (request.isPresent()) {
						Request req = request.get();
						req.setLogin(login);
						req.setPassword(password);
						client.send(req);
						try {
							Optional<Response> resp = client.recieve();
							if (resp.isPresent()) {
								Response response = resp.get();
								if (response.getAnswer() != null) {
									showAnswer(response.getAnswer());
								} else if (response.getMessage() != null){
									showMessage(response.getMessage());
								}
							} else {
								showError("ERROR");
							}
						} catch (NumberFormatException ex) {
							showError("Server is DEAD");
							System.exit(0);
						}
					}
				}
			});
		}

		mainPanel.add(commandPanel, BorderLayout.WEST);

		frame.revalidate();
	}

	public void show() {
		frame.setVisible(true);
	}

	public void hide() {
		frame.setVisible(false);
	}

	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(frame, msg, "INFO", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showAnswer(String[] answer) {
		JOptionPane.showMessageDialog(frame, answer, "INFO", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showError(String error) {
		JOptionPane.showMessageDialog(frame, error, "ERROR", JOptionPane.ERROR_MESSAGE);
	}
}
