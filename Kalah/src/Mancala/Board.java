package Mancala;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Board extends JPanel 
{
	public static final int pits = 6; 
	JButton restart_btn;
	/**
	 * Create the panel.
	 */
	public Board() 
	{
		setLayout(null);
		restart_btn=new JButton();
		restart_btn.setBackground(Color.GRAY);
		restart_btn.setText("Restart");
		restart_btn.setToolTipText("Click to Restart Mankala!");
		restart_btn.setBounds(1380,795, 150, 50);
		restart_btn.setFont(new Font("Serif", Font.BOLD, 20));
		add(restart_btn);
		restart();
	}
	public void restart()
	{
		restart_btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				Game.Close();
				new GuiApp();
			}
			
		});
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponents(g);	
		Draw_Board(g);
		Draw_Seeds(g);
	}
	public void Draw_Board(Graphics g)
	{
		g.setColor(Color.lightGray);
		//board
        g.fillRect(50, 150, 1600, 600);
        g.setColor(Color.GRAY);
        //stores
        g.fillOval(75, 200,150,500);
        g.fillOval(1475, 200,150,500);
        //top pits
        g.fillOval(250, 525, 200, 200);
        g.fillOval(450, 525, 200, 200);
        g.fillOval(650, 525, 200, 200);
        g.fillOval(850, 525, 200, 200);
        g.fillOval(1050, 525, 200, 200);
        g.fillOval(1250, 525, 200, 200);
        //bottom pits
        g.fillOval(250, 175, 200, 200);
        g.fillOval(450, 175, 200, 200);
        g.fillOval(650, 175, 200, 200);
        g.fillOval(850, 175, 200, 200);
        g.fillOval(1050, 175, 200, 200);
        g.fillOval(1250, 175, 200, 200);
      //System.out.println("You selected : " + Start_Screen.seed_selected);
	}
	public void Draw_Seeds(Graphics g)
	{
		g.setColor(Color.RED);
		/*int length =290;
		int y = 600;
		double position_x=0;
		double position_y=0;
		for(int j=0;j<4;j++)
		{
			position_x=(100 +length) * Math.cos(j/pits);
			position_y= (100 + y) * Math.sin(j/pits);
			System.out.println("You selected : " + position_x + "  this   " + position_y);
			g.fillOval((int)position_x,(int)position_y,20,20);
		}*/
		int initial_x=290;
		int initial_y=600;
		int x_spacing = 100;
		for(int i=0; i<2; i++)
		{
			for(int j=0;j<(pits*2);j++)
			{
				g.fillOval(initial_x,initial_y,20,20);
				initial_x=initial_x + x_spacing;
			}
			initial_y=650;
			initial_x=290;
		}
		initial_x=290;
		initial_y=250;
		for(int i=0; i<2; i++)
		{
			for(int j=0;j<(pits*2);j++)
			{
				g.fillOval(initial_x,initial_y,20,20);
				initial_x=initial_x + x_spacing;
			}
			initial_y=305;
			initial_x=290;
		}
		
		
	}

}
