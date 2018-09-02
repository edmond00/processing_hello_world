package iso;

public class Queen extends IsoObject {

	String color;
	public Queen(IsoMap map, int x, int y, String color) {
		super(map, x, y);
		this.color = color;
		action = new Action("speak with the queen", "@ALICESPEAK, @QUEENSPEAK");
	}

	void draw(int centerTileX, int centerTileY) {
		int step = IsoEngine.self.app.getStep();
		int frame = 0;
		if (step % 10 < 5)
			frame = 1;
		if (color.equals("HEART"))
			IsoEngine.self.app.draw("heartQueen", frame, centerTileX, centerTileY);
		if (color.equals("DIAMOND"))
			IsoEngine.self.app.draw("diamondQueen", frame, centerTileX, centerTileY);
		if (color.equals("SPADE"))
			IsoEngine.self.app.draw("spadeQueen", frame, centerTileX, centerTileY);
		if (color.equals("CLUB"))
			IsoEngine.self.app.draw("clubQueen", frame, centerTileX, centerTileY);
	}
}

