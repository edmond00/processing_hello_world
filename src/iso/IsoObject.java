package iso;

public abstract class IsoObject {

	public int x;
	public int y;
	IsoMap map;
	Action action = null;

	public IsoObject(IsoMap map, int x, int y) {
		this.map = map;
		this.x = 0;
		this.y = 0;
		putIn(x, y);
	}

	abstract void draw(int centerTileX, int centerTileY);
	void update() {}

	void putIn(int x, int y) {
		this.map.objects[this.x][this.y].remove(this);
		this.x = x;
		this.y = y;
		this.map.objects[this.x][this.y].addLast(this);
	}

	void remove() {
		this.map.objects[this.x][this.y].remove(this);
	}
}
