package server;

import java.awt.*;
import javax.swing.*;

import common.Keyboard;


public abstract class ServerCoreOld {
	double timePassed = 0;
	int windHeight;
	int windWidth;
	int WindowOffset = 11;
	boolean debug = true;
	boolean entReleased = true, upReleased = true, dwnReleased = true, escReleased = true, sReleased = true;
	JFrame window;
	Keyboard kbd;
	JTextArea taOut;
	JTextArea taIn;
	JScrollPane scroll;
	JScrollBar vert;
	
	String gameState = "init";
	
	boolean customFont = false;
	String custFont = "";
		
	public void init(){

		//setup window
		initWindow();

		kbd = new Keyboard(window);
				
		//set font
//		try {
//			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/8bitoperator.ttf")));
//			customFont = true;
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

		setFontSize(16);

	}
	
	private void initWindow(){
		double scale = 1;
		
		window = new JFrame();
		
		windWidth = (int)(667/scale);
		windHeight = (int)(332/scale);
		
		// Make sure we can terminate by clicking on the 'X' in the upper right hand corner
		// This should be the default, but this way we make sure it is.
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int windowX = window.getGraphicsConfiguration().getDevice().getDisplayMode().getWidth();// get the monitor's width
		int windowY = window.getGraphicsConfiguration().getDevice().getDisplayMode().getHeight(); // get the monitor's height
		windowX = (int) (windowX * 0.5) - (int) (windWidth * 0.5); // divide the screen and background image width by 2
		windowY = (int) (windowY * 0.5) - (int) (windHeight * 0.5); // divide the screen and background image height by 2
		window.setLocation(windowX, windowY); // Set position of the window in the middle of the screen.

		window.setTitle("MP Server"); // Add a window Title Caption
		window.setSize(windWidth, windHeight); // Give the window a size
		window.setResizable(true);
		
//		taIn = new JTextArea();
//		taIn.setLineWrap(false);
//		taIn.setEditable(true);
//		taIn.setSize(new Dimension(windWidth,20));
//		
//		window.add(taIn);

		taOut = new JTextArea();
		taOut.setMaximumSize(new Dimension(50,50));
		taOut.setLineWrap(true);
		taOut.setEditable(false);
//		
//		scroll = new JScrollPane(taOut);
//		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		scroll.setSize(new Dimension(50,50));
//		vert = scroll.getVerticalScrollBar();
//		window.add(taOut);
		

		window.setVisible(true);// Show the window
		
	}
	
	protected void setFontSize(int i) { //the argument is the point size of font
        Font font = new Font("Arial", Font.BOLD, i);  //create the global font.
        
	    if (customFont) { //check if the custom font is loaded
	    	font = new Font(custFont, Font.BOLD, i); //create the global font to the custom font
	    }

    	taOut.setFont(font); // set the global font.
//    	taIn.setFont(font); // set the global font.
	}

	public void run(){
		try{
			init();
			gameState = "server";
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
			MoreCalls();
			//draw and finish
			window.repaint();
			
			try{
				Thread.sleep(24);
			}catch(Exception ex){
				print(ex.getMessage());
			}
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
	
	public abstract void MoreCalls();
}
