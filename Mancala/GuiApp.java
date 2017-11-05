package Mancala;



import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

public class GuiApp extends JFrame 
{

	JPanel contentPane;
	JButton quit_btn;

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
				
				contentPane.setVisible(false);
				contentPane=new Board();
				setContentPane(contentPane);
				quit();
				
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

}
