package game;

import java.util.Hashtable;
import processing.core.*;

public class AnimBank {
	
	Hashtable<String, Animation> bank;

	AnimBank() {
		bank = new Hashtable<String, Animation>();
		add("aliceBackLeft", 20, 5, 18);
		add("aliceBackRight", 20, 5, 18);
		add("aliceFrontLeft", 20, 5, 18);
		add("aliceFrontRight", 20, 5, 18);
		add("doorLeft", 24, 3, 22);
		add("doorRight", 24, 3, 22);
		add("cat", 21, 9, 10);
		add("crow", 22, 10, 15);
		add("rabbit", 17, 8, 14);
		add("heartQueen", 22, 7, 20);
		add("spadeQueen", 22, 7, 20);
		add("clubQueen", 22, 7, 20);
		add("diamondQueen", 22, 7, 20);
	}

	void add(String img, int oneStepHeight, int centerX, int centerY) {
		Game.app.img.add(img);
		bank.put(img, new Animation(img, oneStepHeight, centerX, centerY));
	}

	void addLink(String link, String key) {
		Animation img = bank.get(key);
		if (img == null)
			return ;
		bank.put(link, img);
	}

	Animation get(String key) {
		return bank.get(key);
	}

	void draw(String img, int step, int x, int y) {
		bank.get(img).draw(step, x, y);
	}
}

