package View;

import Controller.Controller;
import Model.*;

import javax.naming.ldap.Control;
import java.util.Scanner;

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
public class Game
{
    //Static variables
    //Direction direction;
    private static Hero player;
    private static Monster monster;
    private static Armor armor;
    private static Weapon weapon;
    private static Elixir elixir;
    private static Puzzle puzzle;
    private static Rooms rooms;
    private static int currentRoom;
    private static int[] roomOptions;
    private static char itemType;
    private static String logInName;
    private static String saveHero;
    private static String saveRoomStates;
    private static String saveHeroInventory;
//    private static String sInpute;
//    private static int iInput;

    private static Scanner input = new Scanner(System.in);


//    //enum to define a direction
//    public static enum Direction
//    {
//        NORTH,
//        SOUTH,
//        EAST,
//        WEST
//    };

    public static boolean login()
    {
        System.out.println("Enter your user name: ");
        String name = input.nextLine();
        return Controller.loginAccount(name);

    }

    public static boolean createAccount()
    {
        System.out.println("Enter a name you would like to create for your account: ");
        input.nextLine(); // necessary since the previous use was for reading an int
        String name = input.nextLine();
        return Controller.createAccount(name);
        //System.out.println("You entered: " + name);
        //return true;
    }

    public static void playGame()
    {
        Boolean waiting = true;
        System.out.println("Welcome to Dragon's Keep!");

        while(waiting)
        {
            System.out.println("Enter 1 to login or 2 to create a new account");
            int in = input.nextInt();

            if (in == 1)
            {

                if (login())
                {

                    waiting = false;
                }
                else
                {
                    System.out.println("Could not locate your user name");
                }
            } else if (in == 2)
            {
                createAccount();
                waiting = false;
            } else
            {
                System.err.println("Error interpreting your last request.");
            }
        }
        loadGame();

        // First time player enters here
        enteredRoom(rooms.getRoomID().get(currentRoom));

    }

    public static void quitGame()
    {

    }

    public static void saveGame()
    {

    }

    public static void loadGame()
    {
        String[] allRooms = Controller.loadAllRooms().split("[|]]");
        rooms = new Rooms();
        String[] temp = new String[4];

        for (int i = 0; i < allRooms.length-11; i++)
        {
            rooms.addRoomID(Integer.parseInt(allRooms[i]));
            temp[0] = allRooms[i+1];
            temp[1] = allRooms[i+2];
            temp[2] = allRooms[i+3];
            temp[3] = allRooms[i+4];
            rooms.addChoices(temp);
            rooms.addRoomDescription(allRooms[i+5]);
            rooms.addIsEmpty(Integer.parseInt(allRooms[i+6]));
            rooms.addIsArmor(Integer.parseInt(allRooms[i+7]));
            rooms.addIsElixir(Integer.parseInt(allRooms[i + 8]));
            rooms.addIsWeapon(Integer.parseInt(allRooms[i+9]));
            rooms.addIsMonster(Integer.parseInt(allRooms[i+10]));
            rooms.addIsPuzzle(Integer.parseInt(allRooms[i+11]));
        }
        currentRoom = 0;
    }

    public static void battle()
    {

        String[] dbMonster = Controller.retrieveMonster(rooms.getIsMonster().get(currentRoom)).split("[|]]");
        monster = new Monster(dbMonster[0], Integer.parseInt(dbMonster[1]), Integer.parseInt(dbMonster[2]));

        //is user requesting to check inventory?
        //is user equiping anything?
        if (monster.getHealth() - player.getAttackPower() > 0)
        {
            monster.setHealth(monster.getHealth() - player.getAttackPower());
        }


    }

    public static void collectItem(char itemType)
    {
        String[] dbArmor = Controller.retrieveArmor(rooms.getIsArmor().get(currentRoom)).split("[|]]");
        armor = new Armor(dbArmor[0], Integer.parseInt(dbArmor[1]));

        String[] dbElixir = Controller.retrieveElixir(rooms.getIsElixir().get(currentRoom)).split("[|]]");
        elixir = new Elixir(dbElixir[0], Integer.parseInt(dbElixir[1]));

        String[] dbWeapon = Controller.retrieveWeapon(rooms.getIsWeapon().get(currentRoom)).split("[|]]");
        weapon = new Weapon(dbWeapon[0], Integer.parseInt(dbWeapon[1]));
    }

    public static void solvePuzzle()
    {
        String[] dbPuzzle = Controller.retrievePuzzle(rooms.getIsPuzzle().get(currentRoom)).split("[|]]");
        puzzle = new Puzzle(dbPuzzle[0], dbPuzzle[1], dbPuzzle[2], dbPuzzle[3], Integer.parseInt(dbPuzzle[4]));

        System.out.println(puzzle.getPuzzle());
        String response = input.nextLine();
        if (puzzle.getSolution().equalsIgnoreCase(response))
        {
            puzzle.getSuccessMessage();
        }
        else
        {
            puzzle.getFailureMessage();
            System.out.println("Try again?");
        }
    }

    public static void enteredRoom(int roomNum)
    {
        System.out.println(rooms.getRoomDescription().get(roomNum));
        String response = input.nextLine();

        if (response.equalsIgnoreCase("head NORTH") && rooms.getChoices().get(roomNum)[0] != null)
        {
            enteredRoom(Integer.parseInt(rooms.getChoices().get(roomNum)[0]));
        }
        else if (response.equalsIgnoreCase("head NORTH") && rooms.getChoices().get(roomNum)[1] != null)
        {
            enteredRoom(Integer.parseInt(rooms.getChoices().get(roomNum)[1]));
        }
        else if (response.equalsIgnoreCase("head South") && rooms.getChoices().get(roomNum)[2] != null)
        {
            enteredRoom(Integer.parseInt(rooms.getChoices().get(roomNum)[2]));
        }
        else if (response.equalsIgnoreCase("head West") && rooms.getChoices().get(roomNum)[3] != null)
        {
            enteredRoom(Integer.parseInt(rooms.getChoices().get(roomNum)[3]));

        }
        else if (rooms.getIsMonster().get(currentRoom) != null)
        {
            battle();
        }
        else if (rooms.getIsPuzzle().get(currentRoom) != null)
        {
            solvePuzzle();
        }

    }

    public static void help()
    {

    }


    public static void main(String[] args)
    {
        playGame();
    }
}
