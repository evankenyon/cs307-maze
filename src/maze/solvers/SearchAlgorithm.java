package maze.solvers;


import maze.model.Maze;
import maze.model.Spot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Purpose (comment borrowed from Prof. Duvall): This class represents the abstraction of a maze search algorithm.
 * Assumptions: This class itself is not instantiated
 * Dependencies: Maze, Spot, Collection, HashMap, List, Map
 * Example: Create a class that extends this abstract class, and use the provided methods to implement a search
 * algorithm to solve a Maze object, which is displayed with MazeDisplay
 *
 * @author Evan Kenyon
 */
public abstract class SearchAlgorithm {
	private final String myDescription;
	protected Spot currSpot;
	protected Maze myMaze;
	protected int currMyFrontierSize;
	protected int maxMyFrontierSize;
	protected int numBacktracks;
	protected boolean reachedEnd;
	protected int currSteps;
	// trail of all spots can be used to recreate chosen path
	protected Map<Spot, Spot> myPaths;

	/**
	 * Purpose (comment borrowed from Prof. Duvall): Create an algorithm with its name.
	 * @param description the name of this algorithm
	 * @param maze the maze that represents the maze that is displayed
	 *             (i.e. it contains the underlying logic)
	 */
	public SearchAlgorithm (String description, Maze maze) {
		myMaze = maze;
		myDescription = description;
		currSpot = maze.getStart();
		currSpot.markAsPath();
		myPaths = new HashMap<>();
		currMyFrontierSize = 0;
		maxMyFrontierSize = 0;
		numBacktracks = 0;
		reachedEnd = false;
		currSteps = 0;
	}

	/**
	 * Purpose (comment borrowed from Prof. Duvall): Take one step searching for solution path for the maze.
	 * Assumptions: Implemented in SearchAlgorithm subclasses
	 * @return true if goal has been found or no more paths possible (comment borrowed from Prof. Duvall)
	 */
	public abstract boolean step ();

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString () {
		return myDescription;
	}

	/**
	 * Purpose: Get the current number of steps this search algorithm has taken
	 * Assumptions: currSteps is properly incremented in all SearchAlgorithm subclasses
	 * @return the current number of steps this search algorithm has taken
	 */
	public int getCurrSteps() {
		return currSteps;
	}

	/**
	 * Purpose: Get the maximum size of the myFrontier data structure for this search algorithm
	 * Assumptions: incrementCurrMyFrontierSize is called appropriately in SearchAlgorithm subclasses
	 * @return the maximum size of the myFrontier data structure for this search algorithm
	 */
	public int getMaxMyFrontierSize() {
		return maxMyFrontierSize;
	}

	/**
	 * Purpose: Return if the search was successful
	 * @return if the search was successful
	 */
	public boolean isSearchSuccessful() {
		return (currSpot != null && currSpot.equals(myMaze.getGoal()));
	}

	/**
	 * Purpose: Get the number of times this search algorithm has had to backtrack
	 * Assumptions: numBacktracks is appropriately incremented in SearchAlgorithm subclasses
	 * @return the number of times this search algorithm has had to backtrack
	 */
	public int getNumBacktracks() {
		return numBacktracks;
	}

	protected void incrementNumBacktracks(){
		if(!reachedEnd) {
			numBacktracks++;
			reachedEnd = true;
		}
	}

	protected void markPath () {
		Spot step = myMaze.getGoal();
		while (step != null) {
			step.markAsPath();
			step = myPaths.get(step);
		}
	}

	protected Spot chooseNextSpot(List<Spot> neighbors) {
		for (Spot spot : neighbors) {
			if (spot.getState() == Spot.EMPTY) {
				return spot;
			}
		}
		return null;
	}

	protected abstract boolean isSearchUnsuccessful();

	protected boolean isSearchOver () {
		 return isSearchUnsuccessful() || isSearchSuccessful();
	}

	protected void incrementCurrMyFrontierSize() {
		currMyFrontierSize++;
		if(currMyFrontierSize > maxMyFrontierSize) {
			maxMyFrontierSize = currMyFrontierSize;
		}
	}

	protected void decrementCurrMyFrontierSize() {
		currMyFrontierSize--;
	}

}
