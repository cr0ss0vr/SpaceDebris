package client;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.sound.sampled.Clip;

public class GameMain extends GameCore{
	private String input = "";
	private ArrayList<String> highScores = new ArrayList<String>();
	private int curScore, lives = 3, level = 0, keyPress = 0;
	boolean written = false, scoreDrawn = false, noScores = true, sound = true;
	private double countDown = 1;
	private double stringTimer = 1;
	
	private ArrayList<GameObjImg> blocks = new ArrayList<GameObjImg>();	
	private GameObjImg barLeft, barRight, barTop, paddle;
	private GameObjRect ball;
		
	private String menuState = "overstart", pMenuState = "overresume";
	private Image GI_LifePaddle;
	private Clip GS_Bonk, GS_Ting, GS_Death;
	
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
		
//		readScores();
	}
	
	private void loadImages(){
		GI_Background = GameImage.loadImage("Images/background.png");
		GI_LifePaddle = GameImage.loadImage("Images/paddle.png");
	}
	
	private void loadSounds(){
		GS_Death = GameSound.loadSound("Sounds/Death.wav");
		GS_Bonk = GameSound.loadSound("Sounds/Bonk.wav");
		GS_Ting = GameSound.loadSound("Sounds/Ting.wav");
	}

	private void setObjects(){
		barLeft = new GameObjImg("Images/bar-left.png", "barLeft");
		barRight = new GameObjImg("Images/bar-right.png", "barRight");
		barTop = new GameObjImg("Images/bar-top.png", "barTop");
		paddle = new GameObjImg("Images/paddle.png", "paddle");
		ball = new GameObjRect(8,8, "ball");
		initBorder();
	}
	
/*	private void readScores(){
		highScores.clear();
		try {
			Scanner in = new Scanner(new FileReader(file));
	    	
	    	while(in.hasNext()){
	    		highScores.add(in.nextLine());
    	}
	    	in.close();
		} catch (IOException e) {
			e.getLocalizedMessage();
		}
		if(highScores.size() > 0){
			noScores = false;
			String temp;
	    	for(int j = 0; j < highScores.size() -1; j++){ //bubble sort :)
	    		for(int i = 0; i < highScores.size(); i++){
	    			if(i != highScores.size()-1){
	    				if(Integer.valueOf(highScores.get(i).substring(6)) < Integer.valueOf(highScores.get(i+1).substring(6))){ // substring is set to 5 as the score starts 5chars after
	    					temp = highScores.get(i);
	    					highScores.set(i, highScores.get(i+1));
	    					highScores.set(i+1, temp);
	    				}
	    			}
	    		}
	    	}
    	}else{
    		noScores = true;
    	}
	}*/
	
	private void initBorder(){
		barLeft.setY(windHeight-GI_Background.getHeight(null)-WindowOffset);
		barRight.setY(windHeight-GI_Background.getHeight(null)-WindowOffset);
		barRight.setX(windWidth-barRight.getWidth());
		barTop.setX(0);
		barTop.setY(windHeight-GI_Background.getHeight(null)-barTop.getHeight()-WindowOffset);
	
	}		
	
	public synchronized void MoreCalls() {

//		print(gameState);
		stringTimer = stringTimer - (timePassed/1000);
		
		if(!gameState.equalsIgnoreCase("paused")){
			countDown = countDown - (timePassed/1000);
		}
		
		if(gameState.equalsIgnoreCase("ingame")){
			bounceCheck();
			checkBlocks();
		}
	}
	
	private void checkBlocks() {
		if(blocks.size() > 0){
			for(int i = 0; i < blocks.size(); i++){
				blockCollision(i);
			}
		}else{
			nextLevel();
		}
	}

	private void bounceCheck() {
		if(ball.getXPos() <= barLeft.getXPos() + barLeft.getWidth()){
			ball.setX(barLeft.getXPos() + barLeft.getWidth() + 1);
			ball.reverseX();
			if(sound){
				playSound(GS_Bonk);
			}
		}
		if(ball.getXPos()+ball.getWidth() >= barRight.getXPos()){
			ball.setX(barRight.getXPos() - ball.getWidth() - 1);
			ball.reverseX();
			if(sound){
				playSound(GS_Bonk);
			}
		}
		if(ball.getYPos() <= barTop.getYPos() + barTop.getHeight()){
			ball.setY(barTop.getYPos() + barTop.getHeight() + 1);
			ball.reverseY();
			if(sound){
				playSound(GS_Bonk);
			}
		}
		if(ball.getYPos()+ball.getHeight() >= windHeight-WindowOffset){
			LifeLost();
			if(sound){
				playSound(GS_Death);
			}
		}
		paddleCollide();
	}
	
	public synchronized void paddleCollide(){
		if(ball.getYPos() + ball.getHeight() >= paddle.getYPos()){
			if((ball.getXPos() + ball.getWidth() > paddle.getXPos()) && (ball.getXPos() < paddle.getXPos() + paddle.getWidth())){
				if(sound){
					playSound(GS_Ting);
				}
				ball.setXSpeed(Math.ceil(-(ball.getYSpeed()/20)*((paddle.getXPos() + (paddle.getWidth()*0.5)) - (ball.getXPos()+4)))-(level*0.33));
				ball.setYSpeed(Math.ceil(-ball.getYSpeed()-(level*0.33)));
				
			}
		}
		
	}
	
	private void LifeLost(){
		lives -= 1;
		
		if(lives <= 0){
			gameState = "gameover";
			
//			readScores();
		}
		newRound();
		ball.setX((int)(paddle.getXPos()+paddle.getWidth()*0.5));
		ball.setY(paddle.getYPos()-ball.getWidth()-3);
	}

	public void newGame(){

			lives = 3;
			level = 0;
			keyPress = 0;
			input = "";
			written = false;
			scoreDrawn = false;

			paddle.setY(588);
			paddle.setX((int)(windWidth * 0.5) - (int)(paddle.getWidth() * 0.5));
			
			
			//print("spawning blocks");
			nextLevel();
			
			curScore = 000000;

			gameState = "inGame";
			newRound();
	}
	
	private void nextLevel() {
		level += 1;

		ball.setX((int)(paddle.getXPos()+paddle.getWidth()*0.5));
		ball.setY(paddle.getYPos()-ball.getWidth()-3);
		
		blockSpawn();
		
	}

	private void newRound() {

		ball.setXSpeed(2);
		ball.setYSpeed(-6);
		
		countDown = 3;
		
	}

	private void blockSpawn(){
		int xOffset = (int) (barLeft.getXPos()) + barLeft.getWidth() + 6;
		int yOffset = (int) (barTop.getYPos()) + barTop.getHeight() + 40;

		//print("entering loop");
		
		blocks.clear();
		countDown = 1;
		
		int curBlocks;

		curBlocks = 54+(level*11);
		
		if(curBlocks > 120){
			curBlocks = 120;
		}
		
		for(int blockCount = 0; blockCount <= curBlocks; blockCount++){
			//print("loop " + blockCount);
			blocks.add(new GameObjImg("Images/grayblock.png", "block" + blockCount));
			blocks.get(blockCount).setX(xOffset);
			//print(xOffset);
			//print(yOffset);
			blocks.get(blockCount).setY(yOffset);
			
			if (xOffset + blocks.get(blockCount).getWidth() + 4 >= 456){
				yOffset = yOffset + blocks.get(blockCount).getHeight() + 6;
				xOffset = (int)(barLeft.getXPos()) + barLeft.getWidth() + 6;
			}else{		
				xOffset = xOffset + blocks.get(blockCount).getWidth() + 7;
			}
		}
		//print("exited loop");
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
		 * SCOREBOARD CONTROLS
		 */
		
		if(gameState.equalsIgnoreCase("scorescreen")){
		}

		if(gameState.equalsIgnoreCase("scorescreen")){
			if(kbd.getKey() != 0 && enterReleased){
				gameState = "inmenu";
				return;
			}
		}
		if(gameState.equalsIgnoreCase("pscorescreen")){
			if(kbd.getKey() != 0 && enterReleased){
				gameState = "paused";
				return;
			}
		}
		
		/*
		 * ENDGAME CONTROLS
		 */
		
//		if(gameState.equalsIgnoreCase("gameOver")){
//			
//			char key = kbd.getKey();
//			// input
//			if ((keyPress < 3) && (key != 0) ){
//				if (key != ' ' && key != 27 && key != KeyEvent.VK_ENTER && key != KeyEvent.VK_BACK_SPACE) {
//					input = input + key;
//					keyPress++;
//				}
//			} else if (keyPress >= 3) {
//				if (!written) {
//					try {
//						PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
//						out.println(input + " - " + curScore);
//						out.close();
//						readScores();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					written = true;
//				} else {
//					if(kbd.getKey() != 0 && enterReleased){
//						gameState = "inmenu";
//						return;
//					}
//				}
//			}
//			// input end.
//		}

		
		/*
		 * INGAME CONTROLS 
		 */
	
		
		if(gameState.equalsIgnoreCase("ingame")){

			if(kbd.getKeyState(KeyEvent.VK_ESCAPE) && escapeReleased){
				gameState = "paused";
				return;
			}
			
			if(kbd.getKeyState(KeyEvent.VK_LEFT)){
				if(paddle.getXPos() > barLeft.getXPos()+barLeft.getWidth()){
					paddle.setX(paddle.getXPos()-10);
				}

			}else if(kbd.getKeyState(KeyEvent.VK_RIGHT)){
				if(paddle.getXPos()+paddle.getWidth() < barRight.getXPos()){
					paddle.setX(paddle.getXPos()+10);
				}

			}
			
			if(countDown > 0){
				ball.setX((int)(paddle.getXPos()+paddle.getWidth()*0.5));
				ball.setY(paddle.getYPos()-ball.getWidth()-3);
			}
			
			if(debug){
				if(kbd.getKey()=='8'){
					ball.setYSpeed(ball.getYSpeed()-1);
					print("ball move up");
				}

				if(kbd.getKey()=='2'){
					ball.setYSpeed(ball.getYSpeed()+1);
					print("ball move down");
				}
				
				if(kbd.getKey()=='4'){
					ball.setXSpeed(ball.getXSpeed()-1);
					print("ball move left");
				}
				
				if(kbd.getKey()=='6'){
					ball.setXSpeed(ball.getXSpeed()+1);
					print("ball move right");
				}
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
				if (menuState.equalsIgnoreCase("overstart")) {
					menuState = "overquit";
					return;
				}

				if (menuState.equalsIgnoreCase("overScores")) {
					menuState = "overStart";
					return;
				}
				
				if (menuState.equalsIgnoreCase("overhelp")) {
					menuState = "overscores";
					return;
				}
				
				if (menuState.equalsIgnoreCase("overquit")) {
					menuState = "overhelp";
					return;
				}
			}

			if (kbd.getKeyState(KeyEvent.VK_DOWN) && downReleased) {
				if (menuState.equalsIgnoreCase("overstart")) {
					menuState = "overScores";
					return;
				}
				
				if (menuState.equalsIgnoreCase("overScores")) {
					menuState = "overhelp";
					return;
				}
				
				if (menuState.equalsIgnoreCase("overhelp")) {
					menuState = "overquit";
					return;
				}
				
				if (menuState.equalsIgnoreCase("overquit")) {
					menuState = "overstart";
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

	private String highestScore(){
		if(highScores.size()>0){
			return highScores.get(0).substring(6);
		}else{
			return "0";
		}
	}

	public synchronized void drawMain(){
		//draw a black layer for heading text
		gr.setColor(new Color(0,0,0));
		gr.fillRect(0, 0, windWidth, windHeight);
		
		gr.drawImage(GI_Background, ((int)(barLeft.getXPos()) + barLeft.getWidth() ), windHeight-GI_Background.getHeight(null)-WindowOffset, null);
		
		barLeft.update(gr);
		barRight.update(gr);
		barTop.update(gr);
	}
	
	public synchronized void overDraw(){//Last draw called, draws over everything

		gr.setColor(new Color(255,255,255));
		setFontSize(30);
		if(stringTimer > 0){
			if(sound){
				rect = fm.getStringBounds("Sound on", gr);
				gr.drawString("Sound on", (int)(windWidth*0.5 - rect.getWidth()*0.5), (int) (barTop.getYPos() + barTop.getHeight() * 3));
			}else{
				rect = fm.getStringBounds("Sound off", gr);
				gr.drawString("Sound off", (int)(windWidth*0.5 - rect.getWidth()*0.5), (int) (barTop.getYPos() + barTop.getHeight() * 3));
			}
		}
		
	}
	
	public synchronized void drawMenu() {

		countDown = 1;
		
		setFontSize(50);
		gr.setColor(new Color(255,255,255));
		//draw title
		rect = fm.getStringBounds("BlockBreaker", gr);
		gr.drawString("BlockBreaker", (int)(windWidth*0.5 - rect.getWidth()*0.5), (int)(windHeight*0.1));

		rect = fm.getStringBounds("START", gr);
		gr.drawString("START", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(GI_Background.getHeight(null) - GI_Background.getHeight(null) * 0.6));

		rect = fm.getStringBounds("HIGHSCORES", gr);
		gr.drawString("HIGHSCORES", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(GI_Background.getHeight(null) - GI_Background.getHeight(null) * 0.43));

		rect = fm.getStringBounds("HELP", gr);
		gr.drawString("HELP", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(GI_Background.getHeight(null) - GI_Background.getHeight(null) * 0.25));
		
		rect = fm.getStringBounds("QUIT", gr);
		gr.drawString("QUIT", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(GI_Background.getHeight(null) - GI_Background.getHeight(null) * 0.05));
		
		//check the position of the selector highlight the selected option
		if (menuState.equalsIgnoreCase("overstart")) {
			gr.setColor(Color.red);
			rect = fm.getStringBounds("START", gr);
			gr.drawString("START", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(GI_Background.getHeight(null) - GI_Background.getHeight(null) * 0.6));

			if (kbd.getKeyState(KeyEvent.VK_ENTER) && enterReleased) {
				enterReleased = false;
				gameState = "newGame";
				newGame();
			}
		}
		
		if (menuState.equalsIgnoreCase("overHelp")) {
			gr.setColor(Color.red);
			rect = fm.getStringBounds("HELP", gr);
			gr.drawString("HELP",(int) (windWidth * 0.5 - rect.getWidth() * 0.5),(int)(GI_Background.getHeight(null) - GI_Background.getHeight(null) * 0.25));

			if (kbd.getKeyState(KeyEvent.VK_ENTER) && enterReleased) {
				gameState = "help";
				enterReleased = false;
			}
		}
		
		if(menuState.equalsIgnoreCase("overScores")){
			gr.setColor(Color.red);
			rect = fm.getStringBounds("HIGHSCORES", gr);
			gr.drawString("HIGHSCORES", (int)(windWidth*0.5 - rect.getWidth()*0.5), (int)(GI_Background.getHeight(null)- GI_Background.getHeight(null)*0.43));

			if (kbd.getKeyState(KeyEvent.VK_ENTER) && enterReleased) {
				gameState = "scorescreen";
				enterReleased = false;
			}
		}
		
		if (menuState.equalsIgnoreCase("overquit")) {
			gr.setColor(Color.red);
			rect = fm.getStringBounds("QUIT", gr);
			gr.drawString("QUIT",(int) (windWidth * 0.5 - rect.getWidth() * 0.5),(int)(GI_Background.getHeight(null) - GI_Background.getHeight(null) * 0.05));

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
	
	public void drawLives(){
		
		setFontSize(11);

		gr.setColor(Color.white);
			
		if(lives-1 != 0){
			gr.drawImage(GI_LifePaddle, barLeft.getWidth(), windHeight-(int)(GI_LifePaddle.getHeight(null)*1.5), (int)(GI_LifePaddle.getWidth(null)*0.75), (int)(GI_LifePaddle.getHeight(null)*0.75), null);
			rect = fm.getStringBounds("x"+(lives-1), gr);
			gr.drawString("x"+(lives-1), (int)(GI_LifePaddle.getWidth(null)) + 5,  windHeight-(int)((GI_LifePaddle.getHeight(null)*1.5)*0.6));
		}
	}
	
	public synchronized void drawGame() {
		//update game logic
		paddle.update(gr);
		gr.setColor(new Color(255,0,0));
		ball.update(gr);
		//loop through the array of blocks and draw them
		for(int i = 0; i < blocks.size(); i++){
			blocks.get(i).update(gr);
		}
		drawLives();
		
		gr.setColor(new Color(255,255,255));

		setFontSize(22);
		
		//draw titles
		rect = fm.getStringBounds("Highest Score", gr);
		gr.drawString("Highest Score", (int)(windWidth) - (int)(rect.getWidth()), (int)(windHeight*0.06));
		
		rect = fm.getStringBounds("Current Score", gr);
		gr.drawString("Current Score", 0, (int)(windHeight*0.06));
		
		
		setFontSize(35);
		//draw highest score
		if(Integer.valueOf(highestScore()) >= curScore){
			rect = fm.getStringBounds(String.format("%06d",Integer.valueOf(highestScore())), gr);
			gr.drawString(String.format("%06d",Integer.valueOf(highestScore())), (int)(windWidth)-(int) (rect.getWidth()), (int)(windHeight*0.12));
		}else{
			rect = fm.getStringBounds(String.format("%06d",curScore), gr);
			gr.drawString(String.format("%06d", curScore), windWidth-(int)rect.getWidth(), (int)(windHeight*0.12));
		}
		//draw current score
		rect = fm.getStringBounds(String.format("%06d",curScore), gr);
		gr.drawString(String.format("%06d", curScore), 0, (int)(windHeight*0.12));
		
		if(countDown > 0){
			rect = fm.getStringBounds(String.format("%.3f", countDown), gr);
			gr.drawString(String.format("%.3f", countDown), (int)((windWidth*0.5)-(rect.getWidth()*0.5)), (int)(windHeight*0.12));
		}else if(countDown > -1 && countDown < 0){
			rect = fm.getStringBounds("PLAY", gr);
			gr.drawString("PLAY", (int)((windWidth*0.5)-(rect.getWidth()*0.5)), (int)(windHeight*0.12));
		}else{
			rect = fm.getStringBounds(String.format("%02d", level), gr);
			gr.drawString(String.format("%02d", level), (int)((windWidth*0.5)-(rect.getWidth()*0.5)), (int)(windHeight*0.12));			
		}
		
	}

	public synchronized void drawEndgame() {
		// TODO

		setFontSize(50);

		gr.setColor(new Color(255,255,255));
		rect = fm.getStringBounds("Game Over", gr);
		gr.drawString("Game Over", (int) ((windWidth - rect.getWidth()) * 0.5), (int) (windHeight * 0.1));

		gr.setColor(Color.orange);
		setFontSize(25);
		fm = gr.getFontMetrics();
		
		rect = fm.getStringBounds("Enter Your Name:", gr);
		gr.drawString("Enter Your Name:", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int) (windHeight * 0.25));
		if(keyPress < 3){
			rect = fm.getStringBounds(input.toUpperCase()+'_', gr);
			gr.drawString(input.toUpperCase()+'_', (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int) (windHeight * 0.3));
		}else{
			rect = fm.getStringBounds(input.toUpperCase(), gr);
			gr.drawString(input.toUpperCase(), (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int) (windHeight * 0.3));
		}
		rect = fm.getStringBounds("Your Score is: " + curScore, gr);
		gr.drawString("Your Score is: " + curScore, (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int) (windHeight * 0.35));

		// display scoreboard here
		printScores();
			
	}


	public synchronized void drawPaused() {
		setFontSize(50);
		gr.setColor(new Color(255,255,255));

		rect = fm.getStringBounds("PAUSED", gr);
		gr.drawString("PAUSED", (int)(windWidth*0.5 - rect.getWidth()*0.5), (int)(windHeight*0.1));

		rect = fm.getStringBounds("RESUME", gr);
		gr.drawString("RESUME", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(GI_Background.getHeight(null) - GI_Background.getHeight(null) * 0.6));

		rect = fm.getStringBounds("HIGHSCORES", gr);
		gr.drawString("HIGHSCORES", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(GI_Background.getHeight(null) - GI_Background.getHeight(null) * 0.43));

		rect = fm.getStringBounds("HELP", gr);
		gr.drawString("HELP", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(GI_Background.getHeight(null) - GI_Background.getHeight(null) * 0.25));
		
		rect = fm.getStringBounds("MENU", gr);
		gr.drawString("MENU", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(GI_Background.getHeight(null) - GI_Background.getHeight(null) * 0.05));
		
		if (pMenuState.equalsIgnoreCase("overresume")) {
			
			gr.setColor(Color.red);
			rect = fm.getStringBounds("RESUME", gr);
			gr.drawString("RESUME", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(GI_Background.getHeight(null) - GI_Background.getHeight(null) * 0.6));

			if (kbd.getKeyState(KeyEvent.VK_ENTER) && enterReleased) {
				gameState = "ingame";
			}
		}

		if(pMenuState.equalsIgnoreCase("overScores")){
			gr.setColor(Color.red);
			rect = fm.getStringBounds("HIGHSCORES", gr);
			gr.drawString("HIGHSCORES", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(GI_Background.getHeight(null) - GI_Background.getHeight(null) * 0.43));

			if (kbd.getKeyState(KeyEvent.VK_ENTER) && enterReleased) {
				gameState = "pscorescreen";
				enterReleased = false;
			}
		}
		
		if (pMenuState.equalsIgnoreCase("overHelp")) {
			
			gr.setColor(Color.red);
			rect = fm.getStringBounds("HELP", gr);
			gr.drawString("HELP", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(GI_Background.getHeight(null) - GI_Background.getHeight(null) * 0.25));

			if (kbd.getKeyState(KeyEvent.VK_ENTER) && enterReleased) {
				gameState = "phelp";
				enterReleased = false;
			}
		}
		
		if (pMenuState.equalsIgnoreCase("overMenu")) {

			gr.setColor(Color.red);
			rect = fm.getStringBounds("MENU", gr);
			gr.drawString("MENU", (int) (windWidth * 0.5 - rect.getWidth() * 0.5), (int)(GI_Background.getHeight(null) - GI_Background.getHeight(null) * 0.05));
			
			if (kbd.getKeyState(KeyEvent.VK_ENTER) && enterReleased) {
				gameState = "inMenu";
			}
		}
	}
	
	public void blockCollision(int index) {

		Rectangle topHit = new Rectangle((int)(blocks.get(index).getXPos()), (int)(blocks.get(index).getYPos()), blocks.get(index).getWidth(), 3);
		Rectangle botHit = new Rectangle((int)(blocks.get(index).getXPos()), (int)(blocks.get(index).getYPos() + blocks.get(index).getHeight()-3), blocks.get(index).getWidth(), 3);
		Rectangle leftHit = new Rectangle((int)(blocks.get(index).getXPos()), (int)(blocks.get(index).getYPos()+3), 3, blocks.get(index).getHeight()-3);
		Rectangle rightHit = new Rectangle((int)(blocks.get(index).getXPos() + blocks.get(index).getWidth()-3), (int)(blocks.get(index).getYPos()+3), 3, blocks.get(index).getHeight()-3);	

		if(ball.getYPos() <= blocks.get(index).getYPos() + blocks.get(index).getHeight() && ball.getYPos() + ball.getHeight() >= blocks.get(index).getYPos()){
			if(ball.getXPos() + ball.getWidth() >= blocks.get(index).getXPos() && ball.getXPos() <= blocks.get(index).getXPos() + blocks.get(index).getWidth()){
				if(sound){
					playSound(GS_Ting);
				}
				if(ball.getYPos() <= topHit.getY() + topHit.getHeight() && ball.getYPos() + ball.getHeight() >= topHit.getY()){
					if(ball.getXPos() + ball.getWidth() >= topHit.getX() && ball.getXPos() <= topHit.getX() + topHit.getWidth()){
						ball.reverseY();
					}
				}

				if(ball.getYPos() <= botHit.getY() + botHit.getHeight() && ball.getYPos() + ball.getHeight() >= botHit.getY()){
					if(ball.getXPos() + ball.getWidth() >= botHit.getX() && ball.getXPos() <= botHit.getX() + botHit.getWidth()){
						ball.reverseY();
					}
				}

				if(ball.getYPos() <= leftHit.getY() + leftHit.getHeight() && ball.getYPos() + ball.getHeight() >= leftHit.getY()){
					if(ball.getXPos() + ball.getWidth() >= leftHit.getX() && ball.getXPos() <= leftHit.getX() + leftHit.getWidth()){
						ball.reverseX();
					}
				}

				if(ball.getYPos() <= rightHit.getY() + rightHit.getHeight() && ball.getYPos() + ball.getHeight() >= rightHit.getY()){
					if(ball.getXPos() + ball.getWidth() >= rightHit.getX() && ball.getXPos() <= rightHit.getX() + rightHit.getWidth()){
						ball.reverseX();
					}
				}
				
				blocks.remove(index);
				curScore = curScore + (10*level);
//				print("hit"+index);

			}
		}
	}

	public void drawScoreScreen() {
		// TODO
		gr.setColor(Color.white);
		setFontSize(50);
		rect = fm.getStringBounds("HighScores", gr);
		gr.drawString("HighScores", (int)(windWidth*0.5 - rect.getWidth()*0.5), (int)(windHeight*0.1));

		printScores();
		
	}

	private void printScores() {
		gr.setColor(Color.orange);
		if(noScores){
			setFontSize(35);
			rect = fm.getStringBounds("No scores found!", gr);
			if(gameState.equalsIgnoreCase("gameOver")){
				gr.drawString("No scores found!", (int)(windWidth*0.5) - (int)rect.getCenterX(), (int)(windHeight * 0.5));
			}else{
				gr.drawString("No scores found!", (int)(windWidth*0.5) - (int)rect.getCenterX(), (int)(windHeight * 0.3));
			}
		}else{
			rect = fm.getStringBounds("OOO : OOO", gr);
			
			for(int c = 0; c < 7 && c < highScores.size(); c++ ){
				if(String.valueOf(highScores.get(c)) != null){
					rect = fm.getStringBounds(highScores.get(c), gr);
					if(gameState.equalsIgnoreCase("gameOver")){
						gr.drawString(highScores.get(c).toUpperCase(), (int)(windWidth*0.5) - (int)rect.getCenterX(), (int)(windHeight * (0.5 + (double)(c)/20)));
					}else{
						gr.drawString(highScores.get(c).toUpperCase(), (int)(windWidth*0.5) - (int)rect.getCenterX(), (int)(windHeight * (0.3 + (double)(c)/10)));
					}
				}
			}
		}
		scoreDrawn = true;
	}
}

/*NOTES
 * 
 *		http://gamedev.stackexchange.com/revisions/4335/2
 *
 *		try{
 *
 *		}catch(Exception e){
 *			print(e.getMessage());
 *		}
 */	