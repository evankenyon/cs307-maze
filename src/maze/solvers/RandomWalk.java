package maze.solvers;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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


	public RandomWalk (Maze maze) {
		super(TITLE, maze);
		myFrontier = new Stack<>();
		((Stack<Spot>) myFrontier).push(currSpot);
		incrementCurrMyFrontierSize();
	}

	/**
	 * @see SearchAlgorithm#step()
	 */
	@Override
	public boolean step () {
		List<Spot> neighbors = myMaze.getNeighbors(currSpot);
		List<Spot> empties = getEmptySpots(neighbors);
		List<Spot> possibles = getPossibleSpots(neighbors);
		Spot next = getNextSpot(empties, possibles);
		next.markAsPath();
		return updateCurrentSpot(next);
	}

	private Spot getNextSpot(List<Spot> empties, List<Spot> possibles) {
		Spot next;
		// prefer exploring empty paths over visited ones
		if (!empties.isEmpty() && Randomness.isRandomEnough(EXPLORE_BIAS)) {
			next = Randomness.getRandomElement(empties);
		} else {
			// guaranteed to be at least one possible, even if it is last spot visited
			next = Randomness.getRandomElement(possibles);
		}
		return next;
	}

	private List<Spot> getEmptySpots(List<Spot> neighbors) {
		List<Spot> emptySpots = new ArrayList<>();
		for(Spot spot: neighbors) {
			if(spot.getState() == Spot.EMPTY) {
				emptySpots.add(spot);
			}
		}
		return emptySpots;
	}

	private List<Spot> getPossibleSpots(List<Spot> neighbors) {
		List<Spot> possibleSpots = new ArrayList<>();
		for(Spot spot: neighbors) {
			if(spot.getState() != Spot.WALL) {
				possibleSpots.add(spot);
			}
		}
		return possibleSpots;
	}
	private boolean updateCurrentSpot(Spot next) {
		currSpot.markAsVisited();
		currSpot = next;
		((Stack<Spot>) myFrontier).pop();
		((Stack<Spot>) myFrontier).push(currSpot);
		return isSearchOver();
	}

}
