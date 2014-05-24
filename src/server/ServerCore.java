package server;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.Dimension;

import javax.swing.border.BevelBorder;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public abstract class ServerCore extends JFrame {
	public ServerCore() {
	}

	JFrame window;
	Dimension windDim;
	JScrollBar vert;
	JTextArea taOut;
	JTextArea taIn; 
	JScrollPane scroll;
	JButton btnSend;
	String gameState = "init";

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ServerCore frame = new ServerCore();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	public void run() {
		try {
			init();
			gameState = "server";
			mainLoop();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			System.exit(-1);
		}
	}

	/**
	 * Create the frame.
	 */
	public void init() {
		window = new JFrame();
		
		window.getContentPane().setBackground(Color.WHITE);
		windDim = new Dimension(667,332);
		window.setMinimumSize(windDim);
		window.getContentPane().setLayout(new MigLayout("", "[grow][]", "[grow][]"));
		
		taOut = new JTextArea();
		taOut.setRequestFocusEnabled(false);
		taOut.setFocusTraversalKeysEnabled(false);
		taOut.setEditable(false);
		window.getContentPane().add(taOut, "cell 0 0 2 1,grow");
		
		scroll = new JScrollPane(taOut);
		scroll.setFocusTraversalKeysEnabled(false);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setSize(new Dimension(50,50));
		vert = scroll.getVerticalScrollBar();
		scroll.setViewportView(taOut);
		window.getContentPane().add(scroll, "cell 0 0 2 1,grow");
		
		taIn = new JTextArea();
		taIn.setBackground(SystemColor.inactiveCaptionBorder);
		taIn.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		window.getContentPane().add(taIn, "cell 0 1, grow,aligny bottom");
		
		btnSend = new JButton("Send");
		window.getRootPane().setDefaultButton(btnSend);
		window.getRootPane().getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "none");
		window.getRootPane().getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released ENTER"), "press");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnSndList();
			}
		});
		window.getContentPane().add(btnSend, "cell 1 1");
		double scale = 1;
				
		int windowX = getGraphicsConfiguration().getDevice().getDisplayMode().getWidth();// get the monitor's width
		int windowY = getGraphicsConfiguration().getDevice().getDisplayMode().getHeight(); // get the monitor's height
		windowX = (int) (windowX * 0.5) - (int) (windDim.width * 0.5); // divide the screen and background image width by 2
		windowY = (int) (windowY * 0.5) - (int) (windDim.height * 0.5); // divide the screen and background image height by 2
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(windowX, windowY, windDim.width, windDim.height);
		
		window.setVisible(true);
	}

	
	
	public void mainLoop(){		
		while(!gameState.equalsIgnoreCase("quitting")){
			
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
	
	private void btnSndList() {
		String txtIn = taIn.getText();
		taOut.append(txtIn + "\n");
		taOut.setCaretPosition(taOut.getDocument().getLength());
		taIn.setText("");
	}
	
	public abstract void MoreCalls();
}
