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

class Peices {
	
}

class Board {
	public enum Hole{
		//player one's holes and pit
		one,
		two,
		three,
		four,
		five,
		six,
		//player two's holes and pit
		seven,
		eight,
		nine,
		ten,
		eleven,
		twelve;
		
		//start with 4 marbles in each hole
		public static final int START_MARBLES = 4;
		//holds the amount of marbles currently in hole
		private double numMarbles;
		Hole (){
			this.numMarbles = START_MARBLES;
		}
	}
	class pit1{
		int numMarbles;
		
		void addMarbles(){
			numMarbles++;
		}
	}
	class pit2{
		int numMarbles;
		
		void addMarbles() {
			numMarbles++;
		}
	}
	
}

class Scoreboard {
	
}