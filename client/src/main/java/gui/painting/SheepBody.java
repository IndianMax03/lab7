package gui.painting;

import java.awt.*;

public class SheepBody {

	private final int[] bodyX;
	private final int[] bodyY;
	private final double midline;

	public SheepBody(double startX, double startY, float area, int marginLeft, int marginTop) {
		int[] LEFT_TOP = new int[]{(int) (startX + marginLeft), (int) (startY + marginTop)};
		int[] RIGHT_TOP = new int[]{(int) (LEFT_TOP[0] + area), LEFT_TOP[1]};
		int[] LEFT_BOTTOM = new int[]{(int) (LEFT_TOP[0] + area/4), (int) (LEFT_TOP[1] + area/2)};
		int[] RIGHT_BOTTOM = new int[]{(int) (RIGHT_TOP[0] - area/4), LEFT_BOTTOM[1]};

		bodyX = new int[] {LEFT_TOP[0], RIGHT_TOP[0], RIGHT_BOTTOM[0], LEFT_BOTTOM[0]};
		bodyY = new int[] {LEFT_TOP[1], RIGHT_TOP[1], RIGHT_BOTTOM[1], LEFT_BOTTOM[1]};
		midline = ((RIGHT_TOP[0] - LEFT_TOP[0]) + (RIGHT_BOTTOM[0] - LEFT_BOTTOM[0]))/2d;

	}

	public Polygon getBody() {
		return new Polygon(bodyX, bodyY, 4);
	}

	public double getMidline() {
		return midline;
	}


}
