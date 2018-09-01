package iso;

public class Cat extends IsoObject {

	public Cat(IsoMap map, int x, int y) {
		super(map, x, y);
		action = new Action("give a piece of lemmon", "@BADFEED. @FEEDED");
	}

	void draw(int centerTileX, int centerTileY) {
		int step = IsoEngine.self.app.getStep();
		if (step % 10 < 5)
			IsoEngine.self.app.draw("cat", 0, centerTileX, centerTileY);
		else
			IsoEngine.self.app.draw("cat", 1, centerTileX, centerTileY);
	}
}

