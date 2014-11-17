package com.company;

//Defines an inventory
public class Inventory
{
    private final int SIZELIMIT = 10;
    private Item[] ruckSack;
    Char[] itemType;

    //Repopulates an inventory from a save.
    public Inventory(String allCollectedItems)
    {
        ruckSack = new Item[10];

        //parse String and load into ruckSack
        String items[] = allCollectedItems.split("[|]]"); // I don't know how items come from the database yet
        // ...
    }

    //Creates an empty Inventory
    public Inventory()
    {
        ruckSack = new Item[SIZELIMIT];
        itemType = new Char[SIZELIMIT];
    }

    //Adds an item to the inventory
    public void add(Item weapArmElix, Char itemType)
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
                x++;
            }
            else
            {
                stop = true;
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
            if (ruckSack[x].getName.equalsIgnoreCase(weaponArmElix))
            {
                itemType[x] = null;
                ruckSack[x] = null;
                stop = true;
            }
            else if(x < SIZELIMIT)
            {
                x++;
            }
            else
            {
                stop = true;
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
            if (ruckSack[x].getName.equalsIgnoreCase(itemName))
            {
                System.out.print("Item type: " + itemType[i]);
                System.out.println(" Item: " + ruckSack[i]);
                stop = true;
            }
            else if(x < SIZELIMIT)
            {
                x++;
            }
            else
            {
                stop = true;
            }
        }
    }
}
