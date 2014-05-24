package client;

import java.awt.*;

public class GameObjRect extends GameObject {
		
	public GameObjRect(int width, int height, String name){//constructor for rect type object
										   // takes in the width and height, then defines them in the object properties.
		setWidth(width);
		setHeight(height);
	}
	
	public void draw(Graphics g){//take in the graphics of the game main class then,
		g.fillRect((int)(getXPos()), (int)(getYPos()), getWidth(), getHeight());
	}
}