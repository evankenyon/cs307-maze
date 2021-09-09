package maze.solvers;


/**
 * This class represents the abstraction of a maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class SearchAlgorithm {
	// name for this search algorithm
	private final String myDescription;


	/**
	 * Create an algorithm with its name.
	 */
	public SearchAlgorithm (String description) {
		myDescription = description;
	}

	/**
	 * Take one step searching for solution path for the maze.
	 * @return true if goal has been found or no more paths possible
	 */
	public boolean step () {
		return false;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString () {
		return myDescription;
	}
}
