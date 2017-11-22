package Mancala;



import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



import java.awt.Color;

//takes care of most of the GUI functionality, to include the board and menus
public class GuiApp extends JFrame 
{

	JPanel contentPane;
	JButton quit_btn;
	JButton Back_btn;
	JPanel Intro;
	static String serverPort;
	static Boolean server;
	static Boolean AI_First;
	static Boolean AI_Second;
	Boolean unfilled_bool;

	/**
	 * Create the frame.
	 */
	public GuiApp()
	{
		serverPort=null;
		server = false;
		AI_Second=false;
		AI_First=false;
		unfilled_bool= false;
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
		ClientGame();
		HostGame();
	}
	public void Unfilled()
	{
		if (unfilled_bool ==false)
		{
			JButton ok = new JButton("OK");
			ok.setBounds(250, 150, 100, 50);
			ok.setFont(new Font("Serif", Font.BOLD, 20));
			JLabel out_text = new JLabel("Please Fill Out All The Parameters!");
			out_text.setBounds(125, 50, 400, 50);
			out_text.setFont(new Font("Serif", Font.BOLD, 20));
			JFrame Unfilled = new JFrame();
			Unfilled.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Unfilled.setBounds(200, 400, 600, 300);
			Unfilled.setTitle("Fill Parameters");
			Unfilled.setLayout(null);
			Unfilled.add(ok);
			Unfilled.add(out_text);
			Unfilled.setVisible(true);
			unfilled_bool = true;
		
			ok.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					Unfilled.dispose();
					unfilled_bool = false;
				}
			});
		}

	}
	public void start()
	{
		Start_Screen.start_btn.addActionListener(new ActionListener()
		{
			@Override
			//start screen
			public void actionPerformed(ActionEvent e) 
			{
				Start_Screen.seed_selected = Start_Screen.seed_menu.getSelectedItem().toString();
				Start_Screen.player_selected = Start_Screen.player_menu.getSelectedItem().toString();
				Start_Screen.opponent_selected = Start_Screen.opponent_menu.getSelectedItem().toString();
				Start_Screen.holes_selected = Start_Screen.holes_menu.getSelectedItem().toString();
				Start_Screen.seconds_in = Start_Screen.time_input.getText();
	
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
					if(player=="Computer")
					{
						AI_First = true;
					}
					if(opponent == "Computer")
					{
						AI_Second=true;
					}
				}
				else
				{
					Unfilled();
				}
				
				
			}
			
		});
	}
	

	//specifically for the client option
	public void ClientGame()
	{
		Start_Screen.Client_btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					GameClient.main(null);
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
				
			}
		});
	}
	//specifically host a server
	public void HostGame()
	{
		
		Start_Screen.Server_btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Start_Screen.seed_selected = Start_Screen.seed_menu.getSelectedItem().toString();
				Start_Screen.player_selected = Start_Screen.player_menu.getSelectedItem().toString();
				Start_Screen.opponent_selected = Start_Screen.opponent_menu.getSelectedItem().toString();
				Start_Screen.holes_selected = Start_Screen.holes_menu.getSelectedItem().toString();
				Start_Screen.seconds_in = Start_Screen.time_input.getSelectedText();
				String seed =Start_Screen.seed_selected ;
				String holes = Start_Screen.holes_selected;
				String player = Start_Screen.player_selected;
				String opponent = Start_Screen.opponent_selected;
				
				if(seed!= "Choose Number Of Seeds" && holes != "Choose Number Of Holes" && player != "Choose a Player" && opponent != "Choose an Opponent")
				{
					if(player=="Computer")
					{
						AI_First = true;
					}
					if(opponent == "Computer")
					{
						AI_Second=true;
					}
					serverPort = JOptionPane.showInputDialog(
				            Game.frame,
				            "What Port Do You Want To Use (Use Port Greater Than 1024):",
				            "Server Port Number",
				            JOptionPane.QUESTION_MESSAGE);
					new ServerMain().start();
					/*try 
					{
						GameClient.main(null);
					} 
					catch (Exception e1) 
					{
						
						e1.printStackTrace();
					}*/
				}
				else
				{
					Unfilled();
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
				System.exit(0);
	
				
			}
			
		});
	}
	//instructions image should be included! 
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

	public static class ServerMain extends Thread
	{
	    public void run()
	    {
	    	try 
			{
				GameServer.main(null);
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
	    }    
	}
}
