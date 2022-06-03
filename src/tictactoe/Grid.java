package tictactoe;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import solver.State;

/**
 * Square grid of Noughts-and-Crosses
 * 
 * @author Tin Leelavimolsilp
 */
public class Grid extends State<Mark, Coordinate> {

    /**
     * Mark the grid cell with the mark of the current {@code player}. This method
     * also check and record the status of this game after the coordinate was
     * marked.
     * <P>
     * After a call to this method, the mark of the current {@code player} will be
     * automatically changed to the opponent.
     * 
     * @param coord an index of grid cell
     */
    @Override
    public final void doAction(Coordinate coord) {
		Coordinate[] set = getAction(); //gets a list of unmarked spots
		boolean available = false;
		
		for(int i = 0; i < set.length; i ++)
		{
			if(set[i].equals(coord))
			{
				available = true; //checks if the coordinate exists within the unmarked spots
			}
		}
		
		if(available)
		{
			grid[coord.row][coord.column] = player; 
			System.out.println(toString());
			
			if (isWinner(player))
			{
				status = player;
				System.out.println("Winner: " + player);
			}
			
			if(getPlayer() == Mark.Cross)
			{
				xCount ++;
				player = Mark.Nought;
			}
			else if(getPlayer() == Mark.Nought)
			{
				oCount ++;
				player = Mark.Cross;
			}
		}
		else
		{
			System.out.println("This coordinate is not available");
		}
		
		int unmarked = countUnmarkedCells();
		if (unmarked == 0 && status == Mark.Unmarked)
		{
			status = null;
			System.out.println("No Winner");
		}
		
    }
    
    
    
    public final boolean isWinner(Mark m)
    {
    	int hCount = 0;
    	int vCount = 0;
    	boolean winner = false;
    	
    	for(int i = 0; i < grid.length; i ++)
    	{
    		for(int j = 0; j < grid.length; j ++)
    		{
    			if(grid[i][j] == m)
    			{
    				hCount ++;
    			}
    			if(grid[j][i] == m)
    			{
    				vCount ++;
    			}
    		}
    		if(hCount == grid.length || vCount == grid.length)
    		{
    			winner = true;
    		}
    		hCount = 0;
    		vCount = 0;
    	}
    	
    	int count = 0;
    	
    	for(int i = 0; i < grid.length; i ++)
    	{
    		if(grid[i][i] == m)
    		{
    			count ++;
    		}
    	}
    	
    	
    	if(count == grid.length)
    	{
    		winner = true;
    	}
    	
    	
    	
    	count = 0;
    	
    	int index = 0;
    	
    	for(int i = grid.length-1; i > 0; i --)
    	{
    		if(grid[index][i] == m)
    		{
    			count ++;
    		}
    		index ++;
    	}
    	
    	
    	if(count == grid.length)
    	{
    		winner = true;
    	}
    	
    	return winner;
    }

    /**
     * Rows of cells; i.e. the {@code grid[i][j]} is the cell at i-th row and j-th
     * column
     */
    private final Mark[][] grid;

    /**
     * Total number of X marks
     */
    private int xCount = 0;

    /**
     * Total number of O marks
     */
    private int oCount = 0;

    /**
     * Status of this game state. Unmarked if it is non-terminal; null if it is a
     * Tie; otherwise mark of the winner
     */
    private Mark status = Mark.Unmarked;

    /**
     * Construct a new square {@code grid}
     * 
     * @param mark the mark of the player that will take turn
     * @param size the size of the {@code grid}
     */
    public Grid(Mark mark, int size) {
	super(mark);
	if (mark == Mark.Unmarked)
	    throw new IllegalArgumentException("the given mark must not be UnMarked.");
	if (size <= 0)
	    throw new IllegalArgumentException("the given size is not positive.");

	grid = new Mark[size][size];
	for (int i = 0; i < grid.length; i++)
	    Arrays.fill(grid[i], Mark.Unmarked);
    }

    /**
     * Construct a new grid that is a copy of the given grid
     * 
     * @param g an existing grid
     */
    public Grid(Grid g) {
	super(g);

	this.grid = new Mark[g.grid.length][g.grid.length];
	for (int i = 0; i < grid.length; i++)
	    for (int j = 0; j < grid[i].length; j++)
		this.grid[i][j] = g.grid[i][j];

	this.oCount = g.oCount;
	this.xCount = g.xCount;
	this.status = g.status;
    }

    /**
     * Return coordinates of unmarked cell, or {@code null} if this is a terminal
     * state.
     */
    @Override
    public final Coordinate[] getAction() {
	if (status != Mark.Unmarked) // if this state is terminal
	    return null;

	// construct a sorted array of permissible actions
	Set<Coordinate> set = new TreeSet<Coordinate>();
	for (int i = 0; i < grid.length; i++)
	    for (int j = 0; j < grid[i].length; j++)
		if (grid[i][j] == Mark.Unmarked)
		    set.add(new Coordinate(i, j));
	return set.toArray(new Coordinate[set.size()]);
    }

    @Override
    public final boolean isTerminated() {
	return status == Mark.Unmarked ? false : true;
    }

    /**
     * Return total number of unmarked cells
     */
    public final int countUnmarkedCells() {
	return (grid.length * grid[0].length) - xCount - oCount;
    }

    /**
     * Return Unmarked if the state is non-terminal; null if it is a tie; otherwise
     * mark of the winner is returned
     */
    public final Mark getWinner() {
	return status;
    }

    @Override
    public final int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Arrays.deepHashCode(grid);
	result = prime * result + oCount;
	result = prime * result + xCount;
	result = prime * result + ((status == null) ? 0 : status.hashCode());
	return result;
    }

    @Override
    public final boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;

	if (!(obj instanceof Grid))
	    return false;
	Grid other = (Grid) obj;

	if (!Arrays.deepEquals(grid, other.grid))
	    return false;
	if (status != other.status)
	    return false;
	if (oCount != other.oCount)
	    return false;
	if (xCount != other.xCount)
	    return false;

	return true;
    }

    @Override
    public final int compareTo(State<Mark, Coordinate> s) {
	if (this == s)
	    return 0;
	if (s == null)
	    throw new NullPointerException("the given state s must not be null.");

	if (!(s instanceof Grid))
	    throw new IllegalArgumentException("the given argument is not an instace of Grid.");
	Grid g = (Grid) s;

	int c;
	if ((c = Integer.compare(this.grid.length, g.grid.length)) != 0)
	    return c;
	if ((c = Integer.compare(this.xCount + this.oCount, g.xCount + g.oCount)) != 0)
	    return c;
	if ((c = this.player.compareTo(g.player)) != 0)
	    return c;

	for (int i = 0; i < grid.length; i++)
	    for (int j = 0; j < grid[i].length; j++)
		if ((c = this.grid[i][j].compareTo(g.grid[i][j])) != 0)
		    return c;

	return 0;
    }

    @Override
    public final String toString() {
	StringBuilder s = new StringBuilder();
	for (int i = 0; i < grid.length; i++) {
	    for (int j = 0; j < grid[i].length; j++)
		s.append(grid[i][j].toString());
	    s.append(System.lineSeparator());
	}

	return s.toString();
    }

}
