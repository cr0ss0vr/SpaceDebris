package client;

import java.awt.*;

public abstract class GameObject {
	
	//DECLARE CONSTANTS HERE LIKE SO:
	//public final <variable type> <variable name> = <variable>
	
	//DECLARE LOCAL VARIABLES LIKE SO:
	//private <variable type> <variable name> = <variable> 
	private double xPos, yPos;
	private double xSpeed, ySpeed;
	private int width, height;
	private String name;
	
	public GameObject(){
		xPos = 0;
		yPos = 0;
		xSpeed = 0;
		ySpeed = 0;
		width = 0;
		height = 0;
		name = "";
	}

	public String getName(){
		return name;
	}
	
	void setName(String newName){
		name = newName;
	}
	
	public void update(Graphics g){
		yPos += ySpeed;
		xPos += xSpeed;

		draw(g);
	}

	public void reverseX(){
		setXSpeed(-xSpeed);
	}
	
	public void reverseY(){
		setYSpeed(-ySpeed);
	}
	
	public abstract void draw(Graphics g);
	
	public void setXSpeed(double newXSpeed){
		xSpeed = newXSpeed;
	}
	
	public void setYSpeed(double newYSpeed){
		ySpeed = newYSpeed;
	}
		
	public double getXSpeed(){
		return xSpeed;
	}
	
	public double getYSpeed(){
		return ySpeed;
	}
	
	public void setX(double newXPos){
		xPos = newXPos;
	}
		
	public void setY(double newYPos){
		yPos = newYPos;
	}

	public double getXPos(){
		return xPos;
	}

	public double getYPos(){
		return yPos;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setWidth(int newWidth){
		width = newWidth;
	}
	
	public void setHeight(int newHeight){
		height = newHeight;
	}
}