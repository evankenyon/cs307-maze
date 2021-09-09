package maze.view;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import maze.model.Maze;
import maze.solvers.BFS;
import maze.solvers.DFS;
import maze.solvers.Greedy;
import maze.solvers.Magic;
import maze.solvers.RandomWalk;
import maze.solvers.SearchAlgorithm;


/**
 * This class displays a maze that can be solved using multiple algorithms.
 *
 * @author Robert C. Duvall
 * @author Shannon Pollard
 */
public class MazeDisplay {
	// Display constants
	public final static String NO_SOLVER_TITLE = "No Solver Selected";
	public final int TITLE_OFFSET = 40;
	public final int BORDER_OFFSET = 20;
	// speed of animation
	public final double SECONDS_DELAY = 0.01;
	// size of each maze space in pixels
	public final int BLOCK_SIZE = 12;
	// color of each of the states
	public final List<Color> STATE_COLORS = List.of(
			Color.DARKRED,		    // wall color
			Color.BLUE,	            // path color
			Color.WHITE,			// empty cell color
			Color.LIGHTBLUE	        // visited cell color
	);

	// Display settings
	private Label myTitle;
	// rectangles that represent the maze's state graphically in colors
	private List<List<Rectangle>> myDisplayMaze;
	// animate searching for maze path
	private Timeline myAnimation;
	private boolean isPaused;
	// Maze settings
	private final Maze myMaze;
	private SearchAlgorithm mySolver;


	/**
	 * Create display based on given maze.
	 */
	public MazeDisplay(Maze maze) {
		myMaze = maze;
	}

	/**
	 * Create simulation's "scene": what shapes will be in the display the maze and their starting properties
	 */
	public Scene setupDisplay (Paint background) {
		VBox root = new VBox();
		root.setSpacing(20);
		root.setPadding(new Insets(10, 10, 10, 10));
		root.getChildren().addAll(makeAlgorithmPanel(), makeMazeDisplay(), makeControlPanel());

		Scene scene = new Scene(root, background);
		scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return scene;
	}

	// Organize maze squares with title
	private Node makeMazeDisplay () {
		VBox panel = new VBox();
		panel.setAlignment(Pos.CENTER);
		panel.setSpacing(10);

		Group group = new Group();
		myDisplayMaze = setupMaze(group);
		myTitle = makeTitle(NO_SOLVER_TITLE);

		panel.getChildren().addAll(myTitle, group);
		return panel;
	}

	// Organize buttons to choose which search algorithm to run
	private Node makeAlgorithmPanel () {
		HBox panel = new HBox();
		panel.setSpacing(5);

		Button dfs = new Button("Depth-First Search");
		dfs.setOnAction(value ->  setSearch(new DFS(myMaze)));
		panel.getChildren().add(dfs);

		return panel;
	}

	// Organize UI elements to control how the maze and search animation perform
	private Node makeControlPanel () {
		HBox panel = new HBox();
		panel.setSpacing(10);

		Button newMazeButton = new Button("New Maze");
		newMazeButton.setOnAction(value ->  newMaze());
		panel.getChildren().add(newMazeButton);

		return panel;
	}

	// Set up title for the maze
	private Label makeTitle (String text) {
		Label label = new Label(text);
		label.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		return label;
	}

	// Take action based on user's input
	private void handleKeyInput (KeyCode code) {
		// NEW Java 14 syntax that some prefer (but watch out for the many special cases!)
		//   https://blog.jetbrains.com/idea/2019/02/java-12-and-intellij-idea/
		switch (code) {
			case N -> newMaze();
			case S -> step();
			case SPACE -> togglePause();
			case D -> setSearch(new DFS(myMaze));
			case B -> setSearch(new BFS(myMaze));
			case G -> setSearch(new Greedy(myMaze));
			case R -> setSearch(new RandomWalk(myMaze));
			case M -> setSearch(new Magic(myMaze));
		}
	}

	// Set up maze display, one rectangle for each of the maze's space that is colored based on its state
	private List<List<Rectangle>> setupMaze (Group parent) {
		List<List<Rectangle>> displayMaze = new ArrayList<>();
		for (int r = 0; r < myMaze.getNumRows(); r+=1) {
			displayMaze.add(new ArrayList<>());
			for (int c = 0; c < myMaze.getNumColumns(); c+=1) {
				Rectangle rect = new Rectangle(c*BLOCK_SIZE+BORDER_OFFSET, r*BLOCK_SIZE+TITLE_OFFSET, BLOCK_SIZE, BLOCK_SIZE);
				rect.setFill(STATE_COLORS.get(myMaze.getSpot(r, c).getState()));
				parent.getChildren().add(rect);
				displayMaze.get(r).add(rect);
			}
		}
		return displayMaze;
	}

	// Take one step with current search algorithm
	private void step () {
		if (mySolver != null) {
			if (mySolver.step()) {
				myAnimation.stop();
			}
			redraw();
		}
	}

	// Start or stop searching animation as appropriate
	private void togglePause() {
		if (isPaused) {
			myAnimation.play();
		}
		else {
			myAnimation.pause();
		}
		isPaused = ! isPaused;
	}

	// Make new random maze to solve
	private void newMaze() {
		myMaze.resetMaze();
		myMaze.createMaze();
		myTitle.setText(NO_SOLVER_TITLE);
		mySolver = null;
		redraw();
	}

	// Start given search algorithm for this maze
	private void setSearch (SearchAlgorithm solver) {
		mySolver = solver;
		myTitle.setText(mySolver.toString());
		myMaze.resetMaze();
		redraw();
		startAnimation();
	}

	// Start new animation to show search algorithm's steps
	private void startAnimation () {
		if (myAnimation != null) {
			myAnimation.stop();
		}
		myAnimation = new Timeline();
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECONDS_DELAY), e -> step()));
		myAnimation.play();
		isPaused = false;
	}

	// Resets rectangle colors according to their current state in the maze, making it match the model maze
	private void redraw () {
		for (int r = 0; r < myMaze.getNumRows(); r+=1) {
			for (int c = 0; c < myMaze.getNumColumns(); c+=1) {
				myDisplayMaze.get(r).get(c).setFill(STATE_COLORS.get(myMaze.getSpot(r, c).getState()));
			}
		}
	}
}
