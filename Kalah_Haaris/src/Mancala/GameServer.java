package Mancala;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class GameServer 
{
	public void createServer() throws Exception
	{
		
	    int port= Integer.parseInt(GuiApp.serverPort);
		System.out.println("Server Running On Port " + port + ".");
        int clientNumber = 1; //in order to count how many clients connected
        ServerSocket listener = new ServerSocket(port); //create server on port 5743
        try 
        {
        	if(clientNumber == 3)
            {
        		System.out.println("This Server Already Has 2 Players");
            }
            while (clientNumber < 3 ) //infinite loop in order to listen for multiple clients numbered clients
            {
                new ServerThread(listener.accept(), clientNumber++).start();
            }
        } 
        finally //always after try exits
        {
            listener.close();
        }
	}
	
    public static void main(String[] args) throws Exception 
    {
    	GameServer server = new GameServer();
        server.createServer();
        
    }

 
    private static class ServerThread extends Thread 
    {
        private Socket socket;
        private int clientNumber;

        public ServerThread(Socket socket, int clientNumber) 
        {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

        
        public void run() 
        {
            try 
            {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Send a welcome message to the client.
                out.println("Welcome to Mancala You Are Player Number " + clientNumber + " .");
                while (true)
                {
                    String input = in.readLine();
                    if (input == null) 
                    {
                        break;
                    }
                }
                
            } 
            catch (IOException e) 
            {
                log("Error handling client# " + clientNumber + ": " + e);
            } 
            finally 
            {
                try
                {
                    socket.close();
                }
                catch (IOException e)
                {
                    log("Couldn't close a socket");
                }
                log("Connection with client# " + clientNumber + " closed");
            }
        }

        
        private void log(String message) 
        {
            System.out.println(message);
        }
    }
}