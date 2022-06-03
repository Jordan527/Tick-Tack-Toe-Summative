
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import solver.GameTree;
import solver.Solver;
import tictactoe.Coordinate;
import tictactoe.Grid;
import tictactoe.Mark;

/**
 * An implementation of game-solving algorithm to solve Noughts-and-Crosses
 * 
 * @author Tin Leelavimolsilp
 */
public class TicTacToeSolver extends Solver<Mark, Coordinate, Grid, TicTacToeGameTree> {

    public static void main(String... arg) {
	try {

	    // init the initial state
	    Grid g = new Grid(Mark.Cross, 2);

	    // print the input
	    System.out.println("INPUT");
	    System.out.println("Player: " + g.getPlayer().toString());
	    System.out.println(g.toString());

	    // create and populate game tree
	    TicTacToeGameTree tree = new TicTacToeGameTree(g);
	    tree.print();

	    // solve the game
	    TicTacToeSolver s = new TicTacToeSolver(tree);
	    s.solve();

	    // print the output
	    System.out.println("OUTPUT");
	    Mark winner = s.getWinner();
	    if (winner == null)
		System.out.println("A tie");
	    else {
		if (winner.equals(g.getPlayer())) {
		    System.out.println("Player " + winner.toString() + " wins.");
		    System.out.println("Action to take: " + Arrays.toString(s.getPolicy(g)));
		} else {
		    System.out.println("Player " + winner.toString() + " loses.");
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    TicTacToeSolver(TicTacToeGameTree t) {
	super(t);
    }

    @Override
    protected final Mark getOpponent(Mark p) {
	switch (p) {
	case Cross:
	    return Mark.Nought;
	case Nought:
	    return Mark.Cross;
	default:
	    throw new AssertionError("The specified player p" + p.toString() + " is invalid.");
	}
    }

    /**
     * A grid is associated with a positive value (or a negative value) if it's a
     * win (or a loss, respectively) for the player of the root state; otherwise a
     * zero for a tie
     */
    @Override
    protected final float evaluateTerminalState(Grid s, Mark p) {
	assert s.isTerminated();

	Mark w = s.getWinner();
	if (w == null)
	    return 0;
	else
	    return w.equals(maxPlayer) ? 1 : -1;
    }

    @Override
    protected final Coordinate[] getNewActionArray(int length) {
	return new Coordinate[length];
    }

}
