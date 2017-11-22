package Mancala;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AI {
	static boolean pie_choice;
	
	public static Vector<Integer> possibleMovesFuture(GameState gameState, Vector<HoleClass> nextHoles) {
		Vector<Integer> moves = new Vector<Integer>();
		//System.out.print("Moves: ");
		for(int i = 0; i < gameState.boardSize*2+1; i++) {
			if(nextHoles.get(i).getNumPieces() != 0) {
				if(nextHoles.get(i).getSide() == gameState.currentMove) {
					if(nextHoles.get(i).getStore() != 1) {
						moves.add(i);
						//System.out.print(i + " ");
					}
				}
			}
		}
		//System.out.println();
		return moves;
	}
	
	public static Vector<Double> narrowMovesFutureMax(Vector<Integer> moves, GameState gameState, Vector<HoleClass> holes, int countdown, int currentDepth, int maxDepth, double pruningValue){
		Vector<Double> moveValues = new Vector<Double>();
		for(int i = 0; i < moves.size(); i++) {
			moveValues.add(-200.0);
		}
		
		//when this turns from false to true stop doing children nodes
		boolean pruning = false;
		
		//future pruning value starts at -200 in max
		double futurePruningValue = -200;
		
		for(int i = 0; i < moves.size(); i++) {
			if((countdown >= 2) && (pruning == false)) {
				System.out.println("Pruning Check! Max Current Depth: " + currentDepth + " Move Number: " + i);
				//System.out.println("0 moves: " + holes.get(0).getNumPieces());
				//Vector<HoleClass> nextHoles = holes;
				GameState gameStateAI = new GameState(gameState);
				
				Vector<HoleClass> nextHoles = new Vector<HoleClass>();
				for(int j = 0; j < holes.size(); j++) {
					HoleClass newHole = new HoleClass(holes.get(j));
					nextHoles.add(newHole);
				}
				
				int totalHolesInBoard = gameState.boardSize*2 + 2;
				double moveResult = fullTurnFutureMax(gameStateAI, moves.get(i), totalHolesInBoard, nextHoles, countdown, currentDepth, maxDepth, futurePruningValue);
				//System.out.println("Move Result: " + moveResult);
				System.out.println("Pruning Check! Max Move Result: " + moveResult + " Pruning Value: " + pruningValue);
				moveValues.set(i, moveResult);
				
				//update pruning
				if(moveResult > pruningValue) {
					//System.out.println("Pruning Check! Max pruned");
					pruning = true;
				}
				
				//update futurePruningValue
				if(moveResult > futurePruningValue) {
					futurePruningValue = moveResult;
				}
			}
		}
		
		return moveValues;
	}
	
	public static Vector<Double> narrowMovesFutureMin(Vector<Integer> moves, GameState gameState, Vector<HoleClass> holes, int countdown, int currentDepth, int maxDepth, double pruningValue){
		Vector<Double> moveValues = new Vector<Double>();
		for(int i = 0; i < moves.size(); i++) {
			moveValues.add(200.0);
		}
		
		//Once moveResult < pruningValue, this becomes true and we stop calculations below this node
		boolean pruning = false;
		
		//This tracks the pruning value for the next level down
		double futurePruningValue = 200;
		
		//finds values for future boards
		for(int i = 0; i < moves.size(); i++) {
			if((countdown >= 2) && (pruning == false)) {
				System.out.println("Pruning Check! Current Depth: " + currentDepth + " Move Number: " + i);
				//Vector<HoleClass> nextHoles = holes;
				GameState gameStateAI = new GameState(gameState);
				
				Vector<HoleClass> nextHoles = new Vector<HoleClass>();
				for(int j = 0; j < holes.size(); j++) {
					HoleClass newHole = new HoleClass(holes.get(j));
					nextHoles.add(newHole);
				}
				
				int totalHolesInBoard = gameState.boardSize*2 + 2;
				double moveResult = AI.fullTurnFutureMin(gameStateAI, moves.get(i), totalHolesInBoard, nextHoles, countdown, currentDepth, maxDepth, futurePruningValue);
				System.out.println("Pruning Check! Min Move Result: " + moveResult + " Pruning Value: " + pruningValue);
				moveValues.set(i, moveResult);
				
				if(moveResult < pruningValue) {
					System.out.println("Pruning Check! Min Hit");
					pruning = true;
				}
				
				//if moveResult < futurePruningValue, update futurePruningValue
				if(moveResult < futurePruningValue) {
					futurePruningValue = moveResult;
				}
			}
		}
		
		return moveValues;
	}
	
	public static int computerMoveFuture(Vector<Integer> moves, Vector<Double> values) {
		double maxValue = Collections.max(values);
		for(int i = 0; i < moves.size(); i++) {
			if(maxValue == values.get(i)) {
				return moves.get(i);
			}
		}
		return 0;
	}
	
	public static double fullTurnFutureMax(GameState gameState, int hole, int totalHolesInBoard, Vector<HoleClass> nextHoles, int countdown, int currentDepth, int maxDepth, double pruningValue){
		boolean result = movePiecesFuture(gameState, hole, nextHoles);
		if(result == true) {
			stealPiecesFuture(gameState, nextHoles);
			//This determines who will move next depending on where the last piece ends up
			endGameFuture(gameState, nextHoles);
			
			if(gameState.gameOver == 1) {
				if(gameState.piecesInPlayerOneStore > gameState.piecesInPlayerTwoStore) {
					if(gameState.currentMove == 1) {
						return 100.0;
					}
					else {
						return -100.0;
					}
				}
				else if(gameState.piecesInPlayerOneStore == gameState.piecesInPlayerTwoStore) {
					return 0;
				}
				else { //PTwo > POne
					if(gameState.currentMove == 1) {
						return -100.0;
					}
					else {
						return 100.0;
					}
				}
			}
			int currentMove = gameState.currentMove;
			nextMoveFuture(gameState, result);
			if(currentMove == gameState.currentMove) {
				Vector<Integer> moves = possibleMovesFuture(gameState, nextHoles);
				Vector<Double> moveQualities = narrowMovesFutureMax(moves, gameState, nextHoles, countdown, currentDepth, maxDepth, pruningValue);
				return Collections.max(moveQualities);
			}
			
			if(currentDepth < maxDepth) {
				Vector<Integer> moves = possibleMovesFuture(gameState, nextHoles);
				currentDepth++;
				Vector<Double> moveQualities = narrowMovesFutureMin(moves, gameState, nextHoles, countdown, currentDepth, maxDepth, pruningValue);
				/*System.out.print("Current Depth " + currentDepth + " ");
				for(int k = 0; k < moveQualities.size(); k++) {
					System.out.print(moveQualities.get(k) + " ");
				}
				System.out.println();*/
				return Collections.min(moveQualities);
			}
		}
		//System.out.println("Howdy 4");
		
		
		
		
		//Choice is where we set player two's choice for the pie rule

		//if it's the first move and if choice == 1, we run the pie rule function
		
		//Ignore Pie Rule for now in futrue prediction
		/*if((gameState.firstMove == 1) && (gameState.currentMove == 2))
		{
			gameState.firstMove = 0;
			pie_choice=true;
			pieFrameFuture(gameState, totalHolesInBoard, nextHoles);
		}*/
		double boardQuality;
		double p1Pieces = (double) gameState.piecesInPlayerOneStore;
		double p2Pieces = (double) gameState.piecesInPlayerTwoStore;
		
		int p1SidePiecesInt = 0;
		int p2SidePiecesInt = 0;
		for(int m = 0; m < gameState.boardSize; m++) {
			p1SidePiecesInt = p1SidePiecesInt + nextHoles.get(m).getNumPieces();
		}
		for(int m = gameState.boardSize + 1; m < gameState.boardSize*2 + 2; m++) {
			p2SidePiecesInt = p2SidePiecesInt + nextHoles.get(m).getNumPieces();
		}
		
		double p1SidePieces = (double) p1SidePiecesInt;
		double p2SidePieces = (double) p2SidePiecesInt;
		
		double storePieces = p1Pieces + p2Pieces;
		double sidePieces = p1SidePieces + p2SidePieces;
		
		double storePercentage = storePieces / (storePieces + sidePieces);
		double sidePercentage = sidePieces / (storePieces + sidePieces);
		
		storePercentage = storePercentage + sidePercentage/2;
		sidePercentage = sidePercentage/2;
		
		
		if(p1Pieces + p2Pieces == 0) {
			return .001;
		}
		else {
			if(gameState.currentMove == 2) {
				//System.out.println("Max Current move 1 p1Pieces " + p1Pieces + " p2Pieces " + p2Pieces);
				if(storePieces == 0) {
					boardQuality = p1SidePieces / (p1SidePieces + p2SidePieces);
				}
				else {
					boardQuality = (p1Pieces / (p1Pieces + p2Pieces))*storePercentage + (p1SidePieces / (p1SidePieces + p2SidePieces))*sidePercentage;
				}
			}
			else {
				//System.out.println("Max Current move 2 p1Pieces " + p1Pieces + " p2Pieces " + p2Pieces);
				if(storePieces == 0) {
					boardQuality = p2SidePieces / (p1SidePieces + p2SidePieces);
				}
				else {
					boardQuality = (p2Pieces / (p1Pieces + p2Pieces))*storePercentage + (p2SidePieces / (p1SidePieces + p2SidePieces))*sidePercentage;
				}
			}
			double preUtilityValue = boardQuality - .5;
			double utilityValue = preUtilityValue*200;
			if(utilityValue == 0) {
				utilityValue = .001; //we do this so that when the game ends in a tie it has a value of 0, but when it is currently tied it has a different value
			}
			else if(utilityValue == 100) {
				utilityValue = 99.99; //100 when game is over, 99.99 when still going
			}
			else if(utilityValue == -100) {
				utilityValue = -99.99; //-100 when game is over, -99.99 when still going
			}
			return utilityValue;
		}
		
	}
	
	public static double fullTurnFutureMin(GameState gameState, int hole, int totalHolesInBoard, Vector<HoleClass> nextHoles, int countdown, int currentDepth, int maxDepth, double pruningValue){
		boolean result = movePiecesFuture(gameState, hole, nextHoles);
		if(result == true) {
			
			stealPiecesFuture(gameState, nextHoles);
			//This determines who will move next depending on where the last piece ends up
			endGameFuture(gameState, nextHoles);
			
			if(gameState.gameOver == 1) {
				if(gameState.piecesInPlayerOneStore > gameState.piecesInPlayerTwoStore) {
					if(gameState.currentMove == 1) {
						return 100.0;
					}
					else {
						return -100.0;
					}
				}
				else if(gameState.piecesInPlayerOneStore == gameState.piecesInPlayerTwoStore) {
					return 0;
				}
				else { //PTwo > POne
					if(gameState.currentMove == 1) {
						return -100.0;
					}
					else {
						return 100.0;
					}
				}
			}
			int currentMove = gameState.currentMove;
			nextMoveFuture(gameState, result);
			if(currentMove == gameState.currentMove) {
				Vector<Integer> moves = possibleMovesFuture(gameState, nextHoles);
				Vector<Double> moveQualities = narrowMovesFutureMin(moves, gameState, nextHoles, countdown, currentDepth, maxDepth, pruningValue);
				return Collections.min(moveQualities);
			}
			
			if(currentDepth < maxDepth) {
				Vector<Integer> moves = possibleMovesFuture(gameState, nextHoles);
				currentDepth++;
				Vector<Double> moveQualities = narrowMovesFutureMax(moves, gameState, nextHoles, countdown, currentDepth, maxDepth, pruningValue);
				
				/*System.out.print("Current Depth " + currentDepth + " ");
				for(int k = 0; k < moveQualities.size(); k++) {
					System.out.print(moveQualities.get(k) + " ");
				}
				System.out.println();*/
				
				return Collections.max(moveQualities);
			}
		}
		
		
		
		
		//Choice is where we set player two's choice for the pie rule

		//if it's the first move and if choice == 1, we run the pie rule function
		
		//Ignore Pie Rule for now in futrue prediction
		/*if((gameState.firstMove == 1) && (gameState.currentMove == 2))
		{
			gameState.firstMove = 0;
			pie_choice=true;
			pieFrameFuture(gameState, totalHolesInBoard, nextHoles);
		}*/
		double boardQuality;
		double p1Pieces = (double) gameState.piecesInPlayerOneStore;
		double p2Pieces = (double) gameState.piecesInPlayerTwoStore;
		
		int p1SidePiecesInt = 0;
		int p2SidePiecesInt = 0;
		for(int m = 0; m < gameState.boardSize; m++) {
			p1SidePiecesInt = p1SidePiecesInt + nextHoles.get(m).getNumPieces();
		}
		for(int m = gameState.boardSize + 1; m < gameState.boardSize*2 + 2; m++) {
			p2SidePiecesInt = p2SidePiecesInt + nextHoles.get(m).getNumPieces();
		}
		
		double p1SidePieces = (double) p1SidePiecesInt;
		double p2SidePieces = (double) p2SidePiecesInt;
		
		double storePieces = p1Pieces + p2Pieces;
		double sidePieces = p1SidePieces + p2SidePieces;
		
		double storePercentage = storePieces / (storePieces + sidePieces);
		double sidePercentage = sidePieces / (storePieces + sidePieces);
		storePercentage = storePercentage + sidePercentage/2;
		sidePercentage = sidePercentage/2;
		
		
		if(p1Pieces + p2Pieces == 0) {
			return .001;
		}
		else {
			if(gameState.currentMove == 2) {
				//System.out.println("Max Current move 1 p1Pieces " + p1Pieces + " p2Pieces " + p2Pieces);
				if(storePieces == 0) {
					boardQuality = p1SidePieces / (p1SidePieces + p2SidePieces);
				}
				else {
					boardQuality = (p1Pieces / (p1Pieces + p2Pieces))*storePercentage + (p1SidePieces / (p1SidePieces + p2SidePieces))*sidePercentage;
				}
			}
			else {
				//System.out.println("Max Current move 2 p1Pieces " + p1Pieces + " p2Pieces " + p2Pieces);
				if(storePieces == 0) {
					boardQuality = p2SidePieces / (p1SidePieces + p2SidePieces);
				}
				else {
					boardQuality = (p2Pieces / (p1Pieces + p2Pieces))*storePercentage + (p2SidePieces / (p1SidePieces + p2SidePieces))*sidePercentage;
				}
			}
			double preUtilityValue = boardQuality - .5;
			double utilityValue = preUtilityValue*200;
			if(utilityValue == 0) {
				utilityValue = .001; //we do this so that when the game ends in a tie it has a value of 0, but when it is currently tied it has a different value
			}
			else if(utilityValue == 100) {
				utilityValue = 99.99; //100 when game is over, 99.99 when still going
			}
			else if(utilityValue == -100) {
				utilityValue = -99.99; //-100 when game is over, -99.99 when still going
			}
			return utilityValue;
		}
		
	}
	
	public static boolean movePiecesFuture (GameState gameState, int holeNumber, Vector<HoleClass> nextHoles){
		HoleClass selectedHole = new HoleClass();
		
		//grabs the hole selected by the user and assigns it to selectedHole
		if(holeNumber < nextHoles.size()){
			selectedHole = nextHoles.get(holeNumber);
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
			if(currentHole != nextHoles.size() - 1){
				currentHole++;
			}
			else{
				currentHole = 0;
			}
			
			
			int piecesInCurrentHole = nextHoles.get(currentHole).getNumPieces();
			
			//adds a piece to the next hole in the sequence as long as it is not the opponents store
			if((nextHoles.get(currentHole).getStore() != 1) || (nextHoles.get(currentHole).getSide() == gameState.getCurrentMove())){
				//add one piece to the current hole
				
				//If the last piece goes into an empty hole on the players side then they can steal the pieces from the opposite hole on the opponents side
				gameState.lastHole = currentHole;
				if((nextHoles.get(currentHole).getNumPieces() == 0) && (nextHoles.get(currentHole).getSide() == gameState.getCurrentMove()) && (nextHoles.get(currentHole).getStore() == 0)){
					if(currentHole < gameState.boardSize) {
						if(nextHoles.get((gameState.boardSize-currentHole) + gameState.boardSize).getNumPieces() == 0) {
							gameState.setStealPieces(0);
						}
						else {
							gameState.setStealPieces(1);
						}
					}
					else {
						if(nextHoles.get(gameState.boardSize-(currentHole-gameState.boardSize)).getNumPieces() == 0) {
							gameState.setStealPieces(0);
						}
						else {
							gameState.setStealPieces(1);
						}
					}
				}
				else{
					gameState.setStealPieces(0);
				}
				
				nextHoles.get(currentHole).setNumPieces(piecesInCurrentHole + 1);
				
				//if the last piece goes into the player's store they get to move again
				if(nextHoles.get(currentHole).getStore() == 1){
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
	
	static void endGameFuture(GameState gameState, Vector<HoleClass> nextHoles) {
		//check to see if one side is empty.
		int side1Peices = 0, side2Peices = 0;
		
		//iterate through side1, add up marbles excluding the store (mancala)
		for(int i=0; i<nextHoles.size()/2; i++) {
			if(nextHoles.get(i).getStore()!=1){
				side1Peices+=nextHoles.get(i).getNumPieces();
			}
		}
		//iterate through side2, add up marbles excluding the store (mancala)
		for(int i=nextHoles.size()/2; i<nextHoles.size(); i++) {
			if(nextHoles.get(i).getStore()!=1){
				side2Peices+=nextHoles.get(i).getNumPieces();
			}
		}
		
		//give store 1 player 2's pieces if player 1 has none left
		if(side1Peices==0) {
			for(int i = nextHoles.size()/2; i < nextHoles.size()-1; i++){
				nextHoles.get(i).setNumPieces(0);
			}
			int currentPeices = nextHoles.get(nextHoles.size()-1).getNumPieces();
			nextHoles.get(nextHoles.size()-1).setNumPieces(currentPeices+side2Peices);
			
			gameState.gameOver = 1;
		}
		//give store 2 player 1's pieces if player 2 has none left
		if(side2Peices==0) {
			for(int i = 0; i < nextHoles.size()/2 - 1; i++){
				nextHoles.get(i).setNumPieces(0);
			}
			int currentPeices = nextHoles.get((nextHoles.size()/2)-1).getNumPieces();
			nextHoles.get((nextHoles.size()/2)-1).setNumPieces(currentPeices+side1Peices);
			
			gameState.gameOver = 1;
		}
		gameState.setPiecesInPlayerOneStore(nextHoles.get(nextHoles.size()/2-1).getNumPieces());
		gameState.setPiecesInPlayerTwoStore(nextHoles.get(nextHoles.size()-1).getNumPieces());
	}
	
	public static void nextMoveFuture(GameState gameState, boolean result){
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
	
	public static void stealPiecesFuture(GameState gameState, Vector<HoleClass> nextHoles){
		if(gameState.getStealPieces() == 1){
			nextHoles.get(gameState.lastHole).setNumPieces(0);
			
			int oppositeHoleIndex = gameState.getBoardSize() * 2 - gameState.lastHole;
			
			int piecesInOpposite = nextHoles.get(oppositeHoleIndex).getNumPieces();
			nextHoles.get(oppositeHoleIndex).setNumPieces(0);
			
			//put pieces into the mover's store
			int storeIndex = 0;
			if(gameState.currentMove == 1){
				storeIndex = gameState.getBoardSize();
			}
			else{
				storeIndex = nextHoles.size() - 1;
			}
			int origStorePieces = nextHoles.get(storeIndex).getNumPieces();
			
			//the extra one at the end accounts for the piece that went from the players own hole to the store
			nextHoles.get(storeIndex).setNumPieces(origStorePieces + piecesInOpposite + 1);
		}
	}
	
	/*public static void pieRuleFuture(GameState gameState, Vector<HoleClass> nextHoles){
		
		gameState.setCurrentMove(1);
		for(int i = 0; i < gameState.boardSize + 1; i++){
			int piecesForPlayerTwo = nextHoles.get(i).getNumPieces();
			int piecesForPlayerOne = nextHoles.get(i+gameState.boardSize+1).getNumPieces();
			nextHoles.get(i).setNumPieces(piecesForPlayerOne);
			nextHoles.get(i+gameState.boardSize+1).setNumPieces(piecesForPlayerTwo);
		}
		Game.frame.repaint();
	}*/
	
	/*public static void pieFrameFuture(GameState gameState, int totalHolesInBoard, Vector<HoleClass> nextHoles)
	{
		JButton yes_pie = new JButton("Yes");
		JButton no_pie = new JButton("No");
		yes_pie.setBounds(125, 150, 100, 50);
		no_pie.setBounds(325,150,100,50);
		yes_pie.setFont(new Font("Serif", Font.BOLD, 20));
		no_pie.setFont(new Font("Serif", Font.BOLD, 20));
		JLabel pie_question = new JLabel("Would You Like To Implement The Pie Rule?");
		pie_question.setBounds(100, 50, 400, 50);
		pie_question.setFont(new Font("Serif", Font.BOLD, 20));
		JFrame pie_rule = new JFrame();
		pie_rule.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pie_rule.setBounds(200, 400, 600, 300);
		pie_rule.setTitle("Pie Rule");
		pie_rule.setLayout(null);
		pie_rule.add(yes_pie);
		pie_rule.add(no_pie);
		pie_rule.add(pie_question);
		pie_rule.setVisible(true);
		
		yes_pie.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
			
				pieRuleFuture(gameState, nextHoles);//implement pie rule
				pie_rule.dispose();
				pie_choice=false;
				System.out.println();
				System.out.print("Player 1 Score: ");
				System.out.print(gameState.getPiecesInPlayerOneStore());
				System.out.print(" Player 2 Score: ");
				System.out.print(gameState.getPiecesInPlayerTwoStore());
				System.out.println();
				
				for(int i = 0; i < totalHolesInBoard; i++){
					System.out.print(nextHoles.get(i).getNumPieces());
					System.out.print(" ");
				}
				System.out.println();
			}
		});
		
		no_pie.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				pie_rule.dispose();
				pie_choice=false;
			}
			
		});
	}*/
}
