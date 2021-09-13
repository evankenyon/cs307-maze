package maze.solvers;

import java.util.*;

import maze.model.Maze;
import maze.model.Spot;


/**
 * This class represents a Greedy maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class Greedy extends QueueSearchAlgorithm {
	public static final String TITLE = "Greedy";

	public Greedy (Maze maze) {
		super(TITLE, maze);
		myMaze = maze;
		myFrontier = new PriorityQueue<>();
		((PriorityQueue<Spot>) myFrontier).add(currSpot);
		incrementCurrMyFrontierSize();
	}


	/**
	 * @see SearchAlgorithm#step()
	 */
	@Override
	public boolean step () {
		if(colorSuccessfulPathFound()) {
			System.out.println(getMaxMyFrontierSize());
			return true;
		}
		// find possible next steps
		List<Spot> neighbors = myMaze.getNeighbors(currSpot);
		// sort in order of closest to goal
		Collections.sort(neighbors);
		Spot next = chooseNextSpot(neighbors);
		markNextStep(next);
		updateCurrentSpot();
		return false;
	}

}
