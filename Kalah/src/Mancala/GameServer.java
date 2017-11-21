package Mancala;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class GameServer 
{
	static boolean players=false;
	static ArrayList<ServerThread> servers = new ArrayList<ServerThread>();
	public void createServer() throws Exception
	{
		
	    int port= Integer.parseInt(GuiApp.serverPort);
		System.out.println("Server Running On Port " + port + ".");
        int clientNumber = 1; //in order to count how many clients connected
        ServerSocket listener = new ServerSocket(port); //create server on port 
        try 
        {
        	if(clientNumber == 3)
            {
        		System.out.println("This Server Already Has 2 Players");
            }
            while (clientNumber < 3 ) //infinite loop in order to listen for multiple clients numbered clients
            {
                ServerThread thread =new ServerThread(listener.accept(), clientNumber++);
                if(clientNumber==3)
             	{
             		players =true;
             	}
                servers.add(thread);
                thread.start();
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
        private Socket socket=null;
        private int clientNumber;
        PrintWriter out=null ;
        BufferedReader in=null;
        private JFrame Waiting ;

        public ServerThread(Socket socket, int clientNumber) 
        {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber);
        }

        
        public void run() 
        {
        	try 
            {
        		in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

               //wait till both players connected
                while(players!=true)
                {
                	sleep(100);
                }
                //Send a welcome message to the client.
                out.println("WELCOME");
                INFO();
                
                while (true)
                {
                	String input = in.readLine();
                	log((clientNumber == 1 ? "[PLAYER 1] " : "[PLAYER 2] ") + input);
                	String token[]= input.split(" ");
                	if(!token[0].equals("READY"))
                	{
	                	servers.get(0).out.println("OK");
	                	if(clientNumber == 1)
	                	{
	                		for(int i =0 ; i<token.length ;i++)
	                		{
	                			servers.get(1).out.println(token[0]);
	                		}
	                		
	                	}
	                	else
	                	{
	                		for(int i =0 ; i<token.length ;i++)
	                		{
	                			servers.get(0).out.println(token[0]);
	                		}
	                		
	                	}
                	}
                	
                }  
            } 
            catch (IOException e) 
            {
                log("Error handling client# " + clientNumber + ": " + e);
            } 
            catch (InterruptedException e) 
            {
				e.printStackTrace();
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
        public void INFO()
        {
            int randomSeed=Board.randomSeed;
            int num_seeds = Integer.parseInt(Start_Screen.seed_selected);
        	int num_holes = Integer.parseInt(Start_Screen.holes_selected);
        	//Vector<HoleClass> holes_vector=Board.addHolesToBoard(num_holes, num_seeds, randomSeed);
        	String random_seeds = null;
            for(int i =0; i<num_holes;i++)
    		{
            //	random_seeds=holes_vector.get(i).getNumPieces() + " " + random_seeds;
    		}
           
            String turn = clientNumber == 1 ? "F " : "S ";
            String random = Board.randomSeed == 1 ? "R " : "S ";
            String info = "INFO " + num_holes + " " + num_seeds + " " + "30000 " + turn + random + random_seeds;
            out.println(info);
            out.flush();
        }
        public void Waiting() 
        {
        	
			JLabel out_text = new JLabel("dsf");
			out_text.setBounds(100, 50, 400, 50);
			out_text.setFont(new Font("Serif", Font.BOLD, 20));
			Waiting = new JFrame();
			Waiting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Waiting.setBounds(200, 400, 600, 300);
			Waiting.setTitle("Waiting");
			Waiting.setLayout(null);
			Waiting.add(out_text);
			Waiting.setVisible(true);

    	}
        private void log(String message) 
        {
            System.out.println(message);
        }
    }
}