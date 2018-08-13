package game;

import java.util.Hashtable;
import processing.core.*;
import ddf.minim.*;

public class ImageBank {
	
	Hashtable<String, PGraphics> bank;
	Double resize;

	ImageBank(Double resize) {
		bank = new Hashtable<String, PGraphics>();
		this.resize = resize;
		add("floor");
		add("leftWall");
		add("rightWall");
	}

	void add(String file) {
		PImage tmp = Game.app.loadImage("./data/img/" + file + ".png");
		PGraphics pg = Game.app.createGraphics(tmp.width, tmp.height);
		pg.beginDraw();
		pg.image(tmp, 0, 0);
		pg.endDraw();
		bank.put(file, pg);
	}

	void addLink(String link, String key) {
		PGraphics img = bank.get(key);
		if (img == null)
			return ;
		bank.put(link, img);
	}

	PImage get(String key) {
		return bank.get(key);
	}

	void draw(String key, int x, int y) {
		PGraphics img =  bank.get(key);
		int rw = (int)(((double)img.width)*this.resize);
		int rh = (int)(((double)img.height)*this.resize);
		Game.app.noSmooth();
		Game.app.image(img, x, y, rw, rh);
	}
}

