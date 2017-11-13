package Mancala;

import java.io.*;
import java.net.*;
import java.net.Socket;

public class GameServer{	
 	
	public static void main(String[] args) {
		System.out.println("GameServer is running. Welcome to the server!");
		int portNum = 9000;
		boolean listening = true;
		try(
			//Don't use ports below 1024!
			ServerSocket listener = new ServerSocket(portNum);
		){
			int pid = 1;
			while(listening){
				new GameServerThread(listener.accept(), pid).start();
			}
		}
		catch(IOException e){
			System.out.println("Couldn't listen on port chosen.");
		}
	}
}
	class ServerProtocol{
			
		String serverProtocol(String clientMessage){
			if(clientMessage==null) return "WELCOME";
			String tokens[] = clientMessage.split(" ");
			//INFO
			if(tokens[0]=="INFO"){
				//send info to other client
				return "READY";
			}
			//READY
			else if(tokens[0]=="READY"){
				return "OK";
			}
			//OK
			else if(tokens[0]=="OK"){
				return "OK";
			}
			//ILLEGAL
			else if(tokens[0]=="ILLEGAL"){
				//terminate game and display loser and winner message
			}
			//TIME
			
			//LOSER
			
			//WINNER
			
			//TIE
			
			//WELCOME
			return "";
		}

	}
class GameServerThread extends Thread {
	private Socket socket = null;
	int pid;
	public GameServerThread(Socket socket, int pid){
		super("GameServerThread");
		this.socket = socket;
		this.pid=pid;
	}
	
	public void run(){
		try{
			System.out.println("recieved new connection!");
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
			BufferedReader in = new BufferedReader(
			new InputStreamReader(socket.getInputStream()));
			String inputLine, outputLine;
			ServerProtocol sp = new ServerProtocol();
			outputLine = sp.serverProtocol(null);
			out.println(outputLine);
			
			while((inputLine=in.readLine())!=null){
				outputLine = sp.serverProtocol(inputLine);
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