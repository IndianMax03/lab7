package gui.view;

import base.City;
import client.Client;
import client.ClientInvoker;
import client.ClientReceiver;
import commandButtons.*;
import gui.listeners.CommandListener;
import gui.listeners.TableCellsListener;
import gui.util.CitiesTable;
import gui.util.MainFrame;
import listening.Request;
import listening.Response;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.util.*;
import java.util.List;

public class AccountView {
	private Font font = MainFrame.getFont();
	private JFrame frame = MainFrame.getFrame();
	private JPanel mainPanel = new JPanel();
	private final CitiesTable citiesTable = new CitiesTable();
	private final JTable table = new JTable(citiesTable);

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

		table.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				Optional<City> changedCity = citiesTable.getChangedCity(e.getFirstRow(), e.getColumn());
				changedCity.ifPresent(city -> notifyTableCellsListeners(city));
			}
		});

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


	private final List<TableCellsListener> tableCellsListeners = new ArrayList<>();

	public void addTableCellsListener(TableCellsListener tableCellsListener) {
		tableCellsListeners.add(tableCellsListener);
	}

	private void notifyTableCellsListeners(City city) {
		for (TableCellsListener tableCellsListener : tableCellsListeners) {
			tableCellsListener.created(city);
		}
	}

}
