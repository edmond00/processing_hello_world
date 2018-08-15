package game;

import java.util.Hashtable;
import processing.core.*;
import tools.*;

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
		draw(key, x, y, 0, 0, -1, -1);
	}
	void draw(String key, int x, int y, int rx, int ry, int rw, int rh) {
		PGraphics img =  bank.get(key);
		if (rw == -1)
			rw = img.width;
		if (rh == -1)
			rh = img.height;
		x = (int)((double)x*resize);
		y = (int)((double)y*resize);
		int w = (int)(((double)rw)*this.resize);
		int h = (int)(((double)rh)*this.resize);
		Game.app.noSmooth();
		Game.app.image(img, x, y, w, h, rx, ry, rx+rw, ry+rh);
	}
}

