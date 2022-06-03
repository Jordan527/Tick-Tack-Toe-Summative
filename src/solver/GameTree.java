package solver;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * A tree of game's states and their transitions
 * 
 * @author Tin Leelavimolsilp
 *
 * @param <P> Type of player
 * @param <A> Type of action
 * @param <S> Type of state
 */
public abstract class GameTree<P extends Comparable<P>, A extends Comparable<A>, S extends State<P, A>>
	implements Iterable<S> {

    /**
     * Construct a game tree and populate the tree with the specified state as the
     * root (or the initial state)
     */
    protected GameTree(S root) 
    {
    	ArrayList<S> initStateList = new ArrayList<>();
    	ArrayList<S> stateList = generateStateList(initStateList, root);
    	
    	states = getNewStateArray(stateList.size());
    	actions = getNewActionArray(stateList.size());
    	
    	for(int i = 0; i < stateList.size(); i ++)
    	{
    		S state = stateList.get(i);
    		
    		A[] permissable = getActions(state);    		
    		states[i] = state;
    		
    		for(int j = 0; j < permissable.length; j ++)
    		{
    			actions[i][j] = permissable[j];
    		}
    	}
    	this.destinations = null;
		this.root = 0;

    }

    public ArrayList<S> generateStateList(ArrayList<S> stateList, S state)
    {
    	stateList.add(state);
    	states = getNewStateArray(stateList.size());
    	for(int i = 0; i < stateList.size(); i ++)
    	{
    		states[i] = stateList.get(i);
    	}
    	
    	A[] permissable = getActions(state);
    	if(permissable == null)
    	{
    		return stateList;
    	}
    	else 
    	{
    		for(int i = 0; i < permissable.length; i++)
	    	{
	    		S nextState = getDestinationState(state, permissable[i]);
	    		stateList = generateStateList(stateList, nextState);
	    	}
    		return stateList;
    	}
    }
    
    
    /**
     * Name of folder that store result text files of state transitions
     */
    public static final String DIR_PATHNAME = System.getProperty("user.dir") + File.separatorChar + "state";

    /**
     * all nodes (states) in this game tree. Note that this array must be naturally
     * sorted via method compareTo() itself.
     */
    protected S[] states;

    /**
     * all actions for each state; i.e. actions[i][j] is the j-th action of the i-th
     * state corresponding to the nodes. If nodes[i] is terminal, then actions[i] is
     * null.
     * <P>
     * Note that each subarray is naturally sorted; i.e. every array actions[] is
     * sorted.
     */
    protected final A[][] actions;

    /**
     * index of state after applying an action; i.e. nodes[destinations[i][j]] is
     * the state after applying actions[i][j]. If nodes[i] is terminal, then
     * destinations[i] is null.
     * <P>
     * Unlike the others, subarrays (or every destinations[]) are not sorted for the
     * above reason.
     */
    protected final int[][] destinations;

    /**
     * initial state of the game tree
     */
    protected final int root;

    /**
     * Return a copy of the specified state
     */
    protected abstract S constructNewState(S s);

    /**
     * Return a new and empty array with the specified length
     */
    protected abstract S[] getNewStateArray(int length);

    /**
     * Return a new and empty array with the first dimension of the specified length
     */
    protected abstract A[][] getNewActionArray(int length);

    /**
     * Return an array of permissible actions on the specified state. Null is
     * returned if the specified state is a terminal state.
     */
    public final A[] getActions(S s) {
	if (s == null)
	    throw new NullPointerException("The specified state s must not be null.");

	// check whether the state belongs to this tree
	int i = Arrays.binarySearch(states, s);
	if (i < 0)
	    throw new IllegalArgumentException("The specified state s does not belong to this game tree.");

	return actions[i];
    }

    /**
     * Return the state that results from applying the given action on the specified
     * state
     */
    public final S getDestinationState(S s, A a) {
	if (s == null)
	    throw new NullPointerException("The specified state s must not be null.");
	if (a == null)
	    throw new NullPointerException("The specified action a must not be null.");

	// check whether the state belongs to this tree
	int si = Arrays.binarySearch(states, s);
	if (si < 0)
	    throw new IllegalArgumentException("The specified state s does not belong to this game tree.");

	// check whether the state is terminal state
	if (actions[si] == null)
	    throw new IllegalArgumentException("The specified state s must not be terminal.");

	// check whether the action is permissible on the state
	int ai = Arrays.binarySearch(actions[si], a);
	if (ai < 0)
	    throw new IllegalArgumentException(
		    "The specified action a is not a legal action on the specified state s.");

	return states[destinations[si][ai]];
    }

    /**
     * Return true only if this tree has the specified state.
     */
    public final boolean contains(S s) {
	if (s == null)
	    throw new NullPointerException("The specified state s must not be null.");

	return Arrays.binarySearch(states, s) >= 0;
    }

    /**
     * Return total number of nodes in this tree
     */
    public final int size() {
	return states.length;
    }

    /**
     * Return an iterator over states in this game tree
     */
    @Override
    public final Iterator<S> iterator() {
	return new Iterator<S>() {

	    /**
	     * Index of states array
	     */
	    private int i = 0;

	    @Override
	    public boolean hasNext() {
		return i < states.length;
	    }

	    @Override
	    public S next() {
		return states[i++];
	    }
	};
    }

    /**
     * Write all states into file
     */
    public final void print() throws IOException {

	// create the folder if not existed
	Path p = Paths.get(DIR_PATHNAME);
	if (Files.notExists(p))
	    Files.createDirectory(p);

	// delete all files in the state folder
	Files.list(p).map(Path::toFile).forEach(File::delete);

	// write each state
	for (int s = 0; s < states.length; s++) {

	    // build the string to be written
	    // the grid
	    StringBuilder sb = new StringBuilder();
	    sb.append(states[s].toString());
	    sb.append(System.lineSeparator());

	    // the player
	    sb.append(states[s].player.toString());
	    sb.append(System.lineSeparator());
	    sb.append(System.lineSeparator());

	    // the destinations if exist
	    if (!states[s].isTerminated())
		for (int d = 0; d < destinations[s].length; d++) {
		    sb.append(states[destinations[s][d]].toString());
		    sb.append(System.lineSeparator());
		}

	    // write to file
	    Files.writeString(Paths.get(DIR_PATHNAME, Integer.toString(s)), sb.toString(), StandardCharsets.UTF_8,
		    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + Arrays.hashCode(states);
	result = prime * result + root;
	result = prime * result + Arrays.deepHashCode(actions);
	result = prime * result + Arrays.deepHashCode(destinations);
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!(obj instanceof GameTree))
	    return false;

	GameTree<P, A, S> other = (GameTree<P, A, S>) obj;
	if (!Arrays.equals(states, other.states))
	    return false;
	if (root != other.root)
	    return false;
	if (!Arrays.deepEquals(actions, other.actions))
	    return false;
	if (!Arrays.deepEquals(destinations, other.destinations))
	    return false;

	return true;
    }

}
