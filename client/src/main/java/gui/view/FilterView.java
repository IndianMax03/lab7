package gui.view;

import client.Main;
import gui.listeners.FilterListener;
import gui.util.DialogFrame;
import gui.util.SmallFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class FilterView {

	private static ResourceBundle RB = ResourceBundle.getBundle("guiView", Main.locale);
	private final JFrame frame = SmallFrame.getFrame();

	public FilterView() {
		JPanel mainPanel = new JPanel();
		Font font = DialogFrame.getFont();
		frame.add(mainPanel);
		frame.setTitle(RB.getString("subFinding"));

		mainPanel.setLayout(new GridBagLayout());

		GridBagConstraints filter = new GridBagConstraints();
		filter.insets = new Insets(3,3,3,3);

		filter.gridx = 0;
		filter.gridy = 0;
		JLabel filterLabel = new JLabel(RB.getString("inputSubstring") + ":");
		filterLabel.setFont(font);
		mainPanel.add(filterLabel, filter);
		filter.gridx = 1;
		filter.gridy = 0;
		JTextField filterField = new JTextField(10);
		filterField.setFont(font);
		mainPanel.add(filterField, filter);

		filter.gridx = 0;
		filter.gridy = 1;
		filter.gridwidth = 2;
		JButton acceptButton = new JButton(RB.getString("accept"));
		acceptButton.setFont(font);
		mainPanel.add(acceptButton, filter);

		frame.revalidate();
		frame.setVisible(true);

		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyFilterListeners(filterField.getText());
			}
		});
	}


	private final List<FilterListener> filterListeners = new ArrayList<>();

	public void addFilterListener(FilterListener filterListener) {
		filterListeners.add(filterListener);
	}

	private void notifyFilterListeners(String line) {
		for (FilterListener filterListener : filterListeners) {
			filterListener.created(line);
		}
	}

	public void hide() {
		frame.setVisible(false);
	}

	public static void refreshRB() {
		RB = ResourceBundle.getBundle("guiView", Main.locale);
	}

}
