import hole.HoleClass;
import gameState.GameState;
import java.util.Enumeration;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
	static Vector<HoleClass> holes = new Vector<HoleClass>();
	
	//this adds the holes to the board
	//numHoles refers to the number of holes on each side of the board
	public static void addHolesToBoard(int numHoles, int piecesPerHole, int randomSeed){
		Vector<Integer> seedAmounts = new Vector<Integer>();
		
		for(int i = 0; i < numHoles; i++){
			seedAmounts.add(piecesPerHole);
		}
		
		if(randomSeed == 1){
			for(int i = 0; i < numHoles; i++){
				seedAmounts.set(i,0);
			}
			
			int seedsRemaining = numHoles * piecesPerHole;
			
			int currentHole = 0;
			while(seedsRemaining > 0){
				Random rand = new Random();
				int n = rand.nextInt(2);
				if(n == 1){
					int previousAmount = seedAmounts.get(currentHole);
					seedAmounts.set(currentHole, previousAmount + 1);
					seedsRemaining--;
				}
				if(currentHole == numHoles - 1){
					currentHole = 0;
				}
				else{
					currentHole++;
				}
			}
			
			for(int i = 0; i < numHoles; i++){
				System.out.println(seedAmounts.get(i));
			}
		}
		
		for(int i = 0; i < numHoles; i++){
			HoleClass holeOne = new HoleClass();
			holeOne.setSide(1);
			holeOne.setNumPieces(seedAmounts.get(i));
			holeOne.setStore(0);
			holes.add(holeOne);
		}
		HoleClass storeOne = new HoleClass();
		storeOne.setSide(1);
		storeOne.setNumPieces(0);
		storeOne.setStore(1);
		holes.add(storeOne);
		for(int i = 0; i < numHoles; i++){
			HoleClass holeTwo = new HoleClass();
			holeTwo.setSide(2);
			holeTwo.setNumPieces(seedAmounts.get(i));
			holeTwo.setStore(0);
			holes.add(holeTwo);
		}
		HoleClass holeTwo = new HoleClass();
		holeTwo.setSide(2);
		holeTwo.setNumPieces(0);
		holeTwo.setStore(1);
		holes.add(holeTwo);
	}
	
	public static void nextMove(GameState gameState, boolean result){
		//it will switch to the next player if the last move did not result in a free move and if it was a valid move
		if((gameState.getMoveAgain() == 0) && (result == true)){
			if(gameState.getCurrentMove() == 1){
				gameState.setCurrentMove(2);
			}
			else{
				gameState.setCurrentMove(1);
			}
		}
	}
	
	public static void stealPieces(GameState gameState){
		if(gameState.getStealPieces() == 1){
			holes.get(gameState.lastHole).setNumPieces(0);
			
			int oppositeHoleIndex = gameState.getBoardSize() * 2 - gameState.lastHole;
			
			int piecesInOpposite = holes.get(oppositeHoleIndex).getNumPieces();
			holes.get(oppositeHoleIndex).setNumPieces(0);
			
			//put pieces into the mover's store
			int storeIndex = 0;
			if(gameState.currentMove == 1){
				storeIndex = gameState.getBoardSize();
			}
			else{
				storeIndex = holes.size() - 1;
			}
			int origStorePieces = holes.get(storeIndex).getNumPieces();
			
			//the extra one at the end accounts for the piece that went from the players own hole to the store
			holes.get(storeIndex).setNumPieces(origStorePieces + piecesInOpposite + 1);
		}
	}
	
	//this function is called whenever a player attempts to move a piece
	//holeNumber refers to the hole that has the pieces the player is trying to move
	//playerMakingMove refers to the player who is moving the pieces
	//return true for valid move, false for invalid move
	public static boolean movePieces (GameState gameState, int holeNumber){
		HoleClass selectedHole = new HoleClass();
		
		//grabs the hole selected by the user and assigns it to selectedHole
		if(holeNumber < holes.size()){
			selectedHole = holes.get(holeNumber);
		}
		else{
			System.out.println("Selected Hole Number does not exist");
			return false;
		}
		
		//if the player does not control the selected hole, the move is invalid
		if(selectedHole.getSide() != gameState.getCurrentMove()){
			System.out.println("Must make a move on the current player's side of the board");
			return false;
		}
		
		//The player cannot make a move from their store
		if(selectedHole.getStore() == 1){
			System.out.println("Cannot make a move out of the current player's store");
			return false;
		}
		
		//gets the amount of pieces in the selected hole
		int piecesInSelectedHole = selectedHole.getNumPieces();
		
		//The hole selected must have at least one piece in it
		if(piecesInSelectedHole == 0){
			System.out.println("Cannot make a move where there are no pieces");
			return false;
		}
		
		//after the move is complete the selected hole will have zero pieces unless it has 13 or more but that situation will be covered later
		selectedHole.setNumPieces(0);
		
		int currentHole = holeNumber;
		
		while(piecesInSelectedHole != 0){
			//if currentHole is the last hole, resets currentHole to be the first hole in the sequence
			if(currentHole != holes.size() - 1){
				currentHole++;
			}
			else{
				currentHole = 0;
			}
			
			
			int piecesInCurrentHole = holes.get(currentHole).getNumPieces();
			
			//adds a piece to the next hole in the sequence as long as it is not the opponents store
			if((holes.get(currentHole).getStore() != 1) || (holes.get(currentHole).getSide() == gameState.getCurrentMove())){
				//add one piece to the current hole
				
				//If the last piece goes into an empty hole on the players side then they can steal the pieces from the opposite hole on the opponents side
				gameState.lastHole = currentHole;
				if((holes.get(currentHole).getNumPieces() == 0) && (holes.get(currentHole).getSide() == gameState.getCurrentMove())){
					gameState.setStealPieces(1);
				}
				else{
					gameState.setStealPieces(0);
				}
				
				holes.get(currentHole).setNumPieces(piecesInCurrentHole + 1);
				
				//if the last piece goes into the player's store they get to move again
				if(holes.get(currentHole).getStore() == 1){
					gameState.setMoveAgain(1);
				}
				else{
					gameState.setMoveAgain(0);
				}
				
				//subtract 1 from our counter whenever a piece is added to a hole
				piecesInSelectedHole--;
			}
			
		}
		
		return true;
		
		
	}
	
	static void endGame(GameState gameState) {
		//check to see if one side is empty.
		int side1Peices = 0, side2Peices = 0;
		
		//iterate through side1, add up marbles excluding the store (mancala)
		for(int i=0; i<holes.size()/2; i++) {
			if(holes.get(i).getStore()!=1){
				side1Peices+=holes.get(i).getNumPieces();
			}
		}
		//iterate through side2, add up marbles excluding the store (mancala)
		for(int i=holes.size()/2; i<holes.size(); i++) {
			if(holes.get(i).getStore()!=1){
				side2Peices+=holes.get(i).getNumPieces();
			}
		}
		
		//give store 1 player 2's pieces if player 1 has none left
		if(side1Peices==0) {
			for(int i = holes.size()/2; i < holes.size()-1; i++){
				holes.get(i).setNumPieces(0);
			}
			int currentPeices = holes.get(holes.size()-1).getNumPieces();
			holes.get(holes.size()-1).setNumPieces(currentPeices+side2Peices);
			
			gameState.gameOver = 1;
		}
		//give store 2 player 1's pieces if player 2 has none left
		if(side2Peices==0) {
			for(int i = 0; i < holes.size()/2 - 1; i++){
				holes.get(i).setNumPieces(0);
			}
			int currentPeices = holes.get((holes.size()/2)-1).getNumPieces();
			holes.get((holes.size()/2)-1).setNumPieces(currentPeices+side1Peices);
			
			gameState.gameOver = 1;
		}
		gameState.setPiecesInPlayerOneStore(holes.get(holes.size()/2-1).getNumPieces());
		gameState.setPiecesInPlayerTwoStore(holes.get(holes.size()-1).getNumPieces());
	}
	
	public static void pieRule(GameState gameState){
		
		gameState.setCurrentMove(1);
		for(int i = 0; i < gameState.boardSize + 1; i++){
			int piecesForPlayerTwo = holes.get(i).getNumPieces();
			int piecesForPlayerOne = holes.get(i+gameState.boardSize+1).getNumPieces();
			holes.get(i).setNumPieces(piecesForPlayerOne);
			holes.get(i+gameState.boardSize+1).setNumPieces(piecesForPlayerTwo);
		}
	}
	
	public static void fullTurn(GameState gameState, int hole, int totalHolesInBoard){
		boolean result = movePieces(gameState, hole);
		stealPieces(gameState);
		//This determines who will move next depending on where the last piece ends up
		nextMove(gameState, result);
		endGame(gameState);
		
		//Choice is where we set player two's choice for the pie rule
		int choice;
		
		//if it's the first move and if choice == 1, we run the pie rule function
		if((gameState.firstMove == 1) && (gameState.currentMove == 2)){
			gameState.firstMove = 0;
			choice = 1; //set choice to 0 for no pie rule
			if(choice == 1){
				pieRule(gameState);
			}
		}
		
		System.out.println();
		System.out.print("Player 1 Score: ");
		System.out.print(gameState.getPiecesInPlayerOneStore());
		System.out.print(" Player 2 Score: ");
		System.out.print(gameState.getPiecesInPlayerTwoStore());
		System.out.println();
		
		for(int i = 0; i < totalHolesInBoard; i++){
			System.out.print(holes.get(i).getNumPieces());
			System.out.print(" ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		//create a function later that could allow user to choose number of holes, for now will make default 6
		int holesOnSide = 6;
		
		//create a function later that could allow user to choose number of pieces in each hole, for now will make default 4
		int piecesPerHole = 5;
		
		//create a function later that could allow user to choose if they want to make the seeding random
		int randomSeed = 1;
		
		//creates board
		
		addHolesToBoard(holesOnSide, piecesPerHole, randomSeed);
		
		//Once we know how many holes are in the board, we can set up the game state
		GameState gameState = new GameState(holesOnSide);
		
		int totalHolesInBoard = holesOnSide*2 + 2;
		
		for(int i = 0; i < totalHolesInBoard; i++){
			System.out.print(holes.get(i).getNumPieces());
			System.out.print(" ");
		}
		System.out.println();
		fullTurn(gameState, 2, totalHolesInBoard);
		fullTurn(gameState, 5, totalHolesInBoard);
		fullTurn(gameState, 12, totalHolesInBoard);
		System.out.println();
		for(int i = 0; i < totalHolesInBoard; i++){
			System.out.print(holes.get(i).getNumPieces());
			System.out.print(" ");
		}
		
		
	}

}
