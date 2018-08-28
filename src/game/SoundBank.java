package game;

import java.util.Hashtable;
import processing.core.*;
import ddf.minim.*;

public class SoundBank {
	
	Hashtable<String, AudioPlayer> bank;

	SoundBank() {
		bank = new Hashtable<String, AudioPlayer>();
		add("bip");
		add("glitch");
		add("changeRoom");
		add("openJournal");
		add("music");
		add("musicMix");
		add("rmusic");
		add("rmusic2");
		add("rmusicFade");
		add("music2");
		add("songBad");
		add("songBad2");
		add("rsongBad");
		add("write");
		add("write2");
		add("erase");
	}

	void add(String file) {
		AudioPlayer tmp = Game.app.minim.loadFile("./data/sound/" + file + ".wav");
		bank.put(file, tmp);
	}

	void addLink(String link, String key) {
		AudioPlayer file = bank.get(key);
		if (file == null)
			return ;
		bank.put(link, file);
	}

	 public void play(String key) {
		AudioPlayer player = bank.get(key);
		if (player != null) {
			player.rewind();
			player.play();
		}
	}

	 void loop(String key) {
		AudioPlayer player = bank.get(key);
		if (player != null) {
			player.rewind();
			player.loop();
		}
	}

	 void mute(String key) {
		AudioPlayer player = bank.get(key);
		if (player != null) {
			player.mute();
		}
	}

	 void unmute(String key) {
		AudioPlayer player = bank.get(key);
		if (player != null) {
			player.unmute();
		}
	}
}

