package Model;

public class Monster extends Actor
{
	//call Monster from monster table
	public Monster(String name, int attackPower, int health) {
		super(name, health, attackPower);
	}
}
