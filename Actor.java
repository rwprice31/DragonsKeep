package Model;

/**
 * author: Thaonguyen Nguyen
 * version: 1.0
 * Course: ITEC 3860 Fall 2014
 * Written: 11/16/2014
 *
 * This class represents an generic actor.
 *
 * Purpose: Allows for the creation and manipulation of an Actor.
 */
abstract class Actor
{
	//instance variables
	private String name;
	private int health;	
	private int attackPower;



    /**One argument constructor
     * @param name The Actor's name
     */
	public Actor(String name) {
		this(name, 100, 0);
	}

    /**Two arg constructor
     * @param name The Actor's name
     * @param health The Actor's health
     */
    public Actor (String name, int health)
    {
        this(name, health, 0);
    }

    /**Three arg constructor
     * @param name The Actor's name
     * @param health The Actor's health
     * @param attackPower The Actor's attack power
     */
    protected Actor(String name, int health, int attackPower)
    {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    /**
     * @return name Gets the Actor's name
     */
    public String getName() {
		return name;
	}

    /**
     * @return health Gets the Actor's health
     */
    public int getHealth() {
		return health;
	}

    /**Sets the Actor's health
     * @param health The Actor's health
     */
    public void setHealth(int health) {
		this.health = health;
	}

    /**
     * @return attackPower Gets the Actor's attack power
     */
    public int getAttackPower() {
		return attackPower;
	}

    /**Sets the Actor's attack power
     * @param attackPower The Actor's attack power
     */
	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}
}
