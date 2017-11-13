package Mancala;



import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

public class GuiApp extends JFrame 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	JButton quit_btn;
	JButton Back_btn;
	JPanel Intro;

	/**
	 * Create the frame.
	 */
	public GuiApp()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBackground(new Color(139, 69, 19));
		setBounds(100, 100, 1700, 1000);
		setTitle("Mankala");
		contentPane = new Start_Screen();
		setContentPane(contentPane);
		setVisible(true);
		Instructions();
		start();
		quit();
		
	}
	public void start()
	{
		Start_Screen.start_btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Start_Screen.seed_selected = Start_Screen.seed_menu.getSelectedItem().toString();
				Start_Screen.player_selected = Start_Screen.player_menu.getSelectedItem().toString();
				Start_Screen.opponent_selected = Start_Screen.opponent_menu.getSelectedItem().toString();
				Start_Screen.holes_selected = Start_Screen.holes_menu.getSelectedItem().toString();
				
				String seed =Start_Screen.seed_selected ;
				String holes = Start_Screen.holes_selected;
				String player = Start_Screen.player_selected;
				String opponent = Start_Screen.opponent_selected;
				if(seed!= "Choose Number Of Seeds" && holes != "Choose Number Of Holes" && player != "Choose a Player" && opponent != "Choose an Opponent")
				{
					contentPane.setVisible(false);
					contentPane=new Board();
					setContentPane(contentPane);
					quit();
				}
				
			}
			
		});
	}
	
	public void quit()
	{
		quit_btn=new JButton();
		quit_btn.setBackground(Color.GRAY);
		quit_btn.setText("Quit");
		quit_btn.setToolTipText("Click to Quit Game!");
		quit_btn.setBounds(1530,895, 150, 50);
		quit_btn.setFont(new Font("Serif", Font.BOLD, 20));
		add(quit_btn);
		quit_btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
	
				
			}
			
		});
	}
	public void Instructions()
	{
		Back_btn = new JButton("Back");
		Back_btn.setBounds(800,850, 150, 50);
		Back_btn.setFont(new Font("Serif", Font.BOLD, 20));
		JLabel image =  new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/images/Instructions.png")));
		image.setBounds(0, 0, 1700, 900);
		Start_Screen.Instruction_btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
					contentPane.setVisible(false);
					Intro=new JPanel();
					Intro.setLayout(null);
					Intro.add(image);
					Intro.add(Back_btn);
					setContentPane(Intro);
					Intro.setVisible(true);
					Back();
				
			}
		});
	}
	public void Back()
	{
		Back_btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
					Intro.setVisible(false);
					setContentPane(contentPane);
					contentPane.setVisible(true);
				
			}
		});
	}
}
