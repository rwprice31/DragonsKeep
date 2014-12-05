package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 12/5/2014
 *
 * This class represents a Controller with save methods
 *
 * Purpose: Gives the ability to save the player's inventory, stats, and rooms into the database
 */
public class ControllerSave
{
    private static Controller tdb = new Controller();
    private static int shiKey = 1;


    /**save the Hero ID, name, hasInventory, score, and health to the database
     * @param heroAttributes The attributes of the player
     * @return true Indicates Hero data was successfully saved
     */
    public static Boolean saveHeroData(String heroAttributes)
    {
        String[] savedHero = heroAttributes.split("[|]");

        //create the sql statement using hero attributes
        int err = tdb.modData("UPDATE playerFile SET hasInventory = " + savedHero[2] + ", score = " +
                savedHero[3] + ", health = " + savedHero[4] + " WHERE playerID = " + savedHero[0] + "");

        //if no rows were changed
        if (err == 0)
        {
            System.err.println("There was an error in updating saved Hero Data to playerFile");
            return false;
        }
        else
        {
            System.out.println("Successfully saved Hero profile to the database.");
        }
        return true;
    }

    /**save the state of the rooms to the savedRooms table
     * @param roomsState The empty or not empty state of all the rooms
     * @return true Indicates the rooms were successfully saved in the database
     */
    public static Boolean saveRoomState(String roomsState)
    {
        String[] rooms = roomsState.split("[|]");

        //inserts room 1-50
        String playerRoomsInsert = "Insert into playerRooms(playerID, currentRoom";
        for (int i = 1; i <= 50; i++)
        {
            playerRoomsInsert += ", room" + i;
        }
        playerRoomsInsert += ") values (" + rooms[0] + ", " + rooms[1];

        //inserts data for room 1-50
        for (int i = 1; i <= 50; i++)
        {
            playerRoomsInsert += ", " + rooms[i+1];
        }
        playerRoomsInsert += ")";
        int err = tdb.modData(playerRoomsInsert);


        //if no rows are updated
        if (err == 0)
        {
            playerRoomsInsert = "UPDATE playerRooms SET currentRoom = " + rooms[1] + ", room1  = " + rooms[2];
            for (int i = 2; i <= 50; i++)
            {
                playerRoomsInsert += ", room" + i + " = " + rooms[i+1];
            }
            playerRoomsInsert += " WHERE playerID = " + rooms[0];
            tdb.modData(playerRoomsInsert);
            System.out.println("Successfully saved the rooms to the database.");
        }
        else
        {
            System.out.println("Successfully saved the rooms to the database.");
        }
        return true;
    }

    /**Saves the hero's inventory to the inventory table
     * @param playerID The player's ID/primary key
     * @param playerInventory The player's inventory
     * @return true Indicates the success of saving the Hero's inventory
     */
    public static Boolean saveHeroInventory(int playerID, String[][] playerInventory)
    {
        boolean looping;

        //if the playerID already exist, delete it
        ResultSet rs = null;
        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from savedInventory");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (rs.getString("playerID").equalsIgnoreCase("" + playerID))
                {
                    int err = tdb.modData("DELETE FROM savedInventory WHERE playerID = " + playerID);
                    break;
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }

        //loops through the inventory and adds each item to the database one at a time
        for (int x = 0; x < playerInventory.length; x++)
        {
            String heroItems = ", " + playerID;

            //checks that the player inventory index is not empty
            if (playerInventory[x][1] != null)
                if (playerInventory[x][1].equalsIgnoreCase("w"))  //checks the itemType in the inventory
                {
                    try
                    {
                        //Query the database. Returns the results in a ResultSet
                        rs = tdb.query("Select * from weapon");
                        //Loop over the result set. next moves the cursor to the next record and returns the current record
                        while(rs.next())
                        {
                            if (rs.getString("name").equalsIgnoreCase(playerInventory[x][0]))
                            {
                                heroItems += ", " + rs.getInt("weaponID");
                                heroItems += ", " + 0;
                                heroItems += ", " + 0;
                                break;
                            }
                        }
                        rs.close();
                    }
                    catch(SQLException sqe)
                    {
                        System.out.println(sqe.getMessage());
                    }
                }
                else if (playerInventory[x][1].equalsIgnoreCase("a"))  //checks the itemType in the inventory
                {
                    try
                    {
                        //Query the database. Returns the results in a ResultSet
                        rs = tdb.query("Select * from armor");
                        //Loop over the result set. next moves the cursor to the next record and returns the current record
                        while(rs.next())
                        {
                            if (rs.getString("name").equalsIgnoreCase(playerInventory[x][0]))
                            {
                                heroItems += ", " + 0;
                                heroItems += ", " + rs.getInt("armorID");
                                heroItems += ", " + 0;
                                break;
                            }
                        }
                        rs.close();
                    }
                    catch(SQLException sqe)
                    {
                        System.out.println(sqe.getMessage());
                    }
                }
                else if (playerInventory[x][1].equalsIgnoreCase("e"))  //checks the itemType in the inventory
                {
                    try
                    {
                        //Query the database. Returns the results in a ResultSet
                        rs = tdb.query("Select * from elixir");
                        //Loop over the result set. next moves the cursor to the next record and returns the current record
                        while(rs.next())
                        {
                            if (rs.getString("name").equalsIgnoreCase(playerInventory[x][0]))
                            {
                                heroItems += ", " + 0;
                                heroItems += ", " + 0;
                                heroItems += ", " + rs.getInt("elixirID");
                                break;
                            }
                        }
                        rs.close();
                    }
                    catch(SQLException sqe)
                    {
                        System.out.println(sqe.getMessage());
                    }
                }
                else
                    continue;

            //takes the sql statement created and inserts it into the database
            if (playerInventory[x][1] != null)
                do
                {
                    int err = tdb.modData("Insert into savedInventory(inventoryID, playerID, weaponID, armorID, elixirID) " +
                            "values (" + shiKey + heroItems + ")");

                    //if no rows were changed
                    if (err == 0)
                    {
                        shiKey++;
                        looping = true;
                    }
                    else
                    {
                        System.out.println("Successfully saved Hero inventory to the database.");
                        looping = false;
                    }
                }while(looping);
        }
        return true;
    }
}
