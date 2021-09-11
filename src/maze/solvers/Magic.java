package maze.solvers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import maze.model.Maze;
import maze.model.Spot;
import maze.util.Randomness;


/**
 * This class represents a Magic maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class Magic extends SearchAlgorithm {
	public static final String TITLE = "Magic";

	// data structure used to keep search frontier -- use a priority queue
	public Magic (Maze maze) {
		super(TITLE, maze);
		myFrontier = new PriorityQueue<>();
		((PriorityQueue<Spot>) getMyFrontier()).add(myCurrent);
	}

	/**
	 * @see SearchAlgorithm#step()
	 */
	@Override
	public boolean step () {
		// color successful path found
		if (isSearchOver()) {
			markPath();
			return true;
		}
		// find possible next steps
		List<Spot> neighbors = myMaze.getNeighbors(myCurrent);
		// choose next spot to explore -- magic means next spot could be a wall!
		Spot next = Randomness.getRandomElement(neighbors);
		// mark next step, if it exists
		if (next != null) {
			next.markAsPath();
			myFrontier.add(next);
			myPaths.put(next, myCurrent);
		}
		else {
			myCurrent.markAsVisited();
			((PriorityQueue<Spot>) getMyFrontier()).remove();
		}
		// update current spot
		myCurrent = ((PriorityQueue<Spot>) getMyFrontier()).peek();
		return false;
	}

}
