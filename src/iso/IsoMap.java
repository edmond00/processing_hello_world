package iso;

import java.util.LinkedList;

public class IsoMap {

	static int tileW = 20;
	static int tileH = 13;
	static int wallH = 6;

	int backWall = 3;
	int frontWall = 7;

	String wallLeft;
	String wallRight;
	String floor;
	int width;
	int depth;
	LinkedList<IsoObject>[][] objects;
	int[][] map;

	public IsoMap(
		int width, int depth,
		String wallLeft,
		String wallRight,
		String floor
	) {
		this.width = width;
		this.depth = depth;
		this.wallLeft = wallLeft;
		this.wallRight = wallRight;
		this.floor = floor;

		map = new int[width][depth];
		objects = new LinkedList[width][depth];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < depth; j++) {
				objects[i][j] = new LinkedList<IsoObject>();
			}
		}

	}

	void update() {
		for (int w = 0; w < width; w++) {
			for (int d = depth - 1; d >= 0; d--) {
				for (IsoObject o : objects[w][d]) {
					o.update();
				}
			}
		}
	}

	void drawObjects(int w, int d, int centerTileX, int centerTileY) {
		for (IsoObject o : objects[w][d]) {
			o.draw(centerTileX, centerTileY);
		}
	}

	public void drawFloor(int x, int y) {
		IsoEngine.self.app.draw(floor, x, y);
	}

	public void drawWall(int x, int y) {
		IsoEngine.self.app.draw(wallLeft, x, y+tileH/2);
		IsoEngine.self.app.draw(wallRight, x+tileW/2-1, y+tileH/2);
	}

	public void drawLeftBackWall(int x, int y) {
		for (int i = 0; i < backWall; i++) {
			IsoEngine.self.app.draw(wallRight, x, y-tileH/2);
			y -= wallH;
		}
	}

	public void drawRightBackWall(int x, int y) {
		for (int i = 0; i < backWall; i++) {
			IsoEngine.self.app.draw(wallLeft, x+tileW/2-1, y-tileH/2);
			y -= wallH;
		}
	}

	public void drawLeftFrontWall(int x, int y) {
		for (int i = 0; i < frontWall; i++) {
			IsoEngine.self.app.draw(wallLeft, x, y+tileH/2+1);
			y += wallH;
		}
	}

	public void drawRightFrontWall(int x, int y) {
		for (int i = 0; i < frontWall; i++) {
			IsoEngine.self.app.draw(wallRight, x+tileW/2-1, y+tileH/2+1);
			y += wallH;
		}
	}

	public void draw(int centerX, int centerY) {
		int ooy = centerY - (tileH-1)/2;
		int oox = centerX - (width * (tileW-1))/2;
		for (int w = 0; w < width; w++) {
			int ox = oox + (tileH/2) * (depth+1);
			int oy = ooy - (tileW/4) * (depth);
			for (int d = depth - 1; d >= 0; d--) {
				if (map[w][d] >= 0) {
					int x = ox;
					int y = oy - (map[w][d]*wallH);
					drawFloor(x, y);
					for (int h = 0; h < map[w][d]; h++) {
						drawWall(x, y + (h*wallH) + 1);
					}
					if (w == 0 /*|| map[w-1][d] < 0*/)
						drawLeftBackWall(x, y);
					if (d == depth-1 /*|| map[w][d+1] < 0*/)
						drawRightBackWall(x, y);
					if (d == 0 || map[w][d-1] < 0)
						drawLeftFrontWall(x, y);
					if (w == width-1 || map[w+1][d] < 0)
						drawRightFrontWall(x, y);
					drawObjects(w, d, x+tileW/2, y+tileH/2);
				}
				oy += tileH / 2;
				ox -= tileW / 2 -1;
			}
			ooy += tileH / 2;
			oox += tileW / 2 -1;
		}
	}
}
