package underscore;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AwesomeProgram extends Functions{

	private Classifier c = new Classifier();
	
	private JFrame frame;
	private JFrame frame2;
	private JLabel l1;
	private JLabel l2 = new JLabel("Initializing");
	private JTextArea a1;
	private JButton b1;
	private JButton b2;
	private JButton b3;
	
	private String lastTxt = "";
	private GENDER lastGender;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new AwesomeProgram();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	@SuppressWarnings("unused")
	public AwesomeProgram() {
		String dirBlogstrainM = "resources/blogstrain/M";
		String dirBlogstrainF = "resources/blogstrain/F";
		String dirSpamtrainHam = "resources/spamtrain/ham";
		String dirSpamtrainSpam = "resources/spamtrain/spam";
		String dirTesttrainM = "resources/testje/HAM";
		String dirTesttrainF = "resources/testje/SPAM";

		String dirBlogstestM = "resources/blogstest/M";
		String dirBlogstestF = "resources/blogstest/F";
		String dirSpamtestHam = "resources/spamtest/ham";
		String dirSpamtestSpam = "resources/spamtest/spam";
		String dirTesttestM = "resources/testje";
		String dirTesttestF = "resources/testje";
		
		new Trainer(dirBlogstrainM, dirBlogstrainF);
		
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
            	System.out.println("Check this text: "+lastTxt);
            	lastGender = c.checkGender(lastTxt);
            	System.out.println("This is the gender("+lastGender+") for this text: "+lastTxt);
            	
            	if(lastGender == GENDER.MALE){
            		System.out.println("1");
            		initializeOut("That is a male sentence.");
            	}else if(lastGender == GENDER.FEMALE){
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
            	processTrainingData(lastTxt, lastGender);
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
            	processTrainingData(lastTxt, lastGender.other());
            	System.out.println("not ok");
            	frame2.dispose();
            	}
            }
        }); 
		frame2.add(b3);
		frame2.setVisible(true);
	}
}
