package gui.view;

import base.Government;
import gui.listeners.GovListener;
import gui.util.DialogFrame;
import gui.util.SmallFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RemoveGovView {

	private final JFrame frame = SmallFrame.getFrame();

	public RemoveGovView() {
		JPanel mainPanel = new JPanel();
		Font font = DialogFrame.getFont();
		frame.add(mainPanel);
		frame.setTitle("Удаление по типу правлению");

		mainPanel.setLayout(new GridBagLayout());

		GridBagConstraints government = new GridBagConstraints();
		government.insets = new Insets(3,3,3,3);

		government.gridx = 0;
		government.gridy = 0;
		JLabel govLabel = new JLabel("Выберите тип правления:");
		govLabel.setFont(font);
		mainPanel.add(govLabel, government);
		government.gridx = 1;
		government.gridy = 0;
		JComboBox<Government> govField = new JComboBox<>(Government.values());
		govField.setFont(font);
		mainPanel.add(govField, government);

		government.gridx = 0;
		government.gridy = 1;
		government.gridwidth = 2;
		JButton acceptButton = new JButton("Подтвердить");
		acceptButton.setFont(font);
		mainPanel.add(acceptButton, government);

		frame.revalidate();
		frame.setVisible(true);

		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyGovernmentListeners((Government) govField.getSelectedItem());
			}
		});
	}


	private final List<GovListener> govListeners = new ArrayList<>();

	public void addGovernmentListener(GovListener govListener) {
		govListeners.add(govListener);
	}

	private void notifyGovernmentListeners(Government government) {
		for (GovListener govListener : govListeners) {
			govListener.created(government);
		}
	}

	public void hide() {
		frame.setVisible(false);
	}

}
