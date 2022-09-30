package gui.view;

import client.Main;
import gui.listeners.IdListener;
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

public class RemoveIdView {
	private final JFrame frame = SmallFrame.getFrame();
	private static ResourceBundle RB = ResourceBundle.getBundle("guiView", Main.locale);

	public RemoveIdView() {
		JPanel mainPanel = new JPanel();
		Font font = DialogFrame.getFont();
		frame.add(mainPanel);
		frame.setTitle(RB.getString("idChoosing"));

		mainPanel.setLayout(new GridBagLayout());

		GridBagConstraints idConstr = new GridBagConstraints();
		idConstr.insets = new Insets(3,3,3,3);

		idConstr.gridx = 0;
		idConstr.gridy = 0;
		JLabel govLabel = new JLabel(RB.getString("inputId") + ":");
		govLabel.setFont(font);
		mainPanel.add(govLabel, idConstr);
		idConstr.gridx = 1;
		idConstr.gridy = 0;
		JTextField idField = new JTextField(6);
		idField.setFont(font);
		mainPanel.add(idField, idConstr);

		idConstr.gridx = 0;
		idConstr.gridy = 1;
		idConstr.gridwidth = 2;
		JButton acceptButton = new JButton(RB.getString("accept"));
		acceptButton.setFont(font);
		mainPanel.add(acceptButton, idConstr);

		frame.revalidate();
		frame.setVisible(true);

		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					notifyIdListeners(Integer.parseInt(idField.getText()));
				} catch (NumberFormatException ex) {
					showError(RB.getString("badInp"));
				}
			}
		});
	}


	private final List<IdListener> idListeners = new ArrayList<>();

	public void addIdListener(IdListener idListener) {
		idListeners.add(idListener);
	}

	private void notifyIdListeners(Integer id) {
		for (IdListener idListener : idListeners) {
			idListener.created(id);
		}
	}

	public void showError(String error) {
		JOptionPane.showMessageDialog(frame, error, "ERROR", JOptionPane.ERROR_MESSAGE);
	}

	public void hide() {
		frame.setVisible(false);
	}

	public static void refreshRB() {
		RB = ResourceBundle.getBundle("guiView", Main.locale);
	}

}
