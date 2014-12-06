package Model;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3860 Fall 2014
 * Written: 11/16/2014
 *
 * This class represents an inventory.
 *
 * Purpose: Allows storing, retrieval, and removal of items.
 */
public class Inventory
{
    //instances variables
    private final int SIZELIMIT = 10;
    private String[][] ruckSack;
    char[] itemType; //A, E, W for armor, elixir, or weapon
    private static int itemCount;
    private Weapon[] weapons;
    private Elixir[] elixirs;
    private Armor[] armors;

    /**No argument constructor
     */
    public Inventory()
    {
        ruckSack = new String[SIZELIMIT][2];  //contains 10 rows and 2 columns for item name and itemType
        itemType = new char[SIZELIMIT];
        itemCount = 0;
        weapons = new Weapon[SIZELIMIT];
        armors = new Armor[SIZELIMIT];
        elixirs = new Elixir[SIZELIMIT];
    }

    /**Adds a weapon to the ruckSack/inventory and weapons array
     * @param weapon a weapon object
     * @return true or false for successfully adding a weapon to inventory
     */
    public boolean add(Weapon weapon)
    {
        if (itemCount < SIZELIMIT)
        {
            for (int x = 0; x < SIZELIMIT; x++)
            {
                if (weapons[x] == null)
                {
                    for(int p = 0; p < SIZELIMIT; p++)
                    {
                        if (ruckSack[p][0] == null)
                        {
                            weapons[x] = weapon;
                            ruckSack[p][0] = weapons[x].getItemName();
                            ruckSack[p][1] = "w";
                            itemCount++;
                            return true;
                        }
                    }
                }
            }
        }
        System.out.println("You are unable to carry any more items.");
        return false;
    }

    /**Adds an armor to the ruckSack/inventory and armors array
     * @param armor An armor object
     * @return true or false for successfully adding an armor to inventory
     */
    public boolean add(Armor armor)
    {
        if (itemCount < SIZELIMIT)
        {
            for (int x = 0; x < SIZELIMIT; x++)
            {
                if (armors[x] == null)
                {
                    for(int p = 0; p < SIZELIMIT; p++)
                    {
                        if (ruckSack[p][0] == null)
                        {
                            armors[x] = armor;
                            ruckSack[p][0] = armors[x].getItemName();
                            ruckSack[p][1] = "a";
                            itemCount++;
                            return true;
                        }
                    }
                }
            }
        }
        System.out.println("You are unable to carry any more items.");
        return false;
    }

    /**Adds an elixir to the ruckSack/inventory and elixirs array
     * @param elixir An elixir object
     * @return true or false for successfully adding a weapon to inventory
     */
    public boolean add(Elixir elixir)
    {
        if (itemCount < SIZELIMIT)
        {
            for (int x = 0; x < SIZELIMIT; x++)
            {
                if (elixirs[x] == null)
                {
                    for(int p = 0; p < SIZELIMIT; p++)
                    {
                        if (ruckSack[p][0] == null)
                        {
                            elixirs[x] = elixir;
                            ruckSack[p][0] = elixirs[x].getItemName();
                            ruckSack[p][1] = "e";
                            itemCount++;
                            return true;
                        }
                    }
                }
            }
        }
        System.out.println("You are unable to carry any more items.");
        return false;
    }

    /**Locates and removes an item given its name from the ruckSack/inventory and either weapons[],
     * armors[], or elixirs[]
     * @param weaponArmElixName The nme of the item to be removed
     * @return true or false for successfully removing an item from inventory
     */
    public Boolean remove(String weaponArmElixName)
    {
        for(int p = 0; p < SIZELIMIT; p++)
        {
            //locate the item name
            if (ruckSack[p][0] != null && ruckSack[p][0].equalsIgnoreCase(weaponArmElixName))
            {
                //identify the item
                if (ruckSack[p][1].equalsIgnoreCase("w"))
                {
                    for (int x = 0; x < SIZELIMIT; x++)
                    {
                        if (weapons[x] != null && weapons[x].getItemName().equalsIgnoreCase(weaponArmElixName))
                        {
                            weapons[x] = null;
                            ruckSack[p][0] = null;
                            ruckSack[p][1] = null;
                            itemCount--;
                            return true;
                        }
                    }
                }
                else if (ruckSack[p][1].equalsIgnoreCase("e"))
                {
                    int x = 0;
                    while (x < SIZELIMIT)
                    {
//                        System.out.println("Elixir saved name: "+ elixirs[x].getItemName()); //DEBUG CODE
//                        System.out.println("User entered name: " + weaponArmElix);  //DEBUG CODE
                        if (elixirs[x] != null && elixirs[x].getItemName().equalsIgnoreCase(weaponArmElixName))
                        {
                            elixirs[x] = null;
                            ruckSack[p][0] = null;
                            ruckSack[p][1] = null;
                            itemCount--;
//                            x = SIZELIMIT;
                            return true;
                        }
                        x++;
                    }
                }
                else //armor
                {
                    for (int x = 0; x < SIZELIMIT; x++)
                    {
                        if (armors[x] != null && armors[x].getItemName().equalsIgnoreCase(weaponArmElixName))
                        {
                            armors[x] = null;
                            ruckSack[p][0] = null;
                            ruckSack[p][1] = null;
                            itemCount--;
                            return true;
                        }
                    }
                }
            }
        }
        System.out.println(weaponArmElixName + " could not be found in your inventory.");
        return false;
    }

    /**Displays a detailed view of the ruckSack/inventory contents by
     * also displaying the stats on each item
     */
    public void view()
    {
        if (itemCount > 0)
        {
            for (int i = 0; i < ruckSack.length; i++)
            {
                if (ruckSack[i][0] != null)
                {
                    System.out.print("Item type: " + ruckSack[i][1]);
                    System.out.print("\tItem: " + ruckSack[i][0]);

                    if (ruckSack[i][1].equalsIgnoreCase("w"))
                    {
                        System.out.println("\tAttack boost: " + getWeapon(ruckSack[i][0]).getStrength());
                    }
                    else if (ruckSack[i][1].equalsIgnoreCase("a"))
                    {
                        System.out.println("\tDefensive boost: " + getArmor( ruckSack[i][0]).getArmorDefense());
                    }
                    else //elixir
                    {
                        System.out.println("\tHealth boost: " + getElixir(ruckSack[i][0]).getHealthBoost());
                    }
                }
            }
        }
        else
        {
            System.out.println("Your inventory appears to be empty.");
        }
    }

    /**Finds an weapon in the ruckSack/inventory and retrieves the weapon from the weapons array
     * @param weaponName The name of a weapon
     * @return weapons[s] Gets the weapon object
     */
    public Weapon getWeapon(String weaponName)
    {
        for (int s = 0; s < SIZELIMIT; s++)
        {
            if (weapons[s] != null && weapons[s].getItemName().equalsIgnoreCase(weaponName))
            {
                return weapons[s];
            }
        }
        System.out.println("Your inventory appears to be empty.");
        return null;
    }

    /**Finds an elixir in the ruckSack/inventory and retrieves the elixir from the elixirs array
     * @param elixirName The name of an elixir
     * @return elixirs[s] Gets the elixir object
     */
    public Elixir getElixir(String elixirName)
    {
        for (int s = 0; s < SIZELIMIT; s++)
        {
            if (elixirs[s] != null && elixirs[s].getItemName().equalsIgnoreCase(elixirName))
            {
                return elixirs[s];
            }
        }
        System.out.println("Your inventory appears to be empty.");
        return null;
    }

    /**Finds an armor in the ruckSack/inventory and retrieves the armor from the armors array
     * @param armorName The name of an armor
     * @return armors[s] Gets the armor object
     */
    public Armor getArmor(String armorName)
    {
        for (int s = 0; s < SIZELIMIT; s++)
        {
            if (armors[s] != null && armors[s].getItemName().equalsIgnoreCase(armorName))
            {
                return armors[s];
            }
        }
        System.out.println("Your inventory appears to be empty.");
        return null;
    }

    /**Confirms an item exist in the ruckSack/inventory
     * @param weaponArmElixName The name of an item
     * @return true or false Gets the existence of an item
     */
    public Boolean confirmItem(String weaponArmElixName)
    {
        int h = 0;

        if (itemCount > 0 && weaponArmElixName != null)
        {
            while (h <= itemCount)
            {
                if (ruckSack[h][0] != null && weaponArmElixName.equalsIgnoreCase(ruckSack[h][0]))
                {
                    return true;
                }
                h++;
            }
            System.out.println("No item with the name: " + weaponArmElixName + " could be found in your inventory. Check your spelling and try again.");
        }
        else
        {
            System.out.println("Your inventory appears to be empty.");
        }
        return false;
    }

    /**Identifies the item type based on the item's name
     * @param weaponArmElixName The name of an item
     * @return ruckSack[s][1] Gets the item type a, e or w
     */
    public String getItemType(String weaponArmElixName)
    {
        for (int s = 0; s < SIZELIMIT; s++)
        {
            if (ruckSack[s][0] != null && ruckSack[s][0].equalsIgnoreCase(weaponArmElixName))
            {
                return ruckSack[s][1];
            }
        }
        System.out.println("An item type for " + weaponArmElixName + " could not be found in your inventory."); //This will come out the final version
        return null;
    }

    /**
     * @return ruckSack Gets the ruckSack
     */
    public String[][] getRuckSack()
    {
        return ruckSack;
    }
}