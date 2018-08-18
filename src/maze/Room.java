package maze;

import tools.*;
import iso.*;

public class Room {
	
	Room backRoom = null;
	Room leftRoom = null;
	Room rightRoom = null;

	IsoMap map;
	int entryW;
	int entryD;
	int floorHeight;
	int wallHeightMin;
	int wallHeightMax;

	public Room(Room backRoom) {
		this.backRoom = backRoom;

		this.floorHeight = Rand.rand(1,3);
		this.wallHeightMin = floorHeight + 3;
		this.wallHeightMax = floorHeight + 6;
		this.map = new IsoMap(
			Rand.rand(4,10), Rand.rand(4,10),
			"leftWall",
			"rightWall",
			"floor",
			floorHeight,
			wallHeightMin, wallHeightMax
		);

		entryW = map.width/2;
		entryD = 0;
		if (backRoom != null) {
			for (int i = 0; i < map.width; i++) {
				if (i != entryW) {
					this.map.putTile(i, 0, new IsoTile(0, null, null, null));
				}
			}
			this.map.map[entryW][entryD].action = new Action("go back", "@BACK");
		}

		addLeftDoor();
		addRightDoor();
	}

	void addLeftDoor() {
		int w = 0;
		int d = 2 + Rand.rand(map.depth-3);
		map.map[w][d].height = wallHeightMax;
		new LeftDoor(map, w, d);
	}

	void addRightDoor() {
		int w = 2 + Rand.rand(map.width-3);
		int d = map.depth-1;
		map.map[w][d].height = wallHeightMax;
		new RightDoor(map, w, d);
	}

	void use() {
		IsoEngine.self.useMap(map, entryW, entryD);
		Maze.self.actualRoom = this;
	}
	
	void goBack() {
		Log.debug("GO BACK : " + backRoom);
		backRoom.use();
	}
	
	void goLeft() {
		if (leftRoom== null)
			leftRoom = new Room(this);
		leftRoom.use();
	}
	
	void goRight() {
		if (rightRoom== null)
			rightRoom = new Room(this);
		rightRoom.use();
	}
}
