package iso;

public class Rabbit extends IsoObject {

	public Rabbit(IsoMap map, int x, int y) {
		super(map, x, y);
		action = new Action("give a piece of lemmon", "@BADFEED. @FEEDED");
	}

	void draw(int centerTileX, int centerTileY) {
		int step = IsoEngine.self.app.getStep();
		if (step % 10 < 5)
			IsoEngine.self.app.draw("rabbit", 0, centerTileX, centerTileY);
		else
			IsoEngine.self.app.draw("rabbit", 1, centerTileX, centerTileY);
	}
}

