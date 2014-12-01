package View;

import Controller.Controller;
import Model.*;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * author: JJ Lindsay
 * version: 4.1
 * Course: ITEC 3860 Fall 2014
 * Written: 11/16/2014
 *
 * This class represents a ...
 *
 * Purpose: Allows the manipulation of a ...
 */
public class Game
{
    private static Hero player;
    private static Monster monster;
    private static Armor armor;
    private static Weapon weapon;
    private static Elixir elixir;
    private static Puzzle puzzle;
    private static Rooms rooms; //should only be used in the creation of roomsMap
    private static int currentRoom;
    private static String[] heroInventory;
    private static Map<Integer,Rooms> roomsMap; //Map<room, roomObj>
    private static String loginName;
    private static Boolean loginResults = false;

    private static Scanner input = new Scanner(System.in);


    public static boolean login()
    {
        System.out.println("Enter your user name: ");
        loginName = input.nextLine();

        //verify user account exist
        if (Controller.loginAccount(loginName))
        {
            //get saved player data
            String[] loginDetails = Controller.loadHero(loginName).split("[|]");
            // player(ID, name, score, health)
            player = new Hero(Integer.parseInt(loginDetails[0]), loginDetails[1], Integer.parseInt(loginDetails[3]), Integer.parseInt(loginDetails[4]));

            //check for an inventory
            if (!loginDetails[2].equalsIgnoreCase("0"))
            {
                int p = 0;
                player.createInventory();
                heroInventory = Controller.loadHeroInventory(player.getPlayerID()).split("[|]");

                while(p < heroInventory.length-2)
                {
                    //checks if a weaponID exists
                    if (!heroInventory[p].equalsIgnoreCase("0"))
                    {
                        //get the weapon from the database
                        String[] dbWeapon = Controller.retrieveWeapon(Integer.parseInt(heroInventory[p])).split("[|]");
                        //builds an weapon(name, strength)
                        weapon = new Weapon(dbWeapon[0], Integer.parseInt(dbWeapon[1]));
                        player.getInventory().add(weapon);
                    }
                    //checks if a armorID exists
                    else if (!heroInventory[p+1].equalsIgnoreCase("0"))
                    {
                        //get the armor from the database
                        String[] dbArmor = Controller.retrieveArmor(Integer.parseInt(heroInventory[p + 1])).split("[|]");
                        //builds an armor(name, defenseBoost)
                        armor = new Armor(dbArmor[0], Integer.parseInt(dbArmor[1]));
                        player.getInventory().add(armor);
                    }
                    //checks if a elixirID exists
                    else if (!heroInventory[p+2].equalsIgnoreCase("0"))
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
        String name = input.nextLine();

        if (!Controller.loginAccount(name))
        {
            //defaults to playerID 0 until save is called
            player = new Hero(name, 0);
//            player = new Hero(name, Controller.createAccount(name));
            return true;
        }
        System.out.println("An account with that name already exist.");
        return false;
    }

    public static void playGame()
    {
        Boolean waiting;
        System.out.println("Welcome to Dragon's Keep!");

        do
        {
            System.out.println("Enter 1 to login or 2 to create a new account");
            String in = input.nextLine();

            if (in.equalsIgnoreCase("1"))
            {
                //necessary to load saved rooms in loadGame()
                loginResults = login();
                if (loginResults)
                {
                    waiting = false;
                    System.out.println("Account located. Loading game...");
                }
                else
                {
                    waiting = true;
                    System.out.println("Could not locate your user name");
                }
            } else if (in.equalsIgnoreCase("2"))
            {
                //the main menu loops if user account already exist
                waiting = !createAccount();
                if (!waiting){System.out.println("Your account was created!");}
            } else
            {
                waiting = true;
                System.err.println("Error interpreting your last request.");
            }
        }while(waiting);
        loadGame();
        enteredRoom();
    }

    public static void quitGame()
    {
    	System.out.println("Do you want to save your game before closing? (yes/no)");
    	String in = input.nextLine();
    	if(in.equalsIgnoreCase("yes"))
    	{
    		saveGame();
    		System.exit(0);
    	}
    	else if(in.equalsIgnoreCase("no"))
    	{
    		System.exit(0);
    	}
    	else
    	{
    		System.out.println("Error interpreting your last request.");
    		quitGame();
    	}

    }

    public static void saveGame()
    {
        //an Account is created in the database and the ID is set if the user did not login
        if (!loginResults)
        {
            player.setPlayerID(Controller.createAccount(player.getName()));
        }


        String heroData = player.getPlayerID() + "|" + player.getName() + "|";
        if (player.getInventory() != null)
        {
            heroData += "1" + "|";
        }
        else
        {
            heroData += "0" + "|";
        }
        heroData += player.getScore() + "|" + player.getHealth();
        //saves the player ID, name, hasInventory, score, and health to the database
        Controller.saveHeroData(heroData);  //testing with fixed values

        //saves the player's inventory
        Controller.saveHeroInventory(player.getPlayerID(), player.getInventory().getRuckSack());

        //saves the state of all the rooms for this player
        String savedRooms = player.getPlayerID() + "|" + currentRoom;
        for (int i = 1; i <= 50;  i++)
        {
            if (roomsMap.get(i).getIsEmpty() == 0)
            {
                savedRooms += "|" + 0;
            }
            else
            {
                savedRooms += "|" + 1;
            }
        }
        Controller.saveRoomState(savedRooms);
    }

    public static void loadGame()
    {
        String[] allRooms = Controller.loadAllRooms().split("[|]");
        String[] temp = new String[4];
        roomsMap = new TreeMap<Integer, Rooms>();

        for (int i = 0; i < allRooms.length-11; i= i+12)
        {
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
        currentRoom = 1;

        //user successfully logged into saved account
        if (loginResults)
        {
            String[] savedRooms = Controller.loadSavedRooms(player.getPlayerID()).split("[|]");

            if (Integer.parseInt(savedRooms[0]) == player.getPlayerID())
            {
                //retrieves the last recorded current room for this playerID
                currentRoom = Integer.parseInt(savedRooms[1]);

                //is the room empty (1 for true, 0 for false)
                for (int y = 2; y <= currentRoom+1; y++)
                {
                    //if the room is empty
                    if (Integer.parseInt(savedRooms[y]) == 1)
                    {
                        roomsMap.get(y-1).setRoomDescription("This room is empty... and it looks a bit familiar");
                        roomsMap.get(y-1).setIsEmpty(1);
                        roomsMap.get(y-1).setIsArmor(0);
                        roomsMap.get(y-1).setIsElixir(0);
                        roomsMap.get(y-1).setIsWeapon(0);
                        roomsMap.get(y-1).setIsPuzzle(0);
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
        boolean looping;
        System.out.println("*****************************************");

        do
        {
            System.out.println("-----------------------------------------");
            System.out.println("Enter \"inventory\" to check inventory. \nEnter \"equip item name\" to equip a specific item in inventory." +
                    "\nEnter \"remove item name\" to throw away an item. \nEnter \"attack\" to start the fight. \nEnter \"run away\" to escape.");
            System.out.println("-----------------------------------------");
            System.out.println("Your health is currently: " + player.getHealth());
            System.out.println(monster.getName() + "'s health is: " + monster.getHealth());
            System.out.println("-----------------------------------------");

            String response = input.nextLine();

            if (response.length() > 5)
            {
                if (response.equalsIgnoreCase("inventory"))
                {
                    player.getInventory().view();
                    looping = true;
                } else if (response.length() > 5 && response.substring(0, 5).equalsIgnoreCase("equip"))
                {
                    //check if the item exist in inventory
                    if (player.getInventory().confirmItem(response.substring(6)))
                    {
                        //identify the item as a weapon, armor, or elixir by its itemType
                        if (player.getInventory().getItemType(response.substring(6)).equalsIgnoreCase("w"))
                        {
                            player.setAttackPower(player.getInventory().getWeapon(response.substring(6)).getStrength());
                            System.out.println("You have drawn your " + response.substring(6));
                        } else if (player.getInventory().getItemType(response.substring(6)).equalsIgnoreCase("a"))
                        {
                            player.setDefenseStrength(player.getInventory().getArmor(response.substring(6)).getArmorDefense());
                            System.out.println("You have put on the " + response.substring(6));
                        } else if (player.getInventory().getItemType(response.substring(6)).equalsIgnoreCase("e"))
                        {
                            player.setHealth(player.getHealth() + player.getInventory().getElixir(response.substring(6)).getHealthBoost());
                            System.out.println("You drank all of the " + response.substring(6));
                            player.getInventory().remove(response.substring(6));
                        }
                    } else
                    {
                        System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
                    }
                    looping = true;
                }
                else if (response.length() > 6 && response.substring(0, 6).equalsIgnoreCase("remove"))
                {
                    //check if the item exist in inventory
                    if (player.getInventory().confirmItem(response.substring(7)))
                    {
                        //remove item from inventory
                        player.getInventory().remove(response.substring(7));
                        System.out.println("You have successfully removed " + response.substring(7) + " from your inventory");

                    } else
                    {
                        System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
                    }
                    looping = true;
                }
                else if (response.equalsIgnoreCase("attack"))
                {
                    //player attacks first
                    if (monster.getHealth() - player.getAttackPower() > 0)
                    {
                        System.out.println("You lunged at " + monster.getName() + " but your attack wasn't good enough to bring'em down.");
                        monster.setHealth(monster.getHealth() - player.getAttackPower());
                        looping = true;
                    } else
                    {
                        System.out.println("You dealt a deadly blow with that last move! You killed " + monster.getName() + ".");
                        //set monster to zero for this room
                        roomsMap.get(currentRoom).setIsMonster(0);

                        //Game won message
                        if (50 == currentRoom)
                        {
                            System.out.println("You have rid Dragons Keep of its corrupted emperor. Thanks to you " + player.getName() + " what evil" +
                            " was brewing just beneath the city wont be unleashed anytime soon...");
                            System.out.println("\n-----------------------------------------------------------------------\n");
                            System.out.println("Congratulations on successfully completing the game Dragons Keep." +
                            "\nIt was a pleasure making this and we hope you enjoyed it! \n Like a challenge? Try completing all 50 rooms and solving all puzzles.");
                            quitGame();
                        }

                        looping = false;
                        continue; //prevents the next if from executing
                    }
                    //Monster retaliates
                    System.out.println("Your last attack didn't defeat " + monster.getName() + " and you've been wounded by a counter-attack.");
                    if ((player.getHealth() + player.getDefenseStrength())   - monster.getAttackPower() > 0)
                    {
                        player.setHealth(player.getHealth() - monster.getAttackPower());
                    } else
                    {
                        System.out.println("Your losing a lot of blood, you don't know how." + monster.getName() + "attacked you so fast! \nYour stumbling towards the door..." +
                                "\nyou've got to get out of here, you think to yourself.\nIt's no use. You collapse on the ground before even reaching door. Just before everything" +
                                " goes black, you think of the\npeople who were depending on you, and with your last breath you whisper I'm sorry.");
                        System.out.println("*****************************************\n");
                        System.out.println("---------------GAME OVER-----------------\n");
                        System.out.println("*****************************************");
                        //player restarts at the main screen
                        playGame();
                        //go back to last save or end game??
                    }
                } else if (response.equalsIgnoreCase("run away"))
                {
                    System.out.println("You ran away screaming; your heart is racing." +
                            "\nRefusing to look back, you think to yourself, that monster can't POSSIBLY get any stronger...");
                    monster.setHealth(monster.getHealth() + 5);
                    looping = false;
                } else
                {
                    System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
                    looping = true;
                }
            }
            else
            {
                System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
                looping = true;
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

            //Adds direction to the empty rooms
            String roomDirection = "<";
            //checks the 4 possible exits
            for (int x = 0; x < 4; x++)
            {
                //if an exit exist
                if (Integer.parseInt(roomsMap.get(currentRoom).getChoices()[x]) != 0)
                {
                    if (x == 0)
                    {
                        roomDirection += "E";
                    } else if (x == 1)
                    {
                        roomDirection += "N";
                    } else if (x == 2)
                    {
                        roomDirection += "S";
                    } else
                    {
                        roomDirection += "W";
                    }
                }
            }
            roomDirection += ">";
            roomsMap.get(currentRoom).setRoomDescription(roomDirection + " This room is empty... and it looks a bit familiar.");
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

        } else
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

    //this is how puzzle solving is handled
    public static void solvePuzzle()
    {
        //create and puzzle from the db
        String[] dbPuzzle = Controller.retrievePuzzle(roomsMap.get(currentRoom).getIsPuzzle()).split("[|]");
        puzzle = new Puzzle(dbPuzzle[0], dbPuzzle[1], dbPuzzle[2], dbPuzzle[3], Integer.parseInt(dbPuzzle[4]));
        Boolean repeatPuzzle = false;

        do
        {
            System.out.println(puzzle.getPuzzle());
            String response = input.nextLine();

            if (puzzle.getSolution().equalsIgnoreCase(response))
            {
                repeatPuzzle = false;
                System.out.println(puzzle.getSuccessMessage());
                roomsMap.get(currentRoom).setIsPuzzle(0);
            }
            else
            {
                System.out.println(puzzle.getFailureMessage());
                Boolean unclearResponse;

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

    public static void gameMenu()
    {
        boolean looping;
        do
        {
            System.out.println("-----------------------------------------");
            System.out.println("Enter \"inventory\" to check inventory. \nEnter \"equip item name\" to equip a specific item in inventory." +
                    "\nEnter \"remove item name\" to throw away an item. \nEnter \"save\" to save your game. \nEnter \"quit\" to quit the game. \nEnter \"exit\" to return to game");
            System.out.println("-----------------------------------------");
            System.out.println("Your health is currently: " + player.getHealth());
            System.out.println("-----------------------------------------");

            String response = input.nextLine();

            if (response.equalsIgnoreCase("inventory"))
            {
                if (player.getInventory() == null)
                {
                    System.err.println("Opps! You don't have an inventory.");
                }
                else
                {
                    player.getInventory().view();
                }
                looping = true;
            }
            else if (response.equalsIgnoreCase("exit"))
            {
                changeRooms();
                looping = false;
            }
            else if (response.equalsIgnoreCase("save"))
            {
                saveGame();
                looping = true;
            }
            else if (response.equalsIgnoreCase("quit"))
            {
                quitGame();
                looping = false;
            }
            //currently broken
            else if (response.length() > 5 && response.substring(0, 5).equalsIgnoreCase("equip"))
            {
                if (player.getInventory() == null)
                {
                    System.err.println("Opps! You don't have an inventory.");
                }
                else
                {
                    //check if the item exist in inventory
                    if (player.getInventory().confirmItem(response.substring(6)))
                    {
                        //identify the item as a weapon, armor, or elixir by its itemType
                        if (player.getInventory().getItemType(response.substring(6)).equalsIgnoreCase("w"))
                        {
                            player.setAttackPower(player.getInventory().getWeapon(response.substring(6)).getStrength());
                            System.out.println("You have drawn your " + response.substring(6));
                        } else if (player.getInventory().getItemType(response.substring(6)).equalsIgnoreCase("a"))
                        {
                            player.setDefenseStrength(player.getInventory().getArmor(response.substring(6)).getArmorDefense());
                            System.out.println("You have put on the " + response.substring(6));
                        } else if (player.getInventory().getItemType(response.substring(6)).equalsIgnoreCase("e"))
                        {
                            player.setHealth(player.getHealth() + player.getInventory().getElixir(response.substring(6)).getHealthBoost());
                            System.out.println("You drank all of the " + response.substring(6));
                            player.getInventory().remove(response.substring(6));
                        }
                    } else
                    {
                        System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
                    }
                }
                looping = true;
            }
            else if(response.length() > 6 && response.substring(0, 6).equalsIgnoreCase("remove"))
            {
                if (player.getInventory() == null)
                {
                    System.err.println("Opps! You don't have an inventory.");
                }
                else
                {
                    //check if the item exist in inventory
                    if (player.getInventory().confirmItem(response.substring(7)))
                    {
                        //remove item from inventory
                        player.getInventory().remove(response.substring(7));
                        System.out.println("You have successfully removed " + response.substring(7) + " from your inventory");

                    } else
                    {
                        System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
                    }
                }
                looping = true;
            }
            else
            {
                System.err.println("There was an error in trying to make sense of you request. Check your spelling.");
                looping = true;
            }
        }while(looping);
    }

    public static void changeRooms()
    {
        Boolean loop;

        //display possible exits
        String roomDirection = "<";
        //checks the 4 possible exits
        for (int x = 0; x < 4; x++)
        {
            //if an exit exist
            if (Integer.parseInt(roomsMap.get(currentRoom).getChoices()[x]) != 0)
            {
                if (x == 0)
                {
                    roomDirection += "E";
                } else if (x == 1)
                {
                    roomDirection += "N";
                } else if (x == 2)
                {
                    roomDirection += "S";
                } else
                {
                    roomDirection += "W";
                }
            }
        }
        roomDirection += "> ";

        do
        {
            System.out.println(roomDirection + "Where would you like to go next or enter \"menu\" to pull up the game menu.");
            String response = input.nextLine();

            if (response.equalsIgnoreCase("menu"))
            {
                loop = false;
                gameMenu();
            }
            else if (response.equalsIgnoreCase("head East") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[0]) != 0)
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

        do
        {
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

    public static void main(String[] args)
    {
        playGame();
    }
}
