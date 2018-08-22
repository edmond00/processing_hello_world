package game;

import java.util.LinkedList;
import tools.*;

public class Drawer {

	static int storyLineLenght = 70;

	public static void useColor(RGBA color) {
		Game.app.fill(color.r, color.g, color.b, color.a);
	}

	public static LinkedList<String> splitStory(String story) {
		int lastCut = 0;
		LinkedList<String> splits = new LinkedList<String>();
		String whitespace = " \t\n\r";
		for (int i = 0; i < story.length(); i++) {
			if (i - lastCut > storyLineLenght && whitespace.indexOf(story.charAt(i)) >= 0) {
				splits.addFirst(story.substring(lastCut, i));
				lastCut = i;
			}
		}
		if (lastCut < story.length()-1)
		splits.addFirst(story.substring(lastCut, story.length()));
		return splits;
	}

	public static void drawStory(String story) {
		int opacityChange = 90;
		int lineGap = 40;
		int x = Game.app.width / 2;
		int y = Game.app.height - 50;
		RGBA color = new RGBA(120,120,140); 

		Game.app.textSize(20);

		LinkedList<String> lines = splitStory(story);
		for (String line : lines) {
			useColor(color);
			Game.app.text(line, x, y);
			y -= lineGap;
			color = color.changeOpacityByPercent(opacityChange);
		}
	}

}
