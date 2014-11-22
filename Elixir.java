package Model; /**Class: Elixer
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
public class Elixir extends Item
{
	private int healthBoost;

	/**
	 * Method: Constructor
	 * Constructor for Elixer class
	 * @param name
     * @param healthBoost
	 */
	public Elixir(String name, int healthBoost)
	{
        //parsing must happen outside this class
        //String[] attributes = elixirAttributes.split("[|]]");
        super(name);
        this.healthBoost = healthBoost;
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
	 * @param healthBoost new health boost of the Elixer object
	 */
	public void setHealthBoost(int healthBoost)
	{
		this.healthBoost = healthBoost;
	}
}
