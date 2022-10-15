package gui.painting;

import base.City;
import base.Coordinates;
import client.Main;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class Sheep {
	private final City city;
	private final float area;
	private double x;
	private double y;
	private static final HashMap<String, double[]> VELOCITIES = new HashMap<>();
	private static ResourceBundle RB = ResourceBundle.getBundle("guiPainting", Main.locale);

	int[][] stick = new int[2][2];
	private final Rectangle flag;
	private final Polygon sheepBody;
	private final String title;
	private final String login;
	private final double bodyMidline;
	private final SheepBody body;

	public Sheep(City city) {
		this.city = city;
		this.login = city.getLogin();
		this.area = city.getArea();
		this.x = city.getCoordinates().getX();
		this.y = city.getCoordinates().getY();
		this.title = city.getName();
		initVelocity();
		stick[0] = new int[]{(int) (x+area/4), (int) (y)};
		stick[1] = new int[]{(int) (x+area/4), (int) (stick[0][1] - area/2)};
		this.flag = new Flag(area, stick, VELOCITIES.get(city.getName())[0] > 0).getFlag();
		SheepBody sheepBody = new SheepBody(x, y, area, 0, 0);
		this.body = sheepBody;
		this.sheepBody = sheepBody.getBody();
		this.bodyMidline = sheepBody.getMidline();
	}

	public void paintSheep(Graphics2D g2) {
		g2.setPaint(Color.RED);
		g2.fill(flag);
		g2.setStroke(new BasicStroke(area*0.03f));
		g2.setPaint(new Color(150, 75, 0));
		g2.drawLine(stick[0][0], stick[0][1], stick[1][0], stick[1][1]);
		g2.setPaint(new Color(login.hashCode()));
		g2.fill(sheepBody);
		g2.setPaint(Color.BLACK);
		g2.drawString(title, (float) (x + (bodyMidline-title.length())/4), (float) y + area/4);
	}

	public void move(CanvassPanel panel, CopyOnWriteArrayList<Sheep> sheepList) {
		if (x < 0 || x > panel.getBounds().getWidth()-area || this.body.getLefttConflict(sheepList) || this.body.getRightConflict(sheepList) || this.body.getHitFromLeftConflict(sheepList) || this.body.getHitFromRightConflict(sheepList)) {
			VELOCITIES.get(city.getName())[0] *= -1;
		}
		if (y < area/2 && VELOCITIES.get(city.getName())[1] < 0 || y > panel.getBounds().getHeight()-area/2 || this.body.getUpConflict(sheepList) || this.body.getBotConflict(sheepList) || this.body.getFromUpConflict(sheepList) || this.body.getFromBotConflict(sheepList)) {
			VELOCITIES.get(city.getName())[1] *= -1;
		}
		x += VELOCITIES.get(city.getName())[0];
		y += VELOCITIES.get(city.getName())[1];
		city.setCoordinates(new Coordinates(x, y));
	}

	private void initVelocity() {
		if (!VELOCITIES.containsKey(city.getName())) {
			VELOCITIES.put(city.getName(), new double[]{Math.random()*2, Math.random()*2});
		}
	}

	public void checkTarget(CanvassFrame frame, int x, int y) {
		if (x >= this.x && x <= this.x+area && y >= this.y-stick[1][1] && y <= y+area/4) {
			JOptionPane.showMessageDialog(frame, city.toString(), RB.getString("sheepCity"), JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void refreshRB() {
		RB = ResourceBundle.getBundle("guiPainting", Main.locale);
	}

	public static void clearVelocities() {
		VELOCITIES.clear();
	}

	public SheepBody getSheepBody() {
		return body;
	}
}
