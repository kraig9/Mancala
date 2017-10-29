package Mancala;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class GuiApp extends JFrame {

	private JPanel contentPane;

	public void paint(Graphics g)
	{
		super.paintComponents(g);	
		g.setColor(Color.lightGray);
        g.fillRect(50, 150, 1600, 600);
        g.setColor(Color.GRAY);
        g.fillOval(75, 200,150,500);
        g.fillOval(1475, 200,150,500);
        
        g.fillOval(250, 525, 200, 200);
        g.fillOval(450, 525, 200, 200);
        g.fillOval(650, 525, 200, 200);
        g.fillOval(850, 525, 200, 200);
        g.fillOval(1050, 525, 200, 200);
        g.fillOval(1250, 525, 200, 200);
        
        g.fillOval(250, 175, 200, 200);
        g.fillOval(450, 175, 200, 200);
        g.fillOval(650, 175, 200, 200);
        g.fillOval(850, 175, 200, 200);
        g.fillOval(1050, 175, 200, 200);
        g.fillOval(1250, 175, 200, 200);
		
	}
	/**
	 * Create the frame.
	 */
	public GuiApp()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1700, 900);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(139, 69, 19));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setVisible(true);
	}

}
