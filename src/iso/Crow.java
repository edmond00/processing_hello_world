package iso;

public class Crow extends IsoObject {

	public Crow(IsoMap map, int x, int y) {
		super(map, x, y);
		action = new Action("give a piece of lemmon", "@BADFEED. @FEEDED");
	}

	void draw(int centerTileX, int centerTileY) {
		int step = IsoEngine.self.app.getStep();
		if (step % 10 < 7)
			IsoEngine.self.app.draw("crow", 0, centerTileX, centerTileY);
		else
			IsoEngine.self.app.draw("crow", 1, centerTileX, centerTileY);
	}
}

