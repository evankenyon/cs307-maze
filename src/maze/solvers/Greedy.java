package maze.solvers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import maze.model.Maze;
import maze.model.Spot;


/**
 * This class represents a Greedy maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class Greedy extends SearchAlgorithm {
	public static final String TITLE = "Greedy";

	private Maze myMaze;
	// data structure used to keep search frontier -- use a priority queue
	private PriorityQueue<Spot> myFrontier;
	// current spot being explored
	private Spot myCurrent;
	// trail of all spots can be used to recreate chosen path
	private Map<Spot, Spot> myPaths;


	public Greedy (Maze maze) {
		super(TITLE);
		myMaze = maze;
		myFrontier = new PriorityQueue<>();
		myCurrent = maze.getStart();
		myCurrent.markAsPath();
		myFrontier.add(myCurrent);
		myPaths = new HashMap<>();
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
		Spot next = null;
		for (Spot spot : neighbors) {
			if (spot.getState() == Spot.EMPTY) {
				next = spot;
				break;
			}
		}
		// mark next step, if it exists
		if (next != null) {
			next.markAsPath();
			myFrontier.add(next);
			myPaths.put(next, myCurrent);
		}
		else {
			myCurrent.markAsVisited();
			myFrontier.remove();
		}
		// update current spot
		myCurrent = myFrontier.peek();
		return false;
	}


	// Search is successful if current spot is the goal.
	// Search is unsuccessful if there are no more frontier spots to consider
	private boolean isSearchOver () {
		return myFrontier.isEmpty() || (myCurrent != null && myCurrent.equals(myMaze.getGoal()));
	}

	// When the search is over, color the chosen correct path using trail of successful spots
	private void markPath () {
		Spot step = myMaze.getGoal();
		while (step != null) {
			step.markAsPath();
			step = myPaths.get(step);
		}
	}
}
