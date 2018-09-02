package iso;

public class Hatter extends IsoObject {

	public Hatter(IsoMap map, int x, int y) {
		super(map, x, y);
		action = new Action("drink tea with the hatter", "@DRINKTEA. @HATTERSPEAK");
	}

	void draw(int centerTileX, int centerTileY) {
		int step = IsoEngine.self.app.getStep();
		int frame = 0;
		IsoEngine.self.app.draw("hatter", frame, centerTileX, centerTileY);
	}
}

