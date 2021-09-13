package maze.solvers;

import java.util.List;
import java.util.Stack;
import maze.model.Maze;
import maze.model.Spot;


/**
 * Purpose (comment borrowed from Prof. Duvall): This class represents a Depth-First maze search algorithm.
 * Assumptions:
 * Dependencies:
 * Example:
 * Other details:
 *
 * @author Evan Kenyon
 */
public class DFS extends SearchAlgorithm {
	public static final String TITLE = "Depth-First";

	/**
	 * Purpose: Construct a DFS maze solving algorithm
	 * @param maze the maze that represents the maze that is displayed
	 *             (i.e. it contains the underlying logic)
	 */
	public DFS (Maze maze) {
		super(TITLE, maze);
		myFrontier = new Stack<>();
		((Stack<Spot>) myFrontier).push(currSpot);
		incrementCurrMyFrontierSize();
	}

	/**
	 * @see SearchAlgorithm#step()
	 */
	@Override
	public boolean step () {
		List<Spot> neighbors = myMaze.getNeighbors(currSpot);
		Spot next = chooseNextSpot(neighbors);
		markNextStep(next);
		currSteps++;
		return updateCurrentSpot();
	}

	private boolean updateCurrentSpot() {
		currSpot = ((Stack<Spot>) getMyFrontier()).peek();
		return isSearchOver();
	}

	private void markNextStep(Spot next) {
		if (next != null) {
			next.markAsPath();
			((Stack<Spot>) myFrontier).push(next);
			incrementCurrMyFrontierSize();
			reachedEnd = false;
		}
		else {
			incrementNumBacktracks();
			currSpot.markAsVisited();
			((Stack<Spot>) myFrontier).pop();
			decrementCurrMyFrontierSize();
		}
		currSteps++;
	}
}
