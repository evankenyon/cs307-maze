package maze.solvers;

import java.util.ArrayList;
import java.util.List;
import maze.model.Maze;
import maze.model.Spot;
import maze.util.Randomness;


/**
 * This class represents a random maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class RandomWalk extends SearchAlgorithm {
	public static final String TITLE = "Random Walk";
	public final double EXPLORE_BIAS = 0.999;

	private Maze myMaze;
	// search frontier - current spot being explored
	private Spot myCurrent;


	public RandomWalk (Maze maze) {
		super(TITLE);
		myMaze = maze;
		myCurrent = maze.getStart();
		myCurrent.markAsPath();
	}

	/**
	 * @see SearchAlgorithm#step()
	 */
	@Override
	public boolean step () {
		// find possible next steps
		List<Spot> neighbors = myMaze.getNeighbors(myCurrent);
		// choose next spot to explore
		Spot next = null;
		List<Spot> empties = new ArrayList<>();
		List<Spot> possibles = new ArrayList<>();
		for (Spot spot : neighbors) {
			if (spot.getState() == Spot.EMPTY) {
				empties.add(spot);
			}
			if (spot.getState() != Spot.WALL) {
				possibles.add(spot);
			}
		}
		// prefer exploring empty paths over visited ones
		if (! empties.isEmpty() && Randomness.isRandomEnough(EXPLORE_BIAS)) {
			next = Randomness.getRandomElement(empties);
		}
		// guaranteed to be at least one possible, even if it is last spot visited
		next = Randomness.getRandomElement(possibles);
		// mark next step
		next.markAsPath();
		// update current spot
		myCurrent.markAsVisited();
		myCurrent = next;
		return isSearchOver();
	}


	// Search is over and unsuccessful if there are no more fringe points to consider.
	// Search is over and successful if the current point is the same as the goal.
	private boolean isSearchOver () {
		return myCurrent != null && myCurrent.equals(myMaze.getGoal());
	}
}
