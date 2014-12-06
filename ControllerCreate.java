package Controller;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 12/5/2014
 *
 * This class represents a Controller with create methods.
 *
 * Purpose: Gives the ability to add new Puzzles and monsters into the database
 */
public class ControllerCreate
{
    //instance variables
    private static Controller tdb = new Controller();
    private static boolean duplicateKey = true;
    private static int cpKey = 1;
    private static int cmKey = 1;

    /**Creates a puzzle to go in the database
     * @param puzzle The puzzle description
     * @param answer The puzzle solution
     * @param successMessage The puzzle success message
     * @param failureMessage The puzzle failure message
     * @param isSolved The indication if the puzzle was won. 0 for false, 1 for true
     * @return true Confirms the puzzle has been created successfully
     */
    public static boolean createPuzzle(String puzzle, String answer, String successMessage, String failureMessage, int isSolved)
    {
        while (duplicateKey)
        {
            //SQL statement
            int err = tdb.modData("Insert into puzzle(puzzleID, puzzle, answer, successMessage, failureMessage, isSolved) " +
                    "values (" + cpKey + ", \'" + puzzle + "\',\'" + answer + "\',\'" + successMessage + "\',\'" + failureMessage + "\'," + isSolved + ")");

            if (err == 0)
            {
                cpKey++;
            }
            else
            {
                duplicateKey = false;
            }
        }
        return true;
    }

    /**creates a new monster for the database
     * @param monsterID The monster's database primary key
     * @param name The monster's name
     * @param attackPower The monster's attack power
     * @param health The monster's health
     * @return true Confirms the monster has been created
     */
    public static boolean createMonster(int monsterID, String name, int attackPower, int health)
    {
        while (duplicateKey)
        {
            int err = tdb.modData("Insert into monster(monsterID, name, attackPower, health) " +
                    "values (" + cmKey + ", \'" + name + "\'," + attackPower + "," + health + ")");

            if (err == 0)
            {
                cmKey++;
            }
            else
            {
                duplicateKey = false;
            }
        }
        return true;
    }
}
