import java.io.*;
import java.net.*;
import java.net.Socket;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Queue;

public class GameServer{	
	Queue<String> p1q=new PriorityQueue<String>();
	Queue<String> p2q=new PriorityQueue<String>();
	public static void main(String[] args) {
		System.out.println("GameServer is running. Welcome to the server!");
		int portNum = 9000;
		boolean listening = true;
		try(
			//Don't use ports below 1024!
			ServerSocket listener = new ServerSocket(portNum);
		){
			while(listening){
				new GameServerThread(listener.accept()).start();
			}
		}
		catch(IOException e){
			System.out.println("Couldn't listen on port chosen.");
		}
	}
}
class GameProtocol{
	
	
		GameProtocol(){
			
		}
		String processInput(String input){
			//WELCOME
			
			//READY
			
			//OK
			
			//ILLEGAL
			
			//TIME
			
			//LOSER
			
			//WINNER
			
			//TIE
			return "Process Input in GameProtocol returned";
		}

	}
class GameServerThread extends Thread {
	private Socket socket = null;
	
	public GameServerThread(Socket socket){
		super("GameServerThread");
		this.socket = socket;
	}
	
	public void run(){
		try{
			System.out.println("recieved new connection!");
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
			BufferedReader in = new BufferedReader(
			new InputStreamReader(socket.getInputStream()));
			String inputLine, outputLine;
			GameProtocol gp = new GameProtocol();
			outputLine = gp.processInput(null);
			out.println(outputLine);
			
			while((inputLine=in.readLine())!=null){
				outputLine = gp.processInput(inputLine);
				out.println(outputLine);
				if(outputLine.equals("Bye"))
					break;
			}
			socket.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
class OutputThread extends Thread {
	public void run() {
		//try {
		while(true) {
			//poll from players queues and output
		}
	}
		/* catch(IOException e){
			e.printStackTrace();
		} */
}
class InputThread extends Thread {
	public void run(){
		while(true) {
			//get message from players
			
			//process
			
			//put result into player's respective queues
		}
	}
}