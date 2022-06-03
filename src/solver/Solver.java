package solver;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import tictactoe.Mark;

/**
 * An implementation of game-solving algorithm to solve turn-based game.
 * 
 * @author Tin Leelavimolsilp
 *
 * @param <P> Type of player
 * @param <A> Type of action
 * @param <S> Type of state
 */
public abstract class Solver<P extends Comparable<P>, A extends Comparable<A>, S extends State<P, A>, T extends GameTree<P, A, S>>
	implements Iterable<Map.Entry<S, A[]>> {

    /**
     * Solve the game tree and return value of the initial state with respect to the
     * player of the initial state.
     * 
     * @return a positive value if it's a win for the player; a negative value if
     *         it's a loss; zero if it's a tie
     */
    public float solve() 
    {    	
    	int maxPlayerVal = 0;
    	int minPlayerVal = 0;
    	int tieVal = 0;
    	
    	P maxPlayerMark = tree.states[0].getPlayer();
    	P minPlayerMark = getOpponent(maxPlayerMark);
    	
    	for(int i = 0; i < tree.actions.length; i ++)
    	{
    		if(tree.actions[i] == null)
    		{
    			if(tree.states[i].getPlayer() == maxPlayerMark)
    			{
    				minPlayerVal ++;
    			}
    			else if (tree.states[i].getPlayer() == minPlayerMark)
    			{
    				maxPlayerVal ++;
    			}
    			else
    			{
    				tieVal ++;
    			}
    		}
    	}
    	if(maxPlayerVal > minPlayerVal && maxPlayerVal > tieVal)
    	{
    		return 1;
    	}
    	else if(minPlayerVal > maxPlayerVal && minPlayerVal > tieVal)
    	{
    		return -1;
    	}
    	else
    	{
    		return 0;
    	}
    }

    /**
     * The game tree
     */
    protected final T tree;

    /**
     * The player of the initial state
     */
    protected final P maxPlayer;

    /**
     * The opponent of the player of the initial state
     */
    protected final P minPlayer;

    /**
     * Value of each state to the max player (or the player of the initial state)
     */
    private float[] maxValue = null;

    /**
     * A map from the state to the best action(s)
     */
    private Map<S, A[]> policy = null;

    /**
     * Null if it is a tie; otherwise the winner of the initial state
     */
    private P winner;

    /**
     * Return the opponent of the specified player
     */
    protected abstract P getOpponent(P p);

    /**
     * Return value of the given state with respect to the specified player
     * 
     * @return a positive value if it's a win for the player, a negative value if
     *         it's a loss; zero if it's a tie
     */
    protected abstract float evaluateTerminalState(S s, P p);

    /**
     * Return a new and empty array of action type
     */
    protected abstract A[] getNewActionArray(int length);

    /**
     * Construct a solver for the given tree
     */
    protected Solver(T tree) {
	if (tree == null)
	    throw new NullPointerException("The specified game tree must not be null.");

	this.tree = tree;
	this.maxPlayer = tree.states[tree.root].player;
	this.minPlayer = getOpponent(this.maxPlayer);
    }

    /**
     * Construct a policy or a map from a game state to an array of optimal actions.
     */
    private void generatePolicy() {
	assert maxValue != null;

	policy = new TreeMap<S, A[]>();
	for (int s = 0; s < tree.states.length; s++)

	    // a terminal state will be mapped to null
	    if (tree.states[s].isTerminated())
		policy.put(tree.states[s], null);

	    // a non-terminal state is mapped to array of best actions
	    else {
		Set<A> bestA = new TreeSet<A>();
		for (int a = 0; a < tree.actions[s].length; a++)
		    if (Math.abs(maxValue[s] - maxValue[tree.destinations[s][a]]) < 0.0001f) // accounting for numerical
											     // overflow
			bestA.add(tree.actions[s][a]);

		policy.put(tree.states[s], bestA.toArray(getNewActionArray(bestA.size())));
	    }
    }

    /**
     * True if method solve() was called before
     */
    public final boolean solved() {
	return maxValue != null;
    }

    /**
     * Return null if it is a tie; otherwise the winner of the initial state is
     * returned.
     */
    public final P getWinner() {
	return winner;
    }

    /**
     * Return an array of best actions to take for the specified state. Null if it
     * is a terminal state.
     */
    public final A[] getPolicy(S s) {
	if (maxValue == null)
	    solve();
	if (policy == null)
	    generatePolicy();

	return policy.get(s);
    }

    @Override
    public Iterator<Entry<S, A[]>> iterator() {
	if (maxValue == null)
	    solve();
	if (policy == null)
	    generatePolicy();

	return policy.entrySet().iterator();
    }

}
