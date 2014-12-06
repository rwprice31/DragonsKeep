package Controller;

import java.sql.*;

/**
 * author: JJ Lindsay
 * version: 2.0
 * Course: ITEC 3860 Fall 2014
 * Written: 11/16/2014
 *
 * This class represents a Controller with account methods.
 *
 * Purpose: Gives the ability to log into or create a new player profile in the database.
 */
public class Controller
{
    //instance variables
    private static Statement stmt;
    private static ResultSet rs = null;
    private static Controller tdb = new Controller();
    private static boolean duplicateKey = true;
    private static int caKey = 1;

    /**No argument constructor
     */
    protected Controller()
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

    /**verifies the user account
     * @param playerName The player name
     * @return true/false If logging in succeeds or fails
     */
    public static boolean loginAccount(String playerName)
    {
        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select name from playerFile");

            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (playerName != null && playerName.equalsIgnoreCase(rs.getString("name")))
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

    /**creates a user profile
     * @param name The player's name
     * @return caKey The database primary key associated with this player name
     */
    public static int createAccount(String name)
    {
        try
        {
            do{
            //BEFORE inserting:: compare user requested name against names already in the db

                //the value returned is the number of effected rows (for us its either 0 or 1)
                int err = tdb.modData("Insert into playerFile (playerID, name, hasInventory, score, health) " +
                        "values (" + caKey + ", \'" + name + "\'," + 0 + "," + 0 + "," + 100 + ")");

                //If 0 rows were effected
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

    /**Allows for querying the database
     * @param sql database query statement
     * @return rs The results from the query
     */
    protected ResultSet query(String sql)
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

    /**Allows for inserting and modifying the SQLite database
     * @param sql database modification statement
     * @return num the number of changed rows
     */
    protected int modData(String sql)
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