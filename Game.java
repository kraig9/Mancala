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
	public static void Open()
	{
		frame = new GuiApp();
	}
	public static void startGame(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try
				{
					frame = new GuiApp();
					//create a function later that could allow user to choose number of holes, for now will make default 6
					//int holesOnSide = 6;
					
					//create a function later that could allow user to choose number of pieces in each hole, for now will make default 4
					//int piecesPerHole = 5;
					
					//create a function later that could allow user to choose if they want to make the seeding random
				//	int randomSeed = 1;
					
					//creates board
					
					//Board.addHolesToBoard(holesOnSide, piecesPerHole, randomSeed);
					
					//Once we know how many holes are in the board, we can set up the game state
					
					//int totalHolesInBoard = holesOnSide*2 + 2;
					
				/*	for(int i = 0; i < totalHolesInBoard; i++){
						System.out.print(Board.holes.get(i).getNumPieces());
						System.out.print(" ");
					}
					System.out.println();
					Board.fullTurn(gameState, 2, totalHolesInBoard);
					Board.fullTurn(gameState, 5, totalHolesInBoard);
					Board.fullTurn(gameState, 12, totalHolesInBoard);
					System.out.println();
					for(int i = 0; i < totalHolesInBoard; i++){
						System.out.print(Board.holes.get(i).getNumPieces());
						System.out.print(" ");
					}*/
					
					

				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
}
