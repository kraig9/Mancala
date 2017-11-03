package Mancala;

import java.awt.EventQueue;

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
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
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
