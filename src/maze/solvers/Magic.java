package maze.solvers;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import maze.model.Maze;
import maze.model.Spot;
import maze.util.Randomness;


/**
 * Purpose (comment borrowed from Prof. Duvall): This class represents a Magic maze search algorithm.
 * A Magic maze search algorithm is one that is equivalent to a greedy algorithm except it ignores walls
 * and can go through them
 * Dependencies: ArrayList, List, PriorityQueue, Maze, Spot, Randomness, solvers
 * Example: Construct a magic maze search algorithm to solve a Maze object, which is displayed with MazeDisplay
 *
 * @author Evan Kenyon
 */
public class Magic extends QueueSearchAlgorithm {
	public static final String TITLE = "Magic";

	private List<Spot> visitedSpots;

	/**
	 * Purpose: Construct a magic maze solving algorithm (described above)
	 * @param maze the maze that represents the maze that is displayed
	 *             (i.e. it contains the underlying logic)
	 */
	public Magic (Maze maze) {
		super(TITLE, maze);
		myFrontier = new PriorityQueue<>();
		myFrontier.add(currSpot);
		visitedSpots = new ArrayList<>();
		visitedSpots.add(currSpot);
		incrementCurrMyFrontierSize();
	}

	/**
	 * @see SearchAlgorithm#step()
	 */
	@Override
	public boolean step () {
		if (colorSuccessfulPathFound())  {
			return true;
		}
		List<Spot> neighbors = myMaze.getNeighbors(currSpot);
		Spot next = chooseNextSpot(neighbors);
		markNextStep(next);
		updateCurrentSpot();
		return false;
	}

	@Override
	protected Spot chooseNextSpot(List<Spot> neighbors) {
		Spot spot = Randomness.getRandomElement(neighbors);
		while(visitedSpots.contains(spot)) {
			spot = Randomness.getRandomElement(neighbors);
		}
		visitedSpots.add(spot);
		return spot;
	}

}
