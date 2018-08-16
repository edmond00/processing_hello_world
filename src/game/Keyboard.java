package game;

import java.util.HashMap;

public class Keyboard {

	public enum KEY_STATE {
		PRESS,
		PRESSED,
		RELEASE,
		RELEASED
	}
	
	HashMap<Integer, KEY_STATE> keymap;

	Keyboard() {
//		Game.app.log("new keyboard");
		keymap = new HashMap<Integer, KEY_STATE>();
	}

	void press(Integer key) {
		KEY_STATE actualState = keymap.get(key);
		if (actualState != null) {
			switch (actualState) {
				case PRESSED:
					return ;
			}
		}
//		Game.app.log("press");
		keymap.put(key, KEY_STATE.PRESS);
	}

	void release(Integer key) {
		KEY_STATE actualState = keymap.get(key);
		keymap.put(key, KEY_STATE.RELEASE);
//		Game.app.log("release");
	}

	void update() {
		for (Integer key : keymap.keySet()) {
			KEY_STATE actualState = keymap.get(key);
			switch (actualState) {
				case PRESS:
//					Game.app.log("pressed");
					keymap.put(key, KEY_STATE.PRESSED);
				break;
				case RELEASE:
//					Game.app.log("released");
					keymap.put(key, KEY_STATE.RELEASED);
				break;
			}
		}
	}

	boolean isPress(Integer key) {
		KEY_STATE actualState = keymap.get(key);
		if (actualState == KEY_STATE.PRESS)
			return true;
		return false;
	}

	boolean isPressed(Integer key) {
		KEY_STATE actualState = keymap.get(key);
		if (actualState == KEY_STATE.PRESSED || actualState == KEY_STATE.PRESS)
			return true;
		return false;
	}

	boolean isRelease(Integer key) {
		KEY_STATE actualState = keymap.get(key);
		if (actualState == KEY_STATE.RELEASE)
			return true;
		return false;
	}

	boolean isReleased(Integer key) {
		KEY_STATE actualState = keymap.get(key);
		if (actualState != KEY_STATE.PRESS && actualState != KEY_STATE.PRESSED)
			return true;
		return false;
	}
}
