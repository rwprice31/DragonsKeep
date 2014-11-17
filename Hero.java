package model;

public class Hero extends Actor{

	private Inventory inventory;
	private int score;
	private int defenseStrength;
	private String allAttributes;
	
	//create a new Hero/player
	public Hero() {
		super();
		score = 0;
		defenseStrength = 0;
	}
	
	//call Hero from playerFile table
	public Hero(String allAttributes) {
		super();
		this.allAttributes = allAttributes;
	}
	
	public void createInventory() {
		
	}
	
	public void createInventory(String allCollectedItems) {
		
	}

	//get item from inventory
	protected Inventory getInventory() {
		return inventory;
	}

	//add new item to inventory
	protected void addInventory(Inventory inventory, Item weapArmElix, Char itemType) {
		this.inventory = inventory.add(weapArmElix, itemType);
	}

	protected int getScore() {
		return score;
	}

	protected void setScore(int score) {
		this.score = score;
	}

	protected int getDefenseStrength() {
		return defenseStrength;
	}

	protected void setDefenseStrength(int defenseStrength) {
		this.defenseStrength = defenseStrength;
	}

}
