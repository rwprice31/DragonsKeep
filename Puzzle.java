package Model;

/**
 * @author Everton Gardiner Jr.
 * @version 1.0
 * Course : ITEC 3860 
 * Written: Nov 14, 2014
 *
 * This class illustrates how to create an Puzzle object
 *
 * Purpose: To create a Puzzle object
 */
public class Puzzle
{
    //instance variables
    private String puzzle;
    private String solution;
    private String successMessage;
	private String failureMessage;
    private int wonGame;

    /**Five arg constructor
     * @param puzzle The puzzle description
     * @param solution The puzzle solution
     * @param successMessage The puzzle success message
     * @param failureMessage The puzzle failure message
     * @param wonGame The indication if the puzzle was won. 0 for false, 1 for true
     */
	public Puzzle(String puzzle, String solution, String successMessage, String failureMessage, int wonGame)
	{
        this.puzzle = puzzle;
        this.solution = solution;
        this.successMessage = successMessage;
		this.failureMessage = failureMessage;
        this.wonGame = wonGame;
    }

	/**
	 * Method: getSolution
	 * This method gets the solution of the Puzzle object
	 * @return The current solution of the Puzzle object
	 */
	public String getSolution()
	{
		return solution;
	}

    /**
	 * Method: getPuzzle
	 * This method returns the current puzzle of the Puzzle object
	 * @return The current puzzle of the Puzzle object
	 */
	public String getPuzzle()
	{
		return puzzle;
	}

    /**
	 * Method: getFailureMessage
	 * This method returns the current failure message of Puzzle object
	 * @return The current failure message of the Puzzle object
	 */
	public String getFailureMessage()
	{
		return failureMessage;
	}

    /**
	 * Method: getSuccessMessage
	 * This method returns the current success message of the Puzzle object
	 * @return The current success message of the Puzzle object
	 */
	public String getSuccessMessage()
	{
		return successMessage;
	}
}
