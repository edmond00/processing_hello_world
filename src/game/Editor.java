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

		addChoiceRegex("door", "(one door on her (left|right)|two doors)");

		//FOR TEST
		addChoice("door", "one door on her left");
		addChoice("door", "one door on her right");
		addChoice("door", "two doors");
		//END TEST
	}

	void addChoiceRegex(String group, String regex) {
		choicesRegex.put(group, Pattern.compile("(" + regex + ")$"));
	}

	void addChoice(String group, String choice) {
		if (choices.containsKey(group) == false) {
			LinkedList<String> tmp = new LinkedList<String>();
			choices.put(group, tmp);
		}
		choices.get(group).add(choice);
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
		editing = true;
		this.story = story;
	}

	void removeLastCharacter() {
		buffer = story.charAt(story.length()-1) + buffer;
		story = story.substring(0, story.length()-1);
	}

	void removeLetter() {
		canBeRewrite();
		if (story.charAt(story.length()-1) == '.') {
			buffer = "";
		}
		if (story.length() <= 0 ||
			story.charAt(story.length()-1) == '.' ||
			actualChoices.size() > 0) {
			endDelete();
			return;
		}
		if (story.charAt(story.length()-1) != ' ')
			Game.app.sound.play("erase");
		removeLastCharacter();
	}

	void replaceLetter() {
		if (toReplace.length() > 0) {
			if (story.charAt(story.length()-1) != ' ')
				Game.app.sound.play("erase");
			story = story.substring(0, story.length()-1);
			toReplace = toReplace.substring(0, toReplace.length()-1);
			return;
		}
		if (toWrite.length() > 0) {
			story += toWrite.charAt(0);
			toWrite = toWrite.substring(1, toWrite.length());
			return;
		}
		if (buffer.length() > 0) {
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
		if (timestep % 2 == 0) {
			if (deleting)
				removeLetter();
			if (rewriting)
				replaceLetter();
		}
	}

	int printOption(String option, int n) {
		int y = Game.app.height/12 + 30 * n;
		int x = Game.app.width/2;
		Game.app.textSize(30);
		if (rewriting == false && deleting == false && selection == n) {
			Drawer.useColor(Game.app.textColor);
		} else {
			Drawer.useColor(Game.app.blocked);
		}
		Game.app.text(option, x, y);
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

	void draw() {
		Drawer.drawStory(story);

		int n = 0;

		if (isSelected(n))
			this.editing = false;
		n = printOption("continue", n);
			

		if (story.length() > 0) {
			if (isSelected(n))
				this.startDelete();
			n = printOption("erase", n);
		}

		int c = 0;
		for (String choice : actualChoices) {
			if (isSelected(n))
				this.startReplace(c);
			n = printOption("rewrite with '" + choice + "'", n);
			c += 1;
		}


		if (selection >= n)
			selection = n-1;
		if (selection < 0)
			selection = 0;
	}
}

