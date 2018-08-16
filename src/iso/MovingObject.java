package iso;

import tools.*;

public abstract class MovingObject extends IsoObject {
	

	int gapX = 9;
	int gapY = 7;

	public enum State {
		STOP_FRONT_LEFT, STOP_FRONT_RIGHT, STOP_BACK_LEFT, STOP_BACK_RIGHT,
		MOVING_FRONT_LEFT, MOVING_FRONT_RIGHT, MOVING_BACK_LEFT, MOVING_BACK_RIGHT
	}

	double destinationX;
	double destinationY;
	double positionX;
	double positionY;
	int gx;
	int gy;

	State state = State.STOP_BACK_RIGHT;

	public MovingObject(IsoMap map, int x, int y) {
		super(map, x, y);
	}

	abstract void drawStopFrontLeft(int centerTileX, int centerTileY);
	abstract void drawStopFrontRight(int centerTileX, int centerTileY);
	abstract void drawStopBackLeft(int centerTileX, int centerTileY);
	abstract void drawStopBackRight(int centerTileX, int centerTileY);
	abstract void drawMovingBackLeft(int centerTileX, int centerTileY);
	abstract void drawMovingBackRight(int centerTileX, int centerTileY);
	abstract void drawMovingFrontLeft(int centerTileX, int centerTileY);
	abstract void drawMovingFrontRight(int centerTileX, int centerTileY);

	void draw(int centerTileX, int centerTileY) {
		switch(state) {
			case STOP_FRONT_LEFT:
				drawStopFrontLeft(centerTileX, centerTileY);
			break;
			case STOP_FRONT_RIGHT:
				drawStopFrontRight(centerTileX, centerTileY);
			break;
			case STOP_BACK_LEFT:
				drawStopBackLeft(centerTileX, centerTileY);
			break;
			case STOP_BACK_RIGHT:
				drawStopBackRight(centerTileX, centerTileY);
			break;
			case MOVING_FRONT_LEFT:
				drawMovingFrontLeft(centerTileX, centerTileY);
			break;
			case MOVING_FRONT_RIGHT:
				drawMovingFrontRight(centerTileX, centerTileY);
			break;
			case MOVING_BACK_LEFT:
				drawMovingBackLeft(centerTileX, centerTileY);
			break;
			case MOVING_BACK_RIGHT:
				drawMovingBackRight(centerTileX, centerTileY);
			break;
		}
	}

	boolean moveToward() {
		double dx = destinationX - positionX;
		double dy = destinationY - positionY;
		double max;

		if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1) {
			return true;
		}

		if (Math.abs(dx) >= Math.abs(dy)) {
			max = Math.abs(dx);
		} else {
			max = Math.abs(dy);
		}
		positionX += dx/max;
		positionY += dy/max;
		return false;
	}

	void endMove() {
		putIn(gx, gy);
		positionX = 0;
		positionY = 0;
		destinationX = 0;
		destinationY = 0;
		if (state == State.MOVING_FRONT_LEFT)
			state = State.STOP_FRONT_LEFT;
		if (state == State.MOVING_FRONT_RIGHT)
			state = State.STOP_FRONT_RIGHT;
		if (state == State.MOVING_BACK_LEFT)
			state = State.STOP_BACK_LEFT;
		if (state == State.MOVING_BACK_RIGHT)
			state = State.STOP_BACK_RIGHT;
	}

	boolean isMoving() {
		if (state == State.MOVING_FRONT_LEFT ||
			state == State.MOVING_FRONT_RIGHT ||
			state == State.MOVING_BACK_LEFT ||
			state == State.MOVING_BACK_RIGHT)
			return true;
		return false;
	}

	void update() {
		if (isMoving()) {
			if (moveToward())
				endMove();
		}
	}

	boolean canGo(int gx, int gy) {
		if (gx < 0 || gx >= map.width || gy < 0 || gy >= map.depth)
			return false;
		if (map.map[gx][gy].height != map.map[x][y].height)
			return false;
		return true;
	}

	void moveLeftFront() {
		if (isMoving())
			return ;
		gx = x;
		gy = y - 1;
		state = State.STOP_FRONT_LEFT;
		if (canGo(gx, gy) == false)
			return ;
		state = State.MOVING_FRONT_LEFT;
		putIn(gx, gy);
		positionX = gapX;
		positionY = -gapY;
		destinationX = 0;
		destinationY = 0;
	}

	void moveRightBack() {
		if (isMoving())
			return ;
		gx = x;
		gy = y + 1;
		state = State.STOP_BACK_RIGHT;
		if (canGo(gx, gy) == false)
			return ;
		state = State.MOVING_BACK_RIGHT;
		destinationX += gapX;
		destinationY -= gapY;
	}

	void moveRightFront() {
		if (isMoving())
			return ;
		gx = x + 1;
		gy = y;
		state = State.STOP_FRONT_RIGHT;
		if (canGo(gx, gy) == false)
			return ;
		state = State.MOVING_FRONT_RIGHT;
		putIn(gx, gy);
		positionX = -gapX;
		positionY = -gapY;
		destinationX = 0;
		destinationY = 0;
	}

	void moveLeftBack() {
		if (isMoving())
			return ;
		gx = x - 1;
		gy = y;
		state = State.STOP_BACK_LEFT;
		if (canGo(gx, gy) == false)
			return ;
		state = State.MOVING_BACK_LEFT;
		destinationX -= gapX;
		destinationY -= gapY;
	}

	int frontY() {
		switch(state) {
			case STOP_FRONT_LEFT:
			case MOVING_FRONT_LEFT:
				return y-1;
			case STOP_BACK_RIGHT:
			case MOVING_BACK_RIGHT:
				return y+1;
		}
		return y;
	}

	int frontX() {
		switch(state) {
			case STOP_FRONT_RIGHT:
			case MOVING_FRONT_RIGHT:
				return x+1;
			case STOP_BACK_LEFT:
			case MOVING_BACK_LEFT:
				return x-1;
		}
		return x;
	}

}
