import java.util.ArrayList;

/**Class: ItemTester
 *
@author Everton Gardiner Jr.	
 * @version 1.0
 * Course : ITEC 3860 Spring 2014
 * Written: Nov 14, 2014
 *
 *
 *
 * This class illustrates how to test the Items class and its subclasses		
 *
 * Purpose: To test the Items class and its subclassess
 * 
 */

/**
 * @author egardiner
 *
 */
public class ItemTester
{


	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ArrayList<Items> items = new ArrayList<Items>();
		Weapon weapon1 = new Weapon(5, "Shotgun");
		Armor armor1 = new Armor("Steel Armor", 4);
		Elixer elixer1 = new Elixer("fruit",3);
		items.add(weapon1);
		items.add(armor1);
		items.add(elixer1);

		System.out.println("The items you have: " + items.toString());



	}

}
