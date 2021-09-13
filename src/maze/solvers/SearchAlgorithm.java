package maze.solvers;


import maze.model.Maze;
import maze.model.Spot;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Purpose (comment borrowed from Prof. Duvall): This class represents the abstraction of a maze search algorithm.
 *
 * @author Evan Kenyon
 */
public abstract class SearchAlgorithm {
	private final String myDescription;
	protected Collection<Spot> myFrontier;
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
	 * Create an algorithm with its name.
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
	 * Take one step searching for solution path for the maze.
	 * @return true if goal has been found or no more paths possible
	 */
	public abstract boolean step ();

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString () {
		return myDescription;
	}

	public int getCurrSteps() {
		return currSteps;
	}

	public int getMaxMyFrontierSize() {
		return maxMyFrontierSize;
	}

	public boolean isSearchSuccessful() {
		return (currSpot != null && currSpot.equals(myMaze.getGoal()));
	}

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

	protected Collection<Spot> getMyFrontier() {
		return myFrontier;
	}

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

	private boolean isSearchUnsuccessful() {
		return myFrontier.isEmpty();
	}
}
