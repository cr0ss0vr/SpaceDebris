package client;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameMain extends GameCore{
	boolean written = false, sound = true;
	@SuppressWarnings("unused")
	private double countDown = 1;
	private double stringTimer = 1;
		
	private String menuState = "oversingle", pMenuState = "overresume";

	
	public static void main(String[] args){
		new GameMain().run();
	}
	
	public void init(){
		//load images
		loadImages();
		
		//load sounds
		loadSounds();
		super.init();
		
		setObjects();
	}
	
	private void loadImages(){
		GE_Background = GameImage.loadImage("img/Background.jpg");
	}
	
	private void loadSounds(){

	}

	private void setObjects(){

	}
	
	public synchronized void MoreCalls() {
		
		stringTimer = stringTimer - (timePassed/1000);

	}

	public void newGame(){

	}

	public void controls() {
		
		/*
		 *  ALL PERPOSE CONTROLS
		 */
		
		if(kbd.getKeyState(KeyEvent.VK_S) && sReleased){
			stringTimer = 1;
			if(sound){
				sound = false;
			}else{
				sound = true;
			}
			sReleased = false;
		}
	
		/*
		 * INGAME CONTROLS 
		 */
			
		if(gameState.equalsIgnoreCase("ingame")){

			if(kbd.getKeyState(KeyEvent.VK_ESCAPE) && escapeReleased){
				gameState = "paused";
				return;
			}
			
			if(kbd.getKeyState(KeyEvent.VK_LEFT)){

			}else if(kbd.getKeyState(KeyEvent.VK_RIGHT)){

			}
			
			if(debug){

			}
		}
		
		/*
		 *  MENU CONTROLS
		 */
		
		if(gameState.equalsIgnoreCase("inmenu")){

			if(kbd.getKeyState(KeyEvent.VK_ESCAPE) && escapeReleased){
				gameState = "quitting";
				return;
			}
		
			if (kbd.getKeyState(KeyEvent.VK_UP) && upReleased) {
				if (menuState.equalsIgnoreCase("oversingle")) {
					menuState = "overquit";
					return;
				}
				
				if (menuState.equalsIgnoreCase("overquit")) {
					menuState = "overhelp";
					return;
				}
				
				if (menuState.equalsIgnoreCase("overhelp")) {
					menuState = "overmulti";
					return;
				}	

				if (menuState.equalsIgnoreCase("overmulti")) {
					menuState = "oversingle";
					return;
				}			
			}

			if (kbd.getKeyState(KeyEvent.VK_DOWN) && downReleased) {
				if (menuState.equalsIgnoreCase("oversingle")) {
					menuState = "overmulti";
					return;
				}
				
				if (menuState.equalsIgnoreCase("overmulti")) {
					menuState = "overhelp";
					return;
				}
				
				if (menuState.equalsIgnoreCase("overhelp")) {
					menuState = "overquit";
					return;
				}
				
				if (menuState.equalsIgnoreCase("overquit")) {
					menuState = "oversingle";
					return;
				}
			}
		}
		
		/*
		 * PAUSED CONTROLS
		 */
		
		if(gameState.equalsIgnoreCase("paused")){

				if(kbd.getKeyState(KeyEvent.VK_ESCAPE) && escapeReleased){
					gameState = "ingame";
					return;
				}
			
				if (kbd.getKeyState(KeyEvent.VK_UP) && upReleased) {
					if (pMenuState.equalsIgnoreCase("overresume")) {
						pMenuState = "overmenu";
						return;
					}

					if (pMenuState.equalsIgnoreCase("overscores")) {
						pMenuState = "overresume";
						return;
					}
					
					if (pMenuState.equalsIgnoreCase("overhelp")) {
						pMenuState = "overscores";
						return;
					}
					
					if (pMenuState.equalsIgnoreCase("overmenu")) {
						pMenuState = "overhelp";
						return;
					}
				}

				if (kbd.getKeyState(KeyEvent.VK_DOWN) && downReleased) {
					if (pMenuState.equalsIgnoreCase("overresume")) {
						pMenuState = "overScores";
						return;
					}
					
					if (pMenuState.equalsIgnoreCase("overscores")) {
						pMenuState = "overhelp";
						return;
					}
					
					if (pMenuState.equalsIgnoreCase("overhelp")) {
						pMenuState = "overmenu";
						return;
					}
					
					if (pMenuState.equalsIgnoreCase("overmenu")) {
						pMenuState = "overresume";
						return;
					}
				}
		}
		
		/*
		 * HELP CONTROLS
		 */
		
		if(gameState.equalsIgnoreCase("help")){
			if(kbd.getKey() != 0 && enterReleased){
				gameState = "inmenu";
				return;
			}
		}
		if(gameState.equalsIgnoreCase("phelp")){
			if(kbd.getKey() != 0 && enterReleased){
				gameState = "paused";
				return;
			}
		}
		
	}


	public synchronized void drawMain(){
		//draw a black layer for heading text
		gr.setColor(new Color(0,0,0));
		gr.fillRect(0, 0, windWidth, windHeight);
		gr.drawImage(GE_Background, 0, 0, windWidth, windHeight, 0, 0, window.getWidth(), window.getHeight(), null);
	}
	
	public synchronized void overDraw(){//Last draw called, draws over everything

		gr.setColor(new Color(255,255,255));
		setFontSize(30);
		if(stringTimer > 0){
			if(sound){
				rect = fm.getStringBounds("Sound on", gr);
				gr.drawString("Sound on", (int)(windWidth*0.5 - rect.getWidth()*0.5), (int) (windHeight / 0.25));
			}else{
				rect = fm.getStringBounds("Sound off", gr);
				gr.drawString("Sound off", (int)(windWidth*0.5 - rect.getWidth()*0.5), (int) (windHeight / 0.25));
			}
		}
		
	}
	
	public synchronized void drawMenu() {

		countDown = 1;
		
		setFontSize(50);
		gr.setColor(new Color(255,255,255));
		//draw title
		rect = fm.getStringBounds("Space Debris", gr);
		gr.drawString("Space Debris", (int)(windWidth*0.5 - rect.getWidth()*0.5), (int)(windHeight*0.1));

		rect = fm.getStringBounds("SINGLEPLAYER", gr);
		gr.drawString("SINGLEPLAYER", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(windHeight - windHeight * 0.55));

		rect = fm.getStringBounds("MULTIPLAYER", gr);
		gr.drawString("MULTIPLAYER", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(windHeight - windHeight * 0.45));

		rect = fm.getStringBounds("HELP", gr);
		gr.drawString("HELP", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(windHeight - windHeight * 0.35));
		
		rect = fm.getStringBounds("QUIT", gr);
		gr.drawString("QUIT", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(windHeight - windHeight * 0.25));
		
		//check the position of the selector highlight the selected option
		if (menuState.equalsIgnoreCase("oversingle")) {
			gr.setColor(Color.red);
			rect = fm.getStringBounds("SINGLEPLAYER", gr);
			gr.drawString("SINGLEPLAYER", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(windHeight - windHeight * 0.55));

			if (kbd.getKeyState(KeyEvent.VK_ENTER) && enterReleased) {
				enterReleased = false;
				gameState = "newGame";
				newGame();
			}
		}
		
		if (menuState.equalsIgnoreCase("overmulti")) {
			gr.setColor(Color.red);
			rect = fm.getStringBounds("MULTIPLAYER", gr);
			gr.drawString("MULTIPLAYER", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(windHeight - windHeight * 0.45));

			if (kbd.getKeyState(KeyEvent.VK_ENTER) && enterReleased) {
				enterReleased = false;
				gameState = "newGame";
				newGame();
			}
		}
		
		if (menuState.equalsIgnoreCase("overHelp")) {
			gr.setColor(Color.red);
			rect = fm.getStringBounds("HELP", gr);
			gr.drawString("HELP",(int) (windWidth * 0.5 - rect.getWidth() * 0.5),(int)(windHeight - windHeight * 0.35));

			if (kbd.getKeyState(KeyEvent.VK_ENTER) && enterReleased) {
				gameState = "help";
				enterReleased = false;
			}
		}
		if (menuState.equalsIgnoreCase("overquit")) {
			gr.setColor(Color.red);
			rect = fm.getStringBounds("QUIT", gr);
			gr.drawString("QUIT",(int) (windWidth * 0.5 - rect.getWidth() * 0.5),(int)(windHeight - windHeight * 0.25));

			if (kbd.getKeyState(KeyEvent.VK_ENTER) && enterReleased) {
				gameState = "quitting";
			}
		}
	}
	
	public synchronized void drawHelp() {
		if (gameState.equalsIgnoreCase("Help") || gameState.equalsIgnoreCase("phelp")) {
			
			setFontSize(50);

			gr.setColor(new Color(255,255,255));
			rect = fm.getStringBounds("Help", gr);
			gr.drawString("Help", (int) ((windWidth - rect.getWidth()) * 0.5), (int) (windHeight * 0.1));

			gr.setColor(Color.orange);
			setFontSize(25);
			fm = gr.getFontMetrics();
			
			rect = fm.getStringBounds("Arrow Keys control", gr);
			gr.drawString("Arrow Keys control", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int) (windHeight * 0.25));
			rect = fm.getStringBounds("the paddle.", gr);
			gr.drawString("the paddle.", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int) (windHeight * 0.3));

			rect = fm.getStringBounds("You start with", gr);
			gr.drawString("You start with", (int) (windWidth * 0.5) - (int) (rect.getWidth() * 0.5), (int) (windHeight * 0.4));

			rect = fm.getStringBounds("three lives.", gr);
			gr.drawString("three lives.", (int) (windWidth * 0.5) - (int) (rect.getWidth() * 0.5), (int) (windHeight * 0.45));

			rect = fm.getStringBounds("Clear all blocks!", gr);
			gr.drawString("Clear all blocks!", (int) (windWidth * 0.5) - (int) (rect.getWidth() * 0.5), (int) (windHeight * 0.6));
			
			rect = fm.getStringBounds("Get the HighScore!", gr);
			gr.drawString("Get the HighScore!", (int) (windWidth * 0.5) - (int) (rect.getWidth() * 0.5), (int) (windHeight * 0.65));

			rect = fm.getStringBounds("S toggles sound.", gr);
			gr.drawString("S toggles sound.", (int) (windWidth * 0.5) - (int) (rect.getWidth() * 0.5), (int) (windHeight * 0.75));

			rect = fm.getStringBounds("PRESS ANY KEY TO BACK", gr);// Create a rectangle that is the same size of the text, for calculations
			gr.drawString("PRESS ANY KEY TO BACK",(int) ((windWidth * 0.5) - (rect.getWidth() * 0.5)),(int) (windHeight * 0.9));
		}
	}

	public synchronized void drawGame() {

	}

	public synchronized void drawEndgame() {

	}


	public synchronized void drawPaused() {
		setFontSize(50);
		gr.setColor(new Color(255,255,255));

		rect = fm.getStringBounds("PAUSED", gr);
		gr.drawString("PAUSED", (int)(windWidth*0.5 - rect.getWidth()*0.5), (int)(windHeight*0.1));

		rect = fm.getStringBounds("RESUME", gr);
		gr.drawString("RESUME", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(windHeight - windHeight * 0.6));

		rect = fm.getStringBounds("HIGHSCORES", gr);
		gr.drawString("HIGHSCORES", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(windHeight - windHeight * 0.43));

		rect = fm.getStringBounds("HELP", gr);
		gr.drawString("HELP", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(windHeight - windHeight * 0.25));
		
		rect = fm.getStringBounds("MENU", gr);
		gr.drawString("MENU", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(windHeight - windHeight * 0.05));
		
		if (pMenuState.equalsIgnoreCase("overresume")) {
			
			gr.setColor(Color.red);
			rect = fm.getStringBounds("RESUME", gr);
			gr.drawString("RESUME", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(windHeight - windHeight * 0.6));

			if (kbd.getKeyState(KeyEvent.VK_ENTER) && enterReleased) {
				gameState = "ingame";
			}
		}

		if(pMenuState.equalsIgnoreCase("overScores")){
			gr.setColor(Color.red);
			rect = fm.getStringBounds("HIGHSCORES", gr);
			gr.drawString("HIGHSCORES", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(windHeight - windHeight * 0.43));

			if (kbd.getKeyState(KeyEvent.VK_ENTER) && enterReleased) {
				gameState = "pscorescreen";
				enterReleased = false;
			}
		}
		
		if (pMenuState.equalsIgnoreCase("overHelp")) {
			
			gr.setColor(Color.red);
			rect = fm.getStringBounds("HELP", gr);
			gr.drawString("HELP", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(windHeight - windHeight * 0.25));

			if (kbd.getKeyState(KeyEvent.VK_ENTER) && enterReleased) {
				gameState = "phelp";
				enterReleased = false;
			}
		}
		
		if (pMenuState.equalsIgnoreCase("overMenu")) {

			gr.setColor(Color.red);
			rect = fm.getStringBounds("MENU", gr);
			gr.drawString("MENU", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(windHeight - windHeight * 0.05));
			
			if (kbd.getKeyState(KeyEvent.VK_ENTER) && enterReleased) {
				gameState = "inMenu";
			}
		}
	}
}