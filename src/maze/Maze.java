package maze;

import tools.*;
import text.*;

public class Maze {

	TextGenerator<Maze> narration = null;
	static Maze self = null;
	String story = "";

	Room actualRoom;

	public Maze() {
		self = this;
		actualRoom = new Room(null);
		actualRoom.use(actualRoom.entryW, actualRoom.entryD);

		narration = new TextGenerator<Maze>(this);

		Conceptualise.entity("LEFTRIGHT").asBagOfWords("on her left #addLeftDoor", "on her right #addRightDoor");
		Conceptualise.entity("ONETWO").asBagOfWords("one door @LEFTRIGHT", "two doors #addBothDoor");
		Conceptualise.entity("FOOD").asBagOfWords("a bone", "some fish", "some worms", "a carrot", "a lemon");
		Conceptualise.entity("ANIMAL").asBagOfWords("a cat", "a rabbit", "a crow", "a wolf");
		Conceptualise.entity("POTION").asBagOfWords("a blue potion", "a red potion", "a green potion");
		Conceptualise.entity("FURNITURE").asBagOfWords("a mirror", "a clock", "a chest");

		Conceptualise.entity("DIDIT").asText("She did it");
		Conceptualise.entity("DIDNOT").asText("But she did not do this");
		Conceptualise.entity("CHOICE").asBagOfWords("@DIDIT", "@DIDNOT");
		Conceptualise.entity("OBJECT").asBagOfWords("@ANIMAL", "@POTION", "@FURNITURE");

		Conceptualise.entity("ACTION")
		.asText("give @FOOD to him").when("OBJECT").equals("@ANIMAL")
		.asText("drink the potion").when("OBJECT").equals("@POTION")
		.asText("look the mirror").when("FURNITURE").contains("mirror")
		.asText("set the clock to 8 AM").when("FURNITURE").contains("clock")
		.asText("open the chest").when("FURNITURE").contains("chest");


		Conceptualise.entity("NEWROOM").asText("She found herself in a room with @ONETWO");
		Conceptualise.entity("OLDROOM")
		.asText("Again, she was in the room with two doors").when("ONETWO").contains("two")
		.asText("Again, she was in the room with one door").when("ONETWO").contains("one");
		Conceptualise.entity("DESCRIPTION")
		.asText("@OLDROOM").when("ONETWO").exists()
		.asText("@NEWROOM").when("ONETWO").doesNotExist();

		Conceptualise.entity("WAKEUP").asText("Alice woke up in a strange maze");
		Conceptualise.entity("ROOMMIDDLE").asText("In the middle of the room, there was @OBJECT");
		Conceptualise.entity("MIND").asText("She wanted to @ACTION");

		Conceptualise.entity("BACK").asText("She turned back #goBack").switchBack();
		Conceptualise.entity("LEFT").asText("She took the door on the left #goLeft").switchTo("leftContext");
		Conceptualise.entity("RIGHT").asText("She took the door on the right #goRight").switchTo("rightContext");
	}

	public void start() {
//		tell("@WAKEUP. @DESCRIPTION");
		narration.listen("Alice woke up in a strange maze. She found herself in a room with two doors. She took the door on the left. She found herself in a room with one door on her right. She took the door on the right. She found herself in a room with one door on her left.");
	}

	public String tell(String str) {
		if (str == null)
			return null;
		String story = narration.say(str);
		Log.debug(story);
		return story;
	}


	public void goBack() {
		actualRoom.goBack();
	}

	public void goRight() {
		actualRoom.goRight();
	}

	public void goLeft() {
		actualRoom.goLeft();
	}

	public void addRightDoor() {
		actualRoom.addRightDoor();
	}

	public void addLeftDoor() {
		actualRoom.addLeftDoor();
	}

	public void addBothDoor() {
		actualRoom.addBothDoor();
	}
}
