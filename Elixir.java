package Model;

/**
 * @author Everton Gardiner Jr.
 * @version 1.0
 * Course : ITEC 3860 Fall 2014
 * Written: Nov 14, 2014
 *
 * This class illustrates how to create and Elixir object
 *
 * Purpose: To create an Elixir object
 */
public class Elixir extends Item
{
	private int healthBoost;

	/**
	 * Method: Constructor
	 * Constructor for Elixir class
	 * @param name The name of the elixir
     * @param healthBoost The health boost found in the elixir
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
	 * This method gets the current health boost of the Elixir object
	 * @return the current health boost of the Elixir object
	 */
	public int getHealthBoost()
	{
		return healthBoost;
	}
}
