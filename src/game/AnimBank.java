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

