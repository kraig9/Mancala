package Mancala;

import java.awt.EventQueue;

public class Game 
{

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try
				{
					GuiApp frame = new GuiApp();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
}
