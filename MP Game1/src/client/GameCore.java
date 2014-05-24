package client;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.sound.sampled.Clip;
import javax.swing.*;

import common.Keyboard;


public abstract class GameCore {
	protected double timePassed = 0;
	protected int windHeight;
	protected int windWidth;
	protected int WindowOffset = 11;
	protected boolean debug = true;
	protected boolean enterReleased = true, upReleased = true, downReleased = true, escapeReleased = true, sReleased = true;
	protected static FontMetrics fm;
	protected JFrame window;
	protected Keyboard kbd;
	protected Graphics gr;
	public Rectangle2D rect;
	static JLabel label;
	
	protected Image GI_Background;
	
	protected String gameState = "init";
	
	static boolean customFont = false;
		
	public void init(){

		
		//set window
		initWindow();

		kbd = new Keyboard(window);
		
//		//set font
//		try {
//			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/8bitoperator.ttf")));
//			customFont = true;
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

		setFontSize(20);

	}
	
	private void initWindow(){
		double scale = 1;
		
		window = new JFrame();
		
		windWidth = (int)(1280/scale);
		windHeight = (int)(720/scale);
		
		// Make sure we can terminate by clicking on the 'X' in the upper right hand corner
		// This should be the default, but this way we make sure it is.
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int windowX = window.getGraphicsConfiguration().getDevice().getDisplayMode().getWidth();// get the monitor's width
		int windowY = window.getGraphicsConfiguration().getDevice().getDisplayMode().getHeight(); // get the monitor's height
		windowX = (int) (windowX * 0.5) - (int) (windWidth * 0.5); // divide the screen and background image width by 2
		windowY = (int) (windowY * 0.5) - (int) (windHeight * 0.5); // divide the screen and background image height by 2
		window.setLocation(windowX, windowY); // Set position of the window in the middle of the screen.

		window.setTitle("Block Breaker"); // Add a window Title Caption
		window.setSize(windWidth, windHeight); // Give the window a size
		window.setResizable(false);

		BufferedImage canvas = new BufferedImage(windWidth, windHeight,BufferedImage.TYPE_INT_ARGB);
		gr = canvas.getGraphics();// Create a "handle" for the canvas which we can use for drawing.
		label = new JLabel(new ImageIcon(canvas));// Drawing actually takes place on a "label", so create it
		
		window.add(label); // add it to the window
		
		window.setVisible(true);// Show the window
		
	}
	
	protected void setFontSize(int i) { //the argument is the point size of font
        Font font = new Font("Arial", Font.BOLD, i);  //create the global font.
        
	    if (customFont) { //check if the custom font is loaded
	    	font = new Font("8bitoperator", Font.BOLD, i); //create the global font to the custom font
	    }
    	gr.setFont(font); // set the global font.
		fm = gr.getFontMetrics();
	}

	public void run(){
		try{
			init();
			gameState = "inmenu";
			mainLoop();
		}finally{
			System.exit(-1);
		}
	}

	public void mainLoop(){
		long startTime = System.currentTimeMillis();
		long curTime = startTime;
		
		while(!gameState.equalsIgnoreCase("quitting")){
			timePassed = System.currentTimeMillis() - curTime;
			curTime += timePassed;
			
			//any other code
			drawMain();
			
			if(gameState.equalsIgnoreCase("inmenu")){
				drawMenu();
			}else if(gameState.equalsIgnoreCase("help") || gameState.equalsIgnoreCase("phelp")){
				drawHelp();
			}else if(gameState.equalsIgnoreCase("ingame")){
				drawGame();
			}else if(gameState.equalsIgnoreCase("newGame")){
				newGame();
			}else if(gameState.equalsIgnoreCase("paused")){
				drawPaused();
			}else if(gameState.equalsIgnoreCase("gameover")){
				drawEndgame();
			}else if(gameState.equalsIgnoreCase("scoreScreen") || gameState.equalsIgnoreCase("pscorescreen")){
				drawScoreScreen();
			}
			
			MoreCalls();
			controls();
			overDraw();
			//boolean switches for key states - make sure they are only pressed once, rather than held
			if(kbd.getKeyState(KeyEvent.VK_UP)){
				upReleased = false;
			}else{
				upReleased = true;
			}
			
			if(kbd.getKeyState(KeyEvent.VK_DOWN)){
				downReleased = false;
			}else{
				downReleased = true;
			}
			
			if(kbd.getKeyState(KeyEvent.VK_ENTER)){
				enterReleased = false;
			}else{
				enterReleased = true;
			}
			
			if(kbd.getKeyState(KeyEvent.VK_ESCAPE)){
				escapeReleased = false;
			}else{
				escapeReleased = true;
			}
			
			if(kbd.getKeyState(KeyEvent.VK_S)){
				sReleased = false;
			}else{
				sReleased = true;
			}

			kbd.resetKey();
			//draw and finish
			window.repaint();
			
			try{
				Thread.sleep(24);
			}catch(Exception ex){}
		}
	}

	static void print(String x){
		System.out.println(x);
	}
	void print(int x){
		System.out.println(x);
	}
	void print(double x){
		System.out.println(x);
	}
	void print(char x){
		System.out.println(x);
	}
	void print(boolean x){
		System.out.println(x);
	}
	
	public void playSound(Clip clip) {
		GameSound.rewindSound(clip);
		GameSound.setVolume(clip, 8);
		GameSound.playSound(clip);
	}
	
	public abstract void blockCollision(int index);

	public abstract void controls();
	
	public abstract void newGame();
	
	public abstract void MoreCalls();

	public abstract void drawMain();
	
	public abstract void overDraw();

	public abstract void drawMenu();
	
	public abstract void drawHelp();

	public abstract void drawGame();
	
	public abstract void drawPaused();
	
	public abstract void drawScoreScreen();
	
	public abstract void drawEndgame();
	
	public void drawRect(Rectangle2D shape){
		gr.drawRect((int)shape.getX(), (int)shape.getY(), (int)shape.getWidth(), (int)shape.getHeight());
	}
	
}
