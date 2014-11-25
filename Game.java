package View;

import Controller.Controller;
import Model.*;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.apache.commons.lang3.text.WordUtils;

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
//    private static int[] roomOptions;
    //private static char itemType;
    //private static String logInName;
   // private static String saveHero;
    //private static String saveRoomStates;
    private static String[] heroInventory;
    private static Map<Integer,Rooms> roomsMap; //Map<room, roomObj>
    private static String loginName;
    private static Boolean loginResults = false;

    private static Scanner input = new Scanner(System.in);


    public static boolean login()
    {
        System.out.println("Enter your user name: ");
//        input.nextLine(); //necessary since the previous use was for reading an int UNTESTED!!!
        loginName = input.nextLine();

        //verify user account
        if (Controller.loginAccount(loginName))
        {
            //get saved player data
            String[] loginDetails = Controller.loadHero(loginName).split("[|]");
            // player(ID, name, score, health)
            player = new Hero(Integer.parseInt(loginDetails[0]), loginDetails[1], Integer.parseInt(loginDetails[3]), Integer.parseInt(loginDetails[4]));

            //check for an inventory
            if (loginDetails[2] != null)
            {
                int p = 0;
                player.createInventory();
                heroInventory = Controller.loadHeroInventory(player.getPlayerID()).split("[|]");

                while(p < heroInventory.length-2)
                {
                    //checks if a weaponID exists
                    if (heroInventory[p] != null)
                    {
                        //get the weapon from the database
                        String[] dbWeapon = Controller.retrieveWeapon(Integer.parseInt(heroInventory[p])).split("[|]");
                        //builds an weapon(name, strength)
                        weapon = new Weapon(dbWeapon[0], Integer.parseInt(dbWeapon[1]));
                        player.getInventory().add(weapon);
                    }
                    //checks if a armorID exists
                    else if (heroInventory[p+1] != null)
                    {
                        //get the armor from the database
                        String[] dbArmor = Controller.retrieveArmor(Integer.parseInt(heroInventory[p + 1])).split("[|]");
                        //builds an armor(name, defenseBoost)
                        armor = new Armor(dbArmor[0], Integer.parseInt(dbArmor[1]));
                        player.getInventory().add(armor);
                    }
                    //checks if a elixirID exists
                    else if (heroInventory[p+2] != null)
                    {
                        //get the elixir from the database
                        String[] dbElixir = Controller.retrieveElixir(Integer.parseInt(heroInventory[p+2])).split("[|]");
                        //builds an elixir(name, healthBoost)
                        elixir = new Elixir(dbElixir[0], Integer.parseInt(dbElixir[1]));
                        player.getInventory().add(elixir);
                    }
                    p+=3;
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    //(!)(!)ERROR:: Somehow ensure player does not enter the same name as another player.
    //CALL login Account FIRST to ensure it comes back false for no name found!
    public static boolean createAccount()
    {
        System.out.println("Enter a name you would like to create for your account: ");
//        input.nextLine(); // necessary since the previous use was for reading an int
        String name = input.nextLine();

        //BEFORE creating Hero, verify the name is unique... somehow
        player = new Hero(name, Controller.createAccount(name));

        return true;
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
            String in = input.nextLine();

            if (in.equalsIgnoreCase("1"))
            {
                loginResults = login();
                if (loginResults)
                {
                    loadGame();
                    waiting = false;
                }
                else
                {
                    System.out.println("Could not locate your user name");
                }
            } else if (in.equalsIgnoreCase("2"))
            {
                createAccount();
                System.out.println("Created your Account!");  //Debug Purposes

                waiting = false;
            } else
            {
                System.err.println("Error interpreting your last request.");
            }
        }
        loadGame();

        // First time player enters here
        enteredRoom();


    }

    //This may be optional
    public static void quitGame()
    {

    }

    public static void saveGame()
    {

    }

    //Not complete!!!
    public static void loadGame()
    {
//        System.out.println("Entering\nload\ngame!");  //Debug Purposes

        String[] allRooms = Controller.loadAllRooms().split("[|]");
        String[] temp = new String[4];
        roomsMap = new TreeMap<Integer, Rooms>();

//        System.out.println("collected all db rooms. Size of allRooms is: " + allRooms.length);  //Debug Purposes
        for (int i = 0; i < allRooms.length-11; i= i+12)
        {
//            System.out.println("loading dbrooms into game");  //Debug Purposes

            rooms = new Rooms();
            temp[0] = allRooms[i+1];
            temp[1] = allRooms[i+2];
            temp[2] = allRooms[i+3];
            temp[3] = allRooms[i+4];
            rooms.setChoices(temp);

            rooms.setRoomDescription(allRooms[i + 5]);  //THIS WOULD CHANGE FOR LOGIN
            rooms.setIsEmpty(Integer.parseInt(allRooms[i + 6]));  //THIS WOULD CHANGE FOR LOGIN
            rooms.setIsArmor(Integer.parseInt(allRooms[i + 7])); //points to the specific armor
            rooms.setIsElixir(Integer.parseInt(allRooms[i + 8])); //points to the specific elixir
            rooms.setIsWeapon(Integer.parseInt(allRooms[i + 9])); //points to the specific weapon
            rooms.setIsMonster(Integer.parseInt(allRooms[i + 10])); //points to the specific monster
            rooms.setIsPuzzle(Integer.parseInt(allRooms[i + 11])); //points to the specific puzzle

            //converts roomID from string to int and stores roomID as key and the room created in a map
            roomsMap.put(Integer.parseInt(allRooms[i]), rooms);
        }
//        System.out.println("have all rooms. Setting current room to 1");  //Debug Purposes

        currentRoom = 1;


        //user successfully logged into saved account
        if (loginResults)
        {
            String[] savedRooms = Controller.loadSavedRooms(player.getPlayerID()).split("[|]");
            if (Integer.parseInt(savedRooms[0]) == player.getPlayerID())
            {
                //retrieves the last recorded current room for this playerID
                currentRoom = Integer.parseInt(savedRooms[1]);

                //emptyPlayerRooms 0 = isEmpty false and 1 = isEmpty true
                for (int y = 2; y <= 51; y++)
                {
                    //if is emptyPlayerRooms is 1 (i.e. true)
                    if (Integer.parseInt(savedRooms[y]) == 1)
                    {
                        roomsMap.get(y).setRoomDescription("This room is empty... and it looks a bit familiar");
                        roomsMap.get(y).setIsEmpty(1);
                        roomsMap.get(y).setIsArmor(0);
                        roomsMap.get(y).setIsElixir(0);
                        roomsMap.get(y).setIsWeapon(0);
                        roomsMap.get(y).setIsPuzzle(0);
                    }
                }
            }
        }
    }

    public static void battle()
    {
        //create monster
        String[] dbMonster = Controller.retrieveMonster(roomsMap.get(currentRoom).getIsMonster()).split("[|]");
        monster = new Monster(dbMonster[0], Integer.parseInt(dbMonster[1]), Integer.parseInt(dbMonster[2]));
        boolean looping = false;
        System.out.println("*****************************************");

        //LOOP THIS for? while? do while?
        do
        {
            System.out.println("-----------------------------------------");
            System.out.println("Enter \"inventory\" to check inventory. \nEnter \"equip item name\" to equip a specific item in inventory." +
                    "\nEnter \"attack\" to start the fight.");
            System.out.println("-----------------------------------------");
            System.out.println("Your health is currently: " + player.getHealth());
            System.out.println(monster.getName() + "'s health is: " + monster.getHealth());
            System.out.println("-----------------------------------------");

            String response = input.nextLine();

            //DEBUG PURPOSES

//            Weapon weapon1 = (Weapon)(player.getInventory().getItem("dagger"));
//            System.out.println("Strength2 is " + weapon1.getStrength()); //gave junk
//
//

            //END OF DEBUG CODE



            if (response.equalsIgnoreCase("inventory"))
            {
                player.getInventory().view();
                looping = true;
            }
            //currently broken
            else if (response.substring(0, 5).equalsIgnoreCase("equip"))
            {
                //check that item exits
                if (player.getInventory().confirmItem(response.substring(6)))
                {
                    if (player.getInventory().getItemType(response.substring(6)).equalsIgnoreCase("w"))
                    {
                        player.setAttackPower(player.getInventory().getWeapon(response.substring(6)).getStrength());
                    } else if (player.getInventory().getItemType(response).equalsIgnoreCase("a"))
                    {
                        player.setDefenseStrength(player.getInventory().getArmor(response.substring(6)).getArmorDefense());
                    } else if (player.getInventory().getItemType(response).equalsIgnoreCase("e"))
                    {
                        player.setHealth(player.getInventory().getElixir(response.substring(6)).getHealthBoost());
                    }
                }
                else
                {
                    System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
                }
                looping = true;
            } else if (response.equalsIgnoreCase("attack"))
            {
                //LOOP this
                //player attacks first
                if (monster.getHealth() - player.getAttackPower() > 0)
                {
                    System.out.println("You lunged at " + monster.getName() + " but your attack wasn't good enough to bring'em down.");
                    monster.setHealth(monster.getHealth() - player.getAttackPower());
                }
                else
                {
                    //temporary phrase
                    System.out.println("You dealt a deadly blow with that last move! You killed " + monster.getName() + ".");
                    //set monster to zero for this room
                    roomsMap.get(currentRoom).setIsMonster(0);
                    looping = false;
                    continue;
                    //player will auto return to enterRoom() to retrieve an item or continue further in the game


                }
                //Monster retaliates
                System.out.println("Your last attack didn't defeat " + monster.getName() + ". You only succeeded in making " + monster.getName() + " angry.");
                if (player.getHealth() - monster.getAttackPower() > 0)
                {
                    player.setHealth(player.getHealth() - monster.getAttackPower());
                } else
                {
                    //temporary phrase
                    System.out.println("Your losing a lot of blood, you don't know how." + monster.getName() + "attacked you so fast! Your stumbling towards the door..." +
                            "\nyou've got to get out of here, you think to yourself.\nIt's no use. You collapse on the ground before even reaching door. Just before everything" +
                    " goes black, you think of the\npeople who were depending on you, and with your last breath you whisper I'm sorry.");
                    //go back to last save or end game
                }
            } else
            {
                System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
            }

        }while(looping);


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
            String[] dbArmor = Controller.retrieveArmor(roomsMap.get(currentRoom).getIsArmor()).split("[|]");
            //builds an armor(name, defenseBoost)
            armor = new Armor(dbArmor[0], Integer.parseInt(dbArmor[1]));
            player.getInventory().add(armor);
            roomsMap.get(currentRoom).setIsArmor(0);

            System.out.println("You have found " + dbArmor[0] + " and added it to your inventory.");

        } else if (roomsMap.get(currentRoom).getIsElixir() > 0)
        {
            //get an elixir from the database
            String[] dbElixir = Controller.retrieveElixir(roomsMap.get(currentRoom).getIsElixir()).split("[|]");
            //builds an elixir(name, healthBoost)
            elixir = new Elixir(dbElixir[0], Integer.parseInt(dbElixir[1]));
            player.getInventory().add(elixir);
            roomsMap.get(currentRoom).setIsElixir(0);

            System.out.println("You have found " + dbElixir[0] + " and added it to your inventory.");

        } else //if (roomsMap.get(currentRoom).getIsWeapon() > 0)
        {
            //get an weapon from the database
            String[] dbWeapon = Controller.retrieveWeapon(roomsMap.get(currentRoom).getIsWeapon()).split("[|]");
            //builds an weapon(name, strength)
            weapon = new Weapon(dbWeapon[0], Integer.parseInt(dbWeapon[1]));
            player.getInventory().add(weapon);
            roomsMap.get(currentRoom).setIsWeapon(0);

            System.out.println("You have found " + dbWeapon[0] + " and added it to your inventory.");
        }

    }

    //Pretty much complete
    public static void solvePuzzle()
    {
        //create and puzzle from the db
        String[] dbPuzzle = Controller.retrievePuzzle(roomsMap.get(currentRoom).getIsPuzzle()).split("[|]");
        puzzle = new Puzzle(dbPuzzle[0], dbPuzzle[1], dbPuzzle[2], dbPuzzle[3], Integer.parseInt(dbPuzzle[4]));
        Boolean repeatPuzzle = false;

        //Loop
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
                Boolean unclearResponse;

                //Loop This
                do
                {
                    System.out.println("Try again? (yes/no)");
                    response = input.nextLine();
                    if (response.equalsIgnoreCase("yes"))
                    {
                        unclearResponse = false;
                        repeatPuzzle = true;

                    } else if (response.equalsIgnoreCase("no"))
                    {
                        unclearResponse = false;
                        repeatPuzzle = false;
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
//        for (int check = 0; check <= 4; check++)
//        {
//            System.out.println(roomsMap.get(currentRoom).getChoices()[check]);
//        }
//            System.out.println();
            Boolean loop;

        //LOOP THIS
        do
        {
            System.out.println("Where would you like to go next?");
            String response = input.nextLine();

            if (response.equalsIgnoreCase("head East") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[0]) != 0)
            {
                currentRoom = Integer.parseInt(roomsMap.get(currentRoom).getChoices()[0]);
                loop = false;
                enteredRoom();
            } else if (response.equalsIgnoreCase("head NORTH") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[1]) != 0)
            {
                currentRoom = Integer.parseInt(roomsMap.get(currentRoom).getChoices()[1]);
                loop = false;
                enteredRoom();
            } else if (response.equalsIgnoreCase("head South") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[2]) != 0)
            {
                currentRoom = Integer.parseInt(roomsMap.get(currentRoom).getChoices()[2]);
                loop = false;
                enteredRoom();
            } else if (response.equalsIgnoreCase("head West") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[3]) != 0)
            {
                currentRoom = Integer.parseInt(roomsMap.get(currentRoom).getChoices()[3]);
                loop = false;
                enteredRoom();
            }
            // if the user correctly enters a direction but there is no room in that direction.
            else if (response.equalsIgnoreCase("head East") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[0]) == 0 ||
                    response.equalsIgnoreCase("head NORTH") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[1]) == 0 ||
                    response.equalsIgnoreCase("head South") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[2]) == 0 ||
                    response.equalsIgnoreCase("head West") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[3]) == 0)
            {
                System.err.println("This is embarrassing. You entered: " + response + " and walked into a wall. There is no door that way. Try again.");
                loop = true;
            }
            // the user entered something else
            else
            {
                System.err.println("There was an error in trying to make sense of you request. Check your spelling and try again.");
                loop = true;
            }
        } while(loop);
    }

    public static void enteredRoom()
    {
        System.out.println(roomsMap.get(currentRoom).getRoomDescription());
        String response;
        boolean loop;
        //NOTE: To deal with rooms with puzzle + item or monster + item

        //LOOP THIS
        do
        {
            //This first if is NEW and needs to be improved/written better
            //rucksack is not an item, monster or puzzle so this is necessary!
            if (2 == currentRoom && player.getInventory() == null)
            {
                System.out.println("Do you want to collect the rucksack? (yes/no)");
                response = input.nextLine();

                if (response.equalsIgnoreCase("yes"))
                {
                    loop = true;
                    player.createInventory();
                }
                //The user may get a different response in the final game so this is Tentative
                else if (response.equalsIgnoreCase("no"))
                {
                    System.out.println("You shake your head no but then hear a terrifying scream of someone further in." + "" +
                            "\nIt sounds like they most certainly met their demise... or wish they had. You look back" +
                            "down on the dirty, stained rucksack and suddenly it doesn't look so bad. Maybe it could come in handy." +
                            "You've acquired a rucksack which gives you access to an inventory for holding 10 items");

                    loop = true;
                    player.createInventory();
                } else
                {
                    loop = true;
                    System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
                }
            }
            //checks if an monster is in the room
            else if (roomsMap.get(currentRoom).getIsMonster() > 0)
            {
                System.out.println("Are you going to fight the monster? (yes/no)");
                response = input.nextLine();

                if (response.equalsIgnoreCase("yes"))
                {
                    loop = true;
                    battle();
                } else if (response.equalsIgnoreCase("no"))
                {
                    loop = false;
                    changeRooms();
                } else
                {
                    loop = true;
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
                    loop = true;
                    solvePuzzle();
                } else if (response.equalsIgnoreCase("no"))
                {
                    loop = false;
                    changeRooms();
                } else
                {
                    loop = true;
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
                    loop = true;
                    collectItem();
                } else if (response.equalsIgnoreCase("no"))
                {
                    loop = false;
                    changeRooms();
                } else
                {
                    loop = true;
                    System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
                }
            }
            //if there is no monster, puzzle, or item.
            else
            {
                loop = false;
                emptyRoom();
                changeRooms();
            }
        }while(loop);
    }

    // optional
    public static void help()
    {

    }


    public static void main(String[] args)
    {
//DEBUG PURPOSE        String word = new String("equip1sword");
//DEBUG PURPOSE        System.out.println("Your word is equip sword before substring. After: " + word.substring(6));
        playGame();
//        String name = "Abc\nBCD\nEfG";
//        String[] tempName = name.split("[\n]");
//        System.out.println(tempName[0]);
//        System.out.println(tempName[1]);
//        System.out.println(tempName[2]);

//        StringBuilder returned = new StringBuilder();
//        returned.append(tempName[0]);
//        returned.append("\n");
//        returned.append(tempName[1]);
//        returned.append("\n");
//        returned.append(tempName[2]);
//
//        System.out.println(returned);
    }
}
