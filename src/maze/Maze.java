package maze;

import tools.*;
import text.*;

public class Maze {

	TextGenerator<Maze> narration = null;
	static Maze self = null;

	Room actualRoom;

	public Maze() {
		self = this;
		actualRoom = new Room(null);
		actualRoom.use();

		narration = new TextGenerator<Maze>(this);

		Conceptualise.entity("LEFTRIGHT").asBagOfWords("on her left", "on her right");
		Conceptualise.entity("ONETWO").asBagOfWords("one door @LEFTRIGHT #test?one", "two doors #test?two");
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


		Conceptualise.entity("WAKEUP").asText("Alice woke up in a strange maze");
		Conceptualise.entity("ROOMDOOR").asText("She found herself in a room with @ONETWO");
		Conceptualise.entity("ROOMMIDDLE").asText("In the middle of the room, there was @OBJECT");
		Conceptualise.entity("MIND").asText("She wanted to @ACTION");

		Conceptualise.entity("BACK").asText("She turned back #goBack");
		Conceptualise.entity("LEFT").asText("She took the door on the left #goLeft");
		Conceptualise.entity("RIGHT").asText("She took the door on the right #goRight");
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
}
