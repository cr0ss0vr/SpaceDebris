package client;

import java.awt.*;

public class GameObjImg extends GameObject {
	
	private Image display;
	
	public GameObjImg(String imgPath, String name){//constructor for rect type object
										   // takes in the width and height, then defines them in the object properties.
		display = GameImage.loadImage(imgPath);
		setWidth(display.getWidth(null));
		setHeight(display.getHeight(null));
		setName(name);
	}
	
	public void draw(Graphics g){//take in the graphics of the game main class then,
		g.drawImage(display, (int)(getXPos()), (int)(getYPos()), getWidth(), getHeight(), null);
	}
}