package maze.solvers;

import maze.model.Maze;
import maze.model.Spot;

import java.util.Queue;

/**
 * Purpose:
 * Assumptions:
 * Dependencies:
 * Example:
 * Other details:
 *
 * @Author Evan Kenyon
 */
public abstract class QueueSearchAlgorithm extends SearchAlgorithm{

    /**
     * Purpose:

     * @param description
     * @param maze
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
            ((Queue<Spot>) getMyFrontier()).remove();
            decrementCurrMyFrontierSize();
        }
        currSteps++;
    }

    protected void updateCurrentSpot() {
        currSpot = ((Queue<Spot>) getMyFrontier()).peek();
    }
}
