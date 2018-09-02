package maze;

import tools.*;
import iso.*;
import game.*;

public class Room {
	
	Room backRoom = null;
	Room leftRoom = null;
	Room rightRoom = null;
	IsoObject leftDoor = null;
	IsoObject rightDoor = null;

	IsoMap map;
	int entryW;
	int entryD;
	int floorHeight;
	int wallHeightMin;
	int wallHeightMax;
	String ambiance = "cold";

	public Room(Room backRoom) {
		this.backRoom = backRoom;

		this.floorHeight = Rand.rand(1,3);
		this.wallHeightMin = floorHeight + 3;
		this.wallHeightMax = floorHeight + 6;
		this.map = new IsoMap(
			Rand.rand(4,10), Rand.rand(5,10),
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
			this.map.map[entryW][entryD].action = new Action("go back", "@BACK. @DESCRIPTION");
		}

	}

	void addBothDoor() {
		addLeftDoor();
		addRightDoor();
	}

	void addLeftDoor() {
		if (leftDoor != null)
			return ;
		int w = 0;
		int d = 1 + Rand.rand(map.depth-3);
		map.map[w][d].height = wallHeightMax -2;
		leftDoor = new LeftDoor(map, w, d);
	}

	void addRightDoor() {
		if (rightDoor != null)
			return ;
		int w = 2 + Rand.rand(map.width-3);
		int d = map.depth-1;
		map.map[w][d].height = wallHeightMax -2;
		rightDoor = new RightDoor(map, w, d);
	}

	void use(int pw, int pd) {
		IsoEngine.self.useMap(map, pw, pd);
		Game.app.sound.play("changeRoom");
		Game.app.glitch = true;
		Game.app.ambiance = ambiance;
		Maze.self.fastUpdate();
		Maze.self.actualRoom = this;
	}
	
	void goBack() {
		Log.debug("GO BACK : " + backRoom);
		int pw, pd;
		if (backRoom.leftRoom == this) {
			pw = backRoom.leftDoor.x + 1;
			pd = backRoom.leftDoor.y;
		} else {
			pw = backRoom.rightDoor.x;
			pd = backRoom.rightDoor.y - 1;
		}
		backRoom.use(pw, pd);
	}
	
	void goLeft() {
		if (leftRoom == null)
			leftRoom = new Room(this);
		leftRoom.use(leftRoom.entryW, leftRoom.entryD);
	}
	
	void goRight() {
		if (rightRoom == null)
			rightRoom = new Room(this);
		rightRoom.use(rightRoom.entryW, rightRoom.entryD);
	}

	void putJournal() {
		int w = 2 + Rand.rand(map.width-2);
		int d = 2 + Rand.rand(map.depth-4);
		new Diary(map, w, d);
	}

	void putBox() {
		int w = 2 + Rand.rand(map.width-2);
		int d = 2 + Rand.rand(map.depth-4);
		new Box(map, w, d);
	}
	void putCat() {
		int w = 2 + Rand.rand(map.width-2);
		int d = 2 + Rand.rand(map.depth-4);
		new Cat(map, w, d);
	}
	void putCrow() {
		int w = 2 + Rand.rand(map.width-2);
		int d = 2 + Rand.rand(map.depth-4);
		new Crow(map, w, d);
	}
	void putRabbit() {
		int w = 2 + Rand.rand(map.width-2);
		int d = 2 + Rand.rand(map.depth-4);
		new Rabbit(map, w, d);
	}
	void putQueen(String color) {
		int w = 2 + Rand.rand(map.width-2);
		int d = 2 + Rand.rand(map.depth-4);
		new Queen(map, w, d, color);
	}
	void putClock() {
		int w = 2 + Rand.rand(map.width-2);
		int d = 2 + Rand.rand(map.depth-4);
		new Clock(map, w, d);
	}
	void putHatter() {
		int w = 2 + Rand.rand(map.width-2);
		int d = 2 + Rand.rand(map.depth-4);
		new Hatter(map, w, d);
	}

	void putCornerJournal() {
		int w = map.width-1;
		int d = map.depth-2;
		new Diary(map, w, d);
	}
}
