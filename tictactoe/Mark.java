package tictactoe;

/**
 * State of grid cell. It is also used to represent the outcome of game; i.e. a
 * non-terminal state should be associated with unmarked (since it still have
 * unmarked cells), a terminal state with a winner should be associated with the
 * mark of the winner, and a terminal state with a tie should not be associated
 * with any element and hence null.
 * 
 * @author Tin Leelavimolsilp
 */
public enum Mark {

    Nought("O"), Cross("X"), Unmarked("-");

    /**
     * the mark letter
     */
    final String c;

    Mark(String c) {
	this.c = c;
    }

    @Override
    public final String toString() {
	return c;
    }
}
