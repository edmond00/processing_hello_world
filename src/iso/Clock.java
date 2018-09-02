package iso;

public class Clock extends IsoObject {

	public Clock(IsoMap map, int x, int y) {
		super(map, x, y);
		action = new Action("set to 12 o'clock", "@BADSET");
	}

	void draw(int centerTileX, int centerTileY) {
		int step = IsoEngine.self.app.getStep();
		int frame = 0;
		if (step % 10 < 5)
			frame = 1;
		IsoEngine.self.app.draw("clock", frame, centerTileX, centerTileY);
	}
}

