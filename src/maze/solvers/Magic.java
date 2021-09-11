package maze.solvers;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import maze.model.Maze;
import maze.model.Spot;
import maze.util.Randomness;


/**
 * This class represents a Magic maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class Magic extends QueueSearchAlgorithm {
	public static final String TITLE = "Magic";

	private List<Spot> visitedSpots;

	// data structure used to keep search frontier -- use a priority queue
	public Magic (Maze maze) {
		super(TITLE, maze);
		myFrontier = new PriorityQueue<>();
		((PriorityQueue<Spot>) myFrontier).add(myCurrent);
		visitedSpots = new ArrayList<Spot>();
		visitedSpots.add(myCurrent);
	}

	/**
	 * @see SearchAlgorithm#step()
	 */
	@Override
	public boolean step () {
		// color successful path found
		if (colorSuccessfulPathFound())  {
			return true;
		}
		// find possible next steps
		List<Spot> neighbors = myMaze.getNeighbors(myCurrent);
		// choose next spot to explore -- magic means next spot could be a wall!
		Spot next = chooseNextSpot(neighbors);
		markNextStep(next);
		return updateCurrentSpot();
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
