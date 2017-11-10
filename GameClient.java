import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;
import java.util.Scanner;

public class GameClient{
	public static void main(String[] args){
		connectToServer();
	}
	public static void connectToServer(){
		//get address to server
		String prompt = "Enter the ip Address of the Server: ";
		System.out.println(prompt);
		Scanner input = new Scanner(System.in);
		String serverAddress = input.next();
		
		//make the connection to the server
		try(
		Socket socket = new Socket(serverAddress, 9000);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		){
			BufferedReader stdIN = new BufferedReader(new InputStreamReader(System.in));
			String fromServer;
			String fromUser;
			
			while((fromServer = in.readLine()) != null){
				System.out.println("Server: " + fromServer);
				if(fromServer.equals("Bye.")) break;
				
				fromUser = stdIN.readLine();
				if(fromUser != null){
					System.out.println("Client: " + fromUser);
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
}