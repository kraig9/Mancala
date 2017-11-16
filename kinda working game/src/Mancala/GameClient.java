package Mancala;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;



public class GameClient
{

    public static BufferedReader in;
    public static PrintWriter out;
    Socket socket;
    public static String player;

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
        String welcomeMessage = in.readLine();
        log(welcomeMessage);
        String tokenz[] = welcomeMessage.split(" ");
        player = tokenz[1];
        
        //wait for board info 
        String line = in.readLine();
        log(line);
        String tokens[] = line.split(" ");
        String seeds = tokens[1];
        String holes = tokens[2];
        Start_Screen.seed_selected = seeds;
        Start_Screen.holes_selected = holes;
        out.println("READY");
        out.flush();
    }
    
    private void log(String message) 
    {
        System.out.println("[SERVER] " + message);
    }

    /**
     * Runs the client application.
     */
    public static void gameClient(String[] args) 
    {
        GameClient client = new GameClient();
        try {
			client.connectToServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
        Game.Client_Main();
    }
}