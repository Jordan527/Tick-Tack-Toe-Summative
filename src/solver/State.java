package solver;

/**
 * State of a game
 * 
 * @author Tin Leelavimolsilp
 *
 * @param <P> Type of player
 * @param <A> Type of action
 */
public abstract class State<P extends Comparable<P>, A extends Comparable<A>> implements Comparable<State<P, A>> {

    /**
     * The player who is to make a move on this state
     */
    protected P player;

    /**
     * Return {@code true} if this is a terminal state of the game
     */
    public abstract boolean isTerminated();

    /**
     * Return the winner of this game's state
     */
    public abstract P getWinner();

    /**
     * Return array of permissible actions. Null should be returned if this is a
     * terminal state.
     */
    public abstract A[] getAction();

    /**
     * Perform the specified action a on this state. If it's a turn-based game, then
     * the player of this state should be changed to the opponent after a call to
     * this method.
     */
    public abstract void doAction(A a);

    @Override
    public abstract String toString();

    protected State(P player) {
	if (player == null)
	    throw new NullPointerException("The given player must not be null.");

	this.player = player;
    }

    protected State(State<P, A> s) {
	if (s == null)
	    throw new NullPointerException("The given state must not be null.");

	this.player = s.player;
    }

    /**
     * Return the player of this state
     */
    public final P getPlayer() {
	return player;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((player == null) ? 0 : player.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!(obj instanceof State))
	    return false;

	State<P, A> other = (State<P, A>) obj;
	if (player == null) {
	    if (other.player != null)
		return false;
	} else if (!player.equals(other.player))
	    return false;

	return true;
    }

    @Override
    public int compareTo(State<P, A> s) {
	if (this == s)
	    return 0;
	if (s == null)
	    throw new NullPointerException("the given state s must not be null.");

	return this.player.compareTo(s.player);
    }

}
