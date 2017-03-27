import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
/**
 * This class controls the graphical user interface of the Minesweeper game, 
 * which contains a menu that helps navigate.
 * @author Frank YU
 * @since June 19, 2016
 */
public class MinesweeperGUI extends JFrame
{
	//Uses containers as different pages
	private Container homepage;
	private Container playScreen;
	private Container difficultySelect;
	private Container customGameSelect;
	
	private Minesweeper mineGame;
	/**
	 * Constructor for the GUI, initializes all the components and adds them the to 
	 * appropriate container. Also includes an actionListener implementation that 
	 * is added to the buttons 
	 */
	public MinesweeperGUI()
	{
		super("Minesweeper Game");
		
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //Set look and feel
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		//Initialize
		JLabel title = new JLabel("Minesweeper");
		title.setFont(new Font("Helvetica", Font.BOLD, 46));
		
		//Initialize the homepage
		homepage = new Container();
		JButton play = new JButton("Play");
		JButton home = new JButton("Home");
		JButton instructions = new JButton("Instructions");
		//JButton score = new JButton("Highscores"); //Unimplemented feature
		
		title.setBounds(150, 40, 300, 50);
		play.setBounds(200, 150, 200 , 50);
		instructions.setBounds(200, 230, 200, 50);
		//score.setBounds(200, 310, 200, 50);
		
		homepage.add(title);
		homepage.add(play);
		homepage.add(instructions);
		//homepage.add(score);
		
		//Initialize the play screen
		playScreen = new Container();
		
		//Initialize the difficulty select page
		difficultySelect = new Container();
		JButton easy = new JButton("Easy");
		JButton medium = new JButton("Medium");
		JButton hard = new JButton("Hard");
		JButton custom = new JButton("Custom");
		
		easy.setBounds(80, 50, 200, 100);
		medium.setBounds(300, 50, 200, 100);
		hard.setBounds(80, 200, 200, 100);
		custom.setBounds(300, 200, 200, 100);
		home.setBounds(200, 380, 200, 50);
		
		difficultySelect.add(easy);
		difficultySelect.add(medium);
		difficultySelect.add(hard);
		difficultySelect.add(custom);
		difficultySelect.add(home);
		
		//Initialize the custom game creation page
		customGameSelect = new Container();
		JTextField lengthIn = new JTextField(5);
		JTextField widthIn = new JTextField(5);
		JTextField numMinesIn = new JTextField(5);
		JLabel length = new JLabel("Length(1-30):");
		JLabel width = new JLabel("Width(1-16):");
		JLabel numMines = new JLabel("Number of mines(<l*w):");
		JButton create = new JButton("Create");
		JButton home2 = new JButton("Home");
		
		Font customSelectFont = new Font("Helvetica", Font.BOLD, 24);
		length.setFont(customSelectFont);
		length.setBounds(50, 50, 190, 30);
		lengthIn.setBounds(350, 50, 100, 30);
		
		width.setFont(customSelectFont);
		width.setBounds(50, 120, 150, 30);
		widthIn.setBounds(350, 120, 100, 30);
		
		numMines.setFont(customSelectFont);
		numMines.setBounds(50, 190, 280, 30);
		numMinesIn.setBounds(350, 190, 100, 30);
		create.setBounds(100, 250, 250, 80);
		home2.setBounds(100, 350, 250, 80);
		
		customGameSelect.add(length);
		customGameSelect.add(lengthIn);
		customGameSelect.add(width);
		customGameSelect.add(widthIn);
		customGameSelect.add(numMines);
		customGameSelect.add(numMinesIn);
		customGameSelect.add(create);
		customGameSelect.add(home2);
		this.add(homepage);
		
		//Custom actionListener for all the buttons
		class ButtonListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(e.getSource() == play)
					selectDifficulty();
				else if(e.getSource() == home || e.getSource() == home2)
					homepage();
				else if(e.getSource() == easy)
					play(Difficulty.EASY);
				else if(e.getSource() == medium)
					play(Difficulty.MEDIUM);
				else if(e.getSource() == hard)
					play(Difficulty.HARD);
				else if(e.getSource() == custom)
				{
					customGame();
				}
				else if(e.getSource() == create)
				{
					try
					{
						int length = Integer.parseInt(lengthIn.getText());
						int width = Integer.parseInt(widthIn.getText());
						int numMines = Integer.parseInt(numMinesIn.getText());
						
						if(checkValues(length, width, numMines))
						{
							play(length, width, numMines);
						}
						else
							showMessage("Invalid Input! Length must be < 30, Width < 16, and Number of Mines must be less than Length x Width");
					}
					catch(Exception ex)
					{
						showMessage("Invalid input!, Please enter numbers.");
					}	
				}
				else if(e.getSource() == instructions)
				{
					showInstructions();
				}
			}
		}
		//Add an actionListener to all the buttons
		ButtonListener bl = new ButtonListener();
		play.addActionListener(bl);
		home.addActionListener(bl);
		home2.addActionListener(bl);
		easy.addActionListener(bl);
		medium.addActionListener(bl);
		hard.addActionListener(bl);
		custom.addActionListener(bl);
		create.addActionListener(bl);
		instructions.addActionListener(bl);
		homepage();
		setVisible(true);
	}
	/**
	 * This method allows the inner classes to display MessageDialogs
	 * @param message
	 */
	private void showMessage(String message)
	{
		JOptionPane.showMessageDialog(this, message);
	}
	
	/**
	 * This method checks if the length, width, and numMines values are valid for a minesweeper game
	 * @param length The length of the minesweeper game
	 * @param width The width of the minesweeper game
	 * @param numMines The number of mines in the minesweeper game
	 * @return true if the values are acceptable, false otherwise
	 */
	private boolean checkValues(int length, int width, int numMines)
	{
			if(length > 30 || length <= 0 || width > 16 || width <= 0 || numMines <= 0 || numMines >= length * width)
				return false;
			
		return true;	
	}
	/**
	 * This method removes all the containers from the JFrame. It is used to help
	 * switch between pages
	 */
	private void clear()
	{
		this.remove(homepage);
		this.remove(difficultySelect);
		this.remove(playScreen);
		this.remove(customGameSelect);
	}
	/**
	 * This method navigates to the home page
	 */
	private void homepage()
	{
		this.clear();
		this.setSize(600, 500);
		this.add(homepage);
		this.repaint();
		this.setVisible(true);
	}
	/**
	 * This method shows the user a popup that contains instruction on how to play Minesweeper
	 */
	private void showInstructions()
	{
		JOptionPane.showMessageDialog(this, "Uncover a mine, and the game ends."
				+ "\nUncover an empty square, and you keep playing."
				+ "\nUncover a number, and it tells you how many mines lay hidden in the eight surrounding squares—information you use to deduce which nearby squares are safe to click."
				+ "\nRight click any unopened tiles to FLAG them."
				+ "\nLeft click any unopened tiles to OPEN them."
				+ "\nTo win, FLAG all the mines and OPEN all the tiles that don't have mines.");
	}
	/**
	 * This method navigates to the difficulty selection page
	 */
	private void selectDifficulty()
	{
		clear();
		setSize(600, 500);
		add(difficultySelect);
		repaint();
		setVisible(true);
	}
	/**
	 * This method navigates to the custom game creation page
	 */
	private void customGame()
	{
		clear();
		setSize(480, 500);
		add(customGameSelect);
		repaint();
		setVisible(true);
	}
	/**
	 * This method is a helper method for play(int length, int width, int mines),
	 * giving predefined values depending on which difficulty was chosen.
	 * @param d The difficulty of the minesweeper game
	 */
	private void play(Difficulty d)
	{
		if(d == Difficulty.EASY)
		{
			play(10, 10, 2);
		}
		else if(d == Difficulty.MEDIUM)
		{
			play(16, 16, 50);
		}
		else if(d == Difficulty.HARD)
		{
			play(40, 20, 100);
		}
	}
	/**
	 * This method creates a minesweeper object using the given arguments and then
	 * creates and navigates the user to the actual game page.
	 * @param length Length of the minesweeper board
	 * @param width Width of the minesweeper board
	 * @param mines The number of mines on the minesweeper board
	 */
	private void play(int length, int width, int mines)
	{
		setSize(length*27, width*27 + 30);
		mineGame = new Minesweeper(length, width, mines);
		playScreen.setLayout(new GridLayout(width, length));
		updateBoardGUI();
		this.clear();
		this.add(playScreen);
		setVisible(true);
	}
	/**
	 * This method is called when the user loses and shows the user a popup explaining their loss, and giving them an option to 
	 * play again or navigate home.
	 */
	private void loseGame()
	{
		showMines();
		Object[] options = {"Home", "Play Again"};
		int result = JOptionPane.showOptionDialog(this, "You Lose!",
			"You Lose!", JOptionPane.YES_NO_OPTION,
			JOptionPane.PLAIN_MESSAGE, new ImageIcon("Images/LoseFace.png"), options, options[1]);
		
		switch(result) 
		{
        case 0: homepage(); break;
        case 1: selectDifficulty(); break;
		}
	}
	/**
	 * This method recursively opens up connected blank spaces
	 * @param x X coordinate of space
	 * @param y Y coordinate of space
	 */
	private void expandSpace(int x, int y)
	{
		if(x >= 0 && x < mineGame.getLength() && y >= 0 && y < mineGame.getWidth() && !mineGame.getTile(x, y).isPressed()) //Verify that x and y are valid
		{
			if( mineGame.getTile(x, y).getNumValue() == 0 && !mineGame.getTile(x, y).hasFlag()) //If the tile at (x, y) is a blank tile
			{
				mineGame.getTile(x, y).pressTile();
				//Recursive method calls
				expandSpace(x+1, y);
				expandSpace(x+1, y+1);
				expandSpace(x+1, y-1);
				
				expandSpace(x-1, y);
				expandSpace(x-1, y+1);
				expandSpace(x-1, y-1);
				
				expandSpace(x, y+1);
				expandSpace(x, y-1);
			}
			else if(!mineGame.getTile(x, y).hasMine() && !mineGame.getTile(x, y).hasFlag())
			{
				mineGame.getTile(x, y).pressTile();
			}
		}
		
	}
	/**
	 * This method is called when the user loses and reveals the locations of the mines on the board
	 */
	private void showMines()
	{
		for(int i=0;i<mineGame.getWidth();i++)
		{
			for(int t=0;t<mineGame.getLength();t++)
			{
				Tile temp = mineGame.getTile(t, i);
				if(temp.hasMine() && !temp.isPressed())
					temp.pressTile();
			}
		}
	}
	/**
	 * This method makes sure the contents of the playScreen container matches the mineGame object
	 */
	private void updateBoardGUI()
	{
		playScreen.removeAll();
		TileListener tl = new TileListener();
		for(int i=0;i<mineGame.getWidth();i++)
		{
			for(int t=0;t<mineGame.getLength();t++)
			{
				mineGame.getTile(t, i).addMouseListener(tl);
				playScreen.add(mineGame.getTile(t, i));
			}
		}
		this.repaint();
		this.setVisible(true);
	}
	/**
	 * This method checks the mineGame object for the win conditions, if they are met the program display a popup
	 * message informing the user of their victory and giving them the option of playing again or returning home.
	 */
	public void checkWin()
	{
		if(mineGame.checkWin())
		{
			Object[] options = {"Home", "Play Again"};
			int result = JOptionPane.showOptionDialog(this, "You Win!",
				"You Win!", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, new ImageIcon("Images/WinSmile.png"), options, options[1]);
			
			switch(result) 
			{
            case 0: homepage(); break;
            case 1: selectDifficulty(); break;
			}
		}
	}
	/**
	 * Starting point of the program. Creates a new MinesweeperGUI object
	 * @param args
	 */
	public static void main(String[] args)
	{
		new MinesweeperGUI();
	}
	/**
	 * Inner class that listens for and reacts to mouse clicks. Left clicks open the tile if it's not flagged,
	 * and right clicks flag and unflag unopened tiles.
	 */
	class TileListener extends MouseAdapter
	{
		@Override
		public void mouseReleased(MouseEvent e)
		{
			if(e.getComponent() != null && e.getComponent() instanceof Tile) //Verify that a Tile was clicked
			{
				if(e.getButton() == MouseEvent.BUTTON1) //If it's a left click
				{
					Tile temp = (Tile)e.getComponent();
					if(!temp.hasFlag() && !temp.isPressed()) //If the tile is not flagged or pressed
					{
						if(temp.hasMine())
						{
							loseGame(); //User loses if they open a mine
						}
						else if(temp.getNumValue() == 0)
						{
							expandSpace(temp.getXValue(), temp.getYValue()); //Open spaces if one is found
						}
						else
							temp.pressTile();
					}
					checkWin();
				}
				if(e.getButton() == MouseEvent.BUTTON3) //If it's a right click
				{
					Tile temp = (Tile)e.getComponent();
					if(!temp.isPressed()) //If the button is not pressed
					{
						temp.setFlag(!temp.hasFlag()); //Change the flag state to the opposite
						repaint();
						setVisible(true);
					}
					checkWin();
				}
			}
		}
	}
}
/**
 * This enum stores the 3 possible difficulties of the minesweeper game.
 */
enum Difficulty
{
	EASY, MEDIUM, HARD;
}
