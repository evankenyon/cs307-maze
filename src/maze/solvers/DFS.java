package maze.solvers;

import java.util.List;
import java.util.Stack;
import maze.model.Maze;
import maze.model.Spot;


/**
 * This class represents a Depth-First maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class DFS extends SearchAlgorithm {
	public static final String TITLE = "Depth-First";

	public DFS (Maze maze) {
		super(TITLE, maze);
		myFrontier = new Stack<>();
		((Stack<Spot>) myFrontier).push(myCurrent);
	}

	/**
	 * @see SearchAlgorithm#step()
	 */
	@Override
	public boolean step () {
		// find possible next steps
		List<Spot> neighbors = myMaze.getNeighbors(myCurrent);
		// choose next spot to explore
		Spot next = chooseNextSpot(neighbors);
		// mark next step, if it exists
		markNextStep(next);
		// update current spot
		return updateCurrentSpot();
	}

	private boolean updateCurrentSpot() {
		myCurrent = ((Stack<Spot>) getMyFrontier()).peek();
		return isSearchOver();
	}

	private void markNextStep(Spot next) {
		if (next != null) {
			next.markAsPath();
			((Stack<Spot>) myFrontier).push(next);
		}
		else {
			myCurrent.markAsVisited();
			((Stack<Spot>) myFrontier).pop();
		}
	}
}
