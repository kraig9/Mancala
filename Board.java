import hole.HoleClass;
import gameState.GameState;
import java.util.Enumeration;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

public class Board {
	static Vector<HoleClass> holes = new Vector<HoleClass>();
	
	//this adds the holes to the board
	//numHoles refers to the number of holes on each side of the board
	public static void addHolesToBoard(Vector<HoleClass> holes, int numHoles){
		for(int i = 0; i < numHoles; i++){
			HoleClass holeOne = new HoleClass();
			holeOne.setSide(1);
			holeOne.setNumPieces(4);
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
			holeTwo.setNumPieces(4);
			holeTwo.setStore(0);
			holes.add(holeTwo);
		}
		HoleClass holeTwo = new HoleClass();
		holeTwo.setSide(2);
		holeTwo.setNumPieces(0);
		holeTwo.setStore(1);
		holes.add(holeTwo);
	}
	
	//this function is called whenever a player attempts to move a piece
	//holeNumber refers to the hole that has the pieces the player is trying to move
	//playerMakingMove refers to the player who is moving the pieces
	//return true for valid move, false for invalid move
	public static boolean movePieces (GameState gameState, Vector<HoleClass> holes, int holeNumber, int playerMakingMove){
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
		if(selectedHole.getSide() != playerMakingMove){
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
			if((holes.get(currentHole).getStore() != 1) || (holes.get(currentHole).getSide() == playerMakingMove)){
				//add one piece to the current hole
				holes.get(currentHole).setNumPieces(piecesInCurrentHole + 1);
				
				//subtract 1 from our counter whenever a piece is added to a hole
				piecesInSelectedHole--;
			}
			
		}
		
		return true;
		
		
	}
	
	public static void main(String[] args) {
		//may create a function later that could allow user to choose number of holes, for now will make default 6
		int holesOnSide = 6;
		
		//creates board
		addHolesToBoard(holes, holesOnSide);
		
		//Once we know how many holes are in the board, we can set up the game state
		GameState gameState = new GameState(holesOnSide);
		
		int totalHolesInBoard = holesOnSide*2 + 2;
		
		for(int i = 0; i < totalHolesInBoard; i++){
			System.out.print(holes.get(i).getNumPieces());
			System.out.print(" ");
		}
		System.out.println();
		//ADD THE CHANGES TO ALLOW THE GAME STATE TO DO ITS JOB!
		movePieces(gameState, holes, 2, 1);
		movePieces(gameState, holes, 9, 2);
		for(int i = 0; i < totalHolesInBoard; i++){
			System.out.print(holes.get(i).getNumPieces());
			System.out.print(" ");
		}
		System.out.println();
	}

}
