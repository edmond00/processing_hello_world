package iso;

public class IsoEngine implements IsoInterface {

	AppInterface app;
	public Avatar avatar;
	public IsoMap map;
	public static IsoEngine self = null;
	int centerX;
	int centerY;
	int scrollX = 0;
	int scrollY = 0;

	public IsoEngine(AppInterface app, int centerX, int centerY) {
		this.app = app;
		this.self = this;
		this.centerX = centerX;
		this.centerY = centerY;

		this.map = null;
		this.avatar = null;
	}

	public void useMap(IsoMap map, int entryW, int entryD) {
		this.map = map;
		if (avatar == null)
			this.avatar = new Avatar(map, entryW, entryD);
		else
			this.avatar.changeMap(map, entryW, entryD);
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

