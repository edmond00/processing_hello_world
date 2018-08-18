package iso;

public class IsoTile {
	public Action action = null;
	public int height = 0;
	int w = 0;
	int d = 0;
	String wallRight = null;
	String wallLeft = null;
	String floor = null;
	IsoMap map = null;

	public IsoTile(int h, String wr, String wl, String f) {
		height = h;
		wallRight = wr;
		wallLeft = wl;
		floor = f;
	}

	public void drawFloor(int x, int y) {
		IsoEngine.self.app.draw(floor, x, y);
	}

	public void drawWall(int x, int y) {
		int leftH = map.getHeight(w, d-1);	
		int rightH = map.getHeight(w+1, d);	
		for (int h = 0; h < height-leftH; h++) {
				IsoEngine.self.app.draw(wallLeft, x, y+IsoMap.tileH/2*(h+1));
		}
		for (int h = 0; h < height-rightH; h++) {
				IsoEngine.self.app.draw(wallRight, x+IsoMap.tileW/2-1, y+(IsoMap.tileH/2*(h+1)));
		}
	}
}
