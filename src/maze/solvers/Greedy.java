package maze.solvers;

import java.util.*;

import maze.model.Maze;
import maze.model.Spot;


/**
 * This class represents a Greedy maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class Greedy extends SearchAlgorithm {
	public static final String TITLE = "Greedy";

	public Greedy (Maze maze) {
		super(TITLE, maze);
		myMaze = maze;
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
		// sort in order of closest to goal
		Collections.sort(neighbors);
		// choose next spot to explore
		Spot next = chooseNextSpot(neighbors);
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
