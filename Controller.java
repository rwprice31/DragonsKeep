package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3860 Fall 2014
 * Written: 11/16/2014
 *
 * This class represents a ...
 *
 * Purpose: Allows the manipulation of a ...
 */
public class Controller
{
    private static Statement stmt;
    private static ResultSet rs = null;
    private static Controller tdb = new Controller();
    private static boolean duplicateKey = true;
    private static int key = 1;

    private Controller()
    {
        //register the driver name
        String sDriver = "org.sqlite.JDBC";
        try
        {
            Class.forName(sDriver);
        }
        catch(ClassNotFoundException cfne)
        {
            System.out.println(cfne.getMessage());
        }

        //Build the URL for SQLite DB
        String database = "DragonsKeep.db";
        String jdbc = "jdbc:sqlite";
        String dbURL = jdbc + ":" + database;

        //Set db timeout
        int timeOut = 30;

        try
        {
            //Establish a connection to the database
            Connection conn = DriverManager.getConnection(dbURL);
            //Create a container for the SQL statement
            stmt = conn.createStatement();
            //set timeout on the statement
            stmt.setQueryTimeout(timeOut);
        }
        catch (SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
    }
    
    //verifies the user account
    public static boolean loginAccount(String playerName)
    {
        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query(tdb, "Select name from playerFile");

            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (playerName.equalsIgnoreCase(rs.getString("name")))
                {
                    return true;
                }
            }
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return false;
    }

    //creates a user profile
    public static boolean createAccount(String name)
    {
//DEBUG PURPOSE -----------------------------------------------------
//Running this code successfully separate from the rest of the createAccount() code confirms a connection to the database
//        try
//        {
//            //Query the database. Returns the results in a ResultSet
//            ResultSet rs = tdb.query(tdb, "Select * from monster");
//            //Loop over the result set. next moves the cursor to the next record and returns the current record
//            while(rs.next())
//            {
//                System.out.println("The monster ID is " + rs.getInt("monsterID"));
//                System.out.println("The monster name is " + rs.getString("name"));
//                System.out.println("The monster attackPower " + rs.getInt("attackPower"));
//                System.out.println("the monster health " + rs.getInt("health") + "\n");
//            }
//        }
//        catch(SQLException sqe)
//        {
//            System.out.println(sqe.getMessage());
//        }
//      ---------------------------------------------------------
        //while (duplicateKey)
        //{
        //BEFORE inserting:: compare user requested name against names already in the db

            boolean err = tdb.modData(tdb, "Insert into playerFile (playerID, name, hasInventory, score, health) " +
                    "values (" + key + ", \'" + name + "\'," + 0 + "," + 0 + "," + 100 + ")");

            if (err)
            {
                key++;
                //throw ends the process!
                //throw new AssertionError("Entered duplicate key in db");
            }
            else
                duplicateKey = false;
       // }
        return true;
    }

    //creates a new puzzle
    public static boolean createPuzzle(String puzzle, String answer, String successMessage, String failureMessage, int isSolved)
    {
        while (duplicateKey)
        {
//            boolean err = tdb.modData(tdb, "Insert into puzzle(puzzleID, puzzle, answer, successMessage, failureMessage, isSolved) " +
//                    "values (" + key + ", \'" + puzzle + "\',\'" + answer + "\',\'" + successMessage + "\',\'" + failureMessage + "\'," + isSolved + ")");
        	//fix: changed the name of some columns to match with the database 
        	boolean err = tdb.modData(tdb, "Insert into puzzle(puzzleID, problem, solution, successMessage, failureMessage, isSolved) " +
                    "values (" + key + ", \'" + problem + "\',\'" + solution + "\',\'" + successMessage + "\',\'" + failureMessage + "\'," + isSolved + ")");

            if (err)
            {
                key++;
                //throw ends the process!
                //throw new AssertionError("Entered duplicate key in db");
            }
            else
                duplicateKey = false;
        }
        return true;

    }

    //creates a new monster
    public static boolean createMonster(int monsterID, String name, int attackPower, int health)
    {
        while (duplicateKey)
        {
            boolean err = tdb.modData(tdb, "Insert into monster(monsterID, name, attackPower, health) " +
                    "values (" + key + ", \'" + name + "\'," + attackPower + "," + health + ")");

            if (err)
            {
                key++;
                //throw ends the process!
                //throw new AssertionError("Entered duplicate key in db");
            }
            else
                duplicateKey = false;
        }
        return true;
    }

    //retrieves all the rooms
    public static String loadSavedRooms(int playerID)
    {
        StringBuilder savedRoomBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query(tdb, "Select * from emptyPlayerRooms WHERE playerID = " + playerID + " ");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                savedRoomBuilder.append(rs.getInt("playerID"));
                savedRoomBuilder.append(rs.getInt("currentRoom"));

                //add saved rooms 1 - 50
                for (int x = 1; x <= 50; x++)
                {
                    savedRoomBuilder.append("|");
                    savedRoomBuilder.append(rs.getInt("room" + x + ""));
                }
            }
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return savedRoomBuilder.toString();
    }

    //retrieves all the rooms
    public static String loadAllRooms()
    {
        StringBuilder roomBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            //simple fix: change rooms to room column
            rs = tdb.query(tdb, "Select * from room"); 
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
                roomBuilder.append(rs.getString("description"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("isEmpty"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("Armor"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("Elixer"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("Weapon"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("Monster"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("Puzzle"));
                roomBuilder.append("|");
            }
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        roomBuilder.deleteCharAt(roomBuilder.lastIndexOf("|"));
        return roomBuilder.toString();

    }

    //(1/2) this is called first from Game class. loads the player profile after loginAccount returns true
    public static String loadHero(String playerName)
    {
        StringBuilder heroBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query(tdb, "Select * from playerFile");
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
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return heroBuilder.toString();

    }

    //(2/2) This is called from Game class auto once loadHero is called
    public static String loadHeroInventory(int playerID)
    {
        StringBuilder heroInventory = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query(tdb, "Select * from savedInventory where playerID = " + playerID + " " );
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
//                rs.getInt("inventoryID");
//                rs.getString("playerID");
                heroInventory.append(rs.getInt("weaponID"));
                heroInventory.append("|");
                heroInventory.append(rs.getInt("armorID"));
                heroInventory.append("|");
                heroInventory.append(rs.getInt("elixerID"));
                heroInventory.append("|");
            }
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return heroInventory.toString();
    }

    //retrieves a particular monster
    public static String retrieveMonster(int monsterIndex)
    {
        StringBuilder monsterBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query(tdb, "Select * from monster");
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
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return monsterBuilder.toString();
    }

    //(3/4)retrieves a particular armor
    public static String retrieveArmor(int armorIndex)
    {
        StringBuilder armorBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query(tdb, "Select * from armor");
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
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return armorBuilder.toString();
    }

    //(4/5)retrieves a particular elixir
    public static String retrieveElixir(int elixirIndex)
    {
        StringBuilder elixirBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query(tdb, "Select * from elixir");
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
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return elixirBuilder.toString();
    }

    //(5/5)retrieves a particular weapon
    public static String retrieveWeapon(int weaponIndex)
    {
        StringBuilder weaponBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query(tdb, "Select * from weapon");
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
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return weaponBuilder.toString();
    }

    //retrieves a particular puzzle
    public static String retrievePuzzle(int puzzleIndex)
    {
        StringBuilder puzzleBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query(tdb, "Select * from puzzle");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (rs.getInt("puzzleID") == puzzleIndex)
                {
                    puzzleBuilder.append(rs.getString("problem"));
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
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return puzzleBuilder.toString();
    }

    //save the Hero attributes to the database
    public static Boolean saveHeroData(String heroAttributes)
    {
        String[] savedHero = heroAttributes.split("[|]");

        while (duplicateKey)
        {
            //May want to modify this and the table to save defenseStrength and armorDefense OR the player can just re-equip these
            boolean err = tdb.modData(tdb, "Insert into playerFile(playerID, name, hasInventory, score, health) " +
                    "values (" + savedHero[0] + ", " + savedHero[1] + ", " + savedHero[2] + ", " + savedHero[3] + ", " + savedHero[4] + ")");

            if (err)
            {
                key++;
                tdb.modData(tdb, "UPDATE playerFile SET name = \'" + savedHero[1] + "\', hasInventory = " + savedHero[2] + ", score = " +
                        savedHero[3] + ", health = " + savedHero[4] + "WHERE playerID = " + savedHero[0]);
            }
            else
                duplicateKey = false;
        }
        return true;
    }

    // UNFINISHED!!!!!!!!!!--------------------------------------NO TABLE EXISTS YET!!!
    //save the rooms state to the savedRooms table
    //array should have key, playerID, and 1-50 rooms 0 or 1 for false/true is the room empty
    public static Boolean saveRoomState(String[] roomsState)
    {
        StringBuilder rooms = new StringBuilder();
        rooms.append("values (");
        rooms.append(key);
        rooms.append(", ");
        rooms.append(roomsState[0]); //this is the playerID

        //
        for (int i = 1; i < roomsState.length; i++)
        {
            rooms.append(", ");
            rooms.append(roomsState[i]);
        }
        rooms.append(")");

        while (duplicateKey)
        {
            //Need to create a savedRooms table
            boolean err = tdb.modData(tdb, "Insert into Rooms(roomsStateID, playerID, attackPower, health) " + rooms.toString());
                    //"values (" + key + ", " + playerID + ", " + attackPower + ", " + health + ")");

            if (err)
            {
                key++;
                //throw ends the process!
                //throw new AssertionError("Entered duplicate key in db");
            }
            else
                duplicateKey = false;
        }
        return true;
    }

    //Save the hero's inventory to the inventory table
    //inventoryState is playerID, itemType and item name
    public static Boolean saveHeroInventory(String inventoryState)
    {
        String[] heroItems = inventoryState.split("[|]");
        boolean err;
        int inventorySize = heroItems.length;
        int[] wIndex = new int[(inventorySize-1)/2];
        int[] aIndex = new int[(inventorySize-1)/2];
        int[] eIndex = new int[(inventorySize-1)/2];

        //InventoryState = itemType|name
        //PARSE inventoryState
        for (int x = 1; x < inventorySize-1; x+=2)
        {
            if (heroItems[x].equalsIgnoreCase("w"))
            {
                try
                {
                    //Query the database. Returns the results in a ResultSet
                    rs = tdb.query(tdb, "Select * from weapon");
                    //Loop over the result set. next moves the cursor to the next record and returns the current record
                    while(rs.next())
                    {
                        if (rs.getString("weaponName").equalsIgnoreCase(heroItems[x+1]))
                        {
                            wIndex[x-1] = rs.getInt("weaponID");
                            aIndex[x-1] = 0;
                            eIndex[x-1] = 0;
                            break;
                        }
                    }
                }
                catch(SQLException sqe)
                {
                    System.out.println(sqe.getMessage());
                }
            }
            else if (heroItems[x].equalsIgnoreCase("a"))
            {
                try
                {
                    //Query the database. Returns the results in a ResultSet
                    rs = tdb.query(tdb, "Select * from armor");
                    //Loop over the result set. next moves the cursor to the next record and returns the current record
                    while(rs.next())
                    {
                        if (rs.getString("armorName").equalsIgnoreCase(heroItems[x+1]))
                        {
                            wIndex[x-1] = 0;
                            aIndex[x-1] = rs.getInt("armorID");
                            eIndex[x-1] = 0;
                            break;
                        }
                    }
                }
                catch(SQLException sqe)
                {
                    System.out.println(sqe.getMessage());
                }
            }
            else if (heroItems[x].equalsIgnoreCase("e"))
            {
                try
                {
                    //Query the database. Returns the results in a ResultSet
                    rs = tdb.query(tdb, "Select * from elixir");
                    //Loop over the result set. next moves the cursor to the next record and returns the current record
                    while(rs.next())
                    {
                        if (rs.getString("elixirName").equalsIgnoreCase(heroItems[x+1]))
                        {
                            wIndex[x-1] = 0;
                            aIndex[x-1] = 0;
                            eIndex[x-1] = rs.getInt("elixirID");
                            break;
                        }
                    }
                }
                catch(SQLException sqe)
                {
                    System.out.println(sqe.getMessage());
                }
            }
        }

        while (duplicateKey)
        {
            err = tdb.modData(tdb, "Insert into savedInventory(inventoryID, playerID, weaponID, armorID, elixirID) " +
                    "values (" + key + ", " + heroItems[0] + ", " + wIndex[0] + ", " + aIndex[0] + ", " + eIndex[0]+ ")");

            if (err)
            {
                key++;
                //throw ends the process!
                // throw new AssertionError("Entered duplicate key in db");
            }
            else
            {
                key++;
                for (int i = 1; i < wIndex.length; i++)
                {
                    tdb.modData(tdb, "Insert into savedInventory(inventoryID, playerID, weaponID, armorID, elixirID) " +
                            "values (" + key + ", " + heroItems[0] + ", " + wIndex[i] + ", " + aIndex[i] + ", " + eIndex[i] + ")");
                }
                duplicateKey = false;
            }
        }
        return true;
    }

    private ResultSet query(Controller tdb, String sql)
    {
        rs = null;
        try
        {
            rs = tdb.stmt.executeQuery(sql);
        }
        catch(SQLException sqe)
        {
            sqe.printStackTrace();
        }
        return rs;
    }

    private boolean modData(Controller tdb, String sql)
    {
        try
        {
            tdb.stmt.executeUpdate(sql);
        }
        catch(SQLException sqe)
        {
            sqe.printStackTrace();
        }
        return true;
    }
}