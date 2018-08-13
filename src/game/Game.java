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

		IsoInterface iso = null;

		static Game app = null;

		public void draw(String img, int x, int y) {
			this.img.draw(img, x, y);
		}


		public static void main(String[] args) {
			PApplet.main("game.Game");
		}

		public void settings(){
			size(800, 800);
			noSmooth();
		}


		public void setup(){
			double resize = 5.0;
			Game.app = this;
			Game.app.log("setup");
			iso = new IsoEngine(this, width/(2*(int)resize), height/(2*(int)resize));
			minim = new Minim(this);
			sound = new SoundBank();
			img = new ImageBank(resize);
			keyboard = new Keyboard();
			//surface.setResizable(true);
			PFont font = loadFont("./data/font/Consolas-48.vlw");
			textFont(font);
			textAlign(CENTER, CENTER);
		}

		public void draw(){
			control();
			keyboard.update();
			background(10,0,10);
			fill(120,50,240);
			rect(x, y, 40, 40);
			//img.draw("floor", 200, 200);
			iso.draw();
			if (keyboard.isPressed((int)' ')) {
				fill(255, 255, 255);
				textSize(30);
				text("Hello", x+20, y+20);
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
				x -= 5;
			if (keyboard.isPressed(RIGHT))
				x += 5;
			if (keyboard.isPressed(UP))
				y -= 5;
			if (keyboard.isPressed(DOWN))
				y += 5;
			if (keyboard.isPress((int)' '))
				sound.play("bip");
			if (keyboard.isRelease((int)' '))
				sound.play("bip");
		}
}
