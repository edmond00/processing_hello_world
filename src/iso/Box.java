package iso;

public class Box extends IsoObject {

	public Box(IsoMap map, int x, int y) {
		super(map, x, y);
		action = new Action("open", "@OPEN");
	}

	void draw(int centerTileX, int centerTileY) {
		IsoEngine.self.app.draw("box", centerTileX-7, centerTileY-12);
	}
}

