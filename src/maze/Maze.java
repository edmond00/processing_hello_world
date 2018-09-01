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
	boolean rewriteFlag = false;

	Room actualRoom;

	public Maze() {
		self = this;
		actualRoom = new Room(null);
		actualRoom.use(actualRoom.entryW, actualRoom.entryD);

		narration = new TextGenerator<Maze>(this);

		Conceptualise.entity("QUAL").asBagOfWords("innocent", "little", "young", "lost");
		Conceptualise.entity("SHE").asLoop("alice", "she", "the @QUAL girl", "she", "the child", "she", "the girl", "she", "the @QUAL child", "she", "the girl", "she", "the child");

		
		Conceptualise.entity("NOEXIT").asBagOfWords("no exit", "no door", "no way to continue");
		Conceptualise.entity("LEFTRIGHT").asBagOfWords("on her left #addLeftDoor", "on her right #addRightDoor").startingWith(0,0);
		Conceptualise.entity("ONETWO").asBagOfWords("@NOEXIT", "one door @LEFTRIGHT", "two doors #addBothDoor").startingWith(1,1,0);
		Conceptualise.entity("JOURNAL").asBagOfWords("her @DREAM journal", "her @DREAM diary");
		Conceptualise.entity("DREAM").asBagOfWords("dream", "nightmare");

		Conceptualise.entity("NOTE").asBagOfWords("note", "write", "record", "rewrite", "revise", "change", "fix");

		Conceptualise.entity("BOX").asBagOfWords("a box", "a chest", "a @QBOX box", "a @QBOX chest");
		Conceptualise.entity("QBOX").asBagOfWords("mysterious", "wooden", "small", "big");

		Conceptualise.entity("DIDIT").asText("@SHE did it");
		Conceptualise.entity("DIDNOT").asText("but she did not do this");
		Conceptualise.entity("CHOICE").asBagOfWords("@DIDIT", "@DIDNOT");
		Conceptualise.entity("OBJECT").asBagOfWords("@JOURNAL #putJournal", "@BOX #putBox").startingWith(1,0);


		Conceptualise.entity("NEWROOM").asText("@SHE found herself in a room with @ONETWO, @ROOMMIDDLE");
		Conceptualise.entity("OLDROOM")
		.asText("again, @SHE was in the room with two doors").when("ONETWO").contains("two")
		.asText("again, @SHE was in the room with one door").when("ONETWO").contains("one")
		.asText("again, @SHE was in the room with @NOEXIT").when("ONETWO").contains("NOEXIT");
		Conceptualise.entity("DESCRIPTION")
		.asText("@OLDROOM").when("ONETWO").exists()
		.asText("@NEWROOM").when("ONETWO").doesNotExist();

		Conceptualise.entity("WAKEUP").asText("@SHE woke up in a strange maze");
		Conceptualise.entity("ROOMMIDDLE").asBagOfWords("the room was empty", "in the middle of the room, there was @OBJECT").startingWith(0,1,1);
		Conceptualise.entity("FIRSTJOURNAL").asText("there was @JOURNAL in the corner of the room #putCornerJournal");
		Conceptualise.entity("MIND").asText("@SHE wanted to @ACTION");

		Conceptualise.entity("BACK").asText("@SHE turned back #goBack").switchBack();
		Conceptualise.entity("LEFT").asText("@SHE took the door on the left #goLeft").switchTo("leftContext");
		Conceptualise.entity("RIGHT").asText("@SHE took the door on the right #goRight").switchTo("rightContext");

		Conceptualise.entity("REWRITE").asText("@SHE opened @JOURNAL to @NOTE her @DREAM #rewrite");


		Conceptualise.entity("EASYEDIT").asBagOfWords(
			"'A ROOM WITH ONE DOOR ON HER RIGHT' #newEdit?door&EASYEDIT",
			"'A ROOM WITH ONE DOOR ON HER LEFT' #newEdit?door&EASYEDIT",
			"'A ROOM WITH TWO DOORS' #newEdit?door&EASYEDIT"
		).startingWith(0);
		Conceptualise.entity("PAPER").asBagOfWords("paper", "letter", "message", "parchment");
		Conceptualise.entity("ITEM")
			.asText("a @PAPER with @EASYEDIT wrote on it");
		Conceptualise.entity("OPEN")
			.asText("@SHE opened again the box but it was empty").when("BOX").contains("box").and("OPEN").exists()
			.asText("@SHE opened again the chest but it was empty").when("BOX").contains("chest").and("OPEN").exists()
			.asText("@SHE opened the box and found inside @ITEM").when("BOX").contains("box")
			.asText("@SHE opened the chest and found inside @ITEM");
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

	public void putCornerJournal() {
		actualRoom.putCornerJournal();
	}

	public void rewrite() {
		rewriteFlag = true;
	}

	public void newEdit(String... arg) {
		if (narration.context != null) {
			String content = narration.context.hasSaid(arg[1]);
			if (content != null) {
				String editable = content.replaceAll("^[\"']|[\"']$", "").toLowerCase();;
				Log.debug("NEW EDIT GROUP : " + arg[0]);
				Log.debug("NEW EDIT ENTITY : " + arg[1]);
				Log.debug("NEW EDIT WORDS : " + editable);
			}
		}
	}
}
