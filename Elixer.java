/**Class: Elixer
 *
@author Everton Gardiner Jr.	
 * @version 1.0
 * Course : ITEC 3860 Fall 2014
 * Written: Nov 14, 2014
 *
 *
 *
 * This class illustrates how to create and Elixer object
 *
 * Purpose: To create an Elixer object
 */

/**
 * @author egardiner
 *
 */
public class Elixer extends Items
{
	private int healthBoost;
	/**
	 * Method: Constructor 
	 * No argument constructor for Elixer class
	 * 
	 */
	public Elixer()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method: Constructor
	 * Constructor for Elixer class
	 * @param aItemName
	 */
	public Elixer(String aItemName,int aHealthBoost)
	{
		super(aItemName);
		this.healthBoost = aHealthBoost;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method: getHealthBoost
	 * This method gets the current health boost of the Elixer object
	 * @return the current health boost of the Elixer object
	 */
	public int getHealthBoost()
	{
		return healthBoost;
	}

	/**
	 * Method: setHealthBoost
	 * This method changes the health boost of the Elixer object
	 * @param The new health boost of the Elixer object
	 */
	public void setHealthBoost(int healthBoost)
	{
		this.healthBoost = healthBoost;
	}

	/**
	 * Method: to string
	 */
	@Override
	public String toString()
	{
		return "Elixer [healthBoost=" + healthBoost + "]";
	}


}
