package Mancala;

import java.awt.EventQueue;

//Start the game here! This class is the first one called.
public class Game 
{

	static GuiApp frame;
	/**
	 * Launch the application.
	 */
	public static void Close()
	{
		frame.dispose();
	}
	public static void Open()
	{
		frame = new GuiApp();
	}
	public static void Client_Main()
	{
		frame.contentPane.setVisible(false);
		//This is where our board(s) are initialized.
		frame.contentPane=new Board();
		frame.setContentPane(new Board());
		frame.quit();
	}
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			//Where the game is run
			public void run() 
			{
				try
				{
					frame = new GuiApp();
					
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
}
