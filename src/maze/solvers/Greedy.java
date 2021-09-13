package maze.solvers;

import java.util.*;

import maze.model.Maze;
import maze.model.Spot;


/**
 * Purpose (comment borrowed from Prof. Duvall): This class represents a Greedy maze search algorithm.
 * Dependencies: java.util, Maze, Spot
 * Example: Construct a greedy maze search algorithm to solve a Maze object, which is displayed with MazeDisplay
 *
 * @author Evan Kenyon
 */
public class Greedy extends QueueSearchAlgorithm {
	public static final String TITLE = "Greedy";

	/**
	 * Purpose: Construct a Greedy maze solving algorithm
	 * @param maze the maze that represents the maze that is displayed
	 *             (i.e. it contains the underlying logic)
	 */
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
