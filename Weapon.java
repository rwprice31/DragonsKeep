package Model; /**Class: Weapon
 *
@author Everton Gardiner Jr.	
 * @version 1.0
 * Course : ITEC 3860
 * Written: Nov 14, 2014
 *
 *
 *
 * This class illustrates how to create an Weapon object
 *
 * Purpose: To create a Weapon object
 */

/**
 * @author egardiner
 *
 */
public class Weapon extends Item
{
	private int strength;

	/**
	 * Method: Constructor for Weapon class
	 * No argument constructor for Weapon class
	 *Constructor for the Weapon class that has an int, and String as arguments
	 */
	public Weapon(String name, int strength)
	{
		super(name);
		this.strength = strength;
	}
	/**
	 * Method: getStrength
	 * This method gets the current strength of the 
	 * @return The current strength of the Weapon object
	 */
	public int getStrength()
	{
		return strength;
	}

	/**
	 * Method: setStrength
	 * This method changes the current strength of the Weapon object
	 * @param strength new strength of the Weapon object
	 */
	public void setStrength(int strength)
	{
		this.strength = strength;
	}
}
