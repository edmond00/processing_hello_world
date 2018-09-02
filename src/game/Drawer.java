package game;

import java.util.LinkedList;
import tools.*;

public class Drawer {

	static int storyLineLenght = 60;
	static RGBA color = new RGBA(120,120,140); 

	static RGBA cwarm = new RGBA(80,30,30);
	static RGBA cdark = new RGBA(20,20,20);
	static RGBA ccold = new RGBA(20,20,120);
	static RGBA cgloomy = new RGBA(50,10,50);

	public static void useColor(RGBA color) {
		Game.app.fill(color.r, color.g, color.b, color.a);
	}

	public static LinkedList<String> splitStory(String story) {
		LinkedList<String> splits = new LinkedList<String>();
		String whitespace = " \t\n\r";
		int i = 0;
		if (story.length() > 1000)
			i = story.length() - 1000;
		int lastCut = i;
		for (i=i; i < story.length(); i++) {
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

		Game.app.textSize(20);

		if (story.length() > 1000) {
			story = story.substring(story.length() -1000);
		}
		LinkedList<String> lines = splitStory(story);
		RGBA c = color;
		for (String line : lines) {
			if (Game.app.glitch) {
				RGBA colorGlitch = new RGBA(
					Rand.rand(130) + 100,
					Rand.rand(130) + 100,
					Rand.rand(130) + 100
				);
				int gy = y + Rand.rand(20) - 10;
				int gx = x + Rand.rand(20) - 10;
				useColor(colorGlitch);
				Game.app.text(line, gx, gy);
			}
			else if (Game.app.ambiance.equals("warm")) {
				int gy = y + Rand.rand(10) - 5;
				int gx = x + Rand.rand(10) - 5;
				useColor(cwarm);
				Game.app.text(line, gx, gy);
			}
			else if (Game.app.ambiance.equals("cold")) {
				int gy = y + Rand.rand(10) - 5;
				int gx = x + Rand.rand(10) - 5;
				useColor(ccold);
				Game.app.text(line, gx, gy);
			}
			else if (Game.app.ambiance.equals("dark")) {
				int gy = y + Rand.rand(20) - 10;
				int gx = x + Rand.rand(20) - 10;
				useColor(cdark);
				Game.app.text(line, gx, gy);
			}
			else if (Game.app.ambiance.equals("gloomy")) {
				int gy = y + Rand.rand(20) - 10;
				int gx = x + Rand.rand(20) - 10;
				useColor(cgloomy);
				Game.app.text(line, gx, gy);
			}
			useColor(c);
			Game.app.text(line, x, y);
			y -= lineGap;
			c = c.changeOpacityByPercent(opacityChange);
			if (y < 0)
				break;
		}
	}

}
