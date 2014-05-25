package server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Test extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][]", "[grow][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 0 2 1,grow");
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		scrollPane.setViewportView(textArea);
		
		textField = new JTextField();
		contentPane.add(textField, "cell 0 1,growx");
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		contentPane.add(btnNewButton, "cell 1 1");
	}

}
