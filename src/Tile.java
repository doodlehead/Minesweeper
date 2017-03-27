
import javax.swing.*;
/**
 * The Tile class represents a Tile in the Minesweeper game. A Tile stores its own state, including its number,
 * if it has a mine, if it's flagged and its coordinates in the board.
 * @author Frank Yu
 * @since June 19, 2016
 */
public class Tile extends JButton
{
	private boolean hasMine;
	private boolean hasFlag;
	private boolean isPressed;
	private int numValue;
	private int x;
	private int y;
	/**
	 * The only constructor for the Tile class. 
	 * @param numValue The number displayed on the Tile
	 * @param hasMine If the Tile contains a mine
	 * @param x The x-coordinate in the board
	 * @param y The y-coordinate in the board
	 */
	public Tile(int numValue, boolean hasMine, int x, int y)
	{
		super();
		
		this.numValue = numValue;
		this.hasMine = hasMine;
		this.x = x;
		this.y = y;
	}
	/**
	 * The accessor method for hasFlag
	 * @return If the Tile is currently flagged.
	 */
	public boolean hasFlag()
	{
		return this.hasFlag;
	}
	
	/**
	 * Mutator method for hasFlag. It also switches the icon depending on the value of hasFlag
	 * @param flag The value to set hasFlag to
	 */
	public void setFlag(boolean flag)
	{
		this.hasFlag = flag;
		if(this.hasFlag)
		{
			if(this.getWidth() >= 40)
				this.setIcon(new ImageIcon("Images/Easy/Flag.png"));
			else
				this.setIcon(new ImageIcon("Images/Hard/Flag.png"));
		}
		else
			this.setIcon(null);
	}
	/**
	 * Accessor method for x
	 * @return The x-coordinate of the Tile
	 */
	public int getXValue()
	{
		return this.x;
	}
	/**
	 * Accessor method for y
	 * @return The y-coordinate of the Tile
	 */
	public int getYValue()
	{
		return this.y;
	}
	/**
	 * Accessor method for hasMine
	 * @return Whether or not the Tile contains a mine
	 */
	public boolean hasMine()
	{
		return this.hasMine;
	}
	/**
	 * Accessor method for isPressed
	 * @return If the Tile is pressed or not
	 */
	public boolean isPressed()
	{
		return this.isPressed;
	}
	/**
	 * This method presses the Tile. This sets isPressed to true and also disables the button.
	 * Depeding on the contents of the button, the icon will also change.
	 */
	public void pressTile()
	{
		this.isPressed = true;
		if(this.hasMine) //If it's a mine
		{
			if(this.getWidth() >= 40)
			{
				this.setIcon(new ImageIcon("Images/Easy/Mine.png"));
				this.setDisabledIcon(new ImageIcon("Images/Easy/Mine.png"));
			}
			else
			{
				this.setIcon(new ImageIcon("Images/Hard/Mine.png"));
				this.setDisabledIcon(new ImageIcon("Images/Hard/Mine.png"));
			}
			
		}
		if(numValue != 0 && !this.hasMine) //If it's a number Tile, if the number is 0, it is blank and has no image.
		{
			if(this.getWidth() >= 40)
			{
				this.setIcon(new ImageIcon("Images/Easy/" + numValue + ".png"));
				this.setDisabledIcon(new ImageIcon("Images/Easy/" + numValue + ".png"));
			}
			else
			{
				this.setIcon(new ImageIcon("Images/Hard/" + numValue + ".png"));
				this.setDisabledIcon(new ImageIcon("Images/Hard/" + numValue + ".png"));
			}
		}
		this.setEnabled(false); //Disable button
	}
	/**
	 * Accessor method for numValue
	 * @return The number displayed on the Tile. The number value for a mine is -1, and a blank space is 0
	 */
	public int getNumValue()
	{
		return this.numValue;
	}

}
