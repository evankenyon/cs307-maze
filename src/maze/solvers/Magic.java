package maze.solvers;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import maze.model.Maze;
import maze.model.Spot;
import maze.util.Randomness;


/**
 * Purpose (comment borrowed from Prof. Duvall): This class represents a Magic maze search algorithm.
 * Assumptions:
 * Dependencies:
 * Example:
 * Other details:
 *
 * @author Evan Kenyon
 */
public class Magic extends QueueSearchAlgorithm {
	public static final String TITLE = "Magic";

	private List<Spot> visitedSpots;

	/**
	 * Purpose:
	 * Assumptions:
	 * @param maze
	 */
	public Magic (Maze maze) {
		super(TITLE, maze);
		myFrontier = new PriorityQueue<>();
		((PriorityQueue<Spot>) myFrontier).add(currSpot);
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
