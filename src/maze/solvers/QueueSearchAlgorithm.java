package maze.solvers;

import maze.model.Maze;
import maze.model.Spot;

import java.util.Queue;

public abstract class QueueSearchAlgorithm extends SearchAlgorithm{
    /**
     * Create an algorithm with its name.
     *
     * @param description
     * @param maze
     */
    public QueueSearchAlgorithm(String description, Maze maze) {
        super(description, maze);
    }

    protected boolean colorSuccessfulPathFound() {
        if (isSearchOver()) {
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
        }
        else {
            currSpot.markAsVisited();
            ((Queue<Spot>) getMyFrontier()).remove();
            decrementCurrMyFrontierSize();
        }
    }

    protected boolean updateCurrentSpot() {
        currSpot = ((Queue<Spot>) getMyFrontier()).peek();
        return false;
    }
}
