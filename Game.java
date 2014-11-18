package View;

import Model.*;

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
    Direction direction;
    private static Hero player;
    private static Monster monster;
    private static Armor amor;
    private static Weapon weapon;
    private static Elixir elixer;
    private static Puzzle puzzle;
    private static Rooms rooms;
    private static int currentRoom;
    private static int[] roomOptions;
    private static char itemType;
    private static String logInName;
    private static String saveHero;
    private static String saveRoomStates;
    private static String saveHeroInventory;

    //enum to define a direction
    public static enum Direction
    {
        NORTH,
        SOUTH,
        EAST,
        WEST
    };

    public static void playGame()
    {

    }

    public static void quitGame()
    {

    }

    public static void saveGame()
    {

    }

    public static void loadGame()
    {

    }

    public static void battle()
    {

    }

    public static void collectItem(char itemType)
    {

    }

    public static void solvePuzzle()
    {

    }

    public static void enteredRoom(int roomNum)
    {

    }

    public static void help()
    {

    }


    public static void main(String[] args)
    {

    }
}
