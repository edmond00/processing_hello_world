package iso;

public class Diary extends IsoObject {

	public Diary(IsoMap map, int x, int y) {
		super(map, x, y);
		action = new Action("open your dream journal", "@REWRITE");
	}

	void draw(int centerTileX, int centerTileY) {
		IsoEngine.self.app.draw("diary", centerTileX-7, centerTileY-7);
	}
}

