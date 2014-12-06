package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 12/5/2014
 *
 * This class represents a Controller with load methods.
 *
 * Purpose: Gives the ability to load saves, Items, Actors, Puzzles, and rooms from the database
 */
public class ControllerLoad
{
    //instance variables
    private static ResultSet rs = null;
    private static Controller tdb = new Controller();

    /**Retrieves the state of the saved rooms
     * @param playerID The player's database ID
     * @return savedRoomBuilder The saved rooms and its attributes
     */
    public static String loadSavedRooms(int playerID)
    {
        //creates a stringBuilder for the savedRooms
        StringBuilder savedRoomBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from playerRooms WHERE playerID = " + playerID);
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                savedRoomBuilder.append(rs.getInt("playerID"));
                savedRoomBuilder.append("|");
                savedRoomBuilder.append(rs.getInt("currentRoom"));

                //add saved rooms 1 - 50
                for (int x = 1; x <= 50; x++)
                {
                    savedRoomBuilder.append("|");
                    savedRoomBuilder.append(rs.getInt("room" + x));
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return savedRoomBuilder.toString();
    }

    /**
     * @return roomBuilder All the rooms and its attributes
     */
    public static String retrieveAllRooms()
    {
        //creates a stringBuilder for the rooms
        StringBuilder roomBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from room");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                roomBuilder.append(rs.getInt("RoomID"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("roomE"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("roomN"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("roomS"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("roomW"));
                roomBuilder.append("|");

                //formats the description with new lines
                String[] temp = rs.getString("description").split("[+]");
                String str = temp[0];
                for (int u = 1; u < temp.length; u++)
                {
                    str += "\n" + temp[u];
                }
                roomBuilder.append(str);

                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("isEmpty"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("Armor"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("Elixir"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("Weapon"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("Monster"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("Puzzle"));
                roomBuilder.append("|");
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        roomBuilder.deleteCharAt(roomBuilder.lastIndexOf("|"));
        return roomBuilder.toString();
    }

    /**Retrieves a player's attributes
     * @param playerName The player's name
     * @return heroBuilder The hero and its attributes
     */
    public static String loadHero(String playerName)
    {
        //creates a stringBuilder for the hero
        StringBuilder heroBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from playerFile");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (playerName.equalsIgnoreCase(rs.getString("name")))
                {
                    heroBuilder.append(rs.getInt("playerID"));
                    heroBuilder.append("|");
                    heroBuilder.append(rs.getString("name"));
                    heroBuilder.append("|");
                    heroBuilder.append(rs.getInt("hasInventory"));
                    heroBuilder.append("|");
                    heroBuilder.append(rs.getInt("score"));
                    heroBuilder.append("|");
                    heroBuilder.append(rs.getInt("health"));
                    heroBuilder.append("|");
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return heroBuilder.toString();
    }

    /**Retrieves a player's inventory
     * @param playerID The player's database ID
     * @return heroInventory The inventory and all its attributes
     */
    public static String loadHeroInventory(int playerID)
    {
        //creates a stringBuilder for the inventory
        StringBuilder heroInventory = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from savedInventory where playerID = " + playerID);
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                heroInventory.append(rs.getInt("weaponID"));
                heroInventory.append("|");
                heroInventory.append(rs.getInt("armorID"));
                heroInventory.append("|");
                heroInventory.append(rs.getInt("elixirID"));
                heroInventory.append("|");
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return heroInventory.toString();
    }

    /**retrieves a particular monster
     * @param monsterIndex the monster database ID
     * @return monsterBuilder The monster and all its attributes
     */
    public static String retrieveMonster(int monsterIndex)
    {
        //creates a stringBuilder for the monster attributes
        StringBuilder monsterBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from monster");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (rs.getInt("monsterID") == monsterIndex)
                {
                    monsterBuilder.append(rs.getString("name"));
                    monsterBuilder.append("|");
                    monsterBuilder.append(rs.getInt("AttackPower"));
                    monsterBuilder.append("|");
                    monsterBuilder.append(rs.getInt("health"));
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return monsterBuilder.toString();
    }

    /**retrieves a particular armor
     * @param armorIndex the armor database ID
     * @return armorBuilder The armor and all its attributes
     */
    public static String retrieveArmor(int armorIndex)
    {
        //creates a stringBuilder for the armor attributes
        StringBuilder armorBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from armor");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (rs.getInt("armorID") == armorIndex)
                {
                    armorBuilder.append(rs.getString("name"));
                    armorBuilder.append("|");
                    armorBuilder.append(rs.getInt("armorDefense"));
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return armorBuilder.toString();
    }

    /**retrieves a particular elixir
     * @param elixirIndex the elixir database ID
     * @return elixirBuilder The elixir and all its attributes
     */
    public static String retrieveElixir(int elixirIndex)
    {
        //creates a stringBuilder for the elixir attributes
        StringBuilder elixirBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from elixir");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (rs.getInt("elixirID") == elixirIndex)
                {
                    elixirBuilder.append(rs.getString("name"));
                    elixirBuilder.append("|");
                    elixirBuilder.append(rs.getInt("healthBoost"));
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return elixirBuilder.toString();
    }

    /**retrieves a particular weapon
     * @param weaponIndex the weapon database ID
     * @return weaponBuilder The weapon and all its attributes
     */
    public static String retrieveWeapon(int weaponIndex)
    {
        //creates a stringBuilder for the weapon attributes
        StringBuilder weaponBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from weapon");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (rs.getInt("weaponID") == weaponIndex)
                {
                    weaponBuilder.append(rs.getString("name"));
                    weaponBuilder.append("|");
                    weaponBuilder.append(rs.getInt("strength"));
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return weaponBuilder.toString();
    }

    /**retrieves a particular puzzle
     * @param puzzleIndex the puzzle database ID
     * @return puzzleBuilder The puzzle and all its attributes
     */
    public static String retrievePuzzle(int puzzleIndex)
    {
        //creates a stringBuilder for the puzzle attributes
        StringBuilder puzzleBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from puzzle");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (rs.getInt("puzzleID") == puzzleIndex)
                {
                    //formats the problem description with new lines
                    String[] temp = rs.getString("problem").split("[+]");
                    String str = temp[0];
                    for (int u = 1; u < temp.length; u++)
                    {
                        str += "\n" + temp[u];
                    }
                    puzzleBuilder.append(str);

                    puzzleBuilder.append("|");
                    puzzleBuilder.append(rs.getString("solution"));
                    puzzleBuilder.append("|");
                    puzzleBuilder.append(rs.getString("successMessage"));
                    puzzleBuilder.append("|");
                    puzzleBuilder.append(rs.getString("failureMessage"));
                    puzzleBuilder.append("|");
                    puzzleBuilder.append(rs.getInt("isSolved"));
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return puzzleBuilder.toString();
    }
}
