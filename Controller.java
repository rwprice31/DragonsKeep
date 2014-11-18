package Controller;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 11/16/2014
 *
 * This class represents a ...
 *
 * Purpose: Allows the manipulation of a ...
 */
public class Controller
{
    private Connection conn; //used to connect to the db
    private Statement stmt;
    private int timeOut;

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
        timeOut = 30;

        try
        {
            //Establish a connection to the database
            conn = DriverManager.getConnection(dbURL);
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
    
    //verifies the user account exists
    public static boolean loginAccount(String playerName)
    {
        Controller tdb = new Controller();
        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query(tdb, "Select name from playerFile");

            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (playerName.equalsIgnoreCase(rs.getString("name")))
                    return true;
            }
            return false;
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
    }

    //creates a user profile
    public static boolean createAccount(String name)
    {
        boolean duplicateKey = true;
        int key = 1;
        Controller tdb = new Controller();

        while (duplicateKey)
        {
            boolean err = tdb.modData(tdb, "Insert into playerFile(playerID, name, hasInventory, score, health) " +
                    "values (" + key + ", \'" + name + "\'," + null + "," + 0 + "," + 100 + ",)");

            if (err)
            {
                key++;
                throw new AssertionError("Entered duplicate key in db");
            }
            else
                duplicateKey = false;
        }
        return !err;
    }

    //creates a new puzzle
    public static boolean createPuzzle(String puzzle, String answer, String successMessage, String failureMessage, int isSolved)
    {
        boolean duplicateKey = true;
        int key = 1;
        Controller tdb = new Controller();

        while (duplicateKey)
        {
            boolean err = tdb.modData(tdb, "Insert into puzzle(puzzleID, puzzle, answer, successMessage, failureMessage, isSolved) " +
                    "values (" + key + ", \'" + puzzle + "\',\'" + answer + "\',\'" + successMessage + "\',\'" + failureMessage + "\'," + isSolved + ")");

            if (err)
            {
                key++;
                throw new AssertionError("Entered duplicate key in db");
            }
            else
                duplicateKey = false;
        }
        return !err;

    }

    //creates a new monster
    public static boolean createMonster(int monsterID, String name, int attackPower, int health)
    {
        boolean duplicateKey = true;
        int key = 1;
        Controller tdb = new Controller();

        while (duplicateKey)
        {
            boolean err = tdb.modData(tdb, "Insert into monster(monsterID, name, attackPower, health) " +
                    "values (" + key + ", \'" + name + "\'," + attackPower + "," + health + ")");

            if (err)
            {
                key++;
                throw new AssertionError("Entered duplicate key in db");
            }
            else
                duplicateKey = false;
        }
        return !err;
    }

    //retrieves all the rooms
    public static String loadAllRooms()
    {
        Controller tdb = new Controller();
        StringBuilder roomBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query(tdb, "Select * from rooms");
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
                roomBuilder.append(rs.getInt("Puzzle") + "|");
            }
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return roomBuilder.toString();

    }

    //(1/2) this is called first from Game class. loads the player profile after loginAccount returns true
    public static String loadHero()
    {
        Controller tdb = new Controller();
        StringBuilder heroBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query(tdb, "Select * from playerFile");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
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
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return heroBuilder.toString();

    }

    //(2/2) This is called from Game class auto once loadHero is called
    public static String loadHeroInventory(int playerID)
    {
        Controller tdb = new Controller();
        StringBuilder heroInventory = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query(tdb, "Select * from savedInventory where playerID = " + playerID + " " );
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
        Controller tdb = new Controller();
        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query(tdb, "Select * from monster");
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
        Controller tdb = new Controller();
        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query(tdb, "Select * from armor");
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
        Controller tdb = new Controller();
        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query(tdb, "Select * from elixir");
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
        Controller tdb = new Controller();
        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query(tdb, "Select * from weapon");
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
        Controller tdb = new Controller();
        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query(tdb, "Select * from puzzle");
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

//VERY UNFINISHED!!!!!!!!!!--------------------------------------
    //save the Hero attributes to the database
    public static Boolean saveHeroData(String heroAttributes)
    {
        boolean duplicateKey = true;
        int key = 1;
        Controller tdb = new Controller();

        while (duplicateKey)
        {
            boolean err = tdb.modData(tdb, "Insert into monster(monsterID, name, attackPower, health) " +
                    "values (" + key + ", \'" + name + "\'," + attackPower + "," + health + ")");

            if (err)
            {
                key++;
                throw new AssertionError("Entered duplicate key in db");
            }
            else
                duplicateKey = false;
        }
        return !err;
    }

//VERY UNFINISHED!!!!!!!!!!--------------------------------------NO TABLE EXISTS YET!!!
    //save the rooms state to the savedRooms table
    public static Boolean saveRoomState(String roomsState)
    {
        boolean duplicateKey = true;
        int key = 1;
        Controller tdb = new Controller();

        while (duplicateKey)
        {
            boolean err = tdb.modData(tdb, "Insert into monster(monsterID, name, attackPower, health) " +
                    "values (" + key + ", \'" + name + "\'," + attackPower + "," + health + ")");

            if (err)
            {
                key++;
                throw new AssertionError("Entered duplicate key in db");
            }
            else
                duplicateKey = false;
        }
        return !err;
    }

//VERY UNFINISHED!!!!!!!!!!--------------------------------------
    //Save the hero's inventory to the inventory table
    public static Boolean saveHeroInventory(String inventoryState)
    {
        String[] heroItems = inventoryState.split("[|]");
        boolean duplicateKey = true;
        int key = 1;
        Controller tdb = new Controller();
        int inventorySize = heroItems.length;
        int[] wIndex = 0;
        int[] aIndex;
        int[] eIndex;

        //InventoryState = itemType|name
        //PARSE inventoryState
        for (int x = 0; x < inventorySize; x++)
        {
            if (heroItems[x].equalsIgnoreCase("w"))
            {
                try
                {
                    //Query the database. Returns the results in a ResultSet
                    ResultSet rs = tdb.query(tdb, "Select * from weapon");
                    //Loop over the result set. next moves the cursor to the next record and returns the current record
                    while(rs.next())
                    {
                        if (rs.getString("weaponName") == heroItems[x+1])
                        {
                            wIndex[x] = rs.getInt("weaponID");
                            aIndex[x] = 0;
                            eIndex[x] = 0;
                        }
                    }
                }
                catch(SQLException sqe)
                {
                    System.out.println(sqe.getMessage());
                }
            }
            else if (heroItems[x].equalsIgnoreCase("a"))
            {}
            else if (heroItems[x].equalsIgnoreCase("e"))
            {}
            else { continue;}
        }

        while (duplicateKey)
        {
            boolean err = tdb.modData(tdb, "Insert into savedInventory(inventoryID, playerID, weaponID, armorID, elixirID) " +
                    "values (" + key + ", \'" + name + "\'," + attackPower + "," + health + ")");

            if (err)
            {
                key++;
                throw new AssertionError("Entered duplicate key in db");
            }
            else
                duplicateKey = false;
        }
        return !err;
    }

    private ResultSet query(Controller tdb, String sql)
    {
        ResultSet rs = null;
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
        boolean success = true;
        try
        {
            tdb.stmt.executeUpdate(sql);
        }
        catch(SQLException sqe)
        {
            sqe.printStackTrace();
        }
        return success;
    }
}
