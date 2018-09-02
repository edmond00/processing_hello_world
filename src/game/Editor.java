package game;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import tools.*;

public class Editor extends Drawer {

	static boolean editing = false;

	boolean pressed;
	boolean deleting;
	boolean rewriting;
	int selection = 0;
	String buffer;
	String toReplace;
	String toWrite;
	String story;
	HashMap<String, LinkedList<String>> choices;
	HashMap<String, Pattern> choicesRegex;
	LinkedList<String> actualChoices = null;

	Editor() {
		deleting = false;
		rewriting = false;
		story = "";
		buffer = "";
		toReplace = "";
		toWrite = "";
		choices = new HashMap<String, LinkedList<String>>(); 
		choicesRegex = new HashMap<String, Pattern>(); 
		actualChoices = new LinkedList<String>();

		addChoiceRegex("door", "(a room with one door on her (left|right)|a room with (two doors|no door|no exit|no way to continue))");
		addChoiceRegex("food", "(a cup of milk|a piece of lemon|some worms|a carrot)");
		addChoiceRegex("object", "(a ((smilling|clever|hungry|lonely|happy|smart|playful|greedy) )?(cat|crow|rabbit)|the queen of (heart|spade|diamond|clubs)|a ((wooden|big|beautiful|mysterious) )?(pendulum )?(clock|box|chest)|a ((mad|crazy|moody|demented|insane) )? hatter)");
		addChoiceRegex("clock", "(to [0-9]+ o`clock)");
		addChoiceRegex("end", "(in a strange maze|in her bed)");

		addChoiceRegex("ambiance", "(cold|warm|dark|gloomy)");
		//DEBUG
		addChoice("clock", "to 3 o`clock");
		addChoice("clock", "to 2 o`clock");
		addChoice("clock", "to 5 o`clock");
		addChoice("clock", "to 7 o`clock");
		addChoice("clock", "to 9 o`clock");
		//addChoice("object", "a hatter");
		//addChoice("end", "in her bed");

	}

	void addChoiceRegex(String group, String regex) {
		choicesRegex.put(group, Pattern.compile("(" + regex + ")$"));
	}

	public void addChoice(String group, String choice) {
		if (choices.containsKey(group) == false) {
			LinkedList<String> tmp = new LinkedList<String>();
			choices.put(group, tmp);
		}
		if (choices.get(group).contains(choice) == false) {
			choices.get(group).add(choice);
		}
			Game.app.newWordsAlert(choice);
			Game.app.sound.play("item");
	}

	boolean choiceMatch(String group) {
		Pattern regex = choicesRegex.get(group);
		Matcher matcher = regex.matcher(story);
		if (matcher.find()) {
			Log.debug("MATCH :" + matcher.group(0));
			toReplace = matcher.group(0);
			return true;
		}
		return false;
	}

	void canBeRewrite() {
		actualChoices.clear();
		for (String group : choices.keySet()) {
			if (choiceMatch(group)) {
				for (String choice : choices.get(group)) {
					if (story.endsWith(choice) == false) {
						actualChoices.addLast(choice);
					}
				}
			}
		}
	}

	void editStory(String story) {
		Game.app.sound.play("openJournal");
		Game.app.rmusic();
		editing = true;
		this.story = story.trim();
	}

	void removeLastCharacter() {
		buffer = story.charAt(story.length()-1) + buffer;
		story = story.substring(0, story.length()-1);
	}

	void removeLetter(int timestep) {
		canBeRewrite();
		if (story.length() > 0 && story.charAt(story.length()-1) == '.') {
			buffer = "";
		}
		if (story.length() <= 0 ||
			story.charAt(story.length()-1) == '.' ||
			actualChoices.size() > 0) {
			endDelete();
			return;
		}
		if (timestep % 2 == 0)
			Game.app.sound.play("erase");
		removeLastCharacter();
	}

	void replaceLetter(int timestep) {
		if (toReplace.length() > 0) {
			if (timestep % 2 == 0)
				Game.app.sound.play("erase");
			story = story.substring(0, story.length()-1);
			toReplace = toReplace.substring(0, toReplace.length()-1);
			return;
		}
		if (toWrite.length() > 0) {
			if (timestep % 2 == 0)
				Game.app.sound.play("write2");
			story += toWrite.charAt(0);
			toWrite = toWrite.substring(1, toWrite.length());
			return;
		}
		if (buffer.length() > 0) {
			if (timestep % 2 == 0)
				Game.app.sound.play("write2");
			story += buffer.charAt(0);
			buffer = buffer.substring(1, buffer.length());
			return;
		}
		rewriting = false;
		canBeRewrite();
	}

	void startReplace(int n) {
		if (deleting == true || rewriting == true || actualChoices.size() == 0)
			return;
		toWrite = actualChoices.get(n);
		rewriting = true;
	}

	void startDelete() {
		if (deleting == true || story.length() <= 0)
			return;
		removeLastCharacter();
		deleting = true;
	}

	void endDelete() {
		deleting = false;
	}

	void update(int timestep) {
		if (timestep % 1 == 0) {
			if (deleting)
				removeLetter(timestep);
			if (rewriting)
				replaceLetter(timestep);
		}
	}

	int printOption(String option, int n) {
		int y = Game.app.height/12 + 40 * n;
		int x = Game.app.width/2;
		Game.app.textSize(30);
		if (rewriting == false && deleting == false && selection == n) {
			Drawer.useColor(Game.app.textColor);
		} else {
			Drawer.useColor(Game.app.lightGrey);
		}
		Game.app.text(option, x, y);
		return n+1;
	}

	int printOption(String option, String legend, int n) {
		int y = Game.app.height/12 + 40 * n + 30;
		int x = Game.app.width/2;
		printOption(option, n);
		Game.app.textSize(20);
		if (rewriting == false && deleting == false && selection == n) {
			Drawer.useColor(Game.app.textColor);
		} else {
			Drawer.useColor(Game.app.lightGrey);
		}
		Game.app.text(legend, x, y);
		return n+1;
	}

	void press() {
		this.pressed = true;
	}

	boolean isSelected(int n) {
		if (this.pressed == true && n == selection) {
			this.pressed = false;
			return true;
		}
		return false;
	}

	void endEditing() {
		Game.app.newMaze(story);
		Game.app.music();
		this.editing = false;
		Game.app.maze.rewriteFlag = false;
	}

	void draw() {
		Drawer.drawStory(story);

		int n = 0;

			
		int c = 0;
		for (String choice : actualChoices) {
			if (isSelected(n))
				this.startReplace(c);
			n = printOption("rewrite with '" + choice + "'", n);
			c += 1;
		}

		if (story.length() > 0) {
			if (isSelected(n))
				this.startDelete();
			n = printOption("erase the last sentence", n);
		}
	
		if (c == 0) {
			if (isSelected(n))
				endEditing();
			n = printOption("continue", n);
		}

		if (isSelected(n)) {
			story = "";
			endEditing();
		}
		n = printOption("erase all and restart from begining", "(the words that you have found will be conserved)", n);



		if (selection >= n)
			selection = n-1;
		if (selection < 0)
			selection = 0;
	}
}

