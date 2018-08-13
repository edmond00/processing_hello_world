package iso;

public class IsoEngine implements IsoInterface {

	AppInterface app;
	IsoMap map;
	static IsoEngine self = null;
	int centerX;
	int centerY;
	int scrollX = 0;
	int scrollY = 0;

	public IsoEngine(AppInterface app, int centerX, int centerY) {
		this.app = app;
		this.self = this;
		this.centerX = centerX;
		this.centerY = centerY;

		this.map = new IsoMap(
			4, 5, 5,
			"wallLeft",
			"wallRight",
			"floor"
		);
		for (int w = 0; w < this.map.width; w++) {
			for (int d = 0; d < this.map.depth; d++) {
				this.map.map[1][w][d] = true;
			}
		}
	}

	public void draw() {
		map.draw(centerX+scrollX, centerY+scrollY);
	}
}

