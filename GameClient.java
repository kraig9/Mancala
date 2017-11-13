package Mancala;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GameClient{
	//static Board clientBoard = new Board();
	Game clientGame = new Game();
	public static Socket socket;
	static BufferedReader in;
	static PrintWriter out;
	static BufferedReader stdIN;
	
	public static void main(String[] args){
		Game.startGame(null);
		connectToServer();
	}
	
	public static void connectToServer(){
		//get address to server
		/*String prompt = "Enter the ip Address of the Server: ";
		System.out.println(prompt);
		Scanner input = new Scanner(System.in);
		String serverAddress = input.next();*/
		String serverAddress = "127.0.0.7";
		
		//make the connection to the server
		try{
			socket = new Socket(serverAddress, 9000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			stdIN = new BufferedReader(new InputStreamReader(System.in));
			String fromServer;
			String fromUser;
			
			while((fromServer = in.readLine()) != null){
				System.out.println("Server: " + fromServer);
				if(fromServer.equals("Bye.")) break;
				
				fromUser = stdIN.readLine();
				if(fromUser != null){
					System.out.println("Client: " + fromUser);
					fromUser = clientProtocol(fromServer);
					out.println(fromUser);
				}
			}
		}
		catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverAddress);
            System.exit(1);
        } 
		catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverAddress);
            System.exit(1);
        }
	}
	public static String clientProtocol(String serverResponse){
		String tokens[] = serverResponse.split(" ");
		if(tokens[0]=="WELCOME"){
			//send back INFO
			//number of holes total including stores
			Board b = (Board)Game.frame.contentPane;
			String boardSize = Integer.toString(Board.holes.size());
			
			//seeds in those holes
			String seeds = null;
			for(int i = 0; i < Board.holes.size(); i++) {
				seeds = seeds + Integer.toString( Board.holes.get(i).numPieces) + " ";
			}
			
			//time to move. this is 30 for now but will be variable later.
			String timeToMove = "30";
			
			//whether client will go first or second. will implement later.
			String firstOrSecond = null;
			
			//concatenate and send to server!
			String info = "INFO" + boardSize + seeds + timeToMove + firstOrSecond;
		}
		else if(tokens[0]=="INFO") {
			//this is from another client. set up game accordingly
			//number of holes server says to use
			//Board.num_holes = (Integer.parseInt(tokens[1])/2)-2;
			
			//number of pieces in each hole
			
			for(int i = 0; i < Board.holes.size(); i++) {
				Board.holes.get(i).numPieces = Integer.parseInt(tokens[i+2]);
			}
			
			//time to move
			//don't do anything for now.
			
			//whether the client will go first or second. implement later.
			
			//after we use the info to build the board, we should be ready.
			return "READY";
		}
		else if(tokens[0]=="READY"){
			//if prompted for ready, return ready
			return "READY";
		}
		else if(tokens[0]=="OK"){
			//make a move
			//this will be handled in the *on click* event handler in Board 
			//(will end up being handled inside fullturn)
			
		}
		else if(tokens[0]=="P"){
			//do pie move
			Board.pie_choice=true;
		}
		else if(tokens[0]=="WINNER"){
			//end the game and say you win
		}
		else if(tokens[0]=="LOSER"){
			//end the game and say you lose
		}
		else if(tokens[0]=="TIE"){
			//end the game and say you tie
		}
		else if(tokens[0]=="ILLEGAL"){
			//tell you move is illegal, you lose, and end game
		}
		return "BYE";
	}
}