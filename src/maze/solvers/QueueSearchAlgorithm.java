package maze.solvers;

import maze.model.Maze;
import maze.model.Spot;

import java.util.Queue;


/**
 * Purpose: This class represents the abstraction of a maze search algorithm
 * whose primary data structure, myFrontier, is a queue of some kind
 * Assumptions: This class itself is not instantiated
 * Dependencies: Maze, Spot, Queue, solvers
 * Example: Create a class that extends this abstract class, and use the provided
 * methods, as well as the methods that SearchAlgorithm provides, to implement a
 * queue based algorithm to solve a Maze object, which is displayed with MazeDisplay
 *
 * @Author Evan Kenyon
 */
public abstract class QueueSearchAlgorithm extends SearchAlgorithm{
    protected Queue<Spot> myFrontier;

    /**
     * Purpose: Construct a maze solving algorithm whose primary data structure,
     * myFrontier, is a queue of some kind
     * @param description the name of this algorithm
     * @param maze the maze that represents the maze that is displayed
     *             (i.e. it contains the underlying logic)
     */
    public QueueSearchAlgorithm(String description, Maze maze) {
        super(description, maze);
    }

    
    protected boolean colorSuccessfulPathFound() {
        if(isSearchOver()) {
            markPath();
            return true;
        }
        return false;
    }

    protected void markNextStep(Spot next) {
        if (next != null) {
            next.markAsPath();
            myFrontier.add(next);
            incrementCurrMyFrontierSize();
            myPaths.put(next, currSpot);
            reachedEnd = false;
        }
        else {
            incrementNumBacktracks();
            currSpot.markAsVisited();
            myFrontier.remove();
            decrementCurrMyFrontierSize();
        }
        currSteps++;
    }

    protected void updateCurrentSpot() {
        currSpot = myFrontier.peek();
    }

    @Override
    protected boolean isSearchUnsuccessful() {
        return myFrontier.isEmpty();
    }
}
