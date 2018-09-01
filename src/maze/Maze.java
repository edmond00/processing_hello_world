package maze;

import tools.*;
import text.*;
import game.*;

public class Maze {

	TextGenerator<Maze> narration = null;
	public static Maze self = null;
	public String story = "";
	public static boolean firstRun = true;
	String buffer = "";
	public boolean rewriteFlag = false;

	Room actualRoom;

	public Maze() {
		self = this;
		actualRoom = new Room(null);
		actualRoom.use(actualRoom.entryW, actualRoom.entryD);

		narration = new TextGenerator<Maze>(this);

		Conceptualise.entity("ANIMAL").asBagOfWords(
			"a cat #putCat",
			"a crow #putCrow",
			"a rabbit #putRabbit",
			"a @QANIMAL cat #putCat",
			"a @QANIMAL crow #putCrow",
			"a @QANIMAL rabbit #putRabbit"
		);
		Conceptualise.entity("QANIMAL").asLoop("smilling", "clever", "hungry", "lonely", "happy", "smart", "playful", "greedy");
		Conceptualise.entity("THEANIMAL")
			.asText("the cat").when("ANIMAL").contains("cat")
			.asText("the crow").when("ANIMAL").contains("crow")
			.asText("the rabbit").when("ANIMAL").contains("rabbit");
		Conceptualise.entity("FOOD").asBagOfWords("a cup of milk", "some worms", "a carrot");
		Conceptualise.entity("BADFEED").asText("@SHE gave him a piece of lemon #feedSound");
		Conceptualise.entity("GOODFEED").asText("@SHE gave him @FOOD #feedSound");
		Conceptualise.entity("FEEDED")
			.asText("@THEANIMAL liked it very much and gave her a @PAPER with @MEDIUMEDIT written on it").when("ANIMAL").contains("cat").and("FOOD").contains("milk")
			.asText("@THEANIMAL liked it very much and gave her a @PAPER with @MEDIUMEDIT written on it").when("ANIMAL").contains("crow").and("FOOD").contains("worm")
			.asText("@THEANIMAL liked it very much and gave her a @PAPER with @MEDIUMEDIT written on it").when("ANIMAL").contains("rabbit").and("FOOD").contains("carrot")
			.asText("however, @THEANIMAL did not like this");


		Conceptualise.entity("QUEEN").asBagOfWords(
			"the queen of heart #putQueen?HEART",
			"the queen of spade #putQueen?SPADE",
			"the queen of diamond #putQueen?DIAMOND",
			"the queen of clubs #putQueen?CLUB");

		Conceptualise.entity("QUAL").asBagOfWords("innocent", "little", "young", "lost");
		Conceptualise.entity("SHE").asLoop("alice", "she", "the @QUAL girl", "she", "the child", "she", "the girl", "she", "the @QUAL child", "she", "the girl", "she", "the child");

		
		Conceptualise.entity("NOEXIT").asBagOfWords("no exit", "no door", "no way to continue");
		Conceptualise.entity("LEFTRIGHT").asBagOfWords("on her left #addLeftDoor", "on her right #addRightDoor").startingWith(0,0);
		Conceptualise.entity("ONETWO").asBagOfWords("@NOEXIT", "one door @LEFTRIGHT", "two doors #addBothDoor").startingWith(1,1,0,2);
		Conceptualise.entity("JOURNAL").asBagOfWords("her @DREAM journal", "her @DREAM diary");
		Conceptualise.entity("DREAM").asBagOfWords("dream", "nightmare");

		Conceptualise.entity("NOTE").asBagOfWords("note", "write", "record", "rewrite", "revise", "change", "fix");

		Conceptualise.entity("BOX").asBagOfWords("a box", "a chest", "a @QBOX box", "a @QBOX chest");
		Conceptualise.entity("QBOX").asBagOfWords("mysterious", "wooden", "small", "big");

		Conceptualise.entity("OBJECT").asBagOfWords("there was @JOURNAL #putJournal", "there was @BOX #putBox", "there was @ANIMAL", "there was @QUEEN").startingWith(1,0);


		Conceptualise.entity("NEWROOM").asText("@SHE found herself in a room with @ONETWO, @ROOMMIDDLE");
		Conceptualise.entity("OLDROOM")
		.asText("again, @SHE was in the room with two doors").when("ONETWO").contains("two")
		.asText("again, @SHE was in the room with one door").when("ONETWO").contains("one")
		.asText("again, @SHE was in the room with @NOEXIT").when("ONETWO").contains("NOEXIT");
		Conceptualise.entity("DESCRIPTION")
		.asText("@OLDROOM").when("ONETWO").exists()
		.asText("@NEWROOM").when("ONETWO").doesNotExist();

		Conceptualise.entity("WAKEUP").asText("@SHE woke up in a strange maze");
		Conceptualise.entity("ROOMMIDDLE").asBagOfWords("the room was empty", "in the middle of the room, @OBJECT", "inside the room, @OBJECT", "in the room, @OBJECT", "@OBJECT inside it", "@OBJECT in this room").startingWith(0,1,1);
		Conceptualise.entity("FIRSTJOURNAL").asText("there was @JOURNAL in the corner of the room #putCornerJournal");
		Conceptualise.entity("MIND").asText("@SHE wanted to @ACTION");

		Conceptualise.entity("BACK").asText("@SHE turned back #goBack").switchBack();
		Conceptualise.entity("LEFT").asText("@SHE took the door on the left #goLeft").switchTo("leftContext");
		Conceptualise.entity("RIGHT").asText("@SHE took the door on the right #goRight").switchTo("rightContext");

		Conceptualise.entity("REWRITE").asText("@SHE opened @JOURNAL to @NOTE her @DREAM #rewrite");


		Conceptualise.entity("EASYEDIT").asBagOfWords(
			"'A ROOM WITH ONE DOOR ON HER RIGHT' #newEdit?door&EASYEDIT",
			"'A ROOM WITH ONE DOOR ON HER LEFT' #newEdit?door&EASYEDIT",
			"'A ROOM WITH TWO DOORS' #newEdit?door&EASYEDIT",
			"'A CUP OF MILK' #newEdit?food&EASYEDIT",
			"'SOME WORMS' #newEdit?food&EASYEDIT",
			"'A CARROT' #newEdit?food&EASYEDIT"
		).startingWith(0);
		Conceptualise.entity("MEDIUMEDIT").asBagOfWords(
			"'A CAT' #newEdit?animal&MEDIUMEDIT",
			"'A CROW' #newEdit?animal&MEDIUMEDIT",
			"'A RABBIT' #newEdit?animal&MEDIUMEDIT"
		);
		Conceptualise.entity("PAPER").asBagOfWords("paper", "letter", "message", "parchment");
		Conceptualise.entity("ITEM")
			.asText("a @PAPER with @EASYEDIT written on it");
		Conceptualise.entity("OPEN")
			.asText("@SHE opened again the box but it was empty #openBoxSound").when("BOX").contains("box").and("OPEN").exists()
			.asText("@SHE opened again the chest but it was empty #openBoxSound").when("BOX").contains("chest").and("OPEN").exists()
			.asText("@SHE opened the box and found inside @ITEM #openBoxSound").when("BOX").contains("box")
			.asText("@SHE opened the chest and found inside @ITEM #openBoxSound");
	}


	public void listen(String str) {
		this.story = str;
		narration.listen(str);
	}

	public void start(String story) {
		narration.clean();
		listen(story);
		Log.debug("start form : " + story);
		narration.global.printHistory();
		if (narration.global.hasSaid("WAKEUP") == null)
			tell("@WAKEUP");
		if (narration.context.hasSaid("DESCRIPTION") == null)
			tell("@DESCRIPTION");
		if (narration.context.hasSaid("GOODFEED") != null && narration.context.hasSaid("FEEDED") == null)
			tell("@FEEDED");
		if (firstRun == false && narration.global.hasSaid("JOURNAL") == null) {
			Log.debug("\nHISTORY : ");
			narration.global.printHistory();
			Log.debug("\n");
			tell("@FIRSTJOURNAL");
		}
	}

	public String tell(String str) {
		if (str == null)
			return null;
		String text = narration.say(str);
		Log.debug("tell : " + text);
		this.buffer = this.buffer + " " + text;
//		this.story = this.story + " " + text;
		return text;
	}
	
	public void update(int timestep) {
		if (rewriteFlag == true) {
			fastUpdate();
			rewriteFlag = false;
			Game.app.rewrite(story);
			return ;
		}
		if (timestep % 1 == 0) {
			if (buffer.length() > 0) {
				story = story + buffer.charAt(0);
				if (timestep % 2 == 0)
					Game.app.sound.play("write2");
				buffer = buffer.substring(1);
			}
		}
	}

	public void fastUpdate() {
		story = story + buffer;
		buffer = "";
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
	public void putJournal() {
		firstRun = false;
		actualRoom.putJournal();
	}

	public void putBox() {
		actualRoom.putBox();
	}
	public void putCat() {
		actualRoom.putCat();
	}
	public void putCrow() {
		actualRoom.putCrow();
	}
	public void putRabbit() {
		actualRoom.putRabbit();
	}
	public void putQueen(String... color) {
		actualRoom.putQueen(color[0]);
	}

	public void putCornerJournal() {
		actualRoom.putCornerJournal();
	}

	public void rewrite() {
		rewriteFlag = true;
	}

	public void openBoxSound() {
		Game.app.sound.play("openBox");
	}
	public void feedSound() {
		Game.app.sound.play("feed");
	}

	public void newEdit(String... arg) {
		if (narration.context != null) {
			String content = narration.context.hasSaid(arg[1]);
			if (content != null) {
				String editable = content.replaceAll("^[\"']|[\"']$", "").toLowerCase();;
				Log.debug("NEW EDIT GROUP : " + arg[0]);
				Log.debug("NEW EDIT ENTITY : " + arg[1]);
				Log.debug("NEW EDIT WORDS : " + editable);
				Game.app.editor.addChoice(arg[0], editable);
			}
		}
	}
}
