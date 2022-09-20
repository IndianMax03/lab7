package gui.util;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.sql.Date;
import java.time.LocalDate;

public class CitiesTable extends AbstractTableModel {
	//  todo tollbar'ы на ячейках
	//  todo ограничения на ячейки
	//  todo дата в ячейках
	//  todo фильтры в колонках

	private static final String[] columnNames = {"id", "name", "x", "y", "creation date", "area", "population",
			"meters_above_sea_level", "climate", "government", "standard_of_living", "governor", "governor_height",
			"governor_birthday", "login"};
	private static Object[][] data = {
			{1, "Maxim", 6f, 7f, Date.valueOf(LocalDate.now()), 12f, 666, -10, "Хороший", "Анархия", "Высокий", "Максимчик",
					180, Date.valueOf(LocalDate.now()), "max"},
			{2, "Anton", 90f, 103f, Date.valueOf(LocalDate.now()), 111f, 777, -102, "Нормик", "Диктатура", "Низкий", "Антончик",
					180, Date.valueOf(LocalDate.now()), "antony"}
	};

	@Override
	public void addTableModelListener(TableModelListener l) {

	}

	public static void init(JTable table) {
		TableColumn column;
		for (int i = 0; i < 15; i++) {
			column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(50);
			column.setHeaderValue(columnNames[i]);
		}
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

//	public boolean isCellEditable(int row, int col) {
//		return TableView.login.equals(getValueAt(row, 4)) && col != 0;
//	}

	public Class<?> getColumnClass(int column) {
		switch (column) {
			case 0:
			case 6:
				return Integer.class;
			case 1:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 14:
				return String.class;
			case 2:
			case 3:
			case 5:
			case 7:
				return Float.class;
			case 4:
			case 13:
				return java.sql.Date.class;
			default: return String.class;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}
}
