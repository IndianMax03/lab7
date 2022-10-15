package gui.painting;

import base.City;
import gui.listeners.TableCollectionListener;
import gui.util.CitiesTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


public class CanvassPanel extends JPanel implements ActionListener {

	private TreeSet<City> collection;
	private final Set<Star> starSet = Collections.synchronizedSet(new HashSet<>());
	private Image image;
	private Graphics2D g2;
	private Ellipse2D.Double sun;

	private final Image ufo = new ImageIcon("Ufo.jpg").getImage();

	public CanvassPanel(TreeSet<City> collectionFromTable, CanvassFrame frame, CitiesTable citiesTable) {
		this.collection = new TreeSet<>(collectionFromTable);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		ImageIcon cosmos = new ImageIcon("Cosmos.jpg");
		image = cosmos.getImage().getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH);
		cosmos = new ImageIcon(image);
		this.setPreferredSize(new Dimension(700, 500));
		initStars();
		citiesTable.addTableCollectionListener(new TableCollectionListener() {
			@Override
			public void created(TreeSet<City> newCollectionFromServer) {
				if (!collection.equals(newCollectionFromServer)){
//					paintBigSun();
					breakStars(collection, newCollectionFromServer);
					collection = new TreeSet<>(newCollectionFromServer);
					repaint();
				} else {
					collection = new TreeSet<>(newCollectionFromServer);
					initStars();
					repaint();
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				for (Star star : starSet) {
					if (star.getStarBounds().contains(x, y)) {
						JOptionPane.showMessageDialog(frame, star.getCity().toString(), "INFO", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
	}

	public void paint(Graphics grap) {
		super.paint(grap);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = this.getSize();
		Graphics2D g2 = (Graphics2D) grap;
		this.g2 = g2;
		g2.drawImage(image, 0,0,null);
		g2.setPaint(Color.YELLOW);
		sun = new Ellipse2D.Double(dimension.width-75, -75, 150, 150);
		g2.fill(sun);
		for (Star star : starSet) {
			star.paintStar(g2);
		}
	}

	private void initStars() {
		starSet.clear();
		for (City city : collection) {
			starSet.add(new Star(city));
		}
	}

	public void clearState() {
		collection.clear();
	}

	private void breakStars(TreeSet<City> oldStars, TreeSet<City> nowStars) {
		if (oldStars.isEmpty()) {
			return;
		}
		oldStars.removeAll(nowStars);
		for (City city : oldStars) {
			city.setArea(800);
			starSet.add(new Star(city));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
