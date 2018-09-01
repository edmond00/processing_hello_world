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
		public boolean glitch = false;
		public boolean sglitch = false;
		Keyboard keyboard;
		Minim minim;
		public SoundBank sound;
		ImageBank img;
		AnimBank anim;
		public Maze maze;
		public Editor editor;
		String music;
		String rmusic;

		RGBA textColor;
		RGBA lightGrey;
		RGBA blocked;
		int step = 0;
		int lastUpdate = 0;

		IsoInterface iso = null;

		public static Game app = null;

		public String alerting = null;

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
			textColor = new RGBA(255,255,255);
			lightGrey = new RGBA(125,125,125);
			blocked = new RGBA(50,50,50);
			textFont(font);
			textAlign(CENTER, CENTER);

			music = "songBad2";
			rmusic = "rsongBad";
			sound.loop(music);
			sound.loop(rmusic);
			sound.loop("rmusicFade");
			music();
		}

		public void music() {
			sound.mute(rmusic);
			sound.unmute(music);
			glitch = true;
		}

		public void rmusic() {
			sound.mute(music);
			sound.unmute(rmusic);
			glitch = true;
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
			if (alerting != null)
				drawAlert();
		}

		public void drawEditor() {
			controlEditor();
			editor.update(step);
			editor.draw();
		}

		public void rewrite(String story) {
			editor.editStory(story);
		}

		public void drawBackground() {
			int step = this.step / 2;
			int r = abs(8 - step % 19);
			int g = 0;
			int b = abs(12 - step % 25);
			tint(240+Rand.rand(15),230+Rand.rand(25),240+Rand.rand(15),255);
			if (Rand.rand(9) == 0) {
				if (Rand.rand(9) == 0)
					sglitch = false;
				glitch = false;
			}
			if (glitch) {
				r = Rand.rand(60);
				g = Rand.rand(20);
				b = Rand.rand(80);
				tint(Rand.rand(255),Rand.rand(255),Rand.rand(255),Rand.rand(255));
			} else if (sglitch) {
				r = Rand.rand(20);
				g = Rand.rand(5);
				b = Rand.rand(30);
				tint(255,100,100+Rand.rand(155),255);
			}
			if (Rand.rand(150) == 0) {
				sglitch = true;
				sound.play("glitch");
			}
			background(r,g,b);
		}

		public void draw(){
			int now = millis();
			if (now - lastUpdate > 30) {
				lastUpdate = now;
				step += 1;
				iso.update();
				drawBackground();

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
				if (alerting != null) {
					alerting = null;
				} else {
					Log.debug("press space : " + iso.getNarration());
					maze.tell(iso.getNarration());
				}
			}
		}

		public void newWordsAlert(String words) {
			alerting = words;
		}

		public void drawAlert() {
			int y = Game.app.height/3;
			int x = Game.app.width/2;
			fill(0,0,0,150);
			rect(0,0,width, height);
			Game.app.textSize(30);
			Drawer.useColor(Game.app.textColor);
			Game.app.text("You found this following words !", x, y);
			y += 30;
			Game.app.text("\"" + alerting + "\"", x, y);
			y += 30;
			Game.app.textSize(20);
			Game.app.text("You should try to write them in your diary", x, y);
			Drawer.useColor(Game.app.lightGrey);
			y += 30;
			Game.app.textSize(20);
			Game.app.text("press space to continue", x, y);
		}
}
