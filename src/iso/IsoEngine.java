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
			8, 7,
			"leftWall",
			"rightWall",
			"floor"
		);
		this.map.map[3][3].height = 3;
		for (int i = 0; i < map.width; i++) {
			if (i != 4) {
				this.map.map[i][0].height = 0;
				this.map.map[i][0].floor = null;
				this.map.map[i][0].wallLeft = null;
				this.map.map[i][0].wallRight = null;
			}
		}
		this.avatar = new Avatar(map, 4, 1);
		this.map.map[0][1].floor = "floor";
		this.map.map[4][0].action = new Action("go back", "@BACK");
		new LeftDoor(map, 0, 1);
		new RightDoor(map, 1, 6);
	}

	public String getAction() {
		if (avatar.action == null)
			return null;
		return avatar.action.name;
	}

	public String getNarration() {
		if (avatar.action == null)
			return null;
		return avatar.action.narration;
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

