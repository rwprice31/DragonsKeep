package Model;
/**
 * @author Everton Gardiner Jr.
 * @version 1.0
 * Course : ITEC 3860 Spring 2014
 * Written: Nov 14, 2014
 *
 * This class illustrates how to create an Armor object
 *
 * Purpose: To create and Armor object
 */
public class Armor extends Item
{
    //instance variables
	private int armorDefense;

	/**
	 * Method: Constructor for Armor class
	 * Constructor for Armor class that has int String, and int as arguments
	 * @param itemName The name of the armor
     * @param armorDefense The defence strength of the armor
	 */
	public Armor(String itemName,int armorDefense)
	{
		super(itemName);
		this.armorDefense = armorDefense;
	}

	/**
	 * Method: getHealthBoost
	 * This method gets the current health boost of the Armor object
	 * @return the current health boost of the current health boost of the Armor object
	 */
	public int getArmorDefense()
	{
		return armorDefense;
	}
}
