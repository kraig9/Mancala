package Mancala;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

//the start screen, used in GuiApp
public class Start_Screen extends JPanel
{
	static JButton start_btn;
	static JButton Instruction_btn;
	static JButton Client_btn;
	static JButton Server_btn;
	JLabel Welcome;
	JLabel Creator;
	JLabel User;
	JLabel Random_option;
	static JComboBox<String> opponent_menu;
	String[] Opponent;
	static String opponent_selected;
	
	static JComboBox<String> player_menu;
	String[] Player;
	static String player_selected;
	
	static JComboBox<String> seed_menu;
	String[] Seeds;
	static String seed_selected;
	
	static JComboBox<String> holes_menu;
	String[] Number_Holes;
	static String holes_selected;
	
	JRadioButton Random_yes;
	JRadioButton Random_no;
	ButtonGroup Random_seeds;
	
	static JTextField time_input;
	JLabel time_in;
	static String seconds_in;
	/*JMenuBar menu;
	JMenuItem User2;
	JMenuItem Computer;*/
	/**
	 * Create the panel.
	 */
	public Start_Screen() 
	{
		start_btn=new JButton();
		start_btn.setBackground(Color.GRAY);
		start_btn.setText("Start");
		start_btn.setToolTipText("Click to Start Mankala!");
		start_btn.setBounds(700,400, 150, 50);
		start_btn.setFont(new Font("Serif", Font.BOLD, 20));
		
		Instruction_btn=new JButton();
		Instruction_btn.setBackground(Color.GRAY);
		Instruction_btn.setText("Instructions");
		Instruction_btn.setToolTipText("Click to Read Instructions!");
		Instruction_btn.setBounds(900,400, 150, 50);
		Instruction_btn.setFont(new Font("Serif", Font.BOLD, 20));
		
		Client_btn=new JButton();
		Client_btn.setBackground(Color.GRAY);
		Client_btn.setText("Connect to Server");
		Client_btn.setBounds(400,730, 200, 50);
		Client_btn.setFont(new Font("Serif", Font.BOLD, 20));
		
		Server_btn=new JButton();
		Server_btn.setBackground(Color.GRAY);
		Server_btn.setText("Host Server");
		Server_btn.setBounds(400,630, 200, 50);
		Server_btn.setFont(new Font("Serif", Font.BOLD, 20));
		
		Welcome = new JLabel();
		Welcome.setText("Play Mancala");
		Welcome.setBounds(600,100,600,450);
		Welcome.setFont(new Font("Serif", Font.BOLD, 100));
		//Welcome.setForeground(fg);
		
		Creator = new JLabel();
		Creator.setText("<html>Created By:<br>Haaris Padela<br>Andrew Grattafiori<br>Kraig Orcutt</html>");
		Creator.setBounds(825,100,600,800);
		
		Player= new String[] {"Choose a Player","Player 1","Computer"};
		player_menu= new JComboBox<>(Player);
		player_menu.setBounds(400, 500, 400,70);
		player_menu.setFont(new Font("Serif", Font.BOLD, 40));
		
		Opponent= new String[] {"Choose an Opponent","Player 2","Computer"};
		opponent_menu= new JComboBox<>(Opponent);
		opponent_menu.setBounds(950, 500, 400, 70);
		opponent_menu.setFont(new Font("Serif", Font.BOLD, 40));
		
		Seeds = new String[] {"Choose Number Of Seeds","1","2","3","4","5","6","7","8","9","10"};
		seed_menu=new JComboBox<>(Seeds);
		seed_menu.setBounds(650, 620, 475, 70);
		seed_menu.setFont(new Font("Serif", Font.BOLD, 40));
		//seed_menu.setSelectedIndex(3);
		//System.out.println("You selected : " + seed_selected);
		
		Number_Holes = new String[] {"Choose Number Of Holes","4","5","6","7","8","9"};
		holes_menu=new JComboBox<>(Number_Holes);
		holes_menu.setBounds(650, 720, 475, 70);
		holes_menu.setFont(new Font("Serif", Font.BOLD, 40));
		
		Random_option = new JLabel("Randomly Distributed Seeds:");
		Random_option.setBounds(1150,600,300,50);
		Random_option.setFont(new Font("Serif", Font.BOLD, 20));
		
		Random_yes = new JRadioButton("Yes");
		Random_yes.setBounds(1150, 650, 60, 60);
		Random_yes.setFont(new Font("Serif", Font.BOLD, 20));

		
		Random_no = new JRadioButton("No");
		Random_no.setSelected(true);
		Random_no.setBounds(1150, 700, 50, 50);
		Random_no.setFont(new Font("Serif", Font.BOLD, 20));;
		
		//ButtonGroup handles making sure only one is selected
		Random_seeds = new ButtonGroup();
		Random_seeds.add(Random_yes);
		Random_seeds.add(Random_no);
		
		time_input = new JTextField("0");
		time_input.setFont(new Font("Serif", Font.BOLD, 20));
		time_input.setBounds(850, 800, 200, 50);
		time_in = new JLabel("Enter Time Limit (sec)");
		time_in.setBounds(650,800,200,50);
		time_in.setFont(new Font("Serif", Font.BOLD, 20));
		
		

		//This action listener checks to see the seed distribution should be random or not
		ActionListener RandomActionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		        AbstractButton aButton = (AbstractButton) actionEvent.getSource();
		        if(aButton.getText()=="Yes")
		        {
		        	Board.randomSeed=1;
		        }
		        else if(aButton.getText()=="No")
		        {
		        	Board.randomSeed=0;
		        }
		        
		      }
		    };

		    Random_yes.addActionListener(RandomActionListener);
		    Random_no.addActionListener(RandomActionListener);

		/*menu = new JMenuBar();
		menu.setBounds(950, 500, 250, 150);
		//menu.set
		User2=new JMenuItem("Player 2");
		User2.setToolTipText("Play against a friend!");
		User2.setFont(new Font("Serif", Font.BOLD, 50));
		Computer=new JMenuItem("Computer");
		Computer.setToolTipText("Play against the Computer!");
		Computer.setFont(new Font("Serif", Font.BOLD, 50));
		menu.add(User2);
		menu.add(Computer);
		menu.s
		menu.setToolTipText("Choose Opponent!");
		menu.setLayout(new BoxLayout(menu,BoxLayout.PAGE_AXIS));
		*/
		add(time_in);
		add(time_input);
		add(start_btn);
		add(Instruction_btn);
		add(Client_btn);
		add(Server_btn);
		add(Creator);
		add(Welcome);
		add(player_menu);
		add(opponent_menu);
		add(seed_menu);
		add(holes_menu);
		add(Random_no);
		add(Random_yes);
		add(Random_option);
		setLayout(null);
		
		
	}

}
