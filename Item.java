package Model; /**Class: Item
 *
@author Everton Gardiner Jr.	
 * @version 1.0
 * Course : ITEC 3860
 * Written: Nov 14, 2014
 *
 *
 *
 * This class illustrates how to create an Items object
 *
 * Purpose: To create an Items object
 */

/**
 * @author egardiner
 *
 */
abstract class Item
{
	private String itemName;

	/**
	 * Method: Constructor for Items class
	 * Contructor for Items class that has a String as an argument
	 * @param itemName
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

	/**
	 * Method: setItemName
	 * This method changes the item name of the object
	 * @param itemName new item name of the object
	 */
	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}
}
