package common;

/* 
 *  Custom Keyboard created my Merryck Reynolds-Ibell (13053647)
 */

import java.awt.Window;
import java.awt.event.*;
import java.util.*;

public class Keyboard implements KeyListener{

	private Map<Integer, Boolean> keyMap = new HashMap<Integer, Boolean>(); //define a map (sort of like an array, only the index is also a value
	private char keyTyped = 0;
	
	public Keyboard(Window w){
		w.addKeyListener(this); //add the listener to make the keyboard respond
		
//		define the keys that are going to be used
		for(int i = 33; i <= 122; i++){
			keyMap.put(i, false);			
		}
		
		keyMap.put(KeyEvent.VK_UP, false);
		keyMap.put(KeyEvent.VK_DOWN, false);
		keyMap.put(KeyEvent.VK_LEFT, false);
		keyMap.put(KeyEvent.VK_RIGHT, false);
		keyMap.put(KeyEvent.VK_ENTER, false);
		keyMap.put(KeyEvent.VK_ESCAPE, false);
		keyMap.put(KeyEvent.VK_PLUS, false);
		keyMap.put(KeyEvent.VK_MINUS, false);
		
	}
	
	public void resetKey(){
		keyTyped = 0;//clear the last key typed
	}
	
	public void keyPressed(KeyEvent e) {
		keyMap.put(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		keyMap.put(e.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent e) {
		keyTyped = e.getKeyChar();
	}
	
	public char getKey(){
		return keyTyped;
	}
	
	public boolean getKeyState(int e){
		return keyMap.get(e);
	}

}
