package Mancala;

public class GameState {
	public int moveAgain; //1 if last move results in a player being able to move again
	public int stealPieces; //1 if last move results in a player being able to steal pieces
	
	public int boardSize; //number of holes on each person's side of the board not counting the stores
	public int currentMove; //1 if player 1's move, 2 if player 2's move
	public int lastHole; //last hole to receive a piece. This is for when figuring out when to steal pieces
	
	public int firstMove; //This is for the pie rule so that we can give player two the choice to take it
	
	public int gameOver; //when the game ends this will be set to 1
	
	public int piecesInPlayerOneStore;
	public int piecesInPlayerTwoStore;
	
	public GameState(int boardSize){
		moveAgain = 0;
		stealPieces = 0;
		this.boardSize = boardSize;
		currentMove = 1;
		firstMove = 1;
		piecesInPlayerOneStore = 0;
		piecesInPlayerTwoStore = 0;
		gameOver = 0;
	}
	
	public int getCurrentMove(){
		return currentMove;
	}
	
	public int getBoardSize(){
		return boardSize;
	}
	
	public int getStealPieces(){
		return stealPieces;
	}
	
	public int getMoveAgain(){
		return moveAgain;
	}
	
	public int getPiecesInPlayerOneStore(){
		return piecesInPlayerOneStore;
	}
	
	public int getPiecesInPlayerTwoStore(){
		return piecesInPlayerTwoStore;
	}
	
	public void setCurrentMove(int currentMove){
		this.currentMove = currentMove;
	}
	
	public void setBoardSize(int boardSize){
		this.boardSize = boardSize;
	}
	
	public void setStealPieces(int stealPieces){
		this.stealPieces = stealPieces;
	}
	
	public void setMoveAgain(int moveAgain){
		this.moveAgain = moveAgain;
	}
	
	public void setPiecesInPlayerOneStore(int pieces){
		this.piecesInPlayerOneStore = pieces;
	}
	
	public void setPiecesInPlayerTwoStore(int pieces){
		this.piecesInPlayerTwoStore = pieces;
	}
}
