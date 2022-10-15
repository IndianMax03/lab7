package gui.util;

import base.City;
import gui.listeners.TableCellsListener;
import gui.listeners.TableCollectionListener;
import gui.painting.CanvassFrame;
import gui.view.AccountView;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.*;

public class CitiesTable extends DefaultTableModel {

	private static TreeSet<City> collection = new TreeSet<>();

//	CitiesTableSorter sorter = new CitiesTableSorter(this);
	private JTable table;

	private static final String[] columnNames = {"id", "name", "x", "y", "creation date", "area", "population",
			"meters_above_sea_level", "climate", "government", "standard_of_living", "governor", "governor_height",
			"governor_birthday", "login"};
	private static ArrayList<Object[]> data = new ArrayList<>(columnNames.length);

	public void init(JTable table) {
		this.table = table;
		table.setModel(this);
//		table.setRowSorter(sorter);
//		table.setAutoCreateRowSorter(true);

		table.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				Optional<City> changedCity = getChangedCity(e.getLastRow());
				changedCity.ifPresent(city -> {
					notifyTableCellsListeners(city);
				});
			}
		});

		TableColumn column;
		for (int i = 0; i < 15; i++) {
			column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(50);
			column.setHeaderValue(columnNames[i]);
		}

	}




	private final List<TableCellsListener> tableCellsListeners = new ArrayList<>();

	public void addTableCellsListener(TableCellsListener tableCellsListener) {
		tableCellsListeners.add(tableCellsListener);
	}

	private void notifyTableCellsListeners(City city) {
		for (TableCellsListener tableCellsListener : tableCellsListeners) {
			tableCellsListener.created(city);
		}
	}


	public Optional<City> getChangedCity(int row) {
		Iterator<City> it = collection.iterator();
		int count = 0;
		while (it.hasNext()) {
			City city = it.next();
			if (count == row) {
				Object[] fields = city.getArray();
				for (int col = 0; col < getColumnCount(); col++) {
					fields[col] = data.get(row)[col];
				}
				return City.getCityByArray(fields);
			}
			count++;
		}
		return Optional.empty();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	public boolean isCellEditable(int row, int col) {
		return AccountView.getLogin().equals(getValueAt(row, columnNames.length - 1))
				&& col != 8
				&& col != 9
				&& col != 10
				&& col != 11
				&& col != columnNames.length-1;
	}

	public Class<?> getColumnClass(int column) {
		switch (column) {
			case 0:
			case 6:
			case 12:
				return Integer.class;
			case 1:
			case 8:
			case 9:
			case 10:
			case 11:
			case 14:
				return String.class;
			case 2:
			case 3:
				return Double.class;
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
		return data.get(rowIndex)[columnIndex];
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		row = table.convertRowIndexToModel(row);
		col = table.convertColumnIndexToModel(col);
		data.get(row)[col] = value;
		fireTableCellUpdated(row, col);
	}

	public void updateData(TreeSet<City> collectionFromServer) {
		if (!collection.equals(collectionFromServer)) {
			collection = collectionFromServer;
			data.clear();
			int row = 0;
			for (City city : collection) {
				data.add(row++, city.getArray());
			}
		}
		notifyTableCollectionListeners(collection);
	}

	public void visualisation() {
		SwingUtilities.invokeLater(() -> {
			CanvassFrame canvassFrame = new CanvassFrame(collection, this);
		});
	}

	private final List<TableCollectionListener> tableCollectionListeners = new ArrayList<>();

	public void addTableCollectionListener(TableCollectionListener tableCollectionListener) {
		tableCollectionListeners.add(tableCollectionListener);
	}

	private void notifyTableCollectionListeners(TreeSet<City> collection) {
		for (TableCollectionListener tableCollectionListener : tableCollectionListeners) {
			tableCollectionListener.created(collection);
		}
	}



	//	public void addCityToCollection(City city) {
//		if (collection.add(city)) {
//			Object[] fields = city.getArray();
//			int row = rowToAdd++;
//			int col = 0;
//			for (Object field : fields) {
//				data[row][col] = field;
//				setValueAt(field, row, col);
//				fireTableCellUpdated(row, col);
//				col++;
//			}
//		}
//	}
//
//	public void updateCity(String ids, City city) {
//		try { // <- updating in table
//			int id = Integer.parseInt(ids);
//			int row = getRowById(id);
//			if (row >= 0) {
//				int col = 0;
//				Object[] fields = city.getArray();
//				for (Object field : fields) {
//					data[row][col] = field;
//					setValueAt(field, row, col);
//					fireTableCellUpdated(row, col);
//					col++;
//				}
//			}
//
//			collection.stream() // <- updating in collection
//					.filter(tmpCity -> tmpCity.getId().equals(id))
//					.findFirst()
//					.ifPresent(value -> value.update(city));
//
//		} catch (NumberFormatException ignore){
//		}
//	}
//	private int getRowById(int id) {
//		int length = getRowCount();
//		for (int row = 0; row < length; row++) {
//			if (((Integer)getValueAt(row, 0)) == id) {
//				return row;
//			}
//		}
//		return -1;
//	}
}
