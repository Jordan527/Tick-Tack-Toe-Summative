
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tictactoe.Coordinate;
import tictactoe.Grid;
import tictactoe.Mark;

/**
 * A collection of tests on solver of Noughts-and-Crosses
 * 
 * @author Tin Leelavimolsilp
 */
class TicTacToeSolverTest {

    @Test
    void test2by2Grid() {

	// the first player always win
	Grid g1 = new Grid(Mark.Cross, 2);
	Grid g2 = new Grid(Mark.Nought, 2);
	assertTrue(new TicTacToeSolver(new TicTacToeGameTree(g1)).solve() > 0);
	assertTrue(new TicTacToeSolver(new TicTacToeGameTree(g2)).solve() > 0);

	// the next player always lose
	g1.doAction(new Coordinate(0, 1));
	g2.doAction(new Coordinate(1, 1));
	assertTrue(new TicTacToeSolver(new TicTacToeGameTree(g1)).solve() < 0);
	assertTrue(new TicTacToeSolver(new TicTacToeGameTree(g2)).solve() < 0);
    }

    @Test
    void test3by3Grid() {

	// it's always a tie
	Grid g1 = new Grid(Mark.Cross, 3);
	Grid g2 = new Grid(Mark.Nought, 3);
	assertEquals(0, new TicTacToeSolver(new TicTacToeGameTree(g1)).solve());
	assertEquals(0, new TicTacToeSolver(new TicTacToeGameTree(g2)).solve());

	g1.doAction(new Coordinate(1, 1));
	g2.doAction(new Coordinate(0, 1));
	assertEquals(0, new TicTacToeSolver(new TicTacToeGameTree(g1)).solve());
	assertEquals(0, new TicTacToeSolver(new TicTacToeGameTree(g2)).solve());

    }

}
