package iso;

public class Avatar extends MovingObject {

	public Avatar(IsoMap map, int x, int y) {
		super(map, x, y);
	}

	void drawStopFrontLeft(int centerTileX, int centerTileY) {
		IsoEngine.self.app.draw("aliceFrontLeft", 0,
			centerTileX+(int)positionX, centerTileY+(int)positionY);
	}
	void drawStopFrontRight(int centerTileX, int centerTileY) {
		IsoEngine.self.app.draw("aliceFrontRight", 0,
			centerTileX+(int)positionX, centerTileY+(int)positionY);
	}
	void drawStopBackLeft(int centerTileX, int centerTileY) {
		IsoEngine.self.app.draw("aliceBackLeft", 1,
			centerTileX+(int)positionX, centerTileY+(int)positionY);
	}
	void drawStopBackRight(int centerTileX, int centerTileY) {
		IsoEngine.self.app.draw("aliceBackRight", 1,
			centerTileX+(int)positionX, centerTileY+(int)positionY);
	}
	void drawMovingBackLeft(int centerTileX, int centerTileY) {
		int step = IsoEngine.self.app.getStep();
		if (step % 6 < 3) {
			IsoEngine.self.app.draw("aliceBackLeft", 0,
				centerTileX+(int)positionX, centerTileY+(int)positionY);
		} else {
			IsoEngine.self.app.draw("aliceBackLeft", 1,
				centerTileX+(int)positionX, centerTileY+(int)positionY);
		}
	}
	void drawMovingBackRight(int centerTileX, int centerTileY) {
		int step = IsoEngine.self.app.getStep();
		if (step % 6 < 3) {
			IsoEngine.self.app.draw("aliceBackRight", 0,
				centerTileX+(int)positionX, centerTileY+(int)positionY);
		} else {
			IsoEngine.self.app.draw("aliceBackRight", 1,
				centerTileX+(int)positionX, centerTileY+(int)positionY);
		}
	}
	void drawMovingFrontLeft(int centerTileX, int centerTileY) {
		int step = IsoEngine.self.app.getStep();
		if (step % 6 < 3) {
			IsoEngine.self.app.draw("aliceFrontLeft", 0,
				centerTileX+(int)positionX, centerTileY+(int)positionY);
		} else {
			IsoEngine.self.app.draw("aliceFrontLeft", 1,
				centerTileX+(int)positionX, centerTileY+(int)positionY);
		}
	}
	void drawMovingFrontRight(int centerTileX, int centerTileY) {
		int step = IsoEngine.self.app.getStep();
		if (step % 6 < 3) {
			IsoEngine.self.app.draw("aliceFrontRight", 0,
				centerTileX+(int)positionX, centerTileY+(int)positionY);
		} else {
			IsoEngine.self.app.draw("aliceFrontRight", 1,
				centerTileX+(int)positionX, centerTileY+(int)positionY);
		}
	}

	void update() {
		super.update();
		IsoObject frontObject = map.getFirstObjectAt(frontX(), frontY());
		action = null;
		if (frontObject != null && frontObject.action != null) {
			action = frontObject.action;
		}
		if (action == null)
			action = map.map[x][y].action;
	}

	void changeMap(IsoMap map, int entryW, int entryD) {
		this.map.objects[this.x][this.y].remove(this);
		this.map = map;
		this.x = entryW;
		this.y = entryD;
		this.map.objects[this.x][this.y].addLast(this);
	}
}
