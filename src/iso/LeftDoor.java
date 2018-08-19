package iso;

public class LeftDoor extends Door {

	public LeftDoor(IsoMap map, int x, int y) {
		super(map, x, y);
		action = new Action("open the door", "@LEFT. @DESCRIPTION");
	}

	void draw(int centerTileX, int centerTileY) {
		int height = map.getHeight(x, y);
		int rightHeight = map.getHeight(x+1, y);
		int y = centerTileY + map.wallH/2 + (map.wallH)*(height-rightHeight);
		int x = centerTileX + (map.tileW/4) -1;
		IsoEngine.self.app.draw("doorLeft", 0, x, y);
	}
}
