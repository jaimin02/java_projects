package Test;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class MyJRadioButton extends JFrame implements ActionListener
{
	JPanel panel = new JPanel();
	String[] name = new String[]{"male","female","maleagain","femaleagain"};
     ButtonGroup bg;
    MyJRadioButton()
{
    setLayout(new FlowLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    bg=new ButtonGroup();
    for(int i=0;i<4;i++)
    {	 JRadioButton rbi;
    	 rbi=new JRadioButton(name[i]);
    	 bg.add(rbi);
    	 add(rbi);
    	 rbi.addActionListener(this);
     }
    
   
    //rb2=new JRadioButton("female");

    //add radio button to button group
   // bg=new ButtonGroup();
   

    //add radio buttons to frame,not button group
   // add(rb1);
  // add(rb2);
    //add action listener to JRadioButton, not ButtonGroup
   // rb1.addActionListener(this);
   // rb2.addActionListener(this);
    pack();
    setVisible(true);
}
public static void main(String[] args)
{
    new MyJRadioButton(); //calling constructor
}
@Override
public void actionPerformed(ActionEvent e) 
{
    System.out.println(((JRadioButton) e.getSource()).getActionCommand());
}
}
