package maze.solvers;


import maze.model.Maze;
import maze.model.Spot;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the abstraction of a maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public abstract class SearchAlgorithm {
	// name for this search algorithm
	private final String myDescription;

	// data structure used to keep search frontier
	protected Collection<Spot> myFrontier;
	// current spot being explored
	protected Spot myCurrent;
	protected Maze myMaze;
	// trail of all spots can be used to recreate chosen path
	protected Map<Spot, Spot> myPaths;

	/**
	 * Create an algorithm with its name.
	 */
	public SearchAlgorithm (String description, Maze maze) {
		myMaze = maze;
		myDescription = description;
		myCurrent = maze.getStart();
		myCurrent.markAsPath();
		myPaths = new HashMap<>();
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

	// When the search is over, color the chosen correct path using trail of successful spots
	protected void markPath () {
		Spot step = myMaze.getGoal();
		while (step != null) {
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

	protected Collection<Spot> getMyFrontier() {
		return myFrontier;
	}

	// Search is over and unsuccessful if there are no more fringe points to consider.
	// Search is over and successful if the current point is the same as the goal.
	protected boolean isSearchOver () {
		return myFrontier.isEmpty() || (myCurrent != null && myCurrent.equals(myMaze.getGoal()));
	}
}
