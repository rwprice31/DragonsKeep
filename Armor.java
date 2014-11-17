/**Class: Armor
 *
@author Everton Gardiner Jr.	
 * @version 1.0
 * Course : ITEC 3860 Spring 2014
 * Written: Nov 14, 2014
 *
 *
 *
 * This class illustrates how to create an Armor object
 *
 * Purpose: To create and Armor object
 */

/**
 * @author egardiner
 *
 */
public class Armor extends Items
{

	private int armorDefense;
	/**
	 * Method: Constructor for Armor class
	 * No argument constructor for Armor class
	 */
	public Armor()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method: Constructor for Armor class
	 * Constructor for Armor class that has int String, and int as arguments
	 * @param aItemName
	 */
	public Armor(String aItemName,int aHealthBoost)
	{
		super(aItemName);
		this.armorDefense = aHealthBoost;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method: getHealthBoost
	 * This method gets the current health boost of the Armor object
	 * @return the current health boost of the current health boost of the Armor object
	 */
	public int getHealthBoost()
	{
		return armorDefense;
	}

	/**
	 * Method: setHealthBoost
	 * This method changes the current health boost of the Armor object
	 * @param The new health boost of the Armor object
	 */
	public void setHealthBoost(int healthBoost)
	{
		this.armorDefense = healthBoost;
	}

	/**
	 * Method: to String
	 */
	@Override
	public String toString()
	{
		return "Armor [healthBoost=" + armorDefense + "]";
	}
	

}
