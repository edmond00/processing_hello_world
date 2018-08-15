package game;

import tools.*;

public class Animation {
	String img;
	int oneStepHeight;
	int centerX;
	int centerY;

	public Animation(String img, int oneStepHeight, int centerX, int centerY) {
		this.img = img;
		this.oneStepHeight = oneStepHeight;
		this.centerX = centerX;
		this.centerY = centerY;
	}

	public void draw(int step, int x, int y) {
		x -= centerX;
		y -= centerY;
		int ry = step * oneStepHeight;
		Game.app.img.draw(img, x, y, 0, ry, -1, oneStepHeight);
	}
}

