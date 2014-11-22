package Model;

public class Hero extends Actor
{

	private Inventory inventory;
	private int score;
	private int defenseStrength;

	//create a new Hero/player for the first time
	public Hero(String name) {
		super(name);
		score = 0;
		defenseStrength = 0;
        inventory = null;
	}
	
	//call Hero from playerFile table
	public Hero(String name, int score, int health) {
		super(name, health);
		this.score = score;
        inventory = null;  //create inventory will update this.
	}
	
	public void createInventory()
    {
        inventory = new Inventory();
	}

	//Retrieve the inventory, doing so allows access to Inventory methods
	public Inventory getInventory() {
		return inventory;
	}

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
}
