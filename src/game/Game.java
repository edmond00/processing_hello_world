package game;

import iso.AppInterface;
import iso.IsoInterface;
import iso.IsoEngine;
import processing.core.*;
import ddf.minim.*;

public class Game extends PApplet implements AppInterface {

		int x = 180;
		int y = 180;
		Keyboard keyboard;
		Minim minim;
		SoundBank sound;
		ImageBank img;
		AnimBank anim;

		int step = 0;
		int lastUpdate = 0;

		IsoInterface iso = null;

		static Game app = null;

		public void draw(String img, int x, int y) {
			this.img.draw(img, x, y);
		}

		public void draw(String img, int step, int x, int y) {
			this.anim.draw(img, step, x, y);
		}


		public static void main(String[] args) {
			PApplet.main("game.Game");
		}

		public void settings(){
			size(800, 800);
			noSmooth();
		}


		public void setup(){
			double resize = 3.0;
			Game.app = this;
			Game.app.log("setup");
			iso = new IsoEngine(this, width/(2*(int)resize), height/(2*(int)resize));
			minim = new Minim(this);
			sound = new SoundBank();
			img = new ImageBank(resize);
			anim = new AnimBank();
			keyboard = new Keyboard();
			//surface.setResizable(true);
			PFont font = loadFont("./data/font/Consolas-48.vlw");
			textFont(font);
			textAlign(CENTER, CENTER);
		}

		public int getStep() {
			return step;
		}

		public void draw(){
			int now = millis();
			if (now - lastUpdate > 30) {
				lastUpdate = now;
				step += 1;
				control();
				keyboard.update();
				iso.update();
				background(10,0,10);
				iso.draw();
			}
		}
		
		void log(String str) {
			System.out.println(str);
		}

		public void keyPressed() {
			if (key == CODED)
				keyboard.press(keyCode);
			else
				keyboard.press((int)key);
		}

		public void keyReleased(){
			if (key == CODED)
				keyboard.release(keyCode);
			else
				keyboard.release((int)key);
		}

		void control() {
			if (keyboard.isPressed(LEFT))
				iso.left();
			if (keyboard.isPressed(RIGHT))
				iso.right();
			if (keyboard.isPressed(UP))
				iso.up();
			if (keyboard.isPressed(DOWN))
				iso.down();
		}
}
