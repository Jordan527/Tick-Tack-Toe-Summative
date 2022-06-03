import solver.GameTree;
import tictactoe.Coordinate;
import tictactoe.Grid;
import tictactoe.Mark;

/**
 * A game tree for Nought-and-Crosses
 * 
 * @author Tin Leelavimolsilp
 */
public class TicTacToeGameTree extends GameTree<Mark, Coordinate, Grid> {

    TicTacToeGameTree(Grid root) {
	super(root);
    }

    @Override
    protected final Grid constructNewState(Grid s) {
	return new Grid(s);
    }

    @Override
    protected final Grid[] getNewStateArray(int length) {
	return new Grid[length];
    }

    @Override
    protected final Coordinate[][] getNewActionArray(int length) {
	return new Coordinate[length][];
    }

}
