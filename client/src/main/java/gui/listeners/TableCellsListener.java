package gui.listeners;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class TableCellsListener  implements TableModelListener {


	@Override
	public void tableChanged(TableModelEvent e) {
		System.out.println("ОПОПВЕЩАЮ");
		// todo обработка изменения таблицы
	}
}
