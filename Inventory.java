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
    private int itemCount;
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
//                    itemType[x] = 'w';

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

    public void add(Armor armor)
    {
        if (itemCount < SIZELIMIT)
        {
            for (int x = 0; x < SIZELIMIT; x++)
            {
                if (armors[x] == null)
                {
                    armors[x] = armor;
//                    itemType[x] = 'a';

                    for(int p = 0; p < SIZELIMIT; p++)
                    {
                        if (ruckSack[p][0] == null)
                        {
                            ruckSack[p][0] = armors[x].getItemName();
                            ruckSack[p][1] = "a";
                            itemCount++;
                            break;
                        }
                    }
                }
            }
        }
        else
        {
            System.out.println("You are unable to carry any more items.");
        }
    }

    public void add(Elixir elixir)
    {
        if (itemCount < SIZELIMIT)
        {
            for (int x = 0; x < SIZELIMIT; x++)
            {
                if (elixirs[x] == null)
                {
                    elixirs[x] = elixir;
//                    itemType[x] = 'e';

                    for(int p = 0; p < SIZELIMIT; p++)
                    {
                        if (ruckSack[p][0] == null)
                        {
                            ruckSack[p][0] = elixirs[x].getItemName();
                            ruckSack[p][1] = "e";
                            itemCount++;
                            break;
                        }
                    }
                }
            }
        }
        else
        {
            System.out.println("You are unable to carry any more items.");
        }
    }


    //Removes an item from the inventory
    public void remove(String weaponArmElix)
    {
        for(int p = 0; p <= SIZELIMIT; p++)
        {
            //locate the item name
            if (ruckSack[p][0].equalsIgnoreCase(weaponArmElix))
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
                            break;
                        }
                    }
                }
                else if (ruckSack[p][1].equalsIgnoreCase("e"))
                {
                    for (int x = 0; x < SIZELIMIT; x++)
                    {
                        if (elixirs[x].getItemName().equalsIgnoreCase(weaponArmElix))
                        {
                            elixirs[x] = null;
                            ruckSack[p][0] = null;
                            ruckSack[p][1] = null;
                            break;
                        }
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
            for (int i = 0; i < itemCount; i++)
            {
                System.out.print("Item type: " + ruckSack[i][1]);
                System.out.println(" Item: " + ruckSack[i][0]);
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

//    //Retrieves the stats of an item in inventory
//    public void getItemAttributes(String itemName)
//    {
//        int x = 0;
//        boolean stop = false;
//
//        while (!stop)
//        {
//            if (ruckSack[x].getItemName().equalsIgnoreCase(itemName))
//            {
//                System.out.print("Item type: " + itemType[x]);
//                System.out.println(" Item: " + ruckSack[x].getItemName());
//                stop = true;
//            }
//            else if(x < SIZELIMIT)
//            {
//                x++;
//            }
//            else
//            {
//                stop = true;
//                System.out.println(itemName + " could not be found in your inventory.");
//            }
//        }
//    }

    //returns itemType
    public String getItemType(String itemName)
    {
        for (int s = 0; s < itemCount; s++)
        {
//            System.out.println("Does this match dagger? " + ruckSack[s][1]);
            if (ruckSack[s][0].equalsIgnoreCase(itemName))
            {
//                System.out.println("entered getItemType");
                return ruckSack[s][1];
            }
        }
        System.out.println("DEBUG CODE " + itemName + " could not be found in your inventory."); //This will come out the final version
        return null;
    }
}


//    //Adds an item to the inventory
//    public void add(Weapon weapon, char itemType)
//    {
//        int x = 0;
//        boolean looping = false;
//
//        do{
//            if (ruckSack[x] == null)
//            {
//                if(itemType == 'w')
//                {
//                    for (int z = 0; z < SIZELIMIT; z++)
//                    {
//                        if (weapons[z] == null)
//                            weapons[z] = weapon;
//                    }
//                }
//                if(itemType == 'e')
//                {}
//                if(itemType == 'a')
//                {}
//                this.itemType[x] = itemType;
//                ruckSack[x] = weapArmElix;
//                stop = true;
//                itemCount++;
//            }
//            else if(x < SIZELIMIT)
//            {
//                //Move the pointer to the next slot in inventory.
//                x++;
//            }
//            else
//            {
//                stop = true;
//                System.out.println("You are unable to carry any more items.");
//            }
//        }while (looping);
//
//    }