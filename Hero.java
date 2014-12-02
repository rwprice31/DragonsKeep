package Model;

public class Hero extends Actor
{

    private int playerID;
    private Inventory inventory;
	private int score;
	private int defenseStrength;

	//create a new Hero/player for the first time
	public Hero(String name, int playerID) {
		super(name);
		score = 0;
		defenseStrength = 0;
        inventory = null;
        this.playerID = playerID;
	}
	
	//call Hero from playerFile table
	public Hero(int playerID, String name, int score, int health) {
		super(name, health);
		this.playerID = playerID;
        this.score = score;
        inventory = null;  //create inventory will update this.
	}
	
	//create a new inventory
	public void createInventory()
    {
        inventory = new Inventory();
	}

	//Retrieve the inventory, doing so allows access to Inventory methods
	public Inventory getInventory() {
		return inventory;
	}

	//getter and setter for score, defenseStrength, and playerID
    public int getScore() {
		return score;
	}

    public void setScore(int score) {
		this.score = score;
	}

    public int getDefenseStrength() {
		return defenseStrength;
	}

    public void setDefenseStrength(int defenseStrength) {
		this.defenseStrength = defenseStrength;
	}

    public int getPlayerID()
    {
        return playerID;
    }

    public void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }
}
