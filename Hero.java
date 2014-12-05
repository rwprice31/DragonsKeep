package Model;

/**
 * author: Thaonguyen Nguyen
 * version: 1.0
 * Course: ITEC 3860 Fall 2014
 * Written: 11/16/2014
 *
 * This class represents a Hero.
 *
 * Purpose: Allows for the creation and manipulation of a Hero player.
 */
public class Hero extends Actor
{
    //instance variables
    private int playerID;
    private Inventory inventory;
	private int score;
	private int defenseStrength;

    /**Two argument constructor
     * @param name The Hero's name
     * @param playerID The Hero's database primary key
     */
	public Hero(String name, int playerID) {
		super(name);
		score = 0;
		defenseStrength = 0;
        inventory = null;
        this.playerID = playerID;
	}

    /**Four argument constructor
     * @param playerID The Hero's database primary key
     * @param name The Hero's name
     * @param score The Hero's score
     * @param health The Hero's health
     */
	public Hero(int playerID, String name, int score, int health) {
		super(name, health);
		this.playerID = playerID;
        this.score = score;
        inventory = null;  //create inventory will update this.
	}

    /**Creates a Hero's inventory
     */
	public void createInventory()
    {
        inventory = new Inventory();
	}

    /**
     * @return inventory Gets the Hero's inventory
     */
	public Inventory getInventory() {
		return inventory;
	}

    /**
     * @return score Gets the Hero's score
     */
    public int getScore() {
		return score;
	}

    /**Sets the Hero's game score
     * @param score The Hero's game score
     */
    public void setScore(int score) {
		this.score = score;
	}

    /**
     * @return defenseStrength Gets the Hero's defense strength
     */
    public int getDefenseStrength() {
		return defenseStrength;
	}

    /**Sets the defense strength of the Hero
     * @param defenseStrength The Hero's defense strength
     */
    public void setDefenseStrength(int defenseStrength) {
		this.defenseStrength = defenseStrength;
	}

    /**
     * @return playerID Gets the player's ID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**Sets the playerID
     * @param playerID The player's database primary key or playerID
     */
    public void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }
}
