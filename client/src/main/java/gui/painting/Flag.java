package gui.painting;

import java.awt.*;

public class Flag {

	double stickLen;
	Rectangle flag;

	public Flag(float area, int[][] stick, boolean right) {
		stickLen = area/2;
		if (right) {
			flag = new Rectangle((int) (stick[1][0] - area/4), (int) (stick[1][1] + stickLen/4), (int) area/4, (int) stickLen/4);
		} else {
			flag = new Rectangle((int) (stick[1][0]), (int) (stick[1][1] + stickLen/4), (int) area/4, (int) stickLen/4);
		}
	}

	public Rectangle getFlag() {
		return flag;
	}

}
