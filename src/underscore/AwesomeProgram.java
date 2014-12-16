package underscore;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AwesomeProgram {

	private Classifier c = new Classifier();
	
	private JFrame frame;
	private JFrame frame2;
	private JPanel panel;
	private JLabel l1;
	private JLabel l2 = new JLabel("Initializing");
	private JTextArea a1;
	private JButton b1;
	private JButton b2;
	private JButton b3;
	
	private String lastTxt = "";
	private SetUp.GENDER lastGender;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AwesomeProgram window = new AwesomeProgram();
					
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
		initializeIn();
	}
	
	private void initializeIn(){

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
            	l2.setText("banaantjes");
            	String lastTxt = a1.getText();
            	a1.setText("");
            	lastGender = c.checkGender(lastTxt);
            	System.out.println(lastGender);
            	if(lastGender == SetUp.GENDER.MALE){
            		System.out.println("1");
            		initializeOut("That is a male sentence.");
            	}else if(lastGender == SetUp.GENDER.FEMALE){
            		System.out.println("2");
            		initializeOut("That is a female sentence.");
            	}else{
            		System.out.println("3");
            		initializeOut("No gender found");
            	}
            }
        });      
		frame.setVisible(true);
	}
	
	private void initializeOut(String txt){
		//output frame
		frame2 = new JFrame();
		frame2.setBounds(100, 100, 400, 300);
		frame2.setLayout(new GridLayout(3,1));
		l2 = new JLabel(txt);
		frame2.add(l2);
		b2 = new JButton("Das ok");
		b2.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {	
            	if(lastGender != null){
            	//program was right
            	SetUp.processTrainingData(lastTxt, lastGender);
            	System.out.println("ok");
            	frame2.dispose();
            	}
            }
        }); 
		frame2.add(b2);
		b3 = new JButton("Das bullshit");
		b3.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	if(lastGender != null){
            	//program was not right
            	SetUp.processTrainingData(lastTxt, lastGender.other());
            	System.out.println("not ok");
            	frame2.dispose();
            	}
            }
        }); 
		frame2.add(b3);
		frame2.setVisible(true);
	}
}
