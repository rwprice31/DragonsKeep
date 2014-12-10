package Model;

/**
 * @author Everton Gardiner Jr.
 * @version 1.0
 * Course : ITEC 3860
 * Written: Nov 14, 2014
 *
 * This class illustrates how to create an Item object
 *
 * Purpose: To create an Item object
 */
abstract class Item
{
    //instance variables
	private String itemName;

	/**
	 * Method: Constructor for Items class
	 * Constructor for Items class that has a String as an argument
	 * @param itemName The item's name
	 */
	protected Item(String itemName)
	{
		this.itemName = itemName;
	}

	/**
	 * Method: getItemName
	 * @return The current Item name of the object
	 */
	public String getItemName()
	{
		return itemName;
	}
}
