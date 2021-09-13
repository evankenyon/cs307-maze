package maze.solvers;

import java.util.LinkedList;
import java.util.List;
import maze.model.Maze;
import maze.model.Spot;


/**
 * This class represents a Breadth-First maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class BFS extends QueueSearchAlgorithm {
	public static final String TITLE = "Breadth-First";

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
		// find possible next steps
		List<Spot> neighbors = myMaze.getNeighbors(currSpot);
		Spot next = chooseNextSpot(neighbors);
		markNextStep(next);
		updateCurrentSpot();
		return false;
	}
}
