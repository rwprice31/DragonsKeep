package Controller;

import java.sql.*;

/**
 * author: JJ Lindsay
 * version: 2.0
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
    private static Connection conn = null;
    private static boolean duplicateKey = true;
//    private static int key = 1;
    private static int caKey = 1;
    private static int shiKey = 1;
    private static int cpKey = 1;
    private static int cmKey = 1;


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

            rs.close();
            stmt.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return false;
    }

    //creates a user profile
    public static int createAccount(String name)
    {

        try
        {
            do{
            //BEFORE inserting:: compare user requested name against names already in the db

                //the value returned is the number of effected rows (for us its either 0 or 1)
                int err = tdb.modData(tdb, "Insert into playerFile (playerID, name, hasInventory, score, health) " +
                        "values (" + caKey + ", \'" + name + "\'," + 0 + "," + 0 + "," + 100 + ")");


                //enters here when duplicate id are used
                //i.e. 0 rows were effected
                if (err == 0)
                {
                    caKey++;
                }
                else
                {
                    duplicateKey = false;
                }
           }while (duplicateKey);

            stmt.close();
        } catch (SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return caKey;
    }

    //creates a new puzzle
    public static boolean createPuzzle(String problem, String solution, String successMessage, String failureMessage, int isSolved)
    {
        try
        {
            while (duplicateKey)
            {
     		//fix: changed the name of some columns to match with the database
                int err = tdb.modData(tdb, "Insert into puzzle(puzzleID, problem, solution, successMessage, failureMessage, isSolved) " +
                        "values (" + cpKey + ", \'" + problem + "\',\'" + solution + "\',\'" + successMessage + "\',\'" + failureMessage + "\'," + isSolved + ")");

                if (err == 0)
                {
                    cpKey++;
                }
                else
                {
                    duplicateKey = false;
                }
            }
            stmt.close();
        }
        catch (SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return true;
    }

    //creates a new monster
    public static boolean createMonster(int monsterID, String name, int attackPower, int health)
    {
        try
        {
            while (duplicateKey)
            {
                int err = tdb.modData(tdb, "Insert into monster(monsterID, name, attackPower, health) " +
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
            stmt.close();
        }
        catch (SQLException sqe)
        {
            System.out.println(sqe.getMessage());
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
            rs = tdb.query(tdb, "Select * from playerRooms WHERE playerID = " + playerID);
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
            stmt.close();
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
            stmt.close();
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

            rs.close();
            stmt.close();
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
            rs = tdb.query(tdb, "Select * from savedInventory where playerID = " + playerID);
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
            stmt.close();
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

            rs.close();
            stmt.close();
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

            rs.close();
            stmt.close();
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

            rs.close();
            stmt.close();
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

            rs.close();
            stmt.close();
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
            stmt.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return puzzleBuilder.toString();
    }

    //save the Hero ID, name, hasInventory, score, health to the database
    public static Boolean saveHeroData(String heroAttributes)
    {
        String[] savedHero = heroAttributes.split("[|]");

        try
        {
                //playerID is the PK
                //May want to modify this and the table to save defenseStrength and armorDefense OR the player can just re-equip these

            //may need to parseInt for some
            int err = tdb.modData(tdb, "UPDATE playerFile SET hasInventory = " + savedHero[2] + ", score = " +
                    savedHero[3] + ", health = " + savedHero[4] + " WHERE playerID = " + savedHero[0] + "");

             //if no rows were changed
            if (err == 0)
            {
                System.err.println("There was an error in updating saved Hero Data to playerFile");
                return false;
            }
            else
                System.out.println("Successfully saved Hero profile to the database.");

            stmt.close();
        } catch (SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return true;
    }


    //save the rooms state to the savedRooms table
    public static Boolean saveRoomState(String roomsState)
    {
        String[] rooms = roomsState.split("[|]");

        try
        {

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
            int err = tdb.modData(tdb, playerRoomsInsert);


            //if no rows are updated
            if (err == 0)
            {
                playerRoomsInsert = "UPDATE playerRooms SET currentRoom = " + rooms[1] + ", room1  = " + rooms[2];
                for (int i = 2; i <= 50; i++)
                {
                    playerRoomsInsert += ", room" + i + " = " + rooms[i+1];
                }
                playerRoomsInsert += " WHERE playerID = " + rooms[0];
                tdb.modData(tdb, playerRoomsInsert);
                System.out.println("Successfully saved the rooms to the database.");
            }
            else
                System.out.println("Successfully saved the rooms to the database.");

            stmt.close();
        } catch (SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return true;
    }

    //Save the hero's inventory to the inventory table
    //inventoryState is playerID, itemType and item name
    public static Boolean saveHeroInventory(int playerID, String[][] playerInventory)
    {
        boolean looping;


        //if the playerID already exist, delete it
        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query(tdb, "Select * from savedInventory");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (rs.getString("playerID").equalsIgnoreCase("" + playerID))
                {
                    int err = tdb.modData(tdb, "DELETE FROM savedInventory WHERE playerID = " + playerID);
                    System.out.println("removed previous inventory.");  //DEBUG CODE
                    break;
                }
            }

            rs.close();
            stmt.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }


        for (int x = 0; x < playerInventory.length; x++)
        {
            String heroItems = ", " + playerID;

            if (playerInventory[x][1] != null)
            if (playerInventory[x][1].equalsIgnoreCase("w"))
            {
                try
                {
                    //Query the database. Returns the results in a ResultSet
                    rs = tdb.query(tdb, "Select * from weapon");
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
                    stmt.close();
                }
                catch(SQLException sqe)
                {
                    System.out.println(sqe.getMessage());
                }
            }
            else if (playerInventory[x][1].equalsIgnoreCase("a"))
            {
                try
                {
                    //Query the database. Returns the results in a ResultSet
                    rs = tdb.query(tdb, "Select * from armor");
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
                    stmt.close();
                }
                catch(SQLException sqe)
                {
                    System.out.println(sqe.getMessage());
                }
            }
            else if (playerInventory[x][1].equalsIgnoreCase("e"))
            {
                try
                {
                    //Query the database. Returns the results in a ResultSet
                    rs = tdb.query(tdb, "Select * from elixir");
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
                    stmt.close();
                }
                catch(SQLException sqe)
                {
                    System.out.println(sqe.getMessage());
                }
            }
            else
                continue;

            if (playerInventory[x][1] != null)
            do
            {
                int err = tdb.modData(tdb, "Insert into savedInventory(inventoryID, playerID, weaponID, armorID, elixirID) " +
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

    private ResultSet query(Controller tdb, String sql)
    {
        rs = null;
        try
        {
            rs = stmt.executeQuery(sql);
        }
        catch(SQLException sqe)
        {
            sqe.printStackTrace();
        }
        return rs;
    }

    //tdb is not needed since it is set in the constructor and globally
    private int modData(Controller tdb, String sql)
    {
        //default value nothing is changed, 0.
        int num = 0;

        try
        {
            num = stmt.executeUpdate(sql);
            return num;
        }
        catch(SQLException sqe)
        {
            //sqe.printStackTrace();
            return num;
        }
    }
}