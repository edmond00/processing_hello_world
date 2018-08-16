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
	IsoTile[][] map;

	public IsoMap(
		int width, int depth,
		String wallLeft,
		String wallRight,
		String floor
	) {
		this.width = width;
		this.depth = depth;

		map = new IsoTile[width][depth];
		objects = new LinkedList[width][depth];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < depth; j++) {
				if (i == 0 || j == depth-1)
					putTile(i, j, new IsoTile(7, wallRight, wallLeft, null));
				else
					putTile(i, j, new IsoTile(2, wallRight, wallLeft, floor));
				objects[i][j] = new LinkedList<IsoObject>();
			}
		}

	}

	void putTile(int w, int d, IsoTile tile) {
		map[w][d] = tile;
		tile.w = w;
		tile.d = d;
		tile.map = this;
	}

	int getHeight(int w, int d) {
		if (w < 0 || d < 0 || w >= width || d >= depth)
			return 0;
		return map[w][d].height;
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

	public void draw(int centerX, int centerY) {
		int ooy = centerY - (tileH-1)/2;
		int oox = centerX - (width * (tileW-1))/2;
		for (int w = 0; w < width; w++) {
			int ox = oox + (tileH/2) * (depth+1);
			int oy = ooy - (tileW/4) * (depth);
			for (int d = depth - 1; d >= 0; d--) {
				int H = map[w][d].height;
				int x = ox;
				int y = oy - (H*wallH);
				map[w][d].drawFloor(x, y);
				map[w][d].drawWall(x, y+1);
				drawObjects(w, d, x+tileW/2, y+tileH/2);
				oy += tileH / 2;
				ox -= tileW / 2 -1;
			}
			ooy += tileH / 2;
			oox += tileW / 2 -1;
		}
	}

	public IsoObject getFirstObjectAt(int w, int d) {
		if (w < 0 || d < 0 || w >= width || d >= depth)
			return null;
		LinkedList<IsoObject> listObjects = objects[w][d];
		if (listObjects.size() <= 0)
			return null;
		return listObjects.getFirst();
	}
}
