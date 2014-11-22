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
 * version: 2.0
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
    private static Rooms rooms; //should only be used in the creation of roomsMap
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
//DEBUG PURPOSE         loadGame();

        // First time player enters here
//DEBUG PURPOSE         enteredRoom();

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
            rooms.setChoices(temp);
            rooms.setRoomDescription(allRooms[i+5]);
            rooms.setIsEmpty(Integer.parseInt(allRooms[i+6]));
            rooms.setIsArmor(Integer.parseInt(allRooms[i+7])); //points to the specific armor
            rooms.setIsElixir(Integer.parseInt(allRooms[i + 8])); //points to the specific elixir
            rooms.setIsWeapon(Integer.parseInt(allRooms[i+9])); //points to the specific weapon
            rooms.setIsMonster(Integer.parseInt(allRooms[i+10])); //points to the specific monster
            rooms.setIsPuzzle(Integer.parseInt(allRooms[i+11])); //points to the specific puzzle

            //converts roomID from string to int and stores roomID as key and the room created in a map
            roomsMap.put(Integer.parseInt(allRooms[i]), rooms);
        }
        currentRoom = 0;
    }

    public static void battle()
    {
        //create monster
        String[] dbMonster = Controller.retrieveMonster(roomsMap.get(currentRoom).getIsMonster()).split("[|]]");
        monster = new Monster(dbMonster[0], Integer.parseInt(dbMonster[1]), Integer.parseInt(dbMonster[2]));

        //LOOP THIS for? while? do while?
        System.out.println("Enter \"inventory\" to check inventory. \n Enter \"equip <item nam>\" to equip a specific item in inventory. " +
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
                //if you kill the monster and it is guarding an item, you get it by default


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

    public static void emptyRoom()
    {
        if (roomsMap.get(currentRoom).getIsMonster() == 0 && roomsMap.get(currentRoom).getIsPuzzle() == 0 &&
            roomsMap.get(currentRoom).getIsArmor() == 0 && roomsMap.get(currentRoom).getIsWeapon() == 0 &&
            roomsMap.get(currentRoom).getIsElixir() == 0)
        {
            roomsMap.get(currentRoom).setIsEmpty(1);
            roomsMap.get(currentRoom).setRoomDescription("This room is empty... and it looks a bit familiar");
        }
    }

    public static void collectItem()
    {
        if (roomsMap.get(currentRoom).getIsArmor() > 0)
        {
            //get an armor from the database
            String[] dbArmor = Controller.retrieveArmor(roomsMap.get(currentRoom).getIsArmor()).split("[|]]");
            //builds an armor(name, defenseBoost)
            armor = new Armor(dbArmor[0], Integer.parseInt(dbArmor[1]));
            player.getInventory().add(armor, 'a');
            roomsMap.get(currentRoom).setIsArmor(0);

            System.out.println("You have found " + dbArmor[0] + " and added it to your inventory.");

        } else if (roomsMap.get(currentRoom).getIsElixir() > 0)
        {
            //get an elixir from the database
            String[] dbElixir = Controller.retrieveElixir(roomsMap.get(currentRoom).getIsElixir()).split("[|]]");
            //builds an elixir(name, healthBoost)
            elixir = new Elixir(dbElixir[0], Integer.parseInt(dbElixir[1]));
            player.getInventory().add(elixir, 'e');
            roomsMap.get(currentRoom).setIsElixir(0);

            System.out.println("You have found " + dbElixir[0] + " and added it to your inventory.");

        } else //if (roomsMap.get(currentRoom).getIsWeapon() > 0)
        {
            //get an weapon from the database
            String[] dbWeapon = Controller.retrieveWeapon(roomsMap.get(currentRoom).getIsWeapon()).split("[|]]");
            //builds an weapon(name, strength)
            weapon = new Weapon(dbWeapon[0], Integer.parseInt(dbWeapon[1]));
            player.getInventory().add(weapon, 'w');
            roomsMap.get(currentRoom).setIsWeapon(0);

            System.out.println("You have found " + dbWeapon[0] + " and added it to your inventory.");
        }

    }

    public static void solvePuzzle()
    {
        //create and puzzle from the db
        String[] dbPuzzle = Controller.retrievePuzzle(roomsMap.get(currentRoom).getIsPuzzle()).split("[|]]");
        puzzle = new Puzzle(dbPuzzle[0], dbPuzzle[1], dbPuzzle[2], dbPuzzle[3], Integer.parseInt(dbPuzzle[4]));
        Boolean repeatPuzzle = false;

        //LOOP THIS
        do
        {
            System.out.println(puzzle.getPuzzle());
            String response = input.nextLine();
            if (puzzle.getSolution().equalsIgnoreCase(response))
            {
                repeatPuzzle = false;
                puzzle.getSuccessMessage();
                roomsMap.get(currentRoom).setIsPuzzle(0);
            }
            else
            {
                puzzle.getFailureMessage();
                Boolean unclearResponse = false;

                //Loop This
                do
                {
                    System.out.println("Try again? (yes/no)");
                    response = input.nextLine();
                    if (response.equalsIgnoreCase("yes"))
                    {
                        repeatPuzzle = true;

                    } else if (response.equalsIgnoreCase("no"))
                    {
                        changeRooms();

                    } else
                    {
                        unclearResponse = true;
                        System.err.println("There was an error in trying to make sense of you request. Check your spelling and try again.");
                    }
                } while (unclearResponse);
            }
        }while(repeatPuzzle);
    }

    //Question for the Team:: should there be a change rooms method since its repeated in several classes?
    public static void changeRooms()
    {
        //LOOP THIS
        System.out.println("Where would you like to go next?");
        String response = input.nextLine();

        if (response.equalsIgnoreCase("head East") && roomsMap.get(currentRoom).getChoices()[0] != null)
        {
            currentRoom = Integer.parseInt(roomsMap.get(currentRoom).getChoices()[0]);
            enteredRoom();
        }
        else if (response.equalsIgnoreCase("head NORTH") && roomsMap.get(currentRoom).getChoices()[1] != null)
        {
            currentRoom = Integer.parseInt(roomsMap.get(currentRoom).getChoices()[1]);
            enteredRoom();
        }
        else if (response.equalsIgnoreCase("head South") && roomsMap.get(currentRoom).getChoices()[2] != null)
        {
            currentRoom = Integer.parseInt(roomsMap.get(currentRoom).getChoices()[2]);
            enteredRoom();
        }
        else if (response.equalsIgnoreCase("head West") && roomsMap.get(currentRoom).getChoices()[3] != null)
        {
            currentRoom = Integer.parseInt(roomsMap.get(currentRoom).getChoices()[3]);
            enteredRoom();
        }
        // if the user correctly enters a direction but there is no room in that direction.
        else if (response.equalsIgnoreCase("head East") && roomsMap.get(currentRoom).getChoices()[0] == null ||
                response.equalsIgnoreCase("head NORTH") && roomsMap.get(currentRoom).getChoices()[1] == null ||
                response.equalsIgnoreCase("head South") && roomsMap.get(currentRoom).getChoices()[2] == null ||
                response.equalsIgnoreCase("head West") && roomsMap.get(currentRoom).getChoices()[3] == null)
        {
            System.out.println("This is embarrassing. You entered: " + response + " and walked into a wall. There is no door that way. Try again.");
        }
        // the user entered something else
        else
        {
            System.err.println("There was an error in trying to make sense of you request. Check your spelling and try again.");
        }
    }

    public static void enteredRoom()
    {
        System.out.println(roomsMap.get(currentRoom).getRoomDescription());
        String response;

        //NOTE: To deal with rooms with puzzle + item or monster + item

        //LOOP THIS

        //checks if an monster is in the room
        if (roomsMap.get(currentRoom).getIsMonster() > 0)
        {
            System.out.println("Are you going to fight the monster? (yes/no)");
            response = input.nextLine();

            if (response.equalsIgnoreCase("yes"))
            {
                battle();
            }
            else if (response.equalsIgnoreCase("no"))
            {
                changeRooms();
            }
            else
            {
                System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
            }
        }
        //checks if an puzzle is in the room
        else if (roomsMap.get(currentRoom).getIsPuzzle() > 0)
        {
            System.out.println("Are you going to attempt this puzzle? (yes/no)");
            response = input.nextLine();

            if (response.equalsIgnoreCase("yes"))
            {
                solvePuzzle();
            }
            else if (response.equalsIgnoreCase("no"))
            {
                changeRooms();
            }
            else
            {
                System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
            }
        }
        //checks if an item is in the room
        else if (roomsMap.get(currentRoom).getIsArmor() > 0 || roomsMap.get(currentRoom).getIsWeapon() > 0 ||
                roomsMap.get(currentRoom).getIsElixir() > 0)
        {
            System.out.println("There is an item to collect. Do you want to collect it? (yes/no)");
            response = input.nextLine();

            if (response.equalsIgnoreCase("yes"))
            {
                collectItem();
            }
            else if (response.equalsIgnoreCase("no"))
            {
                changeRooms();
            }
            else
            {
                System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
            }
        }
        //if there is no monster, puzzle, or item.
        else
        {
            emptyRoom();
            changeRooms();
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
