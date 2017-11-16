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
	static int clientNumber = 1; //in order to count how many clients connected
	static Socket p1,p2 = null;
	public static void createServer() throws Exception
	{
		
	    int port= Integer.parseInt(GuiApp.serverPort);
		System.out.println("Server Running On Port " + port + ".");
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
       createServer();

    }


    private static class ServerThread extends Thread 
    {
    	private int clientNumber;

    	public ServerThread(Socket socket, int clientNumber) 
    	{
    		if(clientNumber==1) p1=socket;
    		else p2=socket;
    		this.clientNumber = clientNumber;
    	}


    	public void run() 
    	{
    		try 
    		{
    			Socket socket = clientNumber == 1 ? p1 : p2;
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Send a welcome message to the client.
                out.println("WELCOME "+clientNumber);
                
                //wait until both players are connected
                while(p2 == null) {
                	sleep(100);
                }
                
                String info = "INFO "+ Start_Screen.seed_selected+" "+Start_Screen.holes_selected;
                out.println(info);
                while (true)
                {
                    String input = in.readLine();
                    System.out.println((clientNumber == 1 ? "[PLAYER 1] " : "[PLAYER 2] ")+ input);
                    if (input == null) 
                    {
                        break;
                    }
                    
                    String tokenzz[] = input.split("\\&");
                    if(tokenzz[0].equals("MOVE")) {
                    	out.println("OK");
                    	Socket other = clientNumber == 1 ? p2 : p1;
                    	PrintWriter otherOut = new PrintWriter(other.getOutputStream(), true);
                    	otherOut.println(input);
                    }
                }
                
            } 
            catch (IOException e) 
            {
                log("Error handling client# " + clientNumber + ": " + e);
            } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        }

        
        private void log(String message) 
        {
            System.out.println(message);
        }
    }
}