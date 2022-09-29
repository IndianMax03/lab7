package gui.painting;

import base.City;
import base.Coordinates;
import gui.listeners.TableCellsListener;
import gui.listeners.TableCollectionListener;
import gui.util.CitiesTable;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.*;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;


public class CanvassPanel extends JPanel implements ActionListener {

	private final Image image;
	Dimension dimension;
	private final Timer timer = new Timer(5, this);
	private final CopyOnWriteArrayList<Sheep> sheepList = new CopyOnWriteArrayList<>();
	private TreeSet<City> collection;

	public CanvassPanel(TreeSet<City> collectionFromTable, CanvassFrame frame, CitiesTable citiesTable) {
		this.collection = new TreeSet<>(collectionFromTable);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		this.dimension = dimension;
		ImageIcon ocean = new ImageIcon("Ocean.jpg");
		image = ocean.getImage().getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH);
		ocean = new ImageIcon(image);
		this.setPreferredSize(new Dimension(700, 500));
		initSheep();
		citiesTable.addTableCollectionListener(new TableCollectionListener() {
			@Override
			public void created(TreeSet<City> newCollectionFromServer) {
				collection = new TreeSet<>(newCollectionFromServer);
			}
		});
		citiesTable.addTableCollectionListener(new TableCollectionListener() {
			@Override
			public void created(TreeSet<City> newCollectionFromServer) {
				collection = new TreeSet<>(newCollectionFromServer);
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				for (Sheep sheep : sheepList) {
					sheep.checkTarget(frame, x, y);
				}
			}
		});
	}

	public void paint(Graphics grap) {
		super.paint(grap);
		Graphics2D g2 = (Graphics2D) grap;
		g2.drawImage(image, 0,0,null);
		sheepList.clear();
		initSheep();
		for (Sheep sheep : sheepList) {
			sheep.paintSheep(g2);
		}
		timer.start();
	}

	private void initSheep() {
		for (City city : collection) {
			sheepList.add(new Sheep(city));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (Sheep sheep : sheepList) {
			sheep.move(this);
		}
		repaint();
	}

}
