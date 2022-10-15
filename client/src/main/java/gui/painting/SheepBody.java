package gui.painting;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class SheepBody {

	private final int[] bodyX;
	private final int[] bodyY;
	private final double midline;
	private final int[][] leftLine;
	private final int[][] rightLine;
	private final int[] LEFT_TOP;
	private final int[] RIGHT_TOP;
	private final int[] LEFT_BOTTOM;
	private final int[] RIGHT_BOTTOM;

	public SheepBody(double startX, double startY, float area, int marginLeft, int marginTop) {
		LEFT_TOP = new int[]{(int) (startX + marginLeft), (int) (startY + marginTop)};
		RIGHT_TOP = new int[]{(int) (LEFT_TOP[0] + area), LEFT_TOP[1]};
		LEFT_BOTTOM = new int[]{(int) (LEFT_TOP[0] + area/4), (int) (LEFT_TOP[1] + area/2)};
		RIGHT_BOTTOM = new int[]{(int) (RIGHT_TOP[0] - area/4), LEFT_BOTTOM[1]};

		bodyX = new int[] {LEFT_TOP[0], RIGHT_TOP[0], RIGHT_BOTTOM[0], LEFT_BOTTOM[0]};
		bodyY = new int[] {LEFT_TOP[1], RIGHT_TOP[1], RIGHT_BOTTOM[1], LEFT_BOTTOM[1]};
		midline = ((RIGHT_TOP[0] - LEFT_TOP[0]) + (RIGHT_BOTTOM[0] - LEFT_BOTTOM[0]))/2d;
		leftLine = new int[][]{LEFT_TOP,LEFT_BOTTOM};
		rightLine = new int[][]{RIGHT_TOP,RIGHT_BOTTOM};
	}

	public Polygon getBody() {
		return new Polygon(bodyX, bodyY, 4);
	}

	public double getMidline() {
		return midline;
	}

	private double getXByYRightLine(int y) {
		return (double) (RIGHT_TOP[0]-RIGHT_BOTTOM[0])*(y-RIGHT_BOTTOM[1])/(RIGHT_TOP[1]-RIGHT_BOTTOM[1]) + RIGHT_BOTTOM[0];
	}

	private double getXByYLefttLine(int y) {
		return (double) (LEFT_TOP[0]-LEFT_BOTTOM[0])*(y-LEFT_BOTTOM[1])/(LEFT_TOP[1]-LEFT_BOTTOM[1]) + LEFT_BOTTOM[0];
	}

	private boolean hitFromRightConflict(int[] enemyLeftTop) {
		if (enemyLeftTop[1] >= RIGHT_TOP[1] && enemyLeftTop[1] <= RIGHT_BOTTOM[1]) {
			return enemyLeftTop[0] <= getXByYRightLine(enemyLeftTop[1]) && enemyLeftTop[0] >= LEFT_TOP[0];
		}
		return false;
	}

	private boolean hitFromLeftConflict(int[] enemyRightTop) {
		if (enemyRightTop[1] >= LEFT_TOP[1] && enemyRightTop[1] <= LEFT_BOTTOM[1]) {
			return enemyRightTop[0] >= getXByYLefttLine(enemyRightTop[1]) && enemyRightTop[0] <= RIGHT_TOP[0];
		}
		return false;
	}

	private boolean rightConflict(int[] leftTop) {
		if (leftTop[1] >= RIGHT_TOP[1] && leftTop[1] <= RIGHT_BOTTOM[1]) {
			return leftTop[0] <= getXByYRightLine(leftTop[1]) && leftTop[0] >= LEFT_TOP[0];
		} else
			return false;
	}

	private boolean leftConflict(int[] rightTop) {
		if (rightTop[1] >= LEFT_TOP[1] && rightTop[1] <= LEFT_BOTTOM[1]) {
			return rightTop[0] >= getXByYLefttLine(rightTop[1]) && rightTop[0] <= RIGHT_TOP[0];
		} else
			return false;
	}

	public boolean getRightConflict(final CopyOnWriteArrayList<Sheep> sheepList) {
		for (Sheep sheep : sheepList) {
			if (!this.equals(sheep.getSheepBody()) && sheep.getSheepBody().rightConflict(LEFT_TOP)) {
				return true;
			}
		}
		return false;
	}
	public boolean getLefttConflict(final CopyOnWriteArrayList<Sheep> sheepList) {
		for (Sheep sheep : sheepList) {
			if (!this.equals(sheep.getSheepBody()) && sheep.getSheepBody().leftConflict(RIGHT_TOP)) {
				return true;
			}
		}
		return false;
	}

	public boolean getHitFromRightConflict(final CopyOnWriteArrayList<Sheep> sheepList) {
		for (Sheep sheep : sheepList) {
			if (!this.equals(sheep.getSheepBody()) && this.hitFromRightConflict(sheep.getSheepBody().LEFT_TOP)) {
				return true;
			}
		}
		return false;
	}
	public boolean getHitFromLeftConflict(final CopyOnWriteArrayList<Sheep> sheepList) {
		for (Sheep sheep : sheepList) {
			if (!this.equals(sheep.getSheepBody()) && this.hitFromLeftConflict(sheep.getSheepBody().RIGHT_TOP)) {
				return true;
			}
		}
		return false;
	}

	public boolean getUpConflict(final CopyOnWriteArrayList<Sheep> sheepList) {
		for (Sheep sheep : sheepList) {
			if (!this.equals(sheep.getSheepBody()) && sheep.getSheepBody().upConflict(LEFT_BOTTOM, RIGHT_BOTTOM)) {
				return true;
			}
		}
		return false;
	}

	public boolean getFromUpConflict(final CopyOnWriteArrayList<Sheep> sheepList) {
		for (Sheep sheep : sheepList) {
			if (!this.equals(sheep.getSheepBody()) && this.upConflict(sheep.getSheepBody().LEFT_BOTTOM, sheep.getSheepBody().RIGHT_BOTTOM)) {
				return true;
			}
		}
		return false;
	}

	private boolean upConflict(int[] enemyLB, int[] enemyRB) {
		if (enemyRB[1] >= LEFT_TOP[1] && enemyRB[1] <= LEFT_BOTTOM[1]) {
			return enemyRB[0] >= LEFT_TOP[0] && enemyRB[0] <= RIGHT_TOP[0] || enemyLB[0] >= LEFT_TOP[0] && enemyLB[0] <= RIGHT_TOP[0];
		}
		return false;
	}

	private boolean botConflict(int[] enemyLT, int[] enemyRT) {
		if (enemyRT[1] <= LEFT_BOTTOM[1] && enemyRT[1] >= LEFT_TOP[1]) {
			return enemyRT[0] >= LEFT_BOTTOM[0] && enemyRT[0] <= RIGHT_BOTTOM[0] || enemyLT[0] >= LEFT_BOTTOM[0] && enemyLT[0] <= RIGHT_BOTTOM[0];
		}
		return false;
	}

	public boolean getBotConflict(final CopyOnWriteArrayList<Sheep> sheepList) {
		for (Sheep sheep : sheepList) {
			if (!this.equals(sheep.getSheepBody()) && sheep.getSheepBody().botConflict(LEFT_BOTTOM, RIGHT_BOTTOM)) {
				return true;
			}
		}
		return false;
	}

	public boolean getFromBotConflict(final CopyOnWriteArrayList<Sheep> sheepList) {
		for (Sheep sheep : sheepList) {
			if (!this.equals(sheep.getSheepBody()) && this.botConflict(sheep.getSheepBody().LEFT_BOTTOM, sheep.getSheepBody().RIGHT_BOTTOM)) {
				return true;
			}
		}
		return false;
	}

	public int[] getLEFT_TOP() {
		return LEFT_TOP;
	}

	public int[] getRIGHT_TOP() {
		return RIGHT_TOP;
	}

	public int[] getLEFT_BOTTOM() {
		return LEFT_BOTTOM;
	}

	public int[] getRIGHT_BOTTOM() {
		return RIGHT_BOTTOM;
	}


}
