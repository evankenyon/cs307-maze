package maze.solvers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import maze.model.Maze;
import maze.model.Spot;


/**
 * This class represents a Breadth-First maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class BFS extends SearchAlgorithm {
	public static final String TITLE = "Breadth-First";

	public BFS (Maze maze) {
		super(TITLE, maze);
		myFrontier = new LinkedList<>();
		myFrontier.add(myCurrent);
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
		Spot next = chooseNextSpot(neighbors);
		// mark next step, if it exists
		if (next != null) {
			next.markAsPath();
			myFrontier.add(next);
			myPaths.put(next, myCurrent);
		}
		else {
			myCurrent.markAsVisited();
			((Queue<Spot>) getMyFrontier()).remove();
		}
		// update current spot
		myCurrent = ((Queue<Spot>) getMyFrontier()).peek();
		return false;
	}

}
