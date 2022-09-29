package gui.painting;

import base.City;
import base.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeSet;


public class CanvassPanel extends JPanel implements ActionListener {

	private final Image image;
	private final Timer timer = new Timer(5, this);
	private final ArrayList<Sheep> sheepList = new ArrayList<>();
	Dimension dimension;
	private ArrayList<Coordinates> coordinates = new ArrayList<>();
	private final TreeSet<City> collection;

	public CanvassPanel(TreeSet<City> collection) {
		this.collection = new TreeSet<>(collection);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		this.dimension = dimension;
		ImageIcon ocean = new ImageIcon("Ocean.jpg");
		image = ocean.getImage().getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH);
		ocean = new ImageIcon(image);
		this.setPreferredSize(new Dimension(700, 500));
		initSheep();
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
