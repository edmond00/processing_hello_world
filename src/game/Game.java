package game;

import processing.core.*;
import ddf.minim.*;

public class Game extends PApplet{

		int x = 180;
		int y = 180;
		Keyboard keyboard;
		Minim minim;
		SoundBank sound;
		static Game app = null;

		public static void main(String[] args) {
			PApplet.main("game.Game");
		}

		public void settings(){
			size(400, 400);
		}

		public void setup(){
			Game.app = this;
			Game.app.log("setup");
			minim = new Minim(this);
			sound = new SoundBank();
			keyboard = new Keyboard();
			surface.setResizable(true);
			PFont font = loadFont("./data/font/Consolas-48.vlw");
			textFont(font);
			textAlign(CENTER, CENTER);
		}

		public void draw(){
			control();
			keyboard.update();
			background(0);
			fill(120,50,240);
			rect(x, y, 40, 40);
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
