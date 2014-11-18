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

    //Creates an empty Inventory
    public Inventory()
    {
        ruckSack = new Item[SIZELIMIT];
        itemType = new char[SIZELIMIT];
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
        for (int i = 0; i <= SIZELIMIT; i++)
        {
            if (ruckSack[i] != null)
            {
                System.out.print("Item type: " + itemType[i]);
                System.out.println(" Item: " + ruckSack[i]);
            }
        }
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
}
