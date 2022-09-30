package gui.util;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class CitiesTableSorter extends TableRowSorter<CitiesTable> {

	public CitiesTableSorter(CitiesTable model) {
		setModel(model);
	}

	@Override
	public boolean isSortable(int column) {
		return column != 8
				&& column != 9
				&& column != 10
				&& column != 11;
	}
}
