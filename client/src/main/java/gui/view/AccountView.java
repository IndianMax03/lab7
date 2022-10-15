package gui.view;

import base.City;
import client.Client;
import client.ClientInvoker;
import client.ClientReceiver;
import client.Main;
import commandButtons.*;
import gui.input.Typer;
import gui.listeners.CommandListener;
import gui.painting.CanvassFrame;
import gui.painting.Sheep;
import gui.util.CitiesTable;
import gui.util.MainFrame;
import listening.Request;
import listening.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class AccountView {
	private static ResourceBundle RB = ResourceBundle.getBundle("guiView", Main.locale);
	private Font font = MainFrame.getFont();
	private JFrame frame = MainFrame.getFrame();
	private JPanel mainPanel = new JPanel();
	private final CitiesTable citiesTable = new CitiesTable();
	private final JTable table = new JTable(citiesTable);
	private static JLabel comLabel;
	private static JLabel infoLabel;

	private static String login = "";
	private String password = "";
	private final ClientReceiver CR = new ClientReceiver();
	private final ClientInvoker clientInvoker = new ClientInvoker(CR);


	public AccountView(String usrLogin, String passord, Client client) {
		login=usrLogin;
		this.password=passord;

		frame.setTitle("Account");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainPanel.setLayout(new BorderLayout());
		frame.getContentPane().add(mainPanel);

		infoLabel = new JLabel(RB.getString("authName") + ": " + login);
		infoLabel.setFont(font);
		JPanel southPanel = new JPanel();
		infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		southPanel.add(infoLabel);
		mainPanel.add(southPanel, BorderLayout.NORTH);

		citiesTable.init(table);
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel commandPanel = new JPanel();
		GridLayout layout = new GridLayout();
		layout.setColumns(1);
		layout.setRows(20);
		commandPanel.setLayout(layout);
		comLabel = new JLabel(RB.getString("commands"));
		comLabel.setFont(font);
		commandPanel.add(comLabel);

		Map<String, ClientButton> buttons = clientInvoker.getCommandMap();

		for (Map.Entry<String, ClientButton> entry : buttons.entrySet()) {
			ClientButton button = entry.getValue();
			commandPanel.add(button);
			button.addCommandListener(new CommandListener() {
				@Override
				public void created(Optional<Request> optionalRequest) {
					if (optionalRequest.isPresent()) {
						Request request = optionalRequest.get();
						request.setLogin(login);
						request.setPassword(password);
						client.send(request);
						try {
							Optional<Response> resp = client.recieve();
							if (resp.isPresent()) {
								parseAnswer(resp.get());
							} else {
								showError(RB.getString("error"));
							}
						} catch (NumberFormatException ex) {
							showError(RB.getString("serverDead"));
							System.exit(0);
						}
					}
				}
			});
		}

		JButton sheepButton = new JButton("visualisation");
		commandPanel.add(sheepButton);
		sheepButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				citiesTable.visualisation();
			}
		});

		mainPanel.add(commandPanel, BorderLayout.WEST);

		JComboBox<String> localizationBox = new JComboBox<>(new String[]{"Russian", "Spain", "Serbian", "Albanian"});
		commandPanel.add(localizationBox);
		localizationBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (localizationBox.getSelectedItem() != null) {
					Object selectedItem = localizationBox.getSelectedItem();
					if ("Spain".equals(selectedItem)) {
						Main.locale = new Locale.Builder().setLanguage("es").setRegion("CR").build();
					} else if ("Serbian".equals(selectedItem)) {
						Main.locale = new Locale.Builder().setLanguage("sr").setRegion("RS").build();
					} else if ("Albanian".equals(selectedItem)) {
						Main.locale = new Locale.Builder().setLanguage("sq").build();
					} else if ("Russian".equals(selectedItem)){
						Main.locale = new Locale.Builder().setLanguage("ru").setRegion("RU").build();
					} else {
						Main.locale = Locale.getDefault();
					}
				}
				CanvassFrame.refreshRB();
				Sheep.refreshRB();
				refreshRB();
				AuthView.refreshRB();
				FilterView.refreshRB();
				RemoveGovView.refreshRB();
				RemoveIdView.refreshRB();
				Typer.refreshRB();
				ClientButton.refreshRB();
				ClientReceiver.refreshRB();
				frame.repaint();
			}
		});

		mainPanel.add(commandPanel, BorderLayout.WEST);

		frame.revalidate();
	}

	public void show() {
		frame.setVisible(true);
	}

	public void hide() {
		frame.setVisible(false);
	}

	public void showInfo(String... msg) {
		JOptionPane.showMessageDialog(frame, msg, "INFO", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showError(String... error) {
		JOptionPane.showMessageDialog(frame, error, "ERROR", JOptionPane.ERROR_MESSAGE);
	}

	public void parseAnswer(Response response) {
		String message = response.getMessage();
		String[] answer = response.getAnswer();
		boolean isDone = response.isDone();
		if (isDone) {
			if (message != null) {
				showInfo(message);
			} else {
				showInfo(answer);
			}
		} else {
			showError(message);
		}
	}

	public void updateTable(TreeSet<City> collection) {
		citiesTable.updateData(collection);
		table.repaint();
	}

	public static String getLogin() {
		return login;
	}

	public CitiesTable getCitiesTable() {
		return this.citiesTable;
	}

	public static void refreshRB() {
		RB = ResourceBundle.getBundle("guiView", Main.locale);
		comLabel.setText(RB.getString("commands"));
		infoLabel.setText(RB.getString("authName") + ": " + login);
	}

}
