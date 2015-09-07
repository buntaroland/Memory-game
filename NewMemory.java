
/*
  NewMemory.java
  this is the main class
  here's the Frame, and the Actionlistener
  I made here a difficulty selection option, but not implemented fully
 */
package memorygame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class NewMemory extends JFrame implements ActionListener
{
	private int buttonsVisible, moves;
	// Difficulty menu variable
	private static int difficulty;
	// Game numberbuttons
	private JButton[] btnNumbers;
	// Game menubuttons
	private JButton btnRestart = new JButton("Újrajátszás");
	private JButton btnQuit = new JButton("Kilépés");
	private JButton btnHighScores = new JButton("Rekordok");
	// Difficulty menubuttons
	private JButton btnEasy = new JButton("Könnyû (16)");
	private JButton btnMedium = new JButton("Közepes (20)");
	private JButton btnHard = new JButton("Nehéz (24)");
	// Game label
	private JLabel lblGameName = new JLabel("Memória játék");
	private String strGameName = "Memória játék";
	// Game panels
	private JPanel pnlNumbers = new JPanel();
	private JPanel pnlMenu = new JPanel( new GridLayout(0, 4, 0, 4) );
	// Difficulty menu panel
	private JPanel pnlDiffMenu = new JPanel ( new BorderLayout() );
	// Games numberbutton font
	private Font fntNumbers = new Font("Arial Bold", Font.PLAIN, 36);
	
	private JLabel lblPlayerName = new JLabel("Játékos neve:");
	
	// Game texfield for player name
	private JTextField txtPlayerName = new JTextField(10);
	private String strPlayerName;
	
	private String strHighScores;
	
	public HighScores hs = new HighScores();
	public ArrayListText alt = new ArrayListText();
	
	// Constructor for NewMemory class
	public NewMemory(int choice)
	{
		super("Memória játék"); // New JFrame (from Superclass)

		btnNumbers = new JButton[choice]; // 2 x 8 buttons for the MemoryGame

		 // generates the random numbers for the board -> ArrayList
		alt.setArrayListText(btnNumbers);
		
		/* serialize the ArrayListText gameList so we can get it back
		whenever we want from a file(serialization.txt) */
		alt.serialize();
		writeOutGameList(alt.deserialize());
		
		// read high scores from a file and put it into the strHighScores
		hs.readFromFile();
		strHighScores = hs.getRecords();
		
		buttonsVisible = 0;
		moves = 0;
		
		setup(500, 300); // method that sets up the frame and draws the panel
		addButtons();// a method that draws the buttons
		setVisible(true); // makes the frame visible
	}
	
	// makes the Layout, sets values, adds panels to menu
	private void setup(int width, int height)
	{
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		try { setDefaultLookAndFeelDecorated(true); } catch (Exception e) { System.out.print(e); }
		
		pnlNumbers.setLayout(new GridLayout(4, 4, 3, 3));
		pnlNumbers.setBackground(Color.BLACK);
		pnlNumbers.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); 
		
		add(pnlNumbers);
		add(pnlMenu, BorderLayout.SOUTH);
		
		// Game Name, after starting the game it will show the moves
		lblGameName.setHorizontalAlignment(SwingConstants.CENTER);
		pnlMenu.add(lblGameName);
		
		// high scores
		btnHighScores.addActionListener(this);
		pnlMenu.add(btnHighScores);
		
		// restart game whenever we want
		btnRestart.addActionListener(this);
		pnlMenu.add(btnRestart);
		
		// quit the game
		btnQuit.addActionListener(this);
		pnlMenu.add(btnQuit);
		
		// non editable label ("Játékos:")
		lblPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
		pnlMenu.add(lblPlayerName);
		
		// editable label, type here the name
		txtPlayerName.addActionListener(this);
		pnlMenu.add(txtPlayerName);
	}
	
	//add and set buttons
	private void addButtons()
	{
		for (int i = 0; i < btnNumbers.length; i++)
		{
			btnNumbers[i] = new JButton();
			btnNumbers[i].setBackground(Color.WHITE);
			btnNumbers[i].setFont(fntNumbers);
			btnNumbers[i].setFocusable(false);
			btnNumbers[i].setEnabled(true);
			btnNumbers[i].setText("");
			pnlNumbers.add(btnNumbers[i]);
			btnNumbers[i].addActionListener(this);
		}
	}
	// writes out gameList in easily readable form (4x4 matrix)
	private void writeOutGameList(ArrayList<Integer> gameList){
		int newLine = 0;
        for (int a = 0; a < gameList.size(); a++) {
            newLine++;
            System.out.print(" " + gameList.get(a));
            if (newLine == 4) {
                System.out.println();
                newLine = 0;
            }
        }
        System.out.print("\n");
	}
	
	// if game is over = all buttons are disabled
	private boolean isGameOver()
	{
		for (int i = 0; i < btnNumbers.length; i++)
		{
			if (btnNumbers[i].isEnabled() == true) return false;
		}
		
		return true;
	}
	
	
	// resets btnNumbers, generates new numbers
	private void resetGame()
	{
		for (int i = 0; i < btnNumbers.length; i++)
		{
			btnNumbers[i].setText("");
			btnNumbers[i].setBackground(Color.WHITE);
			btnNumbers[i].setEnabled(true);
		}
		
		// generate new numbers for the new game
		alt.setArrayListText(btnNumbers);
		
		// serialize it and writes out to the console
		alt.serialize();
		writeOutGameList(alt.deserialize());
		
		strHighScores = hs.getRecords();
		
		lblGameName.setText(strGameName);
		moves = 0;
		buttonsVisible = 0;
	}
	
	// ActionListener method
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource() == btnQuit) {System.exit(0);}
		else if (e.getSource() == btnRestart) {resetGame();}
		else if (e.getSource() == txtPlayerName) {strPlayerName = txtPlayerName.getText();}
		else if (e.getSource() == btnHighScores) {//System.out.print(strHighScores);
												  JOptionPane.showMessageDialog(
														  null,
														  strHighScores,// text
														  "Rekordok",// title
														  JOptionPane.INFORMATION_MESSAGE); //type
											   	 }
		else if (!isGameOver()) // if isGameOver() method returns false do THIS
		{
			if (buttonsVisible == 2)
			{
				for (int i = 0; i < btnNumbers.length; i++)
				{
					if (btnNumbers[i].isEnabled() == true) {btnNumbers[i].setText("");}
				} // end for (i < btnNumbers.length)
				buttonsVisible = 0;
			} // end if (buttonsVisible == 2)
			
			for (int i = 0; i < btnNumbers.length; i++)
			{
				if (e.getSource() == btnNumbers[i])
				{
					btnNumbers[i].setText("" + alt.getGameList().get(i));
					moves++;
					buttonsVisible++;
				} // end if (e.getSource() == btnNumbers[i])
				
				for (int j = 0; j < btnNumbers.length; j++)
				{
					if (e.getSource() == btnNumbers[i] && 
							btnNumbers[i].getText().equals( btnNumbers[j].getText()) && i != j)
					{
						btnNumbers[i].setEnabled(false);
						btnNumbers[j].setEnabled(false);
						
						btnNumbers[i].setBackground(Color.BLACK);
						btnNumbers[j].setBackground(Color.BLACK);
						
					} // end if () 2x setEnabled(false)
				} // end for (j < btnNumbers.length)
			} // end for (i < btnNumbers.length)
			lblGameName.setText("Lépések: " + (moves/2));
		} // end if ( !gameIsOver() )
		
		if (isGameOver()) 
		{   
			//check if new score can be on the high scores
			hs.checkNewRecord(strPlayerName, moves);
			
			
			//game is over -> play again or exit
			String[] choice = {"Igen", "Nem"};

			int answer = JOptionPane.showOptionDialog(
					this, //Component parentComponent
					"Szeretnél újat játszani?", //Object message
					"Válassz egy opciót", //String title
					JOptionPane.YES_NO_OPTION, //int optionType
					JOptionPane.QUESTION_MESSAGE,//int messageType
					null, //Icon icon
					choice, //Object[] options
					"Igen"); //Object initialValue
			if (answer == 0) {resetGame();}
			else {System.exit(0);}
		}
	}
	
	public static void main (String[] args)
	{
		new NewMemory(16);
	}
}