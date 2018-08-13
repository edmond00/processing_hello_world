package iso;

public class IsoMap {

	static int tileW = 20;
	static int tileH = 13;
	static int wallH = 6;
	static int spanW = 2;
	static int spanH = 1;
	static int spanUp = 1;
	static int spanDown = 0;

	String wallLeft;
	String wallRight;
	String floor;
	int width;
	int depth;
	int height;
	boolean[][][] map;

	public IsoMap(
		int height, int width, int depth,
		String wallLeft,
		String wallRight,
		String floor
	) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.wallLeft = wallLeft;
		this.wallRight = wallRight;
		this.floor = floor;

		map = new boolean[height][width][depth];
	}

	public void drawFloor(int x, int y) {
		IsoEngine.self.app.draw(floor, x, y);
	}

	public void draw(int centerX, int centerY) {
		int ooy = centerY - (tileH-spanH)/2;
		int oox = centerX - (width * (tileW-spanW))/2;
		for (int h = 0; h < height; h++) {
			int ox = oox;
			int oy = ooy;
			for (int w = 0; w < width; w++) {
				int x = ox;
				int y = oy;
				for (int d = 0; d < depth; d++) {
					if (map[h][w][d] == true) {
						drawFloor(x, y);
					}
					y -= tileH / 2;
					x += tileW / 2;
				}
				oy += tileH / 2;
				ox += tileW / 2;
			}
			ooy -= wallH;
		}
	}
}
