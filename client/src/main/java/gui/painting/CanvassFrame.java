package gui.painting;

import base.City;

import javax.swing.*;
import java.util.TreeSet;

public class CanvassFrame extends JFrame {

	CanvassPanel panel;

	public CanvassFrame(TreeSet<City> collection) {
		panel = new CanvassPanel(collection);
		this.setTitle("Sheep");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
