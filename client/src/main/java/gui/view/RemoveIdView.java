package gui.view;

import gui.listeners.IdListener;
import gui.util.DialogFrame;
import gui.util.SmallFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RemoveIdView {
	private final JFrame frame = SmallFrame.getFrame();

	public RemoveIdView() {
		JPanel mainPanel = new JPanel();
		Font font = DialogFrame.getFont();
		frame.add(mainPanel);
		frame.setTitle("Выбор id");

		mainPanel.setLayout(new GridBagLayout());

		GridBagConstraints idConstr = new GridBagConstraints();
		idConstr.insets = new Insets(3,3,3,3);

		idConstr.gridx = 0;
		idConstr.gridy = 0;
		JLabel govLabel = new JLabel("Введите id:");
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
		JButton acceptButton = new JButton("Подтвердить");
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
					showError("Некорректный ввод");
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

}
