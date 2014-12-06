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
 * This class represents an Controller JUnit Test.
 *
 * Purpose: Tests all the methods in the Controller class.
 */
public class ControllerTest
{
    private static Controller tdb = new Controller();
    private static int playerID;

    @Test
    public void testCreateAccount() throws Exception
    {
        assertTrue(Controller.createAccount("createTestName") > 0);
    }

    @Test
    public void testLoginAccount() throws Exception
    {
        assertTrue(Controller.loginAccount("createTestName"));

        assertFalse(Controller.loginAccount(null));
        assertFalse(Controller.loginAccount(""));
        assertFalse(Controller.loginAccount(" "));
    }

    @Test
    public void testQuery() throws Exception
    {
        System.out.println("The Query results for createTestName: ");
        System.out.println("--------------------------- ");

        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query("Select * from playerFile WHERE name = \'createTestName\'");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                playerID = rs.getInt("playerID");
                System.out.println("player ID: " + rs.getInt("playerID"));
                System.out.println("player name: " + rs.getString("name"));
                System.out.println("player hasInventory: " + rs.getInt("hasInventory"));
                System.out.println("player score: " + rs.getInt("score"));
                System.out.println("player Health: " + rs.getInt("health"));
                System.out.println();
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
    }

    @Test
    public void testModData() throws Exception
    {
        System.out.println("\nQuery results after modifying createTestName: ");
        System.out.println("------------------------------------------------");

        tdb.modData("UPDATE playerFile SET hasInventory = 6, score = 81, health = 22 " +
                "WHERE playerID = " + playerID);
        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query("Select * from playerFile WHERE name = \'createTestName\'");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                System.out.println("player ID: " + rs.getString("playerID"));
                System.out.println("player name: " + rs.getString("name"));
                System.out.println("player hasInventory: " + rs.getString("hasInventory"));
                System.out.println("player score: " + rs.getString("score"));
                System.out.println("player Health: " + rs.getString("health"));
                System.out.println();
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
    }

    @AfterClass
    public static void tearDown() throws Exception
    {
        tdb.modData("DELETE FROM playerFile WHERE name = \'createTestName\'");
    }
}