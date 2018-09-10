package maze;

import tools.*;
import text.*;
import game.*;

public class Maze {

	public TextGenerator<Maze> narration = null;
	public static Maze self = null;
	public String story = "";
	public static boolean firstRun = true;
	String buffer = "";
	public boolean rewriteFlag = false;

	Room actualRoom;

	public Maze(TextGenerator<Maze> tg) {
		self = this;
		actualRoom = new Room(null);
		actualRoom.use(actualRoom.entryW, actualRoom.entryD);
		narration = tg;
		tg.scene = this;
	}
	public Maze() {
		self = this;
		actualRoom = new Room(null);
		actualRoom.use(actualRoom.entryW, actualRoom.entryD);
		newNarration();
	}

	public void newNarration() {
		narration = new TextGenerator<Maze>(this);

		Conceptualise.entity("AMBIANCE").asBagOfWords("cold #ambiance?cold", "gloomy #ambiance?gloomy", "warm #ambiance?warm", "dark #ambiance?dark").startingWith(0);

		Conceptualise.entity("DRINKTEA").asBagOfWords(
			"@SHE asked the hatter if he wants some tea",
			"@SHE asked the hatter if he wants a cup of tea",
			"@SHE propose to drink a tea with the hatter",
			"@SHE propose a cup of tea to the hatter"
		);

		Conceptualise.entity("TEA0").asBagOfWords(
			"it is not a time to drink tea",
			"it is not the good time for a tea"
		);
		Conceptualise.entity("TEA1").asBagOfWords(
			"let`s drink a tea together at 3 o`clock",
			"come see me at 3 o`clock, @TEA2",
			"come back at 3 o`clock and @TEA2"
		);
		Conceptualise.entity("TEA2").asBagOfWords(
			"we will drink a tea together",
			"i will be pleased to drink a tea with you"
		);

		Conceptualise.entity("NOTIMEFORTEA").asText("'@TEA0, @TEA1' says the hatter");
		Conceptualise.entity("TEATIME").asText("@SHE and the hatter drink a tea together, then, to thanks her for her visit, he gave him a @PAPER with @ENDEDIT written on it");
		Conceptualise.entity("AFTERTEA").asText("'it is time to wake up' says the hatter");

		Conceptualise.entity("HATTERSPEAK")
			.asText("@AFTERTEA #feedSound").when("TEATIME").exists()
			.asText("@TEATIME #feedSound").when("TOOCLOCK").contains("3").globally()
			.asText("@NOTIMEFORTEA #feedSound");

		Conceptualise.entity("BUSY1").asBagOfWords(
			"i can not speak with you right now",
			"it is not a good time",
			"i do not have the time to speak for the moment"
		);
		Conceptualise.entity("BUSY2").asBagOfWords(
			"i am busy",
			"i am working",
			"i am very busy",
			"i am really busy",
			"i have things to do",
			"i have a lot of things to do",
			"i am in the middle of something",
			"i have somethings to do",
			"i have important things to do",
			"i have an important work to do"
		);
		Conceptualise.entity("IFWANTSPEAK").asBagOfWords(
			"if you want to speak with me",
			"if you want a meeting",
			"if you want some help",
			"if you need help",
			"if you need to speak",
			"if you need some help",
			"if you need some information",
			"if you need something"
		);
		Conceptualise.entity("COMEBACK").asBagOfWords(
			"come back later @ATOCLOCK",
			"come back @ATOCLOCK",
			"i will be free @ATOCLOCK",
			"i will be free later @ATOCLOCK",
			"you can come back @ATOCLOCK",
			"you need to come back @ATOCLOCK",
			"i will be available @ATOCLOCK"
		);
		Conceptualise.entity("HELP").asBagOfWords(
			"so you want some help",
			"so you want help to get out",
			"so you want some help to get out",
			"so you want some help to find the exit",
			"so you need some help",
			"so you need help",
			"so you want my help"
		);
		Conceptualise.entity("TAKETHIS").asBagOfWords(
			"take this",
			"take this, it can help you",
			"this will help you"
		);
		Conceptualise.entity("ALREADY").asBagOfWords(
			"i already spoke to you",
			"we have already spoken",
			"i have no more to say"
		);
		Conceptualise.entity("SOMEONEELSE").asBagOfWords(
			"ask to someone else",
			"ask to another queen",
			"go speak to  another queen",
			"go see someone else"
		);


		Conceptualise.entity("BUSY").asBagOfWords(
			"@IFWANTSPEAK, @COMEBACK",
			"@BUSY1, @COMEBACK",
			"@BUSY2, @COMEBACK",
			"@COMEBACK @IFWANTSPEAK",
			"@COMEBACK, @BUSY1",
			"@COMEBACK, @BUSY2",
			"@BUSY1, @BUSY2, @COMEBACK",
			"@BUSY2, @BUSY1, @COMEBACK",
			"@BUSY1, @IFWANTSPEAK, @COMEBACK",
			"@BUSY2, @IFWANTSPEAK, @COMEBACK",
			"@BUSY1, @COMEBACK @IFWANTSPEAK",
			"@BUSY2, @COMEBACK @IFWANTSPEAK"
		);

		Conceptualise.entity("ALREADYSPEAK").asBagOfWords(
			"'@ALREADY, @SOMEONEELSE' says the queen",
			"'@SOMEONEELSE, @ALREADY' says the queen",
			"'@ALREADY' says the queen"
		);

		Conceptualise.entity("BUSYSPEAK").asText("'@BUSY' says the queen #feedSound");
		Conceptualise.entity("HELPSPEAK").asText("'@HELP, @TAKETHIS' says the queen giving her a @PAPER with @HARDEDIT written on it #feedSound");

		Conceptualise.entity("ATOCLOCK")
			.asText("at 9 o`clock").when("QUEEN").contains("heart")
			.asText("at 5 o`clock").when("QUEEN").contains("spade")
			.asText("at 2 o`clock").when("QUEEN").contains("diamond")
			.asText("at 7 o`clock").when("QUEEN").contains("club");

		Conceptualise.entity("STILLCLOCK")
			.asText("it was still 2 o`clock").when("TOOCLOCK").contains("2").globally()
			.asText("it was still 5 o`clock").when("TOOCLOCK").contains("5").globally()
			.asText("it was still 7 o`clock").when("TOOCLOCK").contains("7").globally()
			.asText("it was still 9 o`clock").when("TOOCLOCK").contains("9").globally()
			.asText("it was still 3 o`clock").when("TOOCLOCK").contains("3").globally()
			.asText("it was still 12 o`clock");

		Conceptualise.entity("TOOCLOCK")
			.asBagOfWords("it was 2 o`clock","it was 5 o`clock", "it was 7 o`clock","it was 9 o`clock","it was 3 o`clock");
		Conceptualise.entity("WATCHTIME")
			.asBagOfWords(
				"@SHE looked the clock to check the time",
				"@SHE watched the clock to check the time",
				"@SHE looked the time on the clock",
				"@SHE watched the time on the clock",
				"@SHE checked the time on the clock"
			);
		Conceptualise.entity("WATCHTIMEAGAIN")
			.asBagOfWords(
				"@SHE looked the time again",
				"@SHE watched the time again",
				"@SHE checked the time again",
				"@SHE looked the clock again",
				"@SHE watched the clock again",
				"@SHE checked the clock again"
			);
		Conceptualise.entity("BADSET")
			.asText("@WATCHTIME, it was 12 o`clock #clockSound").when("BADSET").doesNotExist().globally().and("GOODSET").doesNotExist().globally()
			.asText("@WATCHTIMEAGAIN, @STILLCLOCK #clockSound");
		Conceptualise.entity("GOODSET")
			.asText("@WATCHTIME, @TOOCLOCK #clockSound");

		Conceptualise.entity("QUEENSPEAK")
			.asText("@ALREADYSPEAK #feedSound").when("HELPSPEAK").exists()
			.asText("@HELPSPEAK").when("TOOCLOCK").contains("9").globally().and("QUEEN").contains("heart")
			.asText("@HELPSPEAK").when("TOOCLOCK").contains("7").globally().and("QUEEN").contains("club")
			.asText("@HELPSPEAK").when("TOOCLOCK").contains("5").globally().and("QUEEN").contains("spade")
			.asText("@HELPSPEAK").when("TOOCLOCK").contains("2").globally().and("QUEEN").contains("diamond")
			.asText("@BUSYSPEAK");

		Conceptualise.entity("ALICESPEAK").asBagOfWords(
			"@SHE asked some help to the queen",
			"@SHE asked the queen to help her",
			"@SHE tried to speak with the queen",
			"@SHE asked how to get out to the queen",
			"@SHE asked where was the exit to the queen",
			"@SHE spoke to the queen"
		);

		Conceptualise.entity("ANIMAL").asBagOfWords(
			"a cat #putCat",
			"a crow #putCrow",
			"a rabbit #putRabbit",
			"a @QANIMAL cat #putCat",
			"a @QANIMAL crow #putCrow",
			"a @QANIMAL rabbit #putRabbit"
		).startingWith(2);
		Conceptualise.entity("QANIMAL").asLoop("smilling", "clever", "hungry", "lonely", "happy", "smart", "playful", "greedy");
		Conceptualise.entity("THEANIMAL")
			.asText("the cat").when("ANIMAL").contains("cat")
			.asText("the crow").when("ANIMAL").contains("crow")
			.asText("the rabbit").when("ANIMAL").contains("rabbit");
		Conceptualise.entity("FOOD").asBagOfWords("a cup of milk", "some worms", "a carrot").startingWith(2);
		Conceptualise.entity("BADFEED").asText("@SHE gave him a piece of lemon #feedSound");
		Conceptualise.entity("GOODFEED").asText("@SHE gave him @FOOD #feedSound");
		Conceptualise.entity("FEEDED")
			.asText("@THEANIMAL was not hungry anymore").when("FEEDED").exists()
			.asText("@THEANIMAL liked it very much and gave her a @PAPER with @MEDIUMEDIT written on it").when("ANIMAL").contains("cat").and("FOOD").contains("milk")
			.asText("@THEANIMAL liked it very much and gave her a @PAPER with @MEDIUMEDIT written on it").when("ANIMAL").contains("crow").and("FOOD").contains("worm")
			.asText("@THEANIMAL liked it very much and gave her a @PAPER with @MEDIUMEDIT written on it").when("ANIMAL").contains("rabbit").and("FOOD").contains("carrot")
			.asText("however, @THEANIMAL did not like this");


		Conceptualise.entity("QUEEN").asBagOfWords(
			"the queen of heart #putQueen?HEART",
			"the queen of spade #putQueen?SPADE",
			"the queen of diamond #putQueen?DIAMOND",
			"the queen of clubs #putQueen?CLUB");

		Conceptualise.entity("QCLOTHE").asBagOfWords(
			"luxurious",
			"sparkling",
			"shining",
			"pretty",
			"lovely",
			"precious",
			"magnificient",
			"gorgeous",
			"marvellous"
		);
		Conceptualise.entity("CLOTHE").asBagOfWords(
			"her @QCLOTHE dress",
			"her @QCLOTHE crown",
			"her @QCLOTHE ring",
			"her @QCLOTHE necklace",
			"her @QCLOTHE scepter",
			"her @QCLOTHE tiara"
		);

		Conceptualise.entity("QUAL").asBagOfWords("innocent", "little", "young", "lost");
		Conceptualise.entity("SHE").asLoop("alice", "she", "the @QUAL girl", "she", "the child", "she", "the girl", "she", "the @QUAL child", "she", "the girl", "she", "the child");

		
		Conceptualise.entity("NOEXIT").asBagOfWords("no exit", "no door", "no way to continue");
		Conceptualise.entity("LEFTRIGHT").asBagOfWords("on her left #addLeftDoor", "on her right #addRightDoor").startingWith(0,0);
		Conceptualise.entity("ONETWO").asBagOfWords("@NOEXIT", "one door @LEFTRIGHT", "two doors #addBothDoor").startingWith(1,1,0,2);//(2,2,2,2,2);//startingWith
		Conceptualise.entity("JOURNAL").asBagOfWords("her @DREAM journal", "her @DREAM diary");
		Conceptualise.entity("DREAM").asBagOfWords("dream", "nightmare");

		Conceptualise.entity("NOTE").asBagOfWords("note", "write", "record", "rewrite", "revise", "change", "fix");

		Conceptualise.entity("QHATTER").asBagOfWords("mad", "crazy", "moody", "demented", "insane");
		Conceptualise.entity("HATTER").asBagOfWords("a hatter", "a @QHATTER hatter");

		Conceptualise.entity("BOX").asBagOfWords("a box", "a chest", "a @QBOX box", "a @QBOX chest");
		Conceptualise.entity("PENDULUM").asBagOfWords("a clock", "a @QBOX clock", "a pendulum clock", "a @QBOX pendulum clock");
		Conceptualise.entity("QBOX").asBagOfWords("mysterious", "wooden", "beautiful", "big");

		Conceptualise.entity("OBJECT")
			.asBagOfWords("there was @JOURNAL #putJournal", "there was @BOX #putBox", "there was @ANIMAL", "there was @QUEEN with @CLOTHE", "there was @PENDULUM #putClock").startingWith(1,0,1,2)//.startingWith(0,4,3)//
			.asText("there was @HATTER #putHatter");


		Conceptualise.entity("NEWROOM").asText("@SHE found herself in a room with @ONETWO, it was @AMBIANCE and @ROOMMIDDLE");
		Conceptualise.entity("WITHOLD")
			.asText("that was empty").when("ROOMMIDDLE").contains("empty")
			.asText("and with @THEANIMAL").when("ANIMAL").exists()
			.asText("and with the queen").when("QUEEN").exists()
			.asText("and with the clock").when("PENDULUM").exists()
			.asText("and with the box").when("BOX").exists()
			.asText("and with the box").when("BOX").exists()
			.asText("and with the hatter").when("HATTER").exists()
			.asText("and with the journal").when("JOURNAL").exists();

		Conceptualise.entity("OLDROOM")
		.asText("again, @SHE was in the room with two doors @WITHOLD").when("ONETWO").contains("two")
		.asText("again, @SHE was in the room with one door @WITHOLD").when("ONETWO").contains("one")
		.asText("again, @SHE was in the room with @NOEXIT @WITHOLD").when("ONETWO").contains("NOEXIT");
		Conceptualise.entity("DESCRIPTION")
		.asText("@OLDROOM").when("ONETWO").exists()
		.asText("@NEWROOM").when("ONETWO").doesNotExist();

		Conceptualise.entity("WAKEUP").asText("@SHE woke up in a strange maze");
		Conceptualise.entity("ROOMMIDDLE").asBagOfWords("the room was empty", "in the middle of the room, @OBJECT", "inside the room, @OBJECT", "in the room, @OBJECT", " inside it, @OBJECT", "@OBJECT in this room").startingWith(0,2,1);//(1,1,1,1,1,1);//
		Conceptualise.entity("FIRSTJOURNAL").asText("there was @JOURNAL in the corner of the room #putCornerJournal");
		Conceptualise.entity("MIND").asText("@SHE wanted to @ACTION");

		Conceptualise.entity("BACK").asBagOfWords(
			"@SHE turned back #goBack",
			"@SHE came back on her steps #goBack",
			"@SHE came back #goBack",
			"@SHE came back on the previous room #goBack",
			"@SHE turned back on the previous room #goBack",
			"@SHE went back on her steps #goBack",
			"@SHE went back #goBack"
		).switchBack();
		Conceptualise.entity("LEFT").asText("@SHE took the door on the left #goLeft").switchTo("leftContext");
		Conceptualise.entity("RIGHT").asText("@SHE took the door on the right #goRight").switchTo("rightContext");

		Conceptualise.entity("REWRITE").asText("@SHE opened @JOURNAL to @NOTE her @DREAM #rewrite");

		Conceptualise.entity("END").asText("@SHE woke up in her bed #theEnd");
		Conceptualise.entity("PROLOG").asText("@SHE had a very strange @DREAM this night, so she wanted to write it in @JOURNAL but the time she goes looking for it in her wardrobe, she had already forgotten her @DREAM #theEnd");


		Conceptualise.entity("EASYEDIT").asLoop(
			"'A ROOM WITH ONE DOOR ON HER RIGHT' #newEdit?door&EASYEDIT",
			"'A CARROT' #newEdit?food&EASYEDIT",
			"'A CUP OF MILK' #newEdit?food&EASYEDIT",
			"'SOME WORMS' #newEdit?food&EASYEDIT",
			"'A ROOM WITH ONE DOOR ON HER LEFT' #newEdit?door&EASYEDIT",
			"'A RABBIT' #newEdit?object&EASYEDIT",
			"'A CAT' #newEdit?object&EASYEDIT",
			"'A CROW' #newEdit?object&EASYEDIT",
			"'A ROOM WITH TWO DOORS' #newEdit?door&EASYEDIT",
			"'COLD' #newEdit?ambiance&EASYEDIT",
			"'WARM' #newEdit?ambiance&EASYEDIT",
			"'GLOOMY' #newEdit?ambiance&EASYEDIT",
			"'DARK' #newEdit?ambiance&EASYEDIT"
		);
		Conceptualise.entity("MEDIUMEDIT").asLoop(
			"'IT WAS 2 O`CLOCK' #newEdit?clock&MEDIUMEDIT",
			"'IT WAS 5 O`CLOCK' #newEdit?clock&MEDIUMEDIT",
			"'IT WAS 7 O`CLOCK' #newEdit?clock&MEDIUMEDIT",
			"'IT WAS 9 O`CLOCK' #newEdit?clock&MEDIUMEDIT",
			"'A BOX' #newEdit?object&MEDIUMEDIT",
			"'THE QUEEN OF HEART' #newEdit?object&MEDIUMEDIT",
			"'THE QUEEN OF SPADE' #newEdit?object&MEDIUMEDIT",
			"'A CLOCK' #newEdit?object&MEDIUMEDIT",
			"'THE QUEEN OF CLUBS' #newEdit?object&MEDIUMEDIT",
			"'THE QUEEN OF DIAMOND' #newEdit?object&MEDIUMEDIT"
		);
		Conceptualise.entity("HARDEDIT").asLoop(
			"'A HATTER' #newEdit?object&HARDEDIT",
			"'IT WAS 3 O`CLOCK' #newEdit?clock&HARDEDIT"
		);
		Conceptualise.entity("ENDEDIT").asText(
			"'IN HER BED' #newEdit?end&ENDEDIT"
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
		if (narration.global.hasSaid("END") != null) {
			tell("@PROLOG");
			return;
		}
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
	public void putClock() {
		actualRoom.putClock();
	}
	public void putHatter() {
		actualRoom.putHatter();
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
	public void clockSound() {
		Game.app.sound.play("glitch");
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
	public void ambiance(String... argv) {
		actualRoom.ambiance = argv[0];
		Game.app.ambiance = argv[0];
	}

	public void theEnd() {
		Log.debug("THE END");
		Game.app.end = true;
	}
}
