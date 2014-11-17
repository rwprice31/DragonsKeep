/**Class: Weapon
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
 * Purpose: To createa and Weapon object
 */

/**
 * @author egardiner
 *
 */
public class Weapon extends Items
{
	private int strength;
	private String weaponsAttributes;

	/**
	 * Method: Constructor for Weapon class
	 * No argument constructor for Weapon class
	 *Constructor for the Weapon class that has an int, and String as arguments
	 */
	public Weapon(int aStrength, String aWeaponsAttributes) 
	{
		super();
		this.strength = aStrength;
		this.weaponsAttributes = aWeaponsAttributes;
		
		
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
	 * Method getWeaponAttributes
	 * This method returns the current attributes for the Weapon object
	 * @return The current attibutes of the Weapon object
	 */
	public String getWeaponsAttributes()
	{
		return weaponsAttributes;
	}
	/**
	 * Method: setStrength
	 * This method changes the current strength of the Weapon object
	 * @param The new strength of the Weapon object
	 */
	public void setStrength(int strength)
	{
		this.strength = strength;
	}
	/**
	 * Method: setWeaponAttributes
	 * This method changes the weapons attributes of the Weapn object
	 * @param weaponsAttributes the weaponsAttributes to set
	 */
	public void setWeaponsAttributes(String weaponsAttributes)
	{
		this.weaponsAttributes = weaponsAttributes;
	}
	/**
	 * Method: to string
	 */
	@Override
	public String toString()
	{
		return "Weapon [strength=" + strength + ", weaponsAttributes="
				+ weaponsAttributes + "]";
	}

}
