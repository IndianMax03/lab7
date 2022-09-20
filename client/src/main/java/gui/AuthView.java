package gui;

import gui.listeners.LoginPasswordListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

public class AuthView {

	JFrame frame = MainFrame.getFrame();
	Font font = MainFrame.getFONT();
	private static String login;
	private static String password;
	private final List<LoginPasswordListener> lpListeners = new ArrayList<>();

	public void addLPListener(LoginPasswordListener lpListener) {
		lpListeners.add(lpListener);
	}

	private void notifylpListeners(String login, String password) {
		for (LoginPasswordListener listener : lpListeners) {
			listener.created(login, password);
		}
	}


	public AuthView() {
		frame.setTitle("Authorization Window");
		JPanel mainPanel = new JPanel();
		frame.add(mainPanel);
		mainPanel.setLayout(new BorderLayout());

		// window description
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new FlowLayout());
		JLabel hiLable = new JLabel("Авторизуйтесь для продолжения работы");
		hiLable.setFont(font);
		messagePanel.add(hiLable);
		mainPanel.add(messagePanel, BorderLayout.NORTH);

		// submit button
		JPanel confPanel = new JPanel();
		confPanel.setLayout(new FlowLayout());
		JButton confButton = new JButton("Подтвердить");
		confPanel.add(confButton);
		frame.add(confPanel, BorderLayout.SOUTH);

		// panel for input
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		GridBagConstraints const1 = new GridBagConstraints();
		const1.weightx = 0;
		const1.weighty = 0;
		const1.gridx = 0;
		const1.gridy = 0;
		const1.gridheight = 1;
		const1.gridwidth = 4;
		JLabel loginInp = new JLabel("Введите логин:");
		loginInp.setFont(font);
		inputPanel.add(loginInp, const1);

		GridBagConstraints const2 = new GridBagConstraints();
		const2.weightx = 0;
		const2.weighty = 0;
		const2.gridx = 4;
		const2.gridy = 0;
		const2.gridheight = 1;
		const2.gridwidth = 2;
		JTextField loginField = new JTextField(20);
		loginField.setFont(font);
		inputPanel.add(loginField, const2);

		GridBagConstraints const3 = new GridBagConstraints();
		const3.weightx = 0;
		const3.weighty = 0;
		const3.gridx = 0;
		const3.gridy = 1;
		const3.gridheight = 1;
		const3.gridwidth = 4;
		JLabel passwordInp = new JLabel("Введите пароль:");
		passwordInp.setFont(font);
		inputPanel.add(passwordInp, const3);

		GridBagConstraints const4 = new GridBagConstraints();
		const4.weightx = 0;
		const4.weighty = 0;
		const4.gridx = 4;
		const4.gridy = 1;
		const4.gridheight = 1;
		const4.gridwidth = 2;
		JPasswordField passField = new JPasswordField( 20);
		passField.setFont(font);
		inputPanel.add(passField, const4);

		mainPanel.add(inputPanel, BorderLayout.CENTER);

		loginField.setToolTipText("обязательное поле ввода");
		passField.setToolTipText("пароль можно не создавать");

		loginField.requestFocus();

		loginField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				passField.requestFocus();
			}
		});
		passField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				confButton.requestFocus();
			}
		});

		confButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login = loginField.getText();
				password = String.valueOf(passField.getPassword());
				notifylpListeners(login, password);
			}
		});


		frame.getRootPane().setDefaultButton(confButton);
		frame.revalidate();
		frame.setVisible(true);
	}
}
