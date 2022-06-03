package tictactoe;

/**
 * Row index and column index
 * 
 * @author Tin Leelavimolsilp
 */
public class Coordinate implements Comparable<Coordinate> {

    /**
     * Row index
     */
    public final int row;

    /**
     * Column index
     */
    public final int column;

    public Coordinate(int row, int column) {
	if (row < 0)
	    throw new IllegalArgumentException("the given row index is negative");
	if (column < 0)
	    throw new IllegalArgumentException("the given column index is negative");

	this.row = row;
	this.column = column;
    }

    @Override
    public final int compareTo(Coordinate ci) {
	int c;
	if ((c = Integer.compare(this.row, ci.row)) != 0)
	    return c;
	if ((c = Integer.compare(this.column, ci.column)) != 0)
	    return c;

	return 0;
    }

    @Override
    public final int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + column;
	result = prime * result + row;
	return result;
    }

    @Override
    public final boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!(obj instanceof Coordinate))
	    return false;

	Coordinate other = (Coordinate) obj;
	if (column != other.column)
	    return false;
	if (row != other.row)
	    return false;

	return true;
    }

    @Override
    public final String toString() {
	return "(" + row + "," + column + ")";
    }
}
