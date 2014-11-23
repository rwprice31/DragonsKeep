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
    private Item[] ruckSack;
    char[] itemType; //A, E, W for armor, elixir, or weapon
    private int itemCount;

    //Creates an empty Inventory
    public Inventory()
    {
        ruckSack = new Item[SIZELIMIT];
        itemType = new char[SIZELIMIT];
        itemCount = 0;
    }

    //Adds an item to the inventory
    public void add(Item weapArmElix, char itemType)
    {
        int x = 0;
        boolean stop = false;

        while (!stop)
        {
            if (ruckSack[x] == null)
            {
                this.itemType[x] = itemType;
                ruckSack[x] = weapArmElix;
                stop = true;
                itemCount++;
            }
            else if(x < SIZELIMIT)
            {
                //Move the pointer to the next slot in inventory.
                x++;
            }
            else
            {
                stop = true;
                System.out.println("You are unable to carry any more items.");
            }
        }
    }

    //Removes an item from the inventory
    public void remove(String weaponArmElix)
    {
        int x = 0;
        boolean stop = false;

        while (!stop)
        {
            if (ruckSack[x].getItemName().equalsIgnoreCase(weaponArmElix))
            {
                itemType[x] = ' ';
                ruckSack[x] = null;
                stop = true;
                itemCount--;
            }
            else if(x < SIZELIMIT)
            {
                //Move the pointer to the next slot in inventory.
                x++;
            }
            else
            {
                stop = true;
                System.out.println(weaponArmElix + " could not be found in your inventory.");
            }
        }
    }

    //Prints out the contents of the inventory
    public void view()
    {
        if (itemCount > 0)
        {
            for (int i = 0; i <= itemCount; i++)
            {
                System.out.print("Item type: " + itemType[i]);
                System.out.println(" Item: " + ruckSack[i]);
            }
        }
        else
        {
            System.out.println("Your inventory appears to be empty.");
        }
    }

    public Item getItem(String name)
    {
        int h = 0;

        if (itemCount > 0)
        {
            while (h <= itemCount)
            {
                if (name.equalsIgnoreCase(ruckSack[h].getItemName()))
                {
                    return ruckSack[h];
                }
                h++;
            }
            System.out.println("No item with the name: " + name + " could be found in your inventory. Check your spelling and try again.");
        }
        else
        {
            System.out.println("Your inventory appears to be empty.");
        }
        return null;
    }

    //Retrieves the stats of an item in inventory
    public void getItemAttributes(String itemName)
    {
        int x = 0;
        boolean stop = false;

        while (!stop)
        {
            if (ruckSack[x].getItemName().equalsIgnoreCase(itemName))
            {
                System.out.print("Item type: " + itemType[x]);
                System.out.println(" Item: " + ruckSack[x]);
                stop = true;
            }
            else if(x < SIZELIMIT)
            {
                x++;
            }
            else
            {
                stop = true;
                System.out.println(itemName + " could not be found in your inventory.");
            }
        }
    }

    public char getItemType(String itemName)
    {
        int x = 0;

        while (true)
        {
            if (ruckSack[x].getItemName().equalsIgnoreCase(itemName))
            {
                return itemType[x];
            }
            else if(x < itemCount)
            {
                x++;
            }
            else
            {
                System.out.println("DEBUG CODE " + itemName + " could not be found in your inventory."); //This will come out the final version
                return ' ';
            }
        }
    }
}
