package gui.view;

import gui.util.CitiesTable;
import gui.util.MainFrame;

import javax.swing.*;
import java.awt.*;

public class AccountView {
	static Font font = MainFrame.getFont();
	static JFrame frame = MainFrame.getFrame();
	static JPanel mainPanel = new JPanel();
	static String login = "max";
	static JTable table = new JTable(new CitiesTable());

	public static void main(String[] args) {
		frame.setTitle("Account");
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

		JButton addButton = new JButton("add");
		commandPanel.add(addButton);
		JButton addIfMinButton = new JButton("add_if_min");
		commandPanel.add(addIfMinButton);
		JButton clearButton = new JButton("clear");
		commandPanel.add(clearButton);
		JButton executeScriptButton = new JButton("execute_script");
		commandPanel.add(executeScriptButton);
		JButton filterStartsWithNameButton = new JButton("filter_starts_with_name");
		commandPanel.add(filterStartsWithNameButton);
		JButton helpButton = new JButton("help");
		commandPanel.add(helpButton);
		JButton infoButton = new JButton("info");
		commandPanel.add(infoButton);
		JButton printDescendingButton = new JButton("print_descending");
		commandPanel.add(printDescendingButton);
		JButton removeAllByGovButton = new JButton("remove_all_by_government");
		commandPanel.add(removeAllByGovButton);
		JButton removeByIdButton = new JButton("remove_by_id");
		commandPanel.add(removeByIdButton);
		JButton removeGreaterButton = new JButton("remove_greater");
		commandPanel.add(removeGreaterButton);
		JButton removeLowerButton = new JButton("remove_lower");
		commandPanel.add(removeLowerButton);
		JButton showButton = new JButton("show");
		commandPanel.add(showButton);
		JButton updateButton = new JButton("update");
		commandPanel.add(updateButton);
		JButton exitButton = new JButton("exit");
		commandPanel.add(exitButton);

		mainPanel.add(commandPanel, BorderLayout.WEST);

		mainPanel.revalidate();
		frame.setVisible(true);
	}
}
