package gui.view;

import gui.util.CitiesTable;
import gui.util.MainFrame;

import javax.swing.*;

public class TableView {
	private static final JFrame FRAME = MainFrame.getFrame();
	private static final JTable TABLE = new JTable(new CitiesTable());
	public static void main(String[] args) {
		CitiesTable.init(TABLE);
		TABLE.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(TABLE);
		FRAME.add(scrollPane);
		FRAME.setVisible(true);
	}
}




