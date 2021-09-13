package maze.solvers;

import java.util.LinkedList;
import java.util.List;
import maze.model.Maze;
import maze.model.Spot;


/**
 * Purpose (comment borrowed from Prof. Duvall): This class represents a Breadth-First maze search algorithm.
 * Assumptions:
 * Dependencies:
 * Example:
 * Other details:
 *
 * @author Evan Kenyon
 */
public class BFS extends QueueSearchAlgorithm {
	public static final String TITLE = "Breadth-First";

	/**
	 * Purpose:
	 * Assumptions:
	 * @param maze
	 */
	public BFS (Maze maze) {
		super(TITLE, maze);
		myFrontier = new LinkedList<>();
		myFrontier.add(currSpot);
		incrementCurrMyFrontierSize();
	}

	/**
	 * @see SearchAlgorithm#step()
	 */
	@Override
	public boolean step () {
		if(colorSuccessfulPathFound()) {
			return true;
		}
		List<Spot> neighbors = myMaze.getNeighbors(currSpot);
		Spot next = chooseNextSpot(neighbors);
		markNextStep(next);
		updateCurrentSpot();
		return false;
	}
}
