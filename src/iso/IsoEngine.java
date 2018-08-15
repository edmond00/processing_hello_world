package iso;

public class IsoEngine implements IsoInterface {

	AppInterface app;
	Avatar avatar;
	IsoMap map;
	static IsoEngine self = null;
	int centerX;
	int centerY;
	int scrollX = 0;
	int scrollY = 0;

	public IsoEngine(AppInterface app, int centerX, int centerY) {
		this.app = app;
		this.self = this;
		this.centerX = centerX;
		this.centerY = centerY;

		this.map = new IsoMap(
			5, 6,
			"leftWall",
			"rightWall",
			"floor"
		);
		for (int w = 0; w < this.map.width; w++) {
			this.map.map[w][0] = -1;
			for (int d = 1; d < this.map.depth; d++) {
				this.map.map[w][d] = 0;
			}
		}
		this.map.map[0][5] = 3;
		this.map.map[2][0] = 0;
		this.avatar = new Avatar(map, 2, 0);
	}

	public void left() {
		avatar.moveLeftFront();
	}

	public void right() {
		avatar.moveRightBack();
	}

	public void up() {
		avatar.moveLeftBack();
	}

	public void down() {
		avatar.moveRightFront();
	}

	public void update() {
		map.update();
	}

	public void draw() {
		map.draw(centerX+scrollX, centerY+scrollY);
	}
}

