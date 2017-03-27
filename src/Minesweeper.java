import java.util.Random;
/**
 * This class represents the board of the minesweeper game.
 * @author Frank Yu
 * @since June 19, 2016
 */
public class Minesweeper
{
	private int length;
	private int width;
	private int numMines;
	private Tile[][] board;
	/**
	 * Only constructor for the Minesweeper class. Sets up the board using the given arguments.
	 * @param length The length of the board
	 * @param width The width of the board
	 * @param numMines The number of mines on the board
	 */
	public Minesweeper(int length, int width, int numMines)
	{
		this.length = length;
		this.width = width;
		this.numMines = numMines;
		board = new Tile[width][length];
		
		for(int i=0;i<this.width;i++)
		{
			for(int t=0;t<this.length;t++)
			{
				board[i][t] = new Tile(0, false, t, i); //Initializes all the tiles to blank tiles by default
			}
		}
		placeMines();
		placeNumbers();
	}
	/**
	 * This method returns the number of mines surrounding a certain tile(0-8).
	 * @param x The x-coordinate of the tile
	 * @param y The y-coordinate of the tile
	 * @return The number of mines surround the Tile at (x, y)
	 */
	private int getNumber(int x, int y)
	{
		int total = 0;
		total += checkMine(x+1, y);
		total += checkMine(x+1, y+1);
		total += checkMine(x+1, y-1);
		total += checkMine(x-1, y);
		total += checkMine(x-1, y+1);
		total += checkMine(x-1, y-1);
		total += checkMine(x, y+1);
		total += checkMine(x, y-1);
		return total;
	}
	/**
	 * This method checks to see if a certain Tile has a mine. Returns 1 if yes, 0 if no.
	 * @param x X-coordinate of the Tile
	 * @param y Y-Coordinate of the Tile
	 * @return 1 if the Tile contains a mine, 0 if it doesn't or if it's out of bounds
	 */
	private int checkMine(int x, int y)
	{
		if(x < 0 || x >= length || y < 0 || y >= width) //Out of bounds
			return 0;
		else
		{
			if(board[y][x].hasMine())
				return 1;
			else
				return 0;
		}
	}
	/**
	 * This method places numbers on the board that indicate how many mines surround each Tile. Should be called after placeMines().
	 */
	private void placeNumbers()
	{
		for(int i=0;i<this.width;i++)
		{
			for(int t=0;t<this.length;t++)
			{
				if(!board[i][t].hasMine())
					board[i][t] = new Tile(getNumber(t, i), false, t, i);
			}
		}
	}
	/**
	 * This method randomly places numMines amount of mines on the board.
	 */
	private void placeMines()
	{
		int tempMineCount = 0;
		
		while(tempMineCount < this.numMines)
		{
			Random rand = new Random();
			
			int x = rand.nextInt(length);
			int y = rand.nextInt(width);
			
			if(!board[y][x].hasMine()) //If the Tile doesn't contain a mine, put one
			{
				board[y][x] = new Tile(-1, true, x, y);
				tempMineCount++;
			}
		}
	}
	/**
	 * Accessor method for length
	 * @return The length of the board
	 */
	public int getLength()
	{
		return this.length;
	}
	/**
	 * Accessor method for width
	 * @return The width of the board
	 */
	public int getWidth()
	{
		return this.width;
	}
	/**
	 * Accessor method for numMines
	 * @return The number of mines on the board
	 */
	public int getNumMines()
	{
		return this.numMines;
	}
	/**
	 * This method returns the Tile at the location (x, y).
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @return The Tile located at (x, y)
	 */
	public Tile getTile(int x, int y)
	{
		return board[y][x];
	}
	/**
	 * This method checks the board state to see if the user has won. The user has won if they have flagged all the mines and opened
	 * all the Tiles that don't contain mines.
	 * @return
	 */
	public boolean checkWin()
	{
		int flagCount = 0;
		for(int i=0;i<this.width;i++)
		{
			for(int t=0;t<this.length;t++)
			{
				Tile temp = board[i][t];
				if(!temp.isPressed() && (!temp.hasFlag() || !temp.hasMine()))
					return false;
				if(board[i][t].hasMine() && board[i][t].hasFlag())
				{
					flagCount++;
				}
			}
		}
		return flagCount == this.numMines;
	}
}
