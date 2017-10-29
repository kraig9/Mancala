package gameState;

public class GameState {
	int moveAgain; //1 if last move results in a player being able to move again
	int stealPieces; //1 if last move results in a player being able to steal pieces
	
	int boardSize; //number of holes on each person's side of the board not counting the stores
	int currentMove; //1 if player 1's move, 2 if player 2's move
	
	int piecesInPlayerOneStore;
	int piecesInPlayerTwoStore;
	
	public GameState(int boardSize){
		moveAgain = 0;
		stealPieces = 0;
		this.boardSize = boardSize;
		currentMove = 1;
		piecesInPlayerOneStore = 0;
		piecesInPlayerTwoStore = 0;
	}
}
