package Model; /**Class: Puzzle
 *
@author Everton Gardiner Jr.	
 * @version 1.0
 * Course : ITEC 3860 
 * Written: Nov 14, 2014
 *
 *
 *
 * This class illustrates how to create an Puzzle object
 *
 * Purpose: To create a Puzzle object
 */

/**
 * @author egardiner
 *
 */
public class Puzzle
{
    private String puzzle;
    private String solution;
    private String successMessage;
	private String failureMessage;
    private boolean wonGame;

	/**
	 * Method: Constructor for Puzzle class
	 * Constructor for Puzzle class that contains a String, boolean, String
	 * String and String as arguments
	 */
	public Puzzle(String puzzle, String solution, String successMessage, String failureMessage, boolean wonGame)
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
	 * Method: isWonGame
	 * This method returns true or false if the Puzzle object has been solved
	 * @return The current boolean of the Puzzle object
	 */
	public boolean isWonGame()
	{
		return wonGame;
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

    /**
	 * Method: setSolution
	 * This method changes the solution/answer of the Puzzle object
	 * @param solution new solution/answer of the Puzzle object
	 */
	public void setSolution(String solution)
	{
		this.solution = solution;
	}

    /**
	 * Method: setWonGame
	 * This method changes the boolean value of the won game value of the Puzzle object
	 * @param wonGame new won game boolean value of the Puzzle object
	 */
	public void setWonGame(boolean wonGame)
	{
		this.wonGame = wonGame;
	}

    /**
	 * Method: setPuzzle
	 * This method changes the puzzle of the Puzzle object
	 * @param puzzle new puzzle of the Puzzle object
	 */
	public void setPuzzle(String puzzle)
	{
		this.puzzle = puzzle;
	}

    /**
	 * Method: setFailureMessage
	 * This method changes the failure message of the Puzzle object
	 * @param failureMessage new failure message of the Puzzle object
	 */
	public void setFailureMessage(String failureMessage)
	{
		this.failureMessage = failureMessage;
	}

    /**
	 * Method: setSuccessMessage
	 * This method changes the current success message of the Puzzle object
	 * @param successMessage new success message of the Puzzle object
	 */
	public void setSuccessMessage(String successMessage)
	{
		this.successMessage = successMessage;
	}
}
