package Controller;

import org.junit.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3860 Fall 2014
 * Written: 12/5/2014
 *
 * This class represents a ControllerUpdate JUnit Test.
 *
 * Purpose: Tests all the methods in the ControllerUpdate class.
 */
public class ControllerUpdateTest
{
    static Controller tdb = new Controller();
    static int playerID;

    @BeforeClass
    public static void setup()
    {
        assertTrue(Controller.createAccount("JUnitTestName") > 0);

        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query("Select * from playerFile WHERE name = \'JUnitTestName\'");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                playerID = rs.getInt("playerID");
                System.out.println(playerID);
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
    }

    @Test
    public void testSaveHeroData() throws Exception
    {
        assertTrue(ControllerUpdate.saveHeroData(playerID + "|JUnitTestName|1|93|12"));
    }

    @Test
    public void testSaveRoomState() throws Exception
    {
        String rooms = playerID + "|" + 14;
        for (int i = 1; i <= 50;  i++)
        {
            rooms += "|" + (i%2);
        }
        assertTrue(ControllerUpdate.saveRoomState(rooms));
    }

    @Test
    public void testSaveHeroInventory() throws Exception
    {
        String[][] tempInventory = new String[3][2];
        tempInventory[0][0] = "dagger";
        tempInventory[0][1] = "w";
        tempInventory[1][0] = "Standard Armor";
        tempInventory[1][1] = "a";
        tempInventory[2][0] = "Blue Elixir";
        tempInventory[2][1] = "e";

        assertTrue(ControllerUpdate.saveHeroInventory(playerID, tempInventory));
    }

    @AfterClass
    public static void tearDown() throws Exception
    {
        tdb.modData("DELETE FROM playerFile WHERE name = \'JUnitTestName\'");
        tdb.modData("DELETE FROM playerRooms WHERE playerID = " + playerID);
        tdb.modData("DELETE FROM savedInventory WHERE playerID = " + playerID);

    }
}