package gui.painting;

import base.City;
import client.Main;
import gui.util.CitiesTable;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class CanvassFrame extends JFrame {

	private final CanvassPanel panel;
	private static ResourceBundle RB = ResourceBundle.getBundle("guiPainting", Main.locale);

	public CanvassFrame(TreeSet<City> collection, CitiesTable citiesTable) {
		panel = new CanvassPanel(collection, this, citiesTable);
		this.setTitle(RB.getString("sheep"));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				panel.clearState();
			}
		});
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public CanvassPanel getCanvassPanel() {
		return panel;
	}

	public static void refreshRB() {
		RB = ResourceBundle.getBundle("guiPainting", Main.locale);
	}

}
