package iso;

public class RightDoor extends Door {

	public RightDoor(IsoMap map, int x, int y) {
		super(map, x, y);
		action = new Action("open the door", "@RIGHT");;
	}

	void draw(int centerTileX, int centerTileY) {
		int height = map.getHeight(x, y);
		int leftHeight = map.getHeight(x, y-1);
		int y = centerTileY + map.wallH/2 + (map.wallH)*(height-leftHeight);
		int x = centerTileX - (map.tileW/4);
		IsoEngine.self.app.draw("doorRight", 0, x, y);
	}
}
