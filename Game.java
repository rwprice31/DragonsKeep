package View;

import Controller.Controller;
import Model.*;

import javax.naming.ldap.Control;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

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
    private static Map<Integer,Rooms> roomsMap; //Map<room, roomObj>
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
        input.nextLine(); //necessary since the previous use was for reading an int UNTESTED!!!
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
        enteredRoom(currentRoom);

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
        String[] temp = new String[4];
        roomsMap = new TreeMap<Integer, Rooms>();

        for (int i = 0; i < allRooms.length-11; i++)
        {
            rooms = new Rooms();
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

            roomsMap.put(Integer.parseInt(allRooms[i]), rooms);
        }
        currentRoom = 0;
    }

    public static void battle()
    {
        //create monster
        String[] dbMonster = Controller.retrieveMonster(roomsMap.get(currentRoom).getIsMonster().get(currentRoom)).split("[|]]");
        monster = new Monster(dbMonster[0], Integer.parseInt(dbMonster[1]), Integer.parseInt(dbMonster[2]));

        //LOOP THIS
        System.out.println("Enter \"inventory\" to check inventory. \n Enter \"equip -item name-\" to equip a specific item in inventory. " +
                "\n Enter \"attack\" to start the fight.");
        String response = input.nextLine();

        if (response.equalsIgnoreCase("inventory"))
        {
            player.getInventory().view();
        }
        else if (response.substring(0, 5).equalsIgnoreCase("equip"))
        {
            //check that item exits
            if (player.getInventory().getItem(response.substring(6)) != null)
            {
                if (player.getInventory().getItemType(response) == 'w')
                {
                    player.setAttackPower(((Weapon) player.getInventory().getItem(response.substring(6))).getStrength());
                }
                else if (player.getInventory().getItemType(response) == 'a')
                {
                    player.setDefenseStrength(((Armor) player.getInventory().getItem(response.substring(6))).getArmorDefense());
                }
                else if (player.getInventory().getItemType(response) == 'e')
                {
                    player.setHealth(((Elixir) player.getInventory().getItem(response.substring(6))).getHealthBoost());
                }
            }
            {
                System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
            }

        }
        else if (response.equalsIgnoreCase("attack"))
        {
            //LOOP this
            //player attacks first
            if (monster.getHealth() - player.getAttackPower() > 0)
            {
                monster.setHealth(monster.getHealth() - player.getAttackPower());
            } else
            {
                //temporary phrase
                System.out.println("Hurray! You killed the monster.");
            }

            //Monster retaliates
            System.out.println("You weren't able to strike the monster down with your last attack. It looks angry!\nBrace yourself.");
            if (player.getHealth() - monster.getAttackPower() > 0)
            {
                player.setHealth(player.getHealth() - monster.getAttackPower());
            } else
            {
                //temporary phrase
                System.out.println("Oh no!! You were killed by the monster!");
            }
        }
        else
        {
            System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
        }

    }

    public static void collectItem()
    {
        if (roomsMap.get(currentRoom).getIsArmor().get(currentRoom) != null)
        {
            String[] dbArmor = Controller.retrieveArmor(roomsMap.get(currentRoom).getIsArmor().get(currentRoom)).split("[|]]");
            armor = new Armor(dbArmor[0], Integer.parseInt(dbArmor[1]));
//Start here 11/20/14            //place item in player inventory
        }
        else if (roomsMap.get(currentRoom).getIsElixir().get(currentRoom) != null)
        {
            String[] dbElixir = Controller.retrieveElixir(roomsMap.get(currentRoom).getIsElixir().get(currentRoom)).split("[|]]");
            elixir = new Elixir(dbElixir[0], Integer.parseInt(dbElixir[1]));
//Start here 11/20/14            //place item in player inventory
        }
        else if (roomsMap.get(currentRoom).getIsWeapon().get(currentRoom) != null)
        {
            String[] dbWeapon = Controller.retrieveWeapon(roomsMap.get(currentRoom).getIsWeapon().get(currentRoom)).split("[|]]");
            weapon = new Weapon(dbWeapon[0], Integer.parseInt(dbWeapon[1]));
//Start here 11/20/14            //place item in player inventory

        }
    }

    public static void solvePuzzle()
    {
        String[] dbPuzzle = Controller.retrievePuzzle(roomsMap.get(currentRoom).getIsPuzzle().get(currentRoom)).split("[|]]");
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

    //should there be a change rooms method since its repeated otherwise?
    public static void changeRooms()
    {
        System.out.println("Where would you like to go next?");
        String response = input.nextLine();

        if (response.equalsIgnoreCase("head East") && roomsMap.get(currentRoom).getChoices().get(currentRoom)[0] != null)
        {
            enteredRoom(Integer.parseInt(roomsMap.get(currentRoom).getChoices().get(currentRoom)[0]));
        }
        else if (response.equalsIgnoreCase("head NORTH") && roomsMap.get(currentRoom).getChoices().get(currentRoom)[1] != null)
        {
            enteredRoom(Integer.parseInt(roomsMap.get(currentRoom).getChoices().get(currentRoom)[1]));
        }
        else if (response.equalsIgnoreCase("head South") && roomsMap.get(currentRoom).getChoices().get(currentRoom)[2] != null)
        {
            enteredRoom(Integer.parseInt(roomsMap.get(currentRoom).getChoices().get(currentRoom)[2]));
        }
        else if (response.equalsIgnoreCase("head West") && roomsMap.get(currentRoom).getChoices().get(currentRoom)[3] != null)
        {
            enteredRoom(Integer.parseInt(roomsMap.get(currentRoom).getChoices().get(currentRoom)[3]));

        }
    }

    public static void enteredRoom(int roomNum)
    {
        System.out.println(rooms.getRoomDescription().get(roomNum));
        String response;

        //checks if an monster is in the room
        if (roomsMap.get(currentRoom).getIsMonster().get(currentRoom) != null)
        {
            System.out.println("Are you going to fight the monster? (yes/no)");
            response = input.nextLine();

            if (response.equalsIgnoreCase("yes"))
            {
                battle();
            }
            if (response.equalsIgnoreCase("no"))
            {
                changeRooms();
            }
            else
            {
                System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
            }
        }
        //checks if an puzzle is in the room
        else if (roomsMap.get(currentRoom).getIsPuzzle().get(currentRoom) != null)
        {
            System.out.println("Are you going to attempt this puzzle? (yes/no)");
            response = input.nextLine();

            if (response.equalsIgnoreCase("yes"))
            {
                solvePuzzle();
            }
            if (response.equalsIgnoreCase("no"))
            {
                changeRooms();
            }
            else
            {
                System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
            }
        }
        //checks if an item is in the room
        else if (roomsMap.get(currentRoom).getIsArmor().get(currentRoom) != null || roomsMap.get(currentRoom).getIsWeapon().get(currentRoom) != null ||
                roomsMap.get(currentRoom).getIsElixir().get(currentRoom) != null)
        {
            System.out.println("Do you want to collect this item? (yes/no)");
            response = input.nextLine();

            if (response.equalsIgnoreCase("yes"))
            {
                collectItem();
            }
            if (response.equalsIgnoreCase("no"))
            {
                changeRooms();
            }
            else
            {
                System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
            }
        }

    }

    public static void help()
    {

    }


    public static void main(String[] args)
    {
//DEBUG PURPOSE        String word = new String("equip1sword");
//DEBUG PURPOSE        System.out.println("Your word is equip sword before substring. After: " + word.substring(6));
        playGame();
    }
}
