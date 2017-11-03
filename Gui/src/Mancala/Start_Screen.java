package Mancala;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Start_Screen extends JPanel
{
	static JButton start_btn;
	JLabel Welcome;
	JLabel Creator;
	JLabel User;
	String[] Opponent;
	JComboBox<String> menu;
	String[] Player;
	JComboBox<String> player_menu;
	String[] Seeds;
	JComboBox<String> seed_menu;
	String[] Number_Holes;
	JComboBox<String> holes_menu;
	//String seed_selected;
	
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
		start_btn.setBounds(800,400, 150, 50);
		start_btn.setFont(new Font("Serif", Font.BOLD, 20));
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
		menu= new JComboBox<>(Opponent);
		menu.setBounds(950, 500, 400, 70);
		menu.setFont(new Font("Serif", Font.BOLD, 40));
		Seeds = new String[] {"Choose Number Of Seeds","1","2","3","4","5","6","7","8","9","10"};
		seed_menu=new JComboBox<>(Seeds);
		seed_menu.setBounds(650, 620, 475, 70);
		seed_menu.setFont(new Font("Serif", Font.BOLD, 40));
		Number_Holes = new String[] {"Choose Number Of Holes","4","5","6","7","8","9"};
		holes_menu=new JComboBox<>(Number_Holes);
		holes_menu.setBounds(650, 720, 475, 70);
		holes_menu.setFont(new Font("Serif", Font.BOLD, 40));
		
		
		
		//seed_menu.setSelectedIndex(3);
		//seed_selected = (String) seed_menu.getSelectedItem();
		//System.out.println("You selected : " + seed_selected);

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
		add(start_btn);
		add(Creator);
		add(Welcome);
		add(player_menu);
		add(menu);
		add(seed_menu);
		add(holes_menu);
		setLayout(null);
		
		
	}

}
