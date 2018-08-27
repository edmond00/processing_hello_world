package game;

import maze.*;
import iso.AppInterface;
import iso.IsoInterface;
import iso.IsoEngine;
import processing.core.*;
import ddf.minim.*;
import tools.*;

public class Game extends PApplet implements AppInterface {

		int x = 180;
		int y = 180;
		Keyboard keyboard;
		Minim minim;
		public SoundBank sound;
		ImageBank img;
		AnimBank anim;
		Maze maze;
		Editor editor;

		RGBA textColor;
		RGBA blocked;
		int step = 0;
		int lastUpdate = 0;

		IsoInterface iso = null;

		public static Game app = null;

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
			editor = new Editor();
			newMaze("");
			

			//surface.setResizable(true);
			PFont font = loadFont("./data/font/alice.vlw");
			textColor = new RGBA(215,215,215);
			blocked = new RGBA(50,50,50);
			textFont(font);
			textAlign(CENTER, CENTER);

			sound.loop("musicMix");
		}

		public void newMaze(String story) {
			maze = new Maze();
			maze.start(story);
		}

		public void draw(String img, int x, int y) {
			if (img == null)
				return;
			this.img.draw(img, x, y);
		}

		public void draw(String img, int step, int x, int y) {
			if (img == null)
				return;
			this.anim.draw(img, step, x, y);
		}


		public static void main(String[] args) {
			PApplet.main("game.Game");
		}

		public void settings(){
			size(800, 800);
			noSmooth();
		}


		public int getStep() {
			return step;
		}

		public void drawMaze() {
			controlMaze();
			maze.update(step);
			Drawer.drawStory(Maze.self.story);
			iso.draw();
			String action = iso.getAction();
			if (action != null) {
				Drawer.useColor(textColor);
				textSize(40);
				text("Press space to " + action, width/2, height/8);
			}
		}

		public void drawEditor() {
			controlEditor();
			editor.update(step);
			editor.draw();
		}

		public void rewrite(String story) {
			editor.editStory(story);
		}

		public void draw(){
			int now = millis();
			if (now - lastUpdate > 30) {
				lastUpdate = now;
				step += 1;
				iso.update();
				background(10,0,10);

				if (Editor.editing == false)
					drawMaze();
				else
					drawEditor();

				keyboard.update();
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

		void controlEditor() {
			if (keyboard.isPress(UP))
				editor.selection -= 1;
			if (keyboard.isPress(DOWN))
				editor.selection += 1;
			if (keyboard.isPress((int)' ')) {
				editor.press();
			}
		}

		void controlMaze() {
			if (keyboard.isPressed(LEFT))
				iso.left();
			if (keyboard.isPressed(RIGHT))
				iso.right();
			if (keyboard.isPressed(UP))
				iso.up();
			if (keyboard.isPressed(DOWN))
				iso.down();
			if (keyboard.isPress((int)' ')) {
				Log.debug("press space : " + iso.getNarration());
				maze.tell(iso.getNarration());
			}
		}
}
