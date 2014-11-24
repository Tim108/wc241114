package underscore;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AwesomeProgram {

	private Classifier c = new Classifier();
	
	private JFrame frame;
	private JFrame frame2;
	
	private JLabel l1;
	private JLabel l2;
	private JTextArea a1;
	private JButton b1;
	private JButton b2;
	private JButton b3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AwesomeProgram window = new AwesomeProgram(true);
					window.frame.setVisible(true);
					AwesomeProgram window2 = new AwesomeProgram(false);
					window2.frame2.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AwesomeProgram(boolean in) {
		initialize(in);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(boolean in) {
		if(in){
			//input frame
			frame = new JFrame();
			frame.setBounds(100, 100, 400, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());
			l1 = new JLabel("Enter a sentence here");
			frame.add(l1, BorderLayout.NORTH);
			a1 = new JTextArea(400,200);
			frame.add(a1, BorderLayout.CENTER);
			b1 = new JButton("Confirm");
			frame.add(b1, BorderLayout.SOUTH);
			b1.addActionListener(new ActionListener() {
				 
	            public void actionPerformed(ActionEvent e)
	            {
	            	String txt = a1.getText();
	            	System.out.println(txt);
	            }
	        });      
	 
		}else{
			//output frame
			frame2 = new JFrame();
			frame2.setBounds(100, 100, 400, 300);
			frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame2.setLayout(new BorderLayout());
			l2 = new JLabel("This is not manly");
			frame2.add(l2, BorderLayout.NORTH);
			b2 = new JButton("You're right");
			frame2.add(b2, BorderLayout.SOUTH);
			b3 = new JButton("Das bullshit");
			frame2.add(b3, BorderLayout.SOUTH);
		}
	}
}
