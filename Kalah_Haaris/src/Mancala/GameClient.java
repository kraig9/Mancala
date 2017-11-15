package Mancala;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;



public class GameClient
{

    private BufferedReader in;
    private PrintWriter out;
    Socket socket;

    public GameClient() 
    {
    	//action
       
    }

    public void connectToServer() throws IOException 
    {
    	String serverPort;
    	String serverAddress;
    	
        // Get the server address from a dialog box.
    	if(GuiApp.serverPort==null)
    	{
	        serverAddress = JOptionPane.showInputDialog(
	            Game.frame,
	            "Enter IP Address of the Server:",
	            "Server IP Address",
	            JOptionPane.QUESTION_MESSAGE);
	
	        // Get the server port from a dialog box.
	        serverPort = JOptionPane.showInputDialog(
	            Game.frame,
	            "Enter Port Number of the Server:",
	            "Server Port Number",
	            JOptionPane.QUESTION_MESSAGE);
    	}
    	else
    	{
    		serverPort=GuiApp.serverPort;
    		serverAddress="localhost";
    	}
        int port= Integer.parseInt(serverPort);
        // Make connection and initialize streams
        socket = new Socket(serverAddress, port);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        //welcome message from the server
        log(in.readLine());
    }
    
    private void log(String message) 
    {
        System.out.println(message);
    }

    /**
     * Runs the client application.
     */
    public static void main(String[] args) throws Exception 
    {
        GameClient client = new GameClient();
        client.connectToServer();
        Game.Client_Main();
    }
}