package gui.painting;

import base.City;
import gui.util.CitiesTable;

import javax.swing.*;
import java.util.TreeSet;

public class CanvassFrame extends JFrame {

	private final CanvassPanel panel;

	public CanvassFrame(TreeSet<City> collection, CitiesTable citiesTable) {
		panel = new CanvassPanel(collection, this, citiesTable);
		this.setTitle("Sheep");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public CanvassPanel getCanvassPanel() {
		return panel;
	}

}
