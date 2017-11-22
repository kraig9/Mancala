package Mancala;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;



public class GameClient
{

    static BufferedReader in;
    static PrintWriter out;
    Socket socket;
    public static int player;
    public static Vector<Integer> rands;

    public GameClient() 
    {
    	player=0;
    	GuiApp.server=true;
    	rands= new Vector<Integer>(); 
       
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
       // String tokenize[] = welcomeMessage.split(" ");
        //player= tokenize[7];
        
        //wait for board info
        String line = in.readLine();
        log(line);
        String tokens[]=line.split(" ");
        String seeds = tokens [2];
        String holes = tokens [1];
               
        Integer temp2= Integer.parseInt(tokens[3])/1000;
        Start_Screen.seconds_in=temp2.toString();
        
        String random = tokens[5];
        String turn=tokens[4];
        if(turn.equals("F"))
        {
        	player = 1;
        }
        else if(turn.equals("S"))
        {
        	player = 2;
        }
        if(random.equals("R"))
        {
        	Board.randomSeed=1;
        }
        else
        {
        	Board.randomSeed=0;
        }
        
        for(int i=6; i<tokens.length;i++)
        {
        	Integer temp = Integer.valueOf(tokens[i]); 
        	rands.add(temp);
        }
        Start_Screen.seed_selected = seeds;
        Start_Screen.holes_selected = holes;
        out.println("READY");
        out.flush();
        
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
        try
        {
        	client.connectToServer();
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        }
        Game.Client_Main();
    }
}