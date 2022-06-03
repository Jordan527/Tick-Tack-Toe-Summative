
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import tictactoe.Coordinate;
import tictactoe.Grid;
import tictactoe.Mark;

/**
 * A collection of tests on Noughts-and-Crosses Grid
 * 
 * @author Tin Leelavimolsilp
 */
class GridTest {

    @Test
    void testCrossStart2by2Grid() {

	// test 2by2 grid
	Grid grid = new Grid(Mark.Cross, 2);
	Coordinate[] coords = grid.getAction();
	assertEquals(4, coords.length);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(0, 0)) >= 0);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(0, 1)) >= 0);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(1, 0)) >= 0);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(1, 1)) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// cross on top-left
	grid.doAction(new Coordinate(0, 0));
	coords = grid.getAction();
	assertEquals(3, grid.getAction().length);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(0, 1)) >= 0);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(1, 0)) >= 0);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(1, 1)) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// nought on top-right
	grid.doAction(new Coordinate(0, 1));
	coords = grid.getAction();
	assertEquals(2, grid.getAction().length);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(1, 0)) >= 0);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(1, 1)) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// cross on bottom-right
	grid.doAction(new Coordinate(1, 1));
	coords = grid.getAction();
	assertEquals(null, coords);
	assertEquals(Mark.Cross, grid.getWinner());
	assertTrue(grid.isTerminated());
    }

    @Test
    void testNoughtStart2by2Grid() {

	// test 2by2 grid
	Grid grid = new Grid(Mark.Nought, 2);
	Coordinate[] coords = grid.getAction();
	assertEquals(4, coords.length);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(0, 0)) >= 0);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(0, 1)) >= 0);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(1, 0)) >= 0);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(1, 1)) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// nought on bottom-left
	grid.doAction(new Coordinate(1, 0));
	coords = grid.getAction();
	assertEquals(3, grid.getAction().length);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(0, 0)) >= 0);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(0, 1)) >= 0);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(1, 1)) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// cross on top-right
	grid.doAction(new Coordinate(0, 1));
	coords = grid.getAction();
	assertEquals(2, grid.getAction().length);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(0, 0)) >= 0);
	assertTrue(Arrays.binarySearch(coords, new Coordinate(1, 1)) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// nought on top-left
	grid.doAction(new Coordinate(0, 0));
	coords = grid.getAction();
	assertEquals(null, coords);
	assertEquals(Mark.Nought, grid.getWinner());
	assertTrue(grid.isTerminated());
    }

    @Test
    void testCrossStart3by3GridTie() {
	Set<Coordinate> performed = new HashSet<Coordinate>();

	// test 3by3 grid
	Grid grid = new Grid(Mark.Cross, 3);
	Coordinate[] coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		assertTrue(Arrays.binarySearch(coords, new Coordinate(row, col)) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// cross on top-left
	Coordinate c = new Coordinate(0, 0);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// nought on top-right
	c = new Coordinate(0, 2);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// cross on bottom-left
	c = new Coordinate(2, 0);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// nought on mid-left
	c = new Coordinate(1, 0);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// cross on centre
	c = new Coordinate(1, 1);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// nought on bottom-right
	c = new Coordinate(2, 2);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// cross on centre-right
	c = new Coordinate(1, 2);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// nought on middle-top
	c = new Coordinate(0, 1);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// cross on middle-bottom
	c = new Coordinate(2, 1);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	assertEquals(0, grid.countUnmarkedCells());
	assertEquals(null, coords);
	assertEquals(null, grid.getWinner());
	assertTrue(grid.isTerminated());
    }

    @Test
    void testNoughtStart3by3GridTie() {
	Set<Coordinate> performed = new HashSet<Coordinate>();

	// test 3by3 grid
	Grid grid = new Grid(Mark.Nought, 3);
	Coordinate[] coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		assertTrue(Arrays.binarySearch(coords, new Coordinate(row, col)) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// nought on top-left
	Coordinate c = new Coordinate(0, 0);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// cross on top-right
	c = new Coordinate(0, 2);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// nought on bottom-left
	c = new Coordinate(2, 0);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// cross on mid-left
	c = new Coordinate(1, 0);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// nought on centre
	c = new Coordinate(1, 1);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// cross on bottom-right
	c = new Coordinate(2, 2);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// nought on centre-right
	c = new Coordinate(1, 2);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// cross on middle-top
	c = new Coordinate(0, 1);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// nought on middle-bottom
	c = new Coordinate(2, 1);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	assertEquals(0, grid.countUnmarkedCells());
	assertEquals(null, grid.getWinner());
	assertEquals(null, coords);
	assertTrue(grid.isTerminated());
    }

    @Test
    void testNoughtStart3by3GridNoughtWin() {
	Set<Coordinate> performed = new HashSet<Coordinate>();

	// test 3by3 grid
	Grid grid = new Grid(Mark.Nought, 3);
	Coordinate[] coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		assertTrue(Arrays.binarySearch(coords, new Coordinate(row, col)) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// nought on top-left
	Coordinate c = new Coordinate(0, 0);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// cross on top-right
	c = new Coordinate(0, 2);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// nought on centre
	c = new Coordinate(1, 1);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// cross on mid-right
	c = new Coordinate(1, 2);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	for (int row = 0; row < 3; row++)
	    for (int col = 0; col < 3; col++)
		if (!performed.contains((c = new Coordinate(row, col))))
		    assertTrue(Arrays.binarySearch(coords, c) >= 0);
	assertEquals(Mark.Unmarked, grid.getWinner());
	assertFalse(grid.isTerminated());

	// nought on bottom right
	c = new Coordinate(2, 2);
	grid.doAction(c);
	performed.add(c);
	coords = grid.getAction();
	assertEquals(4, grid.countUnmarkedCells());
	assertEquals(Mark.Nought, grid.getWinner());
	assertEquals(null, coords);
	assertTrue(grid.isTerminated());
    }
}
