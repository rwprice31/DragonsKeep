package Model;

/**
 * author: Thaonguyen Nguyen
 * version: 1.0
 * Course: ITEC 3860 Fall 2014
 * Written: 11/16/2014
 *
 * This class represents a Monster.
 *
 * Purpose: Allows for the creation and manipulation of a monster.
 */
public class Monster extends Actor
{
    /**Three argument constructor
     * @param name The monster's name
     * @param attackPower The monster's attack power
     * @param health The monster's health
     */
	public Monster(String name, int attackPower, int health) {
		super(name, health, attackPower);
	}
}
