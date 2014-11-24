package underscore;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;

public class AwesomeProgram {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AwesomeProgram window = new AwesomeProgram();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AwesomeProgram() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		JLabel l1 = new JLabel("Enter a sentence here");
		frame.add(l1, BorderLayout.NORTH);
		JTextArea a1 = new JTextArea(400,200);
		frame.add(a1, BorderLayout.CENTER);
		JButton b1 = new JButton("Confirm");
		frame.add(b1, BorderLayout.SOUTH);
	}
}
