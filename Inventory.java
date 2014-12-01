package Model;

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
public class Inventory
{
    private final int SIZELIMIT = 10;
    private String[][] ruckSack;
    char[] itemType; //A, E, W for armor, elixir, or weapon
    private static int itemCount;
    private Weapon[] weapons;
    private Elixir[] elixirs;
    private Armor[] armors;

    //Creates an empty Inventory
    public Inventory()
    {
        //10rows, 2 columns -- item name / itemType
        ruckSack = new String[SIZELIMIT][2];
        itemType = new char[SIZELIMIT];
        itemCount = 0;
        weapons = new Weapon[SIZELIMIT];
        armors = new Armor[SIZELIMIT];
        elixirs = new Elixir[SIZELIMIT];
    }

    //Adds an item to the inventory
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


    //Removes an item from the inventory
    public void remove(String weaponArmElix)
    {
        for(int p = 0; p < SIZELIMIT; p++)
        {
            //locate the item name
            if (ruckSack[p][0] != null && ruckSack[p][0].equalsIgnoreCase(weaponArmElix))
            {
                //identify the item
                if (ruckSack[p][1].equalsIgnoreCase("w"))
                {
                    for (int x = 0; x < SIZELIMIT; x++)
                    {
                        if (weapons[x].getItemName().equalsIgnoreCase(weaponArmElix))
                        {
                            weapons[x] = null;
                            ruckSack[p][0] = null;
                            ruckSack[p][1] = null;
                            itemCount--;
                            break;
                        }
                    }
                }
                else if (ruckSack[p][1].equalsIgnoreCase("e"))
                {
                    int x = 0;
                    while (x < SIZELIMIT)
                    {
                        if (elixirs[x].getItemName().equalsIgnoreCase(weaponArmElix))
                        {
                            elixirs[x] = null;
                            ruckSack[p][0] = null;
                            ruckSack[p][1] = null;
                            itemCount--;
                            x = SIZELIMIT;
                        }
                        x++;
                    }
                }
                else //armor
                {
                    for (int x = 0; x < SIZELIMIT; x++)
                    {
                        if (armors[x].getItemName().equalsIgnoreCase(weaponArmElix))
                        {
                            armors[x] = null;
                            ruckSack[p][0] = null;
                            ruckSack[p][1] = null;
                            itemCount--;
                            break;
                        }
                    }
                }
            }
        }
        System.out.println(weaponArmElix + " could not be found in your inventory.");
    }

    //Prints out the contents of the inventory
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

    public Weapon getWeapon(String name)
    {
        for (int s = 0; s < SIZELIMIT; s++)
        {
            if (weapons[s].getItemName().equalsIgnoreCase(name))
            {
                return weapons[s];
            }
        }
        System.out.println("Your inventory appears to be empty.");
        return null;
    }

    public Elixir getElixir(String name)
    {
        for (int s = 0; s < SIZELIMIT; s++)
        {
            if (elixirs[s].getItemName().equalsIgnoreCase(name))
            {
                return elixirs[s];
            }
        }
        System.out.println("Your inventory appears to be empty.");
        return null;
    }

    public Armor getArmor(String name)
    {
        for (int s = 0; s < SIZELIMIT; s++)
        {
            if (armors[s].getItemName().equalsIgnoreCase(name))
            {
                return armors[s];
            }
        }
        System.out.println("Your inventory appears to be empty.");
        return null;
    }


    public Boolean confirmItem(String name)
    {
        int h = 0;

        if (itemCount > 0)
        {
            while (h <= itemCount)
            {
                if (name.equalsIgnoreCase(ruckSack[h][0]))
                {
                    return true;
                }
                h++;
            }
            System.out.println("No item with the name: " + name + " could be found in your inventory. Check your spelling and try again.");
        }
        else
        {
            System.out.println("Your inventory appears to be empty.");
        }
        return false;
    }

    //returns itemType
    public String getItemType(String itemName)
    {
        for (int s = 0; s < itemCount; s++)
        {
            if (ruckSack[s][0].equalsIgnoreCase(itemName))
            {
                return ruckSack[s][1];
            }
        }
        System.out.println("DEBUG CODE " + itemName + " could not be found in your inventory."); //This will come out the final version
        return null;
    }

    public String[][] getRuckSack()
    {
        return ruckSack;
    }
}